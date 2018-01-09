package com.homehardware.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hh.integration.constants.Constant;
import com.homehardware.model.ProductItemAttributes;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;

public class HhProdItemAttributeProcessor {
	
	protected static final Logger logger = Logger.getLogger(HhProdItemAttributeProcessor.class);

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
	
	
	public void addOrUpdateProperty(final String value,final String attributeFqn,ApiContext apiContext,final String productCode){
		try {
			final ProductPropertyResource productPropertyResource
				= new ProductPropertyResource(apiContext);

			ProductProperty productProperty 
				= productPropertyResource.getProperty(productCode, attributeFqn);
			if (productProperty == null) {
				final ProductPropertyValue productPropertyValue 
					= createProductProperty(value);
				final List<ProductPropertyValue> productPropertyValues 
					= new ArrayList<ProductPropertyValue>();
				productPropertyValues.add(productPropertyValue);
				productProperty = new ProductProperty();
				productProperty.setAttributeFQN(attributeFqn);
				productProperty.setValues(productPropertyValues);
				productPropertyResource.addProperty(productProperty, productCode);
				logger.info("Property " + attributeFqn 
						+ " added successfully!!!!" );
			} else {
				if (productProperty.getValues() != null && 
						productProperty.getValues().size() != 0){
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

}
