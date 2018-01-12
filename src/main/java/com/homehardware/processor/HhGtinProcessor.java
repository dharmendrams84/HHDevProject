package com.homehardware.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.Gtin;
import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;

@Component
public final class HhGtinProcessor {

	protected static final Logger logger = Logger.getLogger(HhGtinProcessor.class);	

	/**
	 * @param gtin.
	 * @.return
	 */
	public ProductProperty transformHhGtin(final Gtin gtin) {
		final ProductProperty productProperty 
			= PropertyUtility.getProductProperty(gtin.getGtin(),
				HhProductAttributeFqnConstants.Hh_Gtin_Attr_Fqn);
		return productProperty;
	}

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
	
  
}
