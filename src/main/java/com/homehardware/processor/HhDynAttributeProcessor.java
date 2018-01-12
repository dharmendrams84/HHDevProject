package com.homehardware.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hh.integration.constants.Constant;
import com.homehardware.model.DynAttrInfo;
import com.homehardware.model.AttrDefinition;
import com.homehardware.model.ItemDynAttr;
import com.homehardware.utility.ProductUtility;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.productadmin.Attribute;
import com.mozu.api.contracts.productadmin.AttributeInProductType;
import com.mozu.api.contracts.productadmin.AttributeLocalizedContent;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductType;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.AttributeResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.ProductTypeResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;

public class HhDynAttributeProcessor {

	protected static final Logger logger = Logger.getLogger(HhDynAttributeProcessor.class);
	
	
	/**
	 * @param item.
	 * @param apiContext.
	 */
	public void transformHhAttributesDefinition(final List<AttrDefinition> attrDefinitions){
		final List<ProductProperty> list = new ArrayList<>();
		
		for(AttrDefinition a : attrDefinitions){
			list.add(PropertyUtility.
					getProductProperty(a.getInputType(),
							Constant.TENANT+a.getAttrName()));
		}
	}

	
	
	
	/**
	 * @param item.
	 * @param apiContext.
	 */
	public void transformHhDynamicAttributes(
			final List<ItemDynAttr> itemDynAttrs,
			final List<DynAttrInfo> dynAttrInfos,
			final List<AttrDefinition> dynAttrTypes,
			final ApiContext apiContext,
			final int productType)  throws Exception {
		try {
			/*final List<ItemDynAttr> itemDynAttrs = item.getItemDynAttr();*/
			if (itemDynAttrs != null && itemDynAttrs.size() != 0) {
				for (ItemDynAttr id : itemDynAttrs) {
					final AttributeResource attributeResource 
					    = new AttributeResource(apiContext);
					final String attributeName 
					    = Constant.ATTRIBUTE + "_" + id.getDynAttrId();
					String attributeType = getDynAttrType(attributeName, dynAttrTypes);
					createDynamicAttribute(apiContext, 
							attributeResource, attributeName,attributeType,Constant.PRODUCT_TYPE);
					if (attributeType != null && !attributeType.equalsIgnoreCase("YesNo")
							&& !attributeType.equalsIgnoreCase("List")) {
						//addOrUpdateDynamicAttribute(dynAttrInfos, attributeName, product);
						String attributeFqn = Constant.TENANT+Constant.ATTRIBUTE+"_"+id.getDynAttrId();
						//addOrUpdateProperty(id.getDynAttrDesc(), attributeFqn, apiContext, id.getItem());
						PropertyUtility.addOrUpdateProperty(id.getDynAttrDesc(),
								attributeFqn, apiContext, id.getItem());
					}
				}
			}
			logger.info("All Dynamic attributes processed successfully!!!!!");
		} catch (Exception e) {
			logger.info("Exception while transoforming dynamic attribute "
					+ e.getMessage());
			throw e ;
		}
		logger.info("All Dynamic attributes processed successfully!!!! ");
	}

