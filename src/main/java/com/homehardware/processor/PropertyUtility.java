package com.homehardware.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;

public class PropertyUtility {

	protected static final Logger logger = Logger.getLogger(PropertyUtility.class);

	/**
	 * @param value.
	 * @param attributeFqn.
	 * @param apiContext.
	 * @param productCode.
	 */
	public static void addOrUpdateProperty(
			final String value,
			final String attributeFqn,final ApiContext apiContext,
			final String productCode){
		try {
			ProductPropertyResource productPropertyResource = new ProductPropertyResource(apiContext);

			ProductProperty productProperty = productPropertyResource.getProperty(productCode, attributeFqn);
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
}
