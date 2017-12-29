package com.homehardware.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.homehardware.model.Gtin;
import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;

@Component
public class HhGtinProcessor {

	protected static final Logger logger = Logger.getLogger(HhGtinProcessor.class);	

	/**
	 * @param gtin.
	 * @param apiContext.
	 * @param attributeFqn.
	 */
	public void addOrUpdateGtin(final Gtin gtin,
			final ApiContext apiContext, final String attributeFqn) {
		try {
			PropertyUtility
			    .addOrUpdateProperty(gtin.getGtin(), 
					 attributeFqn, apiContext, gtin.getItem());

		} catch (Exception e) {
			e.printStackTrace();
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
				logger.info("Property "+attributeFqn + " added to product "+productCode+" successfully!!!!" );
			} else {
				if (productProperty.getValues() != null && productProperty.getValues().size() != 0){
					if(productProperty.getValues().get(0).getContent()!=null){
					productProperty.getValues().get(0).getContent().setStringValue(value);
					}else{
						ProductPropertyValueLocalizedContent content = new ProductPropertyValueLocalizedContent();
						content.setStringValue(value);
					}
				}

				productPropertyResource.updateProperty(productProperty, productCode, attributeFqn);
				logger.info("Property "+attributeFqn + " updated to product "+productCode+" successfully!!!!" );
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
