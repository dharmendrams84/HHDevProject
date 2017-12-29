package com.homehardware.processor;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hh.integration.constants.Constant;
import com.homehardware.model.ExtDesc;
import com.homehardware.utility.ProductUtility;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;

@Component
public class HhExtDescProcessor {

	/**
	 * @param extDescs.
	 * @param product.
	 */
	public void setProductExtDesc(
			final List<ExtDesc> extDescs, final Product product) {
		
		for (ExtDesc e : extDescs) {
			transformHhExtendedDescription(product, e);
		}
		
	}
	
	/**
	 * @param product.
	 * @param extendedDesc.
	 */
	public void transformHhExtendedDescription(
			final Product product, final ExtDesc extendedDesc) {
		// TODO Auto-generated method stub
		String propertyName = null;
		if (extendedDesc != null && extendedDesc.getType().equalsIgnoreCase(Constant.FB)) {
			final ProductLocalizedContent content = product.getContent();
			content.setLocaleCode(extendedDesc.getLanguage());
			content.setProductFullDescription(extendedDesc.getDescription());
			product.setContent(content);
		} else {
			if (extendedDesc.getType().equalsIgnoreCase(Constant.INGR)) {
				propertyName = Constant.TENANT_INGREDIENTS;

			} else if (extendedDesc.getType().equalsIgnoreCase(Constant.MKTG)) {
				propertyName = Constant.tenant_marketing_description;

			}
			final ProductUtility productUtility = new ProductUtility();
			productUtility.addOrUpdateProperty(product,
					propertyName, extendedDesc.getDescription());
		}
	}

}
