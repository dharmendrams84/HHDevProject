package com.homehardware.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.hh.integration.constants.Constant;
import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.AttrDefinition;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.productadmin.Attribute;
import com.mozu.api.contracts.productadmin.AttributeInProductType;
import com.mozu.api.contracts.productadmin.AttributeLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductType;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.AttributeResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.ProductTypeResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;

public class PropertyUtility {

	
	protected static final Logger logger = Logger.getLogger(PropertyUtility.class);

	/**
	 * @param value.
	 * @param attributeFqn.
	 * @.return
	 */
	public static ProductProperty getProductProperty(
			final String value, final String attributeFqn) {
		ProductProperty productProperty = null;

		if (value != null && value.length() != 0) {
			final ProductPropertyValue productPropertyValue 
				= createProductPropertyValue(value);
			productProperty = getProductProperty(productPropertyValue, attributeFqn);

		}

		return productProperty;
	}

	/*public static List<ProductProperty> getPropertyList(List<Brand> brandList) {
		List<ProductProperty> list = new ArrayList<>();
		if (brandList != null && brandList.size() != 0) {
			for (Brand b : brandList) {
				ProductProperty productProperty = getProperty(b.getBrandCode(),
						HhProductAttributeFqnConstants.Hh_Brand_Code_Attr_Fqn);
				if (productProperty != null) {
					list.add(productProperty);
				}

				productProperty = getProperty(b.getBrandDesc(), HhProductAttributeFqnConstants.Hh_Brand_Desc_Attr_Fqn);
				if (productProperty != null) {
					list.add(productProperty);
				}

				productProperty = getProperty(b.getHomeExclusiveInd(),
						HhProductAttributeFqnConstants.Hh_Home_Exclusive_Ind_Attr_Fqn);
				if (productProperty != null) {
					list.add(productProperty);
				}
			}
		}
		return list;
	}
*/	
	
