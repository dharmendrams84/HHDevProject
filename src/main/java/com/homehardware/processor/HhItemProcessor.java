package com.homehardware.processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.hh.integration.constants.Constant;
import com.homehardware.model.Item;
import com.homehardware.model.Retail;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.core.Measurement;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductInCatalogInfo;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductLocalizedSEOContent;
import com.mozu.api.contracts.productadmin.ProductPrice;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;;

@Component
public class HhItemProcessor {

	protected static final Logger logger = Logger.getLogger(HhItemProcessor.class);	
	
	public Product addOrUpdateProduct(final Item item,final ApiContext apiContext){
		Product product = null;
		try {
		final String productCode = item.getId().getItem();
		final ProductResource productResource
			= new ProductResource(apiContext);
		
			product = productResource.getProduct(productCode);
			
			if(product==null){
				product = new Product();
				createNewProduct(product, productResource, item);
			}else{
				convertHhItemToMozuProduct(item, product);
				productResource.updateProduct(product, productCode);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}
	
	/**
	 * @param product.
	 * @param productResource.
	 * @param item.
	 * @.throws Exception
	 */
	public void createNewProduct(
			final Product product,final 
			ProductResource productResource,
			final Item item) throws Exception {
		
		product.setProductTypeId(Constant.int_7);

		convertHhItemToMozuProduct(item, product);
		productResource.addProduct(product);
		
	}
	
	/**
	 * @param item.
	 * @param product.
	 */
	public Product convertHhItemToMozuProduct(
			final Item item, final Product product) {
		if (product.getProperties() == null 
				|| product.getProperties().size() == 0) {
			final List<ProductProperty> productProperties = new ArrayList<>();
			product.setProperties(productProperties);
		}
		setProductBasicProperties(item, product);
		
		
				
		List<ProductInCatalogInfo> productInCatalogInfos = product.getProductInCatalogs();
		if (productInCatalogInfos == null) {
			productInCatalogInfos = new ArrayList<ProductInCatalogInfo>();
		}
		ProductInCatalogInfo catalog = new ProductInCatalogInfo();
		productInCatalogInfos.add(convertProdCatalogInfo(item, catalog));
	    
		
		
		return product;
	}

	/**
	 * @param item.
	 * @param product.
	 */
	public void setProductBasicProperties(final Item item, final Product product) {
		product.setProductCode(item.getId().getItem());
		setProductCatalogInfo(product);
		setProductContent(item, product);
		product.setProductUsage(Constant.STANDARD_PRODUCT);
		product.setBaseProductCode(item.getHhCtrlBrandInd());
		
		product.setIsValidForProductType(false);
		final Retail retail = new Retail();
		
		retail.setRetail(BigDecimal.ZERO);
		setItemRetailPrice(product, retail);
		setDefaultPackageWeight(product);
	}

	/**
	 * @param product.
	 */
	public final void setDefaultPackageWeight(
			final Product product) {
		final Measurement measurement = new Measurement();
		measurement.setValue(1.0);
		measurement.setUnit("LBS");
		product.setPackageWeight(measurement);
	}

	
	/**
	 * @param product.
	 * @param retail.
	 */
	public void setItemRetailPrice(
			final Product product, final Retail retail) {
		// TODO Auto-generated method stub
		ProductPrice productPrice = product.getPrice();
		if (productPrice == null) {
			productPrice = new ProductPrice();
		}
		final double d = retail.getRetail().doubleValue();
		productPrice.setPrice(d);
		productPrice.setSalePrice(d);
		product.setPrice(productPrice);

	}
	

	
	/**
	 * @param product.
	 */
	public void setProductCatalogInfo(final Product product) {
		product.setMasterCatalogId(Constant.master_catalog_id);
		final List list = new ArrayList<ProductInCatalogInfo>();
		final ProductInCatalogInfo productInCatalogInfo = new ProductInCatalogInfo();
		productInCatalogInfo.setCatalogId(Constant.master_catalog_id);
		productInCatalogInfo.setIsActive(true);
		list.add(productInCatalogInfo);
		product.setProductInCatalogs(list);
	}
	
	/**
	 * @param item.
	 * @param product.
	 */
	public void setProductContent(final Item item,final Product product) {
		final ProductLocalizedContent productLocalizedContent
		    = new ProductLocalizedContent();
		productLocalizedContent.setLocaleCode(Constant.LOCALE);
		productLocalizedContent.setProductName(item.getItemDesc());
		product.setContent(productLocalizedContent);
	}

	/**
	 * @param item.
	 * @param productInCatalogInfo.
	 */
	public ProductInCatalogInfo convertProdCatalogInfo(final Item item, ProductInCatalogInfo productInCatalogInfo) {
		if (item != null) {
			if (productInCatalogInfo == null) {
				productInCatalogInfo 
					= new ProductInCatalogInfo();
			}
			productInCatalogInfo.setCatalogId(Constant.FR_CATALOG_ID);
			if (item.getItemDescFr() != null && item.getItemDescFr().length() != 0) {
				productInCatalogInfo.setCatalogId(Constant.FR_CATALOG_ID);
			}
			final ProductLocalizedContent content = new ProductLocalizedContent();
			content.setLocaleCode(Constant.FR_CA);
			content.setProductName(item.getItemDescFr());
			productInCatalogInfo.setContent(content);

			final ProductLocalizedSEOContent seoContent = new ProductLocalizedSEOContent();
			//seoContent.setMetaTagKeywords(item.get);
			seoContent.setLocaleCode(Constant.FR_CA);
			productInCatalogInfo.setSeoContent(seoContent);
			productInCatalogInfo.setDateFirstAvailableInCatalog(new DateTime());

			productInCatalogInfo.setIsContentOverridden(true);
			productInCatalogInfo.setIsActive(true);
			
			productInCatalogInfo.setIsPriceOverridden(false);

		}
		return productInCatalogInfo;
	}
}