	/**
	 * @param apiContext.
	 * @param attributeResource.
	 * @param attributeName.
	 */
	public final void createDynamicAttribute(
			final ApiContext apiContext,
			final AttributeResource attributeResource,
			final String attributeName,final String attributeType,
			final int productTypeCode) throws Exception {
			
		try {
			com.mozu.api.contracts.productadmin.Attribute 
		        	attribute = attributeResource
		        	.getAttribute(Constant.TENANT + attributeName);
			
			
			if (attribute == null) {
				attribute = new Attribute();
				createNewAttribute(attributeName, attribute, attributeType);
				attribute = attributeResource.addAttribute(attribute);
				logger.info("Dynamic attribute " + attributeName + 
						" added successfully to kibo!!!! ");
			} else {
				logger.info("Dynamic attribute " + attributeName + 
						" already exists in kibo!!!!");
			}
						
			final ProductTypeResource productTypeResource
				    = new ProductTypeResource(apiContext);
			if (!isAttributeInProductType(productTypeResource, productTypeCode,
					Constant.TENANT + attributeName)) {
				final ProductType productType = 
						productTypeResource
						.getProductType(productTypeCode);
				productType.getExtras();
				final AttributeInProductType attrInProdType 
					= new AttributeInProductType();
				createAttributeInProductType(attribute, attrInProdType);

				addAttributeInProductType(attrInProdType, productType, productTypeResource);
				logger.info("Dynamic attribute " + attributeName + " added successfully "
						+ " And Linked to product type " + Constant.PRODUCT_TYPE);
			} else {
				logger.info("Dynamic attribute " + attributeName 
						+ " already Linked to product type " + Constant.PRODUCT_TYPE);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception while adding attribute " + attributeName);
			throw e;
		}

	}
	
	/**
	 * @param attributeName.
	 * @param attribute.
	 */
	public void createNewAttribute(final String attributeName,
			 final Attribute attribute,final String attributeType) {
		
		setAttributeValues(attributeName, attribute);
		setAttributeType(attribute, attributeType);
		final AttributeLocalizedContent	attributeLocalizedContent
		    = new AttributeLocalizedContent();
		attributeLocalizedContent.setLocaleCode(Constant.LOCALE);
		attributeLocalizedContent.setName(attributeName);
		final List<com.mozu.api.contracts.productadmin.AttributeLocalizedContent> list
		    = new ArrayList<>();
		list.add(attributeLocalizedContent);
		attribute.setLocalizedContent(list);
		
	}
	
	public String getDynAttrType(final String attributeName,final List<AttrDefinition> dynAttrTypes){
		String attributeType = null;
		for(AttrDefinition d: dynAttrTypes){
			if(attributeName.equalsIgnoreCase(Constant.ATTRIBUTE+"_"+d.getDynAttrId())){
				attributeType = d.getInputType();
				break;
			}
		}
		
		return attributeType;
	}
	
	
	/**
	 * @param attributeName.
	 * @param attribute.
	 */
	public  void setAttributeValues(final String attributeName,
			final Attribute attribute) {
		attribute.setAdminName(attributeName);
		attribute.setInputType(Constant.TEXT_BOX);
		attribute.setDataType(Constant.STRING);
		attribute.setMasterCatalogId(Constant.master_catalog_id);
		attribute.setValueType(Constant.ADMIN_ENTERED);
		attribute.setIsExtra(false);
		attribute.setIsOption(false);
		attribute.setIsProperty(true);
		attribute.setAttributeCode(attributeName);
		
	}
	
	public  void setAttributeType(final Attribute attribute, String type) {
		if (type.equalsIgnoreCase("YesNo")) {
			attribute.setInputType("YesNo");
			attribute.setDataType("Bool");
		} else if (type.equalsIgnoreCase("TextArea")) {
			attribute.setInputType("TextArea");
			attribute.setDataType("String");
		} else if (type.equalsIgnoreCase("List")) {
			attribute.setInputType("List");
			attribute.setDataType("String");
			attribute.setValueType("Predefined");
		}else if (type.equalsIgnoreCase("Text")) {
			attribute.setInputType(Constant.TEXT_BOX);
			attribute.setDataType("String");
		}
	}

	
	/**
	 * @param attribute.
	 * @param attrInProdType.
	 */
	public  void createAttributeInProductType(
			final Attribute attribute,final AttributeInProductType attrInProdType) {
		
		attrInProdType.setAttributeDetail(attribute);
		attrInProdType.setAttributeFQN(attribute.getAttributeFQN());
		attrInProdType.setIsAdminOnlyProperty(false);
		attrInProdType.setIsHiddenProperty(false);
		attrInProdType.setIsInheritedFromBaseType(false);
		attrInProdType.setIsMultiValueProperty(false);
		attrInProdType.setIsProductDetailsOnlyProperty(true);
		attrInProdType.setIsRequiredByAdmin(false);
		attrInProdType.setOrder(0);
		
	}
	
	/**
	 * @param attrInProdType.
	 * @param productType.
	 * @param productTypeResource.
	 */
	public static void addAttributeInProductType(
			final AttributeInProductType attrInProdType,
			final ProductType productType,
			final ProductTypeResource productTypeResource) {
		try {
			final List<AttributeInProductType> 
			    propertiesList = productType.getProperties();
			propertiesList.add(attrInProdType);
			productType.setProperties(propertiesList);
			productTypeResource.updateProductType(productType, 2);
		} catch (Exception e) {
			logger.error("Exception while updating product type "
					+ productType + " error message "	+ e.getMessage());
		}
	}

	public void addOrUpdateProperty(final String value,final String attributeFqn,ApiContext apiContext,final String productCode){
		try {
			ProductPropertyResource productPropertyResource = new ProductPropertyResource(apiContext);

			ProductProperty productProperty = productPropertyResource.getProperty(productCode, attributeFqn);
			if (productProperty == null) {
				ProductPropertyValue productPropertyValue = createProductProperty(value);
				List<ProductPropertyValue> productPropertyValues = new ArrayList<ProductPropertyValue>();
				productPropertyValues.add(productPropertyValue);
				productProperty = new ProductProperty();
				productProperty.setAttributeFQN(attributeFqn);
				productProperty.setValues(productPropertyValues);
				productPropertyResource.addProperty(productProperty, productCode);
				logger.info("Property "+attributeFqn + " added successfully!!!!" );
			} else {
				if (productProperty.getValues() != null 
						&& productProperty.getValues().size() != 0){
					if(productProperty.getValues().get(0).getContent()!=null){
					productProperty.getValues().get(0).getContent().setStringValue(value);
					}else{
						ProductPropertyValueLocalizedContent content = new ProductPropertyValueLocalizedContent();
						content.setStringValue(value);
					}
				}

				productPropertyResource.updateProperty(productProperty, productCode, attributeFqn);
				logger.info("Property "+attributeFqn + " updated successfully!!!!" );
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
   
   /**
	 * @param value.
	 * @.return
	 */
	public ProductPropertyValue createProductProperty(final String value) {
		final ProductPropertyValue productPropertyValue = new ProductPropertyValue();
		final ProductPropertyValueLocalizedContent content;
		content = new ProductPropertyValueLocalizedContent();
		content.setStringValue(value);
		productPropertyValue.setContent(content);
		return productPropertyValue;
	}
	
	public String getDynAttrInfo(List<DynAttrInfo> dynAttrInfos){
		String dynAttrValue = null;
		return dynAttrValue;
	}

	public boolean isAttributeInProductType(final ProductTypeResource productTypeResource,final int productTypeCode,
			final String attributeFQN) {
		boolean isAttributeInProductType = false;
		try {

			final ProductType productType = productTypeResource.getProductType(productTypeCode);
			List<AttributeInProductType> attributesList = productType.getProperties();
			if (attributesList != null && attributesList.size() != 0) {
				for (AttributeInProductType a : attributesList) {
					if (a.getAttributeFQN().equalsIgnoreCase(attributeFQN)) {
						isAttributeInProductType = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return isAttributeInProductType;
	}
}
