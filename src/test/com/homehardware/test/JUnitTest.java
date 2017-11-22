package com.homehardware.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hardware.constants.AttributeConstants;
import com.homehardware.beans.Store;
import com.homehardware.dao.HhDaoImpl;
import com.homehardware.model.EcoFees;
import com.homehardware.model.ExtDesc;
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

import com.mozu.api.ApiException;
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
import com.mozu.api.contracts.mzdb.EntityCollection;
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
import com.mozu.api.resources.platform.entitylists.EntityResource;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {
// "file:src/main/webapp/WEB-INF/spring/homehardware/applicationContext.xml" })
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/homehardware/applicationContext.xml" })

public class JUnitTest {

	public JUnitTest() {

	}

	@Autowired
	private HhDaoImpl hhDaoImpl;

	// @Test
	public void testFetchHHStoreObjectFromDB() throws Exception {
		hhDaoImpl.getStore();

	}

	// @Test
	public void testInsertOrUpdateLocationObjectIntoKibo() throws Exception {
		LocationResource locationResource = new LocationResource(new MozuApiContext(24094, 35909));
		Store store = hhDaoImpl.getStore();
		String locationCode = store.getStoreNumber();
		Location location = locationResource.getLocation(locationCode);

		store.setStoreName(locationCode);
		if (location == null) {
			location = new Location();
			// convertStoreTOMozuLocation(location, store);
			TransformFromHhLocationToKiboLocation.testTransformHhLocationToKiboLocation(location, store);
			locationResource.addLocation(location);
		} else {
			TransformFromHhLocationToKiboLocation.testTransformHhLocationToKiboLocation(location, store);
			// convertStoreTOMozuLocation(location, store);
			locationResource.updateLocation(location, locationCode);
		}

	}

	public void convertStoreTOMozuLocation(Location location, Store store) {
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

		Coordinates coordinates = new Coordinates();
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

	

	// @Test
	public void testFetcgHhProductFromDb() {
		try {

			Item item = hhDaoImpl.getItem();
			ProductResource productResource = new ProductResource(new MozuApiContext(24094, 35909));
			Product product = productResource.getProduct(item.getItem());

			if (product == null) {
				product = new Product();
				product.setProductTypeId(7);
				convertItemToMozuProduct(item, product);
				productResource.addProduct(product);

			} else {
				convertItemToMozuProduct(item, product);
				productResource.updateProduct(product, product.getProductCode());
				// setProductAttributes(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void testFetcgHhProductAttributesFromDb() {
		try {
			Item item = hhDaoImpl.getItem();
			ProductResource productResource = new ProductResource(new MozuApiContext(24094, 35909));
			Product product = productResource.getProduct(item.getItem());

			if (product == null) {
				System.out.println("Product " + item.getItem() + " is not existing ");
			} else {

				setProductAttributes(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void testFetchHHEcoFeesFromDB() throws Exception {
		
		List<EcoFees> ecoFeesList = hhDaoImpl.getEcoFes();
		int tenantId=24094 ,siteId = 35909;
		final String fullPathName = "EHF_TEMPLATE" + "@" + "HomeH";

		for(EcoFees e : ecoFeesList){
			EntityCollection ec = getEntityCollection(tenantId, siteId, e.getItem(), e.getProv());
		 System.out.println(ec);
		 
		 if(ec !=null&&ec.getItems()!=null&&ec.getItems().size()==0){			
			 		//insertEntityList(tenantId, siteId, e.getItem(), e.getProv(), e.getFeeAmt(), fullPathName);
			 configureEntity(tenantId, siteId,e.getItem(),e.getProv(),new Double(e.getFeeAmt()).toString());
		 }else{
			 updateEntity(tenantId, siteId, "", "", "", null);
		 }
		}
	}
	
	
	public EntityCollection getEntityCollection(int tenantId,int siteId,String productCode,String province) {
		EntityCollection entityCollection = null;
		try{
		MozuApiContext context = new MozuApiContext();
		context.setTenantId(tenantId);
		context.setSiteId(siteId);
		EntityResource entityResource = new EntityResource(context);
		final String EHF_TEMPLATE = "EHF-TEMPLATE";
		final String fullName = EHF_TEMPLATE + "@" + "HomeH";
		String filterString = "(productCode eq '";
		filterString = filterString+productCode+"') and (province eq '"+province+"')";
		
		System.out.println(filterString);
		
		entityCollection = entityResource.getEntities(fullName, null, null, filterString, null, null);
		
		
		}catch(Exception e){
			System.out.println("Exception while getting EntityCollection");
			e.printStackTrace();
		}
		return entityCollection;
	}
	
		public void fetchEntityListFromDB() {
		MozuApiContext context = new MozuApiContext();
		context.setTenantId(24094);
		context.setSiteId(35909);
		final String EHF_TEMPLATE = "EHF-TEMPLATE";
		final String ITEM = "productCode";
		final String PROVINCE = "province";
		final String FEE_AMT = "feeAmt";
		EntityResource entityResource = new EntityResource(context);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode fitmentNode = mapper.getNodeFactory().objectNode();

		fitmentNode.put(ITEM, "10001");
		fitmentNode.put(PROVINCE, "ON");
		fitmentNode.put(FEE_AMT, "2.00");
		JsonNode returnNode = null;
		try {
			String fullName = EHF_TEMPLATE + "@" + "HomeH";
			//JsonNode jsonNode =  entityResource.getEntity(EHF_TEMPLATE + "@" + "HomeH", "b87fb7634b6247abb82101f16996fbbd");
			//System.out.println(jsonNode.toString());
//			JsonNode jsonNode2 = jsonNode.get("productCode");
			//ObjectNode objectNode = (ObjectNode)jsonNode;
			//objectNode.put("productCode", "10003");
			String productCode ="10003" , province ="ON";
			String filterString = "(productCode eq '";
			filterString = filterString+productCode+"') and (province eq '"+province+"')";
			
			System.out.println(filterString);
			//EntityCollection entityCollection = entityResource.getEntities(fullName, null, null, "(productCode eq '10003') and (province eq 'ON')", null, null);
			EntityCollection entityCollection = entityResource.getEntities(fullName, null, null, filterString, null, null);
			//(productCode eq ”10003”) and (province eq ”ON”)
			
			List<JsonNode> list = entityCollection.getItems();
			System.out.println(list);
			/*entityResource.updateEntity(jsonNode,EHF_TEMPLATE + "@" + "HomeH", "b87fb7634b6247abb82101f16996fbbd");
			JsonNode jsonNode3 =  entityResource.getEntity(EHF_TEMPLATE + "@" + "HomeH", "b87fb7634b6247abb82101f16996fbbd");
			System.out.println("updated JSON "+jsonNode3.toString());
			*/
		} catch (Exception e) {
			System.out.println("Error processing Entity for : " + fitmentNode.get(ITEM));
			e.printStackTrace();
		}

	}
	//@Test
	public void configureEntity(int tenantId, int siteId,String productCode,String province,String feeAmount)  {
		MozuApiContext context = new MozuApiContext();
		context.setTenantId(tenantId);
		context.setSiteId(siteId);
		final String EHF_TEMPLATE = "EHF-TEMPLATE";
		final String ITEM = "productCode";
		final String PROVINCE = "province";
		final String FEE_AMT = "feeAmt";
		EntityResource entityResource = new EntityResource(context);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode fitmentNode = mapper.getNodeFactory().objectNode();

		fitmentNode.put(ITEM, productCode);
		fitmentNode.put(PROVINCE, province);
		fitmentNode.put(FEE_AMT, feeAmount);
		JsonNode returnNode = null;
		try {
		returnNode = entityResource.insertEntity(fitmentNode, EHF_TEMPLATE+"@"+"HomeH");
		System.out.println("Added Entity for : "+ returnNode.get(ITEM));
		} catch (Exception e) {
		System.out.println("Error processing Entity for : "+ fitmentNode.get(ITEM));
		e.printStackTrace();
		}
		}

	public void updateEntity(int tenantId, int siteId,String productCode,String province,String feeAmount,ObjectNode fitmentNode1 )  {
		MozuApiContext context = new MozuApiContext();
		context.setTenantId(tenantId);
		context.setSiteId(siteId);
		final String EHF_TEMPLATE = "EHF-TEMPLATE";
		final String ITEM = "productCode";
		final String PROVINCE = "province";
		final String FEE_AMT = "feeAmt";
		EntityResource entityResource = new EntityResource(context);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode fitmentNode = mapper.getNodeFactory().objectNode();

		fitmentNode.put(ITEM, productCode);
		fitmentNode.put(PROVINCE, province);
		fitmentNode.put(FEE_AMT, feeAmount);
		JsonNode returnNode = null;
		try {
		//returnNode = entityResource.insertEntity(fitmentNode, EHF_TEMPLATE+"@"+"HomeH");
		//entityResource.updateEntity(fitmentNode,  EHF_TEMPLATE+"@"+"HomeH", );
		JsonNode jsonNode3 =  entityResource.getEntity(EHF_TEMPLATE + "@" + "HomeH", "7834105e07b84e96871c6b9d3b85a5bc");
		//entityResource.get
		System.out.println("updated JSON "+jsonNode3.toString());
		System.out.println("Added Entity for : "+ returnNode.get(ITEM));
		} catch (Exception e) {
		System.out.println("Error processing Entity for : "+ fitmentNode.get(ITEM));
		e.printStackTrace();
		}
		}



	
	@Test
	public void testFetchExtendedDescriptionFromDB() {
		try {
			String productCode = "4466-443";
			List<ExtDesc> extDescs = hhDaoImpl.geExtendedDescription(productCode);
			ProductResource productResource = new ProductResource(new MozuApiContext(24094, 35909));
			Product product = productResource.getProduct(productCode);
			final int tenantId = 24094, siteId = 35909;
			if (product != null) {
				if (extDescs != null && extDescs.size() != 0) {
					for (ExtDesc e : extDescs) {
						String type = e.getType();
						String description = e.getDescription();
						if (!isEmpty(description) && description.trim().length() != 0) {
							if (!isEmpty(type) && type.equalsIgnoreCase(AttributeConstants.MKTG)) {
								if (isProductPropertyExist(product.getProperties(),
										AttributeConstants.tenant_marketing_description)) {
									updateProductproperty(AttributeConstants.tenant_marketing_description,
											e.getDescription(), product, tenantId, siteId);
								} else {
									addProductProperty(AttributeConstants.tenant_marketing_description,
											e.getDescription(), product, tenantId, siteId);
								}
							} else if (!isEmpty(type) && type.equalsIgnoreCase(AttributeConstants.INGR)) {
								if (isProductPropertyExist(product.getProperties(),
										AttributeConstants.TENANT_INGREDIENTS)) {
									updateProductproperty(AttributeConstants.TENANT_INGREDIENTS, e.getDescription(),
											product, tenantId, siteId);
								} else {
									addProductProperty(AttributeConstants.TENANT_INGREDIENTS, e.getDescription(),
											product, tenantId, siteId);
								}
							} else if (!isEmpty(type) && type.equalsIgnoreCase(AttributeConstants.FB)) {
								if (product.getContent() != null) {
									product.getContent().setProductFullDescription(e.getDescription());
									productResource.updateProduct(product, product.getProductCode());
								}
							}

						} else {
							System.out.println("Not updating " + e.getType() + " as this is a blank value");
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void setProductAttributes(Product product) {
		List<ProductAttribute> productAttributes = hhDaoImpl.getProductAttributesList();
		List<ProductProperty> productProperties = product.getProperties();
		if (productProperties != null && productProperties.size() != 0) {
			for (ProductAttribute p : productAttributes) {
				ProductItemAttributes productItemAttributes = hhDaoImpl.getProductItemAttribute(p.getProductAttrId(),
						product.getProductCode());
				if (productItemAttributes != null && isStatusInitial(productItemAttributes.getStatus())
						&& isProductPropertyExist(product.getProperties(), p.getProductAttrId())) {

					convertProductAttribute(p.getProductAttrId(), productItemAttributes, product);
				} else {
					if (productItemAttributes != null && isStatusInitial(productItemAttributes.getStatus())
							&& productItemAttributes.getId() != null
							&& productItemAttributes.getId().getProductAttrId() != null) {
						addProductProperty(productItemAttributes, product);
					}
				}
			}
		} else {
			productProperties = new ArrayList<>();
			product.setProperties(productProperties);
			for (ProductAttribute p : productAttributes) {
				ProductItemAttributes productItemAttributes = hhDaoImpl.getProductItemAttribute(p.getProductAttrId(),
						product.getProductCode());
				if (productItemAttributes != null && isStatusInitial(productItemAttributes.getStatus()))
					addProductProperty(productItemAttributes, product);
			}
			product.setProperties(productProperties);
		}
	}

	protected static void convertItemToMozuProduct(Item item, final Product product) {
		product.setProductCode(item.getItem());
		ProductLocalizedContent productLocalizedContent = new ProductLocalizedContent();
		productLocalizedContent.setLocaleCode("en-US");
		// productLocalizedContent.setProductName("29 Espresso Adelaide Swivel
		// Stool3");
		productLocalizedContent.setProductName(item.getItemDesc());
		product.setContent(productLocalizedContent);
		// product.setProductTypeId(7);

		product.setProductUsage("Standard");
		product.setMasterCatalogId(2);

		// product.getPr

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
		// productInCatalogInfo.setCatalogId(2);

		list.add(productInCatalogInfo);
		product.setProductInCatalogs(list);

		Measurement measurement = new Measurement();
		measurement.setUnit("lbs");
		measurement.setValue(new Double(2));
		product.setPackageWeight(measurement);

		product.setBaseProductCode(item.getBrandCode());

		product.setIsValidForProductType(false);

	}

	protected void convertProductAttribute(String attrFqnId, ProductItemAttributes productItemAttributes,
			final Product product) {
		try {
			List<ProductProperty> productProperties = product.getProperties();
			if (productProperties != null && productProperties.size() != 0) {
				for (ProductProperty p : productProperties) {
					if (p.getAttributeFQN().equalsIgnoreCase(attrFqnId)) {
						ProductPropertyValue productPropertyValue = p.getValues().get(0);
						if (productPropertyValue instanceof ProductPropertyValue
								&& productPropertyValue.getContent() != null) {

							productPropertyValue.getContent().setStringValue(productItemAttributes.getAttributeValue());
						}
						productPropertyValue.setValue(productItemAttributes.getAttributeValue());
						ProductPropertyResource productPropertyResource = new ProductPropertyResource(
								new MozuApiContext(24094, 35909));
						productPropertyResource.updateProperty(p, product.getProductCode(), p.getAttributeFQN());
					}
				}
			}
			;
			productItemAttributes.setStatus("SUCCESS");
			hhDaoImpl.updateProductItemAttributes(productItemAttributes);
			System.out
					.println("Property " + productItemAttributes.getId().getProductAttrId() + " updated successfully ");
		} catch (Exception e) {

			productItemAttributes.setStatus("ERROR");
			hhDaoImpl.updateProductItemAttributes(productItemAttributes);
			System.out.println("Exception while updating property " + productItemAttributes.getId().getProductAttrId()
					+ " " + e.getCause());
		}

	}

	protected void updateProductproperty(String attributeFqn, String attributeValue, final Product product,
			int tenantId, int siteId) {
		try {
			List<ProductProperty> productProperties = product.getProperties();
			if (productProperties != null && productProperties.size() != 0) {
				for (ProductProperty p : productProperties) {
					if (p.getAttributeFQN().equalsIgnoreCase(attributeFqn)) {
						ProductPropertyValue productPropertyValue = p.getValues().get(0);
						if (productPropertyValue instanceof ProductPropertyValue
								&& productPropertyValue.getContent() != null) {

							productPropertyValue.getContent().setStringValue(attributeValue);
						}
						//productPropertyValue.setValue(attributeValue);
						ProductPropertyResource productPropertyResource = new ProductPropertyResource(
								new MozuApiContext(tenantId, siteId));
						productPropertyResource.updateProperty(p, product.getProductCode(), p.getAttributeFQN());
					}
				}
			}
			System.out.println(" property " + attributeFqn + " updated successfully");
		} catch (Exception e) {

			System.out.println("Exception while updating property " + attributeFqn + " " );
			e.printStackTrace();
		}

	}

	protected void addProductProperty(ProductItemAttributes productItemAttributes, final Product product) {
		try {
			ProductProperty productProperty = new ProductProperty();
			productProperty.setAttributeFQN(productItemAttributes.getId().getProductAttrId());

			ProductPropertyValue productPropertyValueAttr = new ProductPropertyValue();
			List<ProductPropertyValue> productPropertyValue = new ArrayList<ProductPropertyValue>();
			productPropertyValueAttr.setValue(productItemAttributes.getAttributeValue());

			ProductPropertyValueLocalizedContent content = new ProductPropertyValueLocalizedContent();
			content.setLocaleCode("En-US");
			content.setStringValue(productItemAttributes.getAttributeValue());
			productPropertyValueAttr.setContent(content);

			productPropertyValue.add(productPropertyValueAttr);
			productProperty.setValues(productPropertyValue);
			product.getProperties().add(productProperty);
			ProductPropertyResource productPropertyResource = new ProductPropertyResource(
					new MozuApiContext(24094, 35909));
			productPropertyResource.addProperty(productProperty, product.getProductCode());
			productItemAttributes.setStatus("SUCCESS");
			hhDaoImpl.updateProductItemAttributes(productItemAttributes);
			System.out.println("Property " + productItemAttributes.getId().getProductAttrId() + " added successfully ");

		} catch (Exception e) {
			productItemAttributes.setStatus("ERROR");
			hhDaoImpl.updateProductItemAttributes(productItemAttributes);
			System.out.println("Exception while adding property " + productItemAttributes.getId().getProductAttrId()
					+ " " + e.getCause());
		}
	}

	protected void addProductProperty(String attributeFqn, String attributeValue, final Product product, int tenantId,
			int siteId) {
		try {
			ProductProperty productProperty = new ProductProperty();
			productProperty.setAttributeFQN(attributeFqn);

			ProductPropertyValue productPropertyValue = new ProductPropertyValue();
			List<ProductPropertyValue> productPropertyValuesList = new ArrayList<ProductPropertyValue>();
			//productPropertyValueAttr.setValue("");

			ProductPropertyValueLocalizedContent content = new ProductPropertyValueLocalizedContent();
			content.setLocaleCode("en-US");
			content.setStringValue(attributeValue);
			productPropertyValue.setContent(content);

			productPropertyValuesList.add(productPropertyValue);
			productProperty.setValues(productPropertyValuesList);
			product.getProperties().add(productProperty);
			ProductPropertyResource productPropertyResource = new ProductPropertyResource(
					new MozuApiContext(tenantId, siteId));
			productPropertyResource.addProperty(productProperty, product.getProductCode());
			System.out.println("Property " + attributeFqn + " added successfully ");

		} catch (Exception e) {
			System.out.println("Exception while adding property " + attributeFqn + " " );
			e.printStackTrace();
		}
	}

	protected static boolean isProductPropertyExist(List<ProductProperty> productProperties, String produtFqn) {
		boolean productPropertyExist = false;
		if (productProperties != null && productProperties.size() != 0) {
			for (ProductProperty pp : productProperties) {
				if (pp.getAttributeFQN().equalsIgnoreCase(produtFqn)) {
					productPropertyExist = true;
					break;
				}
			}
		}
		return productPropertyExist;
	}

	protected static boolean isStatusInitial(String itemAttributeStatus) {
		boolean isStatusInitial = false;
		if (!isEmpty(itemAttributeStatus) && itemAttributeStatus.equalsIgnoreCase("INITIAL")) {
			isStatusInitial = true;
		}
		return isStatusInitial;
	}

	protected static boolean isEmpty(String str) {
		if (str == null || str.length() == 0) {
			return true;
		} else {
			return false;
		}
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
