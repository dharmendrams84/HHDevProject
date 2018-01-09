package com.homehardware.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.ItemAffiliated;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;

@Component
public class HhAffiliatedItemProcessor {

	protected static final Logger logger = Logger.getLogger(HhAffiliatedItemProcessor.class);	
	/**
	 * @param product.
	 * @param affiliatedItems.
	 */
	public void setProductAffiliatedItems(
			final List<ItemAffiliated> affiliatedItems,
			final ApiContext apiContext, final String attributeFqn,final String productCode) {
		try {
			final ProductPropertyResource productPropertyResource 
			    = new ProductPropertyResource(apiContext);
			
			ProductProperty productProperty 
				= productPropertyResource.getProperty(productCode, attributeFqn);
			if(productProperty==null){
				productProperty = new ProductProperty();
				productProperty.setAttributeFQN(attributeFqn);
				createNewProperty(affiliatedItems, productProperty);
				productPropertyResource.addProperty(productProperty, productCode);
				logger.info("Property "+attributeFqn +" added successfully!!!!");
			}else{
			final ProductPropertyValue productPropertyValue 
				= new ProductPropertyValue();
			final List<ProductPropertyValue> productPropertyValuesList = new ArrayList<>();
			for (ItemAffiliated i : affiliatedItems) {
				
				productPropertyValuesList.add(createProductProperty(i.getItemAffiliated()));
			}
			productProperty.setValues(productPropertyValuesList);
			productPropertyResource.updateProperty(productProperty, productCode, attributeFqn);
			
			
			}
		} catch (Exception e) {
			logger.error("Error " + e);
		}
	}
	
	
	public void createNewProperty(final List<ItemAffiliated> affiliatedItems,
			final ProductProperty productProperty ){
		final ProductPropertyValue productPropertyValue 
		= new ProductPropertyValue();
		final List<ProductPropertyValue> productPropertyValuesList = new ArrayList<>();
		for (ItemAffiliated i : affiliatedItems) {
			
			productPropertyValuesList.add(createProductProperty(i.getItemAffiliated()));
		}
		productProperty.setValues(productPropertyValuesList);
	}
		/**
		 * @param value.
		 * @.return
		 */
		public  ProductPropertyValue createProductProperty(final String value) {
			final ProductPropertyValue productPropertyValue = new ProductPropertyValue();
			final ProductPropertyValueLocalizedContent content;
			content = new ProductPropertyValueLocalizedContent();
			content.setStringValue(value);
			productPropertyValue.setContent(content);
			return productPropertyValue;
		}
		
		// TODO Auto-generated method stub
//		final List<ProductProperty> productProperties = product.getProperties();
		// try {
	/*	if (productProperties != null && productProperties.size() != 0) {
			if (isPropertyExists(productProperties,
					HhProductAttributeFqnConstants
					.Hh_Product_CrossSell_Attr_Fqn)) {
				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(
							HhProductAttributeFqnConstants
							.Hh_Product_CrossSell_Attr_Fqn)) {
						updateAffiliatedItem(
								updateProductProperties, product,
								affiliatedItems);
					}

				});
			} else {
				addProductAffiliatedItem(product,
						HhProductAttributeFqnConstants
						.Hh_Product_CrossSell_Attr_Fqn, affiliatedItems);
			}
		} 
	}*/


}
