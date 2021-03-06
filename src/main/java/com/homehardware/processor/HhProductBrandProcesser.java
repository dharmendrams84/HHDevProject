package com.homehardware.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hardware.constants.Utility;
import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.Brand;
import com.homehardware.test.JUnitClass;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;

@Component
public class HhProductBrandProcesser {

	protected static final Logger logger = Logger.getLogger(HhProductBrandProcesser.class);
	String brandCode;
	
	/**
	 * @param brandsList.
	 */
	public List<ProductProperty> transformHhProductBrandList(
			final List<Brand> brandsList) {
		final List<ProductProperty> list = new ArrayList<>();
		ProductProperty productProperty = new ProductProperty();
		if (brandsList != null && brandsList.size() != 0) {
			for (Brand brand : brandsList) {
				productProperty = PropertyUtility
						.getProductProperty(
								brand.getBrandDesc(), 
								HhProductAttributeFqnConstants
								.Hh_Brand_Desc_Attr_Fqn);
				if (productProperty != null) {
					list.add(productProperty);
				}
				
				
				productProperty = PropertyUtility
						.getProductProperty(brand.getBrandCode(),
						 HhProductAttributeFqnConstants
						.Hh_Brand_Code_Attr_Fqn);
				if (productProperty != null) {
					list.add(productProperty);
				}
				
				
				productProperty = PropertyUtility
						.getProductProperty(brand.getHomeExclusiveInd(), 
						 HhProductAttributeFqnConstants
						.Hh_Home_Exclusive_Ind_Attr_Fqn);
				if (productProperty != null) {
					list.add(productProperty);
				}
				
				
			}
			logger.info("Brand details processed successfully!!!");

		}
		return list;
	}
	
	
	
	/**
	 * @param item.
	 * @param product.
	 */
	public void setProductBrandList(
			final List<Brand> brandsList, final ApiContext apiContext) {
		
		transformHhBrandToKiboBrand(brandsList, apiContext);
		/*for (Brand b : brandsList) {
			transformHhBrandToKiboBrand(product, b);
		}*/
	}
	
	/**
	 * @param product.
	 * @param brand.
	 */
	public Map<String,ProductProperty> transformHhBrandToKiboBrand(
			final List<Brand> brandsList,final ApiContext apiContext) {

		
		final Map<String,ProductProperty> map = new HashMap();
		// transform product brand description obj to kibo product brand
		// description
		
		if (brandsList != null && brandsList.size() != 0) {
			for (Brand brand : brandsList) {
			
			ProductProperty productProperty = PropertyUtility.addOrUpdateProperty(brand.getBrandDesc(),
						HhProductAttributeFqnConstants
						.Hh_Brand_Desc_Attr_Fqn, 
						 apiContext, brand.getItem());
			String key= HhProductAttributeFqnConstants
					.Hh_Brand_Desc_Attr_Fqn+ " : "+brand.getItem();
			map.put(key,productProperty );
			productProperty = PropertyUtility.addOrUpdateProperty(brand.getBrandCode(),
						HhProductAttributeFqnConstants
						.Hh_Brand_Code_Attr_Fqn, apiContext,
						brand.getItem());
			key= HhProductAttributeFqnConstants
					.Hh_Brand_Code_Attr_Fqn+ " : "+brand.getItem();
			map.put(key,productProperty );
			productProperty = PropertyUtility.addOrUpdateProperty(brand.getHomeExclusiveInd(),
						HhProductAttributeFqnConstants
						.Hh_Home_Exclusive_Ind_Attr_Fqn, 
						 apiContext, brand.getItem());
			key= HhProductAttributeFqnConstants
					.Hh_Home_Exclusive_Ind_Attr_Fqn+ " : "+brand.getItem();
			map.put(key,productProperty );
			}
		}
		logger.info("Brand details processed successfully!!!");
		
		//addOrUpdateProperty(value, attributeFqn, apiContext, productCode);
		/*addOrUpdateProperty(product,
				HhProductAttributeFqnConstants.Hh_Brand_Desc_Attr_Fqn,
				brand.getBrandDesc());

		// transform HH product brand code obj to kibo product brand code obj
		addOrUpdateProperty(product, HhProductAttributeFqnConstants
				.Hh_Brand_Code_Attr_Fqn, brand.getBrandCode());

		// transform product image id to kibo product image id
		setProductHomeExclusiveInd(product, brand);
		addOrUpdateProperty(product, HhProductAttributeFqnConstants
				.Hh_Home_Exclusive_Ind_Attr_Fqn,
				brand.getHomeExclusiveInd());
*/
		
		return map;
	}


	/*public void addOrUpdateProperty(final String value,final String attributeFqn,ApiContext apiContext,final String productCode){
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
				if (productProperty.getValues() != null && productProperty.getValues().size() != 0) {
					if (productProperty.getValues().get(0).getContent() != null) {
						productProperty.getValues().get(0).getContent().setStringValue(value);
					} else {
						ProductPropertyValueLocalizedContent content = new ProductPropertyValueLocalizedContent();
						content.setStringValue(value);
					}
				}

				productPropertyResource.updateProperty(productProperty, productCode, attributeFqn);
				logger.info("Property " + attributeFqn + " updated successfully!!!!");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
   
   *//**
	 * @param value.
	 * @.return
	 *//*
	public ProductPropertyValue createProductProperty(final String value) {
		final ProductPropertyValue productPropertyValue = new ProductPropertyValue();
		final ProductPropertyValueLocalizedContent content;
		content = new ProductPropertyValueLocalizedContent();
		content.setStringValue(value);
		productPropertyValue.setContent(content);
		return productPropertyValue;
	}
*/

}
