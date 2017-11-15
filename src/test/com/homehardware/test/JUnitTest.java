package com.homehardware.test;

import com.homehardware.beans.Store;
import com.homehardware.dao.HhDaoImpl;
import com.homehardware.model.Item;
import com.homehardware.model.ProductAttribute;
import com.homehardware.model.ProductItemAttributes;
import com.homehardware.model.TransformFromHhLocationToKiboLocation;

import java.util.ArrayList;
import java.util.List;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import com.mozu.api.contracts.core.Address;
import com.mozu.api.contracts.core.Measurement;
import com.mozu.api.contracts.location.Coordinates;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.location.LocationType;
import com.mozu.api.contracts.location.ShippingOriginContact;
import com.mozu.api.contracts.mzdb.EntityList;
import com.mozu.api.resources.commerce.admin.LocationResource;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductInCatalogInfo;
import com.mozu.api.contracts.productadmin.ProductInventoryInfo;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductPrice;
import com.mozu.api.resources.platform.EntityListResource;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/homehardware/applicationContext.xml" })
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/homehardware/applicationContext.xml" })

public class JUnitTest {

	public JUnitTest() {

	}

	@Autowired
	private HhDaoImpl hhDaoImpl;
	
	//@Test
	public void testFetchHHStoreObjectFromDB()throws Exception{
		hhDaoImpl.getStore();
		
	}

	//@Test
	public void testInsertOrUpdateLocationObjectIntoKibo() throws Exception {
		LocationResource locationResource = new LocationResource(new MozuApiContext(24094,35909)); 
		Store store = hhDaoImpl.getStore();
		String locationCode = store.getStoreNumber();
		Location location = locationResource.getLocation(locationCode);
		
		
		store.setStoreName(locationCode);
		if(location==null){
			location = new Location();
			//convertStoreTOMozuLocation(location, store);
			TransformFromHhLocationToKiboLocation.testTransformHhLocationToKiboLocation(location, store);
			locationResource.addLocation(location);
		}else{
			TransformFromHhLocationToKiboLocation.testTransformHhLocationToKiboLocation(location, store);
			//convertStoreTOMozuLocation(location, store);
			locationResource.updateLocation(location, locationCode);
		}
		
		
		
					
	}
	
	public void convertStoreTOMozuLocation(Location location , Store store){
		location.setPhone("3333333334");
		location.setName("Home Hardware Central Warehouse1");
		
		Address address = new Address();
		address.setAddress1("Clarke & Clarke Home Hardware Building Centre");
		address.setCountryCode("CA");
		address.setStateOrProvince("NL");
		address.setCityOrTown("Bell Island");
		address.setPostalOrZipCode("A0A 4H0");
		address.setAddressType("Commercial");
		address.setIsValidated(false);
		location.setAddress(address);
		location.setAllowFulfillmentWithNoStock(false);
		location.setCode(store.getStoreName());
		location.setIsDisabled(false);
		
		Coordinates coordinates= new Coordinates();
		coordinates.setLat(0.0);
		coordinates.setLng(0.0);
		location.setGeo(coordinates);
		
		location.setDescription("description");
		ShippingOriginContact shippingOriginContact = new ShippingOriginContact();
		shippingOriginContact.setFirstName("Alex");
		shippingOriginContact.setLastNameOrSurname("Graham");
		shippingOriginContact.setCompanyOrOrganization("HH");
		shippingOriginContact.setEmail("ag@hh.ca");
		shippingOriginContact.setPhoneNumber("1111111111");
		
		
		location.setShippingOriginContact(shippingOriginContact);
		
		List<LocationType> list = new ArrayList<LocationType>();
		LocationType locationType = new LocationType();
		locationType.setCode("WH1");
		locationType.setName("WareHouse");
		list.add(locationType);
		location.setLocationTypes(list);
		

	}

	//@Test
	public void testFetchHHEcoFeesFromDB() throws Exception{
		//hhDaoImpl.getEcoFees();
		EntityList entityList = new EntityList();
		entityList.setName("testName");
		EntityListResource entityListResource = new EntityListResource(new MozuApiContext(24094,35909));
		
		entityListResource.createEntityList(entityList);
		System.out.println("After creating entityList resource"+entityListResource);
	}

