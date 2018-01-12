package com.homehardware.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hh.integration.constants.Constant;
import com.homehardware.model.AttrDefinition;
import com.homehardware.model.ProductAttr;
import com.homehardware.model.ProductItemAttr;
import com.homehardware.model.ProductItemAttributes;
import com.homehardware.utility.AttributeUtility;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.productadmin.Attribute;
import com.mozu.api.contracts.productadmin.AttributeInProductType;
import com.mozu.api.contracts.productadmin.AttributeVocabularyValue;
import com.mozu.api.contracts.productadmin.AttributeVocabularyValueInProductType;
import com.mozu.api.contracts.productadmin.AttributeVocabularyValueLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductOption;
import com.mozu.api.contracts.productadmin.ProductOptionValue;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductType;
import com.mozu.api.contracts.sitesettings.order.VocabularyValue;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.AttributeResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.ProductTypeResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.attributes.AttributeVocabularyValueResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductOptionResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;

@Component
public class HhProdItemAttributeProcessor {
	
	
	
	protected static final Logger logger = Logger.getLogger(HhProdItemAttributeProcessor.class);

	
	
	/**
	 * @param productItemAttributes.
	 * @param apiContext.
	 */
	public void transformHhProductItemAttributes(
			final List<ProductItemAttributes> productItemAttributes) {
		List<ProductProperty> list = new ArrayList<>();
		if (productItemAttributes != null && productItemAttributes.size() != 0) {
			for (ProductItemAttributes p : productItemAttributes) {
				
				final ProductProperty productProperty
					= PropertyUtility.getProductProperty(p.getAttributeValue(),
						p.getId().getProductAttrId());
				if (productProperty != null) {
					list.add(productProperty);
				}
			}
		}
		logger.info("All product Item attributes processed successfully!!!!");
	}
	
	
	
	/**
	 * @param productItemAttributes.
	 * @param apiContext.
	 */
	public void transformProductItemAttributes(
			final List<ProductItemAttributes> productItemAttributes,
			final ApiContext apiContext) {
		if (productItemAttributes != null && productItemAttributes.size() != 0) {
			for (ProductItemAttributes p : productItemAttributes) {
				PropertyUtility.addOrUpdateProperty(
						p.getAttributeValue(),
						p.getId().getProductAttrId(), apiContext,
						p.getId().getItem());
			}
		}
		logger.info("All product Item attributes processed successfully!!!!");
	}
	