	/**
	 * @param value.
	 * @param attributeFqn.
	 * @param apiContext.
	 * @param productCode.
	 */
	public static ProductProperty addOrUpdateProperty(
			final String value,
			final String attributeFqn,final ApiContext apiContext,
			final String productCode){
		ProductProperty productProperty = null;
		try {
			ProductPropertyResource productPropertyResource = new ProductPropertyResource(apiContext);

			productProperty 
				= productPropertyResource.getProperty(productCode, attributeFqn);
			if (productProperty == null) {
				final ProductPropertyValue productPropertyValue
					= createProductPropertyValue(value);
				if (attributeFqn
						.equalsIgnoreCase(
						HhProductAttributeFqnConstants.Hh_Product_Class)){
					productPropertyValue.setValue(value);
				}
				/*final List<ProductPropertyValue> productPropertyValues
					= new ArrayList<ProductPropertyValue>();
				productPropertyValues.add(productPropertyValue);
				productProperty = new ProductProperty();
				productProperty.setAttributeFQN(attributeFqn);
				productProperty.setValues(productPropertyValues);*/
				productProperty = getProductProperty(productPropertyValue, attributeFqn);
				productPropertyResource.addProperty(productProperty, productCode);
				logger.info("Property "+ attributeFqn + " added successfully!!!!" );
			} else {
				if (productProperty.getValues() != null 
						&& productProperty.getValues().size() != 0) {
					if (productProperty.getValues().get(0)!= null
							&& productProperty
							.getValues().get(0).getContent() != null) {
						productProperty
						.getValues().get(0)
						.getContent().setStringValue(value);
						
					} else {
						final ProductPropertyValueLocalizedContent content
						    = new ProductPropertyValueLocalizedContent();
						content.setStringValue(value);
					}
				}

				productPropertyResource
				.updateProperty(productProperty, productCode, attributeFqn);
				logger.info("Property " + attributeFqn + " updated to product " + productCode + "successfully!!!!");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productProperty;
   }
   
	/**
	 * @param value.
	 * @param attributeFqn.
	 * @param apiContext.
	 * @param productCode.
	 */
	public static ProductProperty addOrUpdateYesNo(
			final String value,
			final String attributeFqn,final ApiContext apiContext,
			final String productCode){
		ProductProperty productProperty = null;
		try {
			final ProductPropertyResource productPropertyResource 
				= new ProductPropertyResource(apiContext);
			
			productProperty = productPropertyResource.getProperty(productCode, attributeFqn);
			if(productProperty==null){
				
				productProperty = new ProductProperty();
				productProperty.setAttributeFQN(attributeFqn);
				List<ProductPropertyValue> list = new ArrayList<>();
				ProductPropertyValue productPropertyValue = new ProductPropertyValue();
				Date date = new java.util.Date();

				productPropertyValue.setValue(new Boolean(value));
				list.add(productPropertyValue);
				productProperty.setValues(list);
				productPropertyResource.addProperty(productProperty, productCode);
	
			}else		if (productProperty.getValues() != null 
						&& productProperty.getValues().size() != 0) {
					 productProperty.getValues().get(0).setValue(new Boolean(value));
					 productPropertyResource
						.updateProperty(productProperty, productCode, attributeFqn);

				}

								logger.info("Property " + attributeFqn + " updated to product " + productCode + "successfully!!!!");
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productProperty;
   }

	/**
	 * @param value.
	 * @param attributeFqn.
	 * @param apiContext.
	 * @param productCode.
	 */
	public static ProductProperty addOrUpdateDateProperty(
			final String value,
			final String attributeFqn,final ApiContext apiContext,
			final String productCode){
		logger.info("inside addOrUpdateDateProperty!!!!");
		ProductProperty productProperty = null;
		try {
			final ProductPropertyResource productPropertyResource 
				= new ProductPropertyResource(apiContext);
			productProperty = productPropertyResource.getProperty(productCode, attributeFqn);
			if (productProperty == null) {
				productProperty = new ProductProperty();
				productProperty.setAttributeFQN(attributeFqn);
				List<ProductPropertyValue> list = new ArrayList<>();
				ProductPropertyValue productPropertyValue = new ProductPropertyValue();
				Date date = new java.util.Date();

				productPropertyValue.setValue(date);
				list.add(productPropertyValue);
				productProperty.setValues(list);
				productPropertyResource.addProperty(productProperty, productCode);
			} else if (productProperty.getValues() != null && productProperty.getValues().size() != 0) {
				if (productProperty.getValues() != null && productProperty.getValues().size() != 0) {
					productProperty.getValues().get(0).setValue(new Date());
					
					productPropertyResource.updateProperty(productProperty, productCode, attributeFqn);
				}
					
							
				/*else if (productProperty.getValues() != null 
						&& productProperty.getValues().size() != 0) {
					 productProperty.getValues().get(0).setValue(new Date());;
					 productPropertyResource
						.updateProperty(productProperty, productCode, attributeFqn);
				}*/

				
				logger.info("Property " + attributeFqn + " updated to product " + productCode + "successfully!!!!");
					}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productProperty;
   }

	
   /**
	 * @param value.
	 * @.return
	 */
	public static ProductPropertyValue createProductPropertyValue(final String value) {
		final ProductPropertyValue productPropertyValue = new ProductPropertyValue();
		final ProductPropertyValueLocalizedContent content;
		content = new ProductPropertyValueLocalizedContent();
		content.setStringValue(value);
		productPropertyValue.setContent(content);
		
		return productPropertyValue;
	}

	/**
	 * @param productPropertyValue.
	 * @param attributeFqn.
	 */
	public static ProductProperty getProductProperty(
			final ProductPropertyValue productPropertyValue,
			final String attributeFqn) {
		final List<ProductPropertyValue> productPropertyValues
			= new ArrayList<ProductPropertyValue>();
		productPropertyValues.add(productPropertyValue);
		final ProductProperty productProperty = new ProductProperty();
		productProperty.setAttributeFQN(attributeFqn);
		productProperty.setValues(productPropertyValues);
		return productProperty;
	}
	
	/**
	 * @param apiContext.
	 * @param attributeResource.
	 * @param attributeName.
	 */
	public static final void createDynamicAttribute(
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

				addAttributeInProductType(attrInProdType, productType, productTypeResource,productTypeCode);
				logger.info("Dynamic attribute " + attributeName + " added successfully "
						+ " And Linked to product type " + productTypeCode);
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
	public static void createNewAttribute(final String attributeName,
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
	
	public static String getDynAttrType(final String attributeName,final List<AttrDefinition> dynAttrTypes){
		String attributeType = null;
		for (AttrDefinition d : dynAttrTypes) {
			if( attributeName.
					equalsIgnoreCase(
					d.getAttrName()) ) {
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
	public static void setAttributeValues(final String attributeName,
			final Attribute attribute) {
		attribute.setAdminName(attributeName);
		//attribute.setInputType(Constant.TEXT_BOX);
		//attribute.setDataType(Constant.STRING);
		attribute.setMasterCatalogId(Constant.master_catalog_id);
		attribute.setValueType(Constant.ADMIN_ENTERED);
		attribute.setIsExtra(false);
		attribute.setIsOption(false);
		attribute.setIsProperty(true);
		attribute.setAttributeCode(attributeName);
		
	}
	
	public static void setAttributeType(final Attribute attribute, String type) {
		if (type.equalsIgnoreCase("YesNo")) {
			attribute.setInputType("YesNo");
			attribute.setDataType("Bool");
		} else if (type.equalsIgnoreCase("TextArea")|| type.equalsIgnoreCase(Constant.INPUT_TYPE_TEXT_AREA)) {
			attribute.setInputType("TextArea");
			attribute.setDataType("String");
		} else if (type.equalsIgnoreCase("List")) {
			attribute.setInputType("List");
			attribute.setDataType("String");
			attribute.setValueType("Predefined");
		}else if (type.equalsIgnoreCase("Text")||(type.equalsIgnoreCase(Constant.INPUT_TYPE_TEXT_BOX))) {
			attribute.setInputType(Constant.TEXT_BOX);
			attribute.setDataType("String");
		}else if(type.equalsIgnoreCase("Date")){
			attribute.setDataType("DateTime");
			attribute.setInputType("Date");
		}
	}

	
	/**
	 * @param attribute.
	 * @param attrInProdType.
	 */
	public static void createAttributeInProductType(
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
			final ProductTypeResource productTypeResource,
			final int productTypeCode) {
		try {
			final List<AttributeInProductType> 
			    propertiesList = productType.getProperties();
			propertiesList.add(attrInProdType);
			productType.setProperties(propertiesList);
			productTypeResource.updateProductType(productType, productTypeCode);
		} catch (Exception e) {
			logger.error("Exception while updating product type "
					+ productType + " error message "	+ e.getMessage());
		}
		logger.info("Attribute linked to product type");
	}

	public static boolean isAttributeInProductType(final ProductTypeResource productTypeResource,final int productTypeCode,
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
	
	
	public static String generateAttributeFqn(final String attributeName){
		String attributeFqn = null;
		if(!attributeName.isEmpty()){
			attributeFqn = "TENANT~"+attributeName;
		}
		return attributeFqn;
	}


}
