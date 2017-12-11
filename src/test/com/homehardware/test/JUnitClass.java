package com.homehardware.test;

import com.hardware.constants.Constants;
import com.hh.integration.constants.Constant;
import com.homehardware.dao.HhDaoObjectImpl;
import com.homehardware.model.Brand;
import com.homehardware.model.ExtDesc;
import com.homehardware.model.Gtin;
import com.homehardware.model.Item;
import com.homehardware.model.ItemAffiliated;
import com.homehardware.model.ItemLoc;
import com.homehardware.model.ItemRestricted;
import com.homehardware.model.ProductItemAttributes;
import com.homehardware.model.RetailMsrp;
import com.homehardware.utility.ProductUtility;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.core.Measurement;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductInCatalogInfo;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/webapp/WEB-INF/spring/homehardware/spring-context.xml" })
public final class JUnitClass {

	/*@PersistenceUnit
	EntityManagerFactory entityManagerFactory;
	*/
	
	@Autowired
	private HhDaoObjectImpl hhDaoObjectImpl;

	@Test
	public void testFetchHhProductFromDb() {
		System.out.println("testEntityManager!!!!");
		try {
			/*final EntityManager entityManager = 
			    entityManagerFactory.createEntityManager();
*/
			final ProductResource productResource = new ProductResource(
					new MozuApiContext(Constants.tenantId, Constants.siteId));

			final List list = hhDaoObjectImpl.getItemsList("1", "INITIAL");
			for (Object o : list) {
				final Item item = (Item) o;
				Product product =
						productResource.getProduct(item.getId().getItem());

				if (product == null) {
					product = new Product();
					
					createNewProduct(product, productResource, item);

				} else {
					
					updateProduct(product, productResource, item);
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("testEntityManager!!!!");
	}
	
	/**
	 * @param product.
	 * @param productResource.
	 * @param item.
	 * 
	 */
	public void createNewProduct(
			final Product product,final 
			ProductResource productResource,
			final Item item) throws Exception {
		
		product.setProductTypeId(Constant.int_7);

		convertHhItemToMozuProduct(item, product);
	    Product newProduct = productResource.addProduct(product);
	    if(newProduct!=null){
	    	System.out.println("New Product with product code "+newProduct.getProductCode()+" created successfully!!!!!");
	    }
	}
	
	/**
	 * @param product.
	 * @param productResource.
	 * @param item.
	 * @.throws Exception
	 */
	public void updateProduct(
			final Product product,final 
			ProductResource productResource, final Item item) throws Exception {
		convertHhItemToMozuProduct(item, product);
		productResource.updateProduct(product, product.getProductCode());
		System.out.println("Product "+product.getProductCode()+ " updated successfully!!!!");
	}
	
	
	
	protected void convertHhItemToMozuProduct(final Item item, final Product product) {
		setProductBasicProperties(item, product);
		final List<ItemAffiliated> itemAffiliateds = item.getItemAfffliated();
		ProductUtility
		.transformHhRelatedProductToKiboRelatedProductCode(product,itemAffiliateds);
		setProductBrandList(item, product);
		setProductExtDesc(item, product);
		setProductGtin(item, product);
		setProductLocation(item, product);
		setProductItemRestricted(item, product);
		setProductRetailMsrp(item, product);
                setProductItemAttributes(item, product);
	}
	
	/**
	 * @param item.
	 * @param product.
	 */
	protected void setProductBasicProperties(final Item item, final Product product) {
		product.setProductCode(item.getId().getItem());
		setProductCatalogInfo(product);
		setProductContent(item, product);
		product.setProductUsage("Standard");
		product.setBaseProductCode(item.getHhCtrlBrandInd());
		//setProductMeasurement(product);
		product.setIsValidForProductType(false);
		
	}
	
	/**
	 * @param product.
	 */
	protected void setProductMeasurement(final Product product) {

		final Measurement measurement = new Measurement();
		measurement.setUnit("lbs");
		measurement.setValue(new Double(2));
		product.setPackageWeight(measurement);

	}
	
	
	/**
	 * @param item.
	 * @param product.
	 */
	protected void setProductContent(final Item item,final Product product) {
		final ProductLocalizedContent productLocalizedContent
		    = new ProductLocalizedContent();
		productLocalizedContent.setLocaleCode("en-US");
		productLocalizedContent.setProductName(item.getItemDesc());
		product.setContent(productLocalizedContent);
	}
	
	/**
	 * @param product.
	 */
	protected void setProductCatalogInfo(final Product product) {
		product.setMasterCatalogId(2);
		final List list = new ArrayList<ProductInCatalogInfo>();
		final ProductInCatalogInfo productInCatalogInfo = new ProductInCatalogInfo();
		productInCatalogInfo.setCatalogId(2);
		productInCatalogInfo.setIsActive(true);
		list.add(productInCatalogInfo);
		product.setProductInCatalogs(list);
	}
	
	/**
	 * @param item.
	 * @param product.
	 */
	protected void setProductBrandList(final Item item, final Product product) {
		final List<Brand> brandsList = item.getBrand();
		for (Brand b : brandsList) {
			ProductUtility.testTransformationFromHhBrandToKiboBrand(product, b);
		}
	}
	
	/**
	 * @param item.
	 * @param product.
	 */
	protected void setProductExtDesc(final Item item, final Product product) {
		if (item.getExtDesc() != null && item.getExtDesc().size() != 0) {

			for (ExtDesc e : item.getExtDesc()) {
				ProductUtility
				.testTransformationFromHhExtendedDescToKiboExtendedDesc(product, e);
			}
		}
	}
	
	/**
	 * @param item.
	 * @param product.
	 */
	protected void setProductGtin(final Item item, final Product product) {
		if (item.getGtin() != null && item.getGtin().size() != 0) {
			for (Gtin g : item.getGtin()) {

				ProductUtility.transformHhProductGtinToKiboProductGtin(product, g);
			}
		}
	}
	
	/**
	 * @param item.
	 * @param product.
	 */
	protected void setProductLocation(final Item item, final Product product) {
		if (item.getItemLoc() != null && item.getItemLoc().size() != 0) {
			final ItemLoc itemLoc = item.getItemLoc().get(0);
			ProductUtility
			.testTransformationFromHhItemLocToKiboItemLoc(product, itemLoc);
		}
	}
	
	/**
	 * @param item.
	 * @param product.
	 */
	protected void setProductItemRestricted(final Item item, final Product product) {
		if (item.getItemRestricted() != null && item.getItemRestricted().size() != 0) {
			for (ItemRestricted i : item.getItemRestricted()) {
				ProductUtility
				.testTransformFromHhItemRestrictedToKiboItemRestricted(product, i);
			}
		}
	}
	
	/**
	 * @param item.
	 * @param product.
	 */
	protected void setProductRetailMsrp(final Item item, final Product product) {
		if (item.getRetailMsrp() != null && item.getRetailMsrp().size() != 0) {
			for (RetailMsrp r : item.getRetailMsrp()) {
				ProductUtility.testTransformationFromHhPriceToKiboPrice(product, r);
			}
		}
	}
	
	/**
	 * @param item.
	 * @param product.
	 */
	protected void setProductItemAttributes(final Item item, final Product product) {
		for (ProductItemAttributes p : item.getProductItemAttributes()) {
			if (ProductUtility
					.isPropertyExists(
							product.getProperties(),
							p.getId().getProductAttrId())) {
				for (ProductProperty pp : product.getProperties()) {
					if (pp.getAttributeFQN()
							.equalsIgnoreCase(
									p.getId()
									.getProductAttrId())) {
						ProductUtility
						    .updateProductPropertiesAttribute(
								pp, p.getAttributeValue());
					}
				}

			} else {
				ProductUtility
				    .addProductProperty(
						product, p.getAttributeValue(), 
						p.getId().getProductAttrId());
			}
		}
	}
}