	public void transformProdItemAttributes(
			final List<ProductItemAttr> productItemAttributes,
			final List<ProductAttr> productAttributes,
			final List<AttrDefinition> attrDefinitions,
			final ApiContext apiContext,
			final int productTypeCode) throws Exception {
		if (productAttributes != null 
				&& productAttributes.size() != 0
				&& productItemAttributes != null
				&& productItemAttributes.size() != 0) {
			
			for (ProductAttr p : productAttributes) {
				AttributeResource attributeResource = new AttributeResource(apiContext);
				if (isOption(attrDefinitions, p)) {
					final ProductItemAttr productItemAttr
						= getProductItemAttr(productItemAttributes, p);
					final String productCode = productItemAttr.getItem();
					final String attributeFqN 
						= Constant.TENANT + p.getAttributeName();
					
					Attribute attribute = attributeResource.getAttribute(attributeFqN);
					List<AttributeVocabularyValue> vocabularyValuesList = attribute.getVocabularyValues();
					if(vocabularyValuesList==null ){
						vocabularyValuesList = new ArrayList<AttributeVocabularyValue>();
					}
					
					for (ProductItemAttr pa : productItemAttributes) {
						if (pa.getProductAttrId() == p.getProdAttrId()) {
						if(!isVocabularyValueExist(
								vocabularyValuesList, pa.getAttributeValue())){
									final AttributeVocabularyValueResource 
										attributeVocabularyValueResource =
										new AttributeVocabularyValueResource(apiContext);
									AttributeVocabularyValue attributeVocabularyValue =  
											
								 AttributeUtility.
										createAttributeVocabularyValue(pa.getAttributeValue());
								//AttributeVocabularyValue attributeVocabularyValue1 = attributeVocabularyValueResource.getAttributeVocabularyValue(attributeFqN, pa.getAttributeValue());
								//if(attributeVocabularyValue1==null){
								attributeVocabularyValueResource
									.addAttributeVocabularyValue
							(attributeVocabularyValue, 
									 attributeFqN);
								//}
							logger.info("Option value "+pa.getAttributeValue()+ " added to tenant~finish successfully!!!!!!");	
								
								
							}
							
							final ProductTypeResource productTypeResource = new ProductTypeResource(apiContext);
							final ProductType productType = productTypeResource.getProductType(productTypeCode);

							final AttributeInProductType attributeInProductType = productType.getOptions().get(0);
							List<AttributeVocabularyValueInProductType> attributeVocabularyValueInProductTypes = attributeInProductType.getVocabularyValues();
							if(!containsVocabulary(attributeVocabularyValueInProductTypes, pa.getAttributeValue())){
								attributeInProductType.getVocabularyValues()
								.add(AttributeUtility.createAttrVocInProdType(pa.getAttributeValue()));

						final ProductType productTypeupdated = productTypeResource.updateProductType(productType,
								productTypeCode);
						logger.info(pa.getAttributeValue()+ " updated to attribute "+attributeFqN+ " updated to product type successfully!!!!!");
							}
							
								addOrUpdateProductOption(productCode,
									attributeFqN, apiContext,
									pa
									.getAttributeValue());
						}
					}
				} else  {
					
					final String inputType =
							PropertyUtility.getDynAttrType(
									p.getAttributeName(),
									attrDefinitions);
					PropertyUtility.createDynamicAttribute(
							apiContext, attributeResource, 
							p.getAttributeName(), 
							inputType, productTypeCode);
					final String attributeFqN 
						= Constant.TENANT + p.getAttributeName();
					final ProductItemAttr productItemAttr 
						= getProductItemAttr(productItemAttributes, p);
					if(inputType.equalsIgnoreCase(Constant.INPUT_TYPE_TEXT_BOX) || 
							inputType.equalsIgnoreCase(Constant.INPUT_TYPE_TEXT_AREA)){
					PropertyUtility.addOrUpdateProperty(
							productItemAttr.getAttributeValue(), 
							attributeFqN, apiContext, 
							productItemAttr.getItem());
					}else{
						if(inputType.equalsIgnoreCase(Constant.INPUT_TYPE_YES_NO)) {
							logger.info("");
							PropertyUtility.addOrUpdateYesNo(productItemAttr.getAttributeValue(),
									attributeFqN, apiContext,
									productItemAttr.getItem());
						}else if(inputType.equalsIgnoreCase(Constant.DATE)){
							PropertyUtility.addOrUpdateDateProperty(productItemAttr.getAttributeValue(), attributeFqN, apiContext, productItemAttr.getItem());
						}
					}
				}
			}
		}
		logger.info("All product Item attributes processed successfully!!!!");
	}
	
	
	public boolean isVocabularyValueExist(
			final List<AttributeVocabularyValue> vocabularyValuesList,
			final String value){
		boolean isExist = false;
		if (vocabularyValuesList != null && vocabularyValuesList.size() != 0) {
			for (AttributeVocabularyValue a : vocabularyValuesList) {
				if (a.getValue().toString().equalsIgnoreCase(value)) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}
	
	
	
	
	/**
	 * @param productCode.
	 * @param apiContext.
	 */
	public void addOrUpdateProductOption(
			final String productCode,
			final String attributeFqN,
			final ApiContext apiContext,final String value) {
		try {
			final ProductOptionResource productOptionResource 
				= new ProductOptionResource(apiContext);
			
			
			/*productOptionResource.deleteOption(productCode, attributeFqN);*/
			ProductOption productOption 
				= productOptionResource.getOption(productCode, attributeFqN);
			if (productOption == null) {
				productOption = new ProductOption();
				productOption.setAttributeFQN(attributeFqN);
				productOption 
					= productOptionResource.addOption(
							productOption, productCode);
				logger.info("After Adding productOption " 
							+ attributeFqN + " !!!!! ");
			}
			
			List<ProductOptionValue> values = productOption.getValues();
			if (values == null) {
				values = new ArrayList<ProductOptionValue>();
				productOption.setValues(values);
			}
			final ProductOptionValue productOptionValue 
				= createProductOptionValue(value);
			values.add(productOptionValue);
			final ProductOption productOptionUpdated 
				= productOptionResource.updateOption(productOption, productCode,
					attributeFqN);
			logger.info("After updating productOption !!!!"+ attributeFqN+" with value "+value) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public ProductOptionValue createProductOptionValue(final String value){
		final ProductOptionValue productOptionValue = new ProductOptionValue();
		final AttributeVocabularyValue attributeVocabularyValue
			= new AttributeVocabularyValue();
		final AttributeVocabularyValueLocalizedContent attributeValueLocalizedContent 
			= new AttributeVocabularyValueLocalizedContent();
		attributeValueLocalizedContent.setLocaleCode(Constant.LOCALE);
		attributeValueLocalizedContent.setStringValue(value);;
		attributeVocabularyValue.setContent(attributeValueLocalizedContent);
		attributeVocabularyValue.setValue(value);
		productOptionValue.setAttributeVocabularyValueDetail(attributeVocabularyValue);
		productOptionValue.setValue(value);
		return productOptionValue;
	}
	
	
	/**
	 * @param productItemAttributes.
	 * @param pa.
	 * @.return
	 */
	public ProductItemAttr getProductItemAttr(
			final List<ProductItemAttr> productItemAttributes,
			final ProductAttr pa) {
		ProductItemAttr productItemAttr = null;
		for (ProductItemAttr p : productItemAttributes) {
			if (p.getProductAttrId() == pa.getProdAttrId()) {
				productItemAttr = p;
				break;
			}
		}
		return productItemAttr;
	}
	
	public boolean isOption(final List<AttrDefinition> attrDefinitions,ProductAttr pa){
		boolean isOption = false;
		for(AttrDefinition a :attrDefinitions){
			if((a.getInputType().equalsIgnoreCase("List"))&&(pa.getProdAttrId()==a.getDynAttrId())
					&& (a.getAttrType().equalsIgnoreCase("Option"))){
				isOption = true;
				break;
			}
		}
		return isOption;
	}
	public boolean isText(final List<AttrDefinition> attrDefinitions,ProductAttr pa){
		boolean isText = false;
		for(AttrDefinition a :attrDefinitions){
			if ((a.getInputType().equalsIgnoreCase(Constant.INPUT_TYPE_TEXT_BOX)
					|| a.getInputType().equalsIgnoreCase(Constant.INPUT_TYPE_TEXT_AREA)
					|| a.getInputType().equalsIgnoreCase("Date"))
					&&(pa.getProdAttrId()==a.getDynAttrId())
					&& (a.getAttrType().equalsIgnoreCase("Property"))){
				isText = true;
				break;
			}
		}
		return isText;
	}
	
	/*public String getAttributeType(final List<AttrDefinition> attrDefinitions, ProductAttr pa) {
		String attributeTypeName = null;
		for (AttrDefinition a : attrDefinitions) {
			if (a.getAttrName().equalsIgnoreCase(pa.getAttributeName())) {
				attributeTypeName = a.getI
			}
		}
		return attributeTypeName;
	}*/

	public boolean containsVocabulary(List<AttributeVocabularyValueInProductType> attributeVocabularyValueInProductTypes,String value){
		boolean isExists = false;
		if(attributeVocabularyValueInProductTypes!=null && attributeVocabularyValueInProductTypes.size()!=0){
			for(AttributeVocabularyValueInProductType a :attributeVocabularyValueInProductTypes){
				if(a.getValue().toString().equalsIgnoreCase(value)){
					isExists = true;
					break;
				}
			}
		}
		return isExists;
	}

}
