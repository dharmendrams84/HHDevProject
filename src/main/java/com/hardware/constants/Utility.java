package com.hardware.constants;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.mozu.api.ApiContext;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;

public class Utility {

	
	public static DateTime convertDateToDateTime(
			final java.util.Date date) {
		return new DateTime(date);
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
			} else {
				if (productProperty.getValues() != null && productProperty.getValues().size() != 0)
					productProperty.getValues().get(0).getContent().setStringValue(value);

				productPropertyResource.updateProperty(productProperty, productCode, attributeFqn);

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