	@Test
	public void testFetcgHhProductFromDb() {
		try{
	
		Item item = hhDaoImpl.getItem();
		ProductResource productResource = new ProductResource(new MozuApiContext(24094, 35909));
		Product product = productResource.getProduct(item.getItem());
		List<ProductAttribute> productAttributes  =  hhDaoImpl.getProductAttributesList();
		
		
		
		if (product == null) {
			System.out.println("Adding new product");
			product = new Product();
			//product.setPrice(product1.getPrice());
			//product.setProductInCatalogs(product1.getProductInCatalogs());
			product.setProductTypeId(7);
			convertItemToMozuProduct(item, product);
			productResource.addProduct(product);
			
			} else {
				System.out.println("Updating existing product");
				// convertItemToMozuProduct(item, product);
				// product.setProductInCatalogs(product1.getProductInCatalogs());
				hhDaoImpl.getProductItemAttribute();
				List<ProductProperty> productProperties = product.getProperties();
				if (productProperties != null && productProperties.size() != 0) {
					for (ProductAttribute p : productAttributes) {
						ProductItemAttributes productItemAttributes = hhDaoImpl
								.getProductItemAttribute(p.getProductAttrId(), product.getProductCode());
						if (productItemAttributes != null
								&& isProductPropertyExist(product.getProperties(), p.getProductAttrId())) {
							convertProductAttribute(p.getProductAttrId(), productItemAttributes, product);
						} else {
							if (p.getProductAttrId().equals("tenant~unit-of-measure-fr")) {
								addProductProperty(productItemAttributes, product);
							}
						}
					}
				}else{
					 productProperties = new ArrayList<>();
					 for (ProductAttribute p : productAttributes) {
						 ProductItemAttributes productItemAttributes = hhDaoImpl
									.getProductItemAttribute(p.getProductAttrId(), product.getProductCode());
						 addProductProperty(productItemAttributes, product);
					 }
					 product.setProperties(productProperties);
				}
				productResource.updateProduct(product, product.getProductCode());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected static void convertItemToMozuProduct(Item item, final Product product) {
		product.setProductCode(item.getItem());
		ProductLocalizedContent productLocalizedContent = new ProductLocalizedContent();
		productLocalizedContent.setLocaleCode("en-US");
		//productLocalizedContent.setProductName("29 Espresso Adelaide Swivel Stool3");
		productLocalizedContent.setProductName(item.getItemDesc());
		product.setContent(productLocalizedContent);
		//product.setProductTypeId(7);
		
		product.setProductUsage("Standard");
		product.setMasterCatalogId(2);
		
		//product.getPr
		
		ProductPrice price = new ProductPrice();
		price.setPrice(209.99);
		price.setIsoCurrencyCode("CAD");
		price.setSalePrice(209.99);
		product.setPrice(price);
		List list = new ArrayList<ProductInCatalogInfo>();
		ProductInCatalogInfo productInCatalogInfo = new ProductInCatalogInfo();
		productInCatalogInfo.setCatalogId(2);
		
		productInCatalogInfo.setPrice(price);
		
		productInCatalogInfo.setIsActive(true);
		//productInCatalogInfo.setCatalogId(2);
		
		list.add(productInCatalogInfo);
		product.setProductInCatalogs(list);
		
		
		Measurement measurement = new Measurement();
		measurement.setUnit("lbs");
		measurement.setValue(new Double(2));
		product.setPackageWeight(measurement);
		
		product.setBaseProductCode(item.getBrandCode()); 
		
		product.setIsValidForProductType(false);
		
		
	}
	
	protected static void convertProductAttribute(String attrFqnId ,ProductItemAttributes productItemAttributes, final Product product) {
		try{
		List<ProductProperty> productProperties = product.getProperties();
		if(productProperties!=null&&productProperties.size()!=0){
			for(ProductProperty p : productProperties){
				if(p.getAttributeFQN().equalsIgnoreCase(attrFqnId)){
					ProductPropertyValue productPropertyValue = p.getValues().get(0);
					if(productPropertyValue instanceof ProductPropertyValue && productPropertyValue.getContent()!=null){
						
						productPropertyValue.getContent().setStringValue(productItemAttributes.getAttributeValue());
					}
				  ProductPropertyResource productPropertyResource = new ProductPropertyResource(new MozuApiContext(24094, 35909));
				  productPropertyResource.updateProperty(p, product.getProductCode(), p.getAttributeFQN());
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	protected static void addProductProperty(ProductItemAttributes productItemAttributes, final Product product) {
		try {
		ProductProperty productProperty = new ProductProperty();
	    productProperty.setAttributeFQN(productItemAttributes.getId().getProductAttrId());
	 
	    ProductPropertyValue productPropertyValueAttr = new ProductPropertyValue();
	    List<ProductPropertyValue> productPropertyValue = new ArrayList<ProductPropertyValue>();
	    productPropertyValueAttr.setValue(productItemAttributes.getAttributeValue());
	    
	    ProductPropertyValueLocalizedContent content = new ProductPropertyValueLocalizedContent();
	    content.setLocaleCode("En");
	    content.setStringValue(productItemAttributes.getAttributeValue());
	    productPropertyValueAttr.setContent(content);
	    
	    productPropertyValue.add(productPropertyValueAttr);
	    productProperty.setValues(productPropertyValue);
	    product.getProperties().add(productProperty);
	    ProductPropertyResource productPropertyResource = new ProductPropertyResource(new MozuApiContext(24094, 35909));
	    //productPropertyResource.updateProperty(p, product.getProductCode(), p.getAttributeFQN());
	    
			productPropertyResource.addProperty(productProperty, product.getProductCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected static boolean isProductPropertyExist(List<ProductProperty> productProperties, String produtFqn){
		boolean productPropertyExist = false;
		for(ProductProperty pp : productProperties){
			if(pp.getAttributeFQN().equalsIgnoreCase(produtFqn)){
				productPropertyExist = true;
				break;
			}
		}
		
		return productPropertyExist;
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}
}
