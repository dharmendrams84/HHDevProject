package com.homehardware.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hardware.constants.Constants;
import com.homehardware.beans.Store;
import com.homehardware.dao.HhDaoImpl;
import com.homehardware.exception.AddUpdateProductException;
import com.homehardware.model.Brand;
import com.homehardware.model.EcoFees;
import com.homehardware.model.ExtDesc;
import com.homehardware.model.Gtin;
import com.homehardware.model.Item;
import com.homehardware.model.ItemAffiliated;
import com.homehardware.model.ItemLoc;
import com.homehardware.model.ItemRestricted;
import com.homehardware.model.ProductAttribute;
import com.homehardware.model.ProductItemAttributes;
import com.homehardware.model.RetailMsrp;
import com.homehardware.model.TransformFromHhLocationToKiboLocation;
import com.homehardware.utility.ProductUtility;

import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.productadmin.ProductPrice;
import com.mozu.api.contracts.core.Address;
import com.mozu.api.contracts.core.Measurement;
import com.mozu.api.contracts.location.Coordinates;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.location.LocationType;
import com.mozu.api.contracts.location.ShippingOriginContact;

import com.mozu.api.contracts.productadmin.Attribute;
import com.mozu.api.contracts.productadmin.AttributeLocalizedContent;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductInCatalogInfo;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;

import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import com.mozu.api.resources.commerce.admin.LocationResource;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.AttributeResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;

import com.mozu.api.resources.platform.entitylists.EntityResource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {
// "file:src/main/webapp/WEB-INF/spring/homehardware/applicationContext.xml" })
@ContextConfiguration(locations = { 
		"file:src/main/webapp/WEB-INF/spring/homehardware/applicationContext.xml" 
		})

public final class JUnitTest {

	

	private static final String TENANT = "tenant~";
	private static final int master_catalog_id = 2;
	private static final String TEXT_BOX = "TextBox";
	private static final String STRING = "String";
	private static final String ADMIN_ENTERED = "AdminEntered";
	public static final int _7 = 7;

	public JUnitTest() {

	}

	@Autowired
	private HhDaoImpl hhDaoImpl;

	// @Test
	public final void testFetchHhStoreObjectFromDb()
			throws Exception {
		hhDaoImpl.getStore();

	}

	//@Test
	
	/**.
	 *. @throws Exception.
	 */
	public final void testInsertOrUpdateLocationObjectIntoKibo() throws Exception {
		final LocationResource locationResource 
		    = new LocationResource(
				new MozuApiContext(Constants.tenantId, Constants.siteId));
		final Store store = hhDaoImpl.getStore();
		final String locationCode = hhDaoImpl.getStore().getStoreNumber();
		Location location = locationResource.getLocation(locationCode);

		//store.setStoreName(locationCode);
		if (location == null) {
			location = new Location();
			// convertStoreTOMozuLocation(location, store);
			TransformFromHhLocationToKiboLocation
			.testTransformHhLocationToKiboLocation(location, store);
			locationResource.addLocation(location);
		} else {
			TransformFromHhLocationToKiboLocation
			.testTransformHhLocationToKiboLocation(location, store);
			// convertStoreTOMozuLocation(location, store);
			locationResource.updateLocation(location, locationCode);
		}

	}

	/**
	 * @param location.
	 * @param store.
	 */
	public final void convertStoreToMozuLocation(
			final Location location, final Store store) {
		setLocationDefaultValues(location, store);
		setAddress(location);
		setCoordinates(location);
		setShippingOriginContact(location);
		final List<LocationType> list = new ArrayList<LocationType>();
		final LocationType locationType = new LocationType();
		locationType.setCode("WH1");
		locationType.setName("WareHouse");
		list.add(locationType);
		location.setLocationTypes(list);

	}

	/**
	 * @param location.
	 */
	public void setAddress(final Location location) {

		final Address address = new Address();
		address.setAddress1("Clarke & Clarke Home Hardware Building Centre");
		address.setCountryCode("CA");
		address.setStateOrProvince("NL");
		address.setCityOrTown("Bell Island");
		address.setPostalOrZipCode("A0A 4H0");
		address.setAddressType("Commercial");
		address.setIsValidated(false);
		location.setAddress(address);

	}
	
	/**
	 * @param location.
	 */
	public final void setCoordinates(
			final Location location) {
		final Coordinates coordinates = new Coordinates();
		coordinates.setLat(0.0);
		coordinates.setLng(0.0);
		location.setGeo(coordinates);

		location.setDescription("description");

	}
	
	
	/**
	 * @param location.
	 */
	public void setShippingOriginContact(final Location location) {
		final ShippingOriginContact shippingOriginContact = new ShippingOriginContact();
		shippingOriginContact.setFirstName("Alex");
		shippingOriginContact.setLastNameOrSurname("Graham");
		shippingOriginContact.setCompanyOrOrganization("HH");
		shippingOriginContact.setEmail("ag@hh.ca");
		shippingOriginContact.setPhoneNumber("1111111111");

		location.setShippingOriginContact(shippingOriginContact);
	}
	
	/**
	 * @param location.
	 * @param store.
	 */
	public void setLocationDefaultValues(final Location location,final Store store) {
		location.setPhone("3333333334");
		location.setName("Home Hardware Central Warehouse1");
		location.setAllowFulfillmentWithNoStock(false);
		location.setCode(store.getStoreName());
		location.setIsDisabled(false);
	}
	
	//@Test
	public void testFetcgHhProductFromDb() throws Exception {
		try {

		/*	final EntityManager entitymanager = hhDaoImpl.entityManagerFactory.createEntityManager();
			entitymanager.createQuery("");*/
			// Item item = hhDaoImpl.getItem();
			final ProductResource productResource = new ProductResource(
					new MozuApiContext(Constants.tenantId, Constants.siteId));

			final List list = hhDaoImpl.getItemsList("1", "INITIAL");
			for (Object o : list) {
				final Item item = (Item) o;
				Product product 
				    = productResource.getProduct(item.getId().getItem());

				if (product == null) {
					product = new Product();
					/*product.setProductTypeId(_7);

					convertHhItemToMozuProduct(item, product);
					productResource.addProduct(product);*/
					createNewProduct(product, productResource, item);

				} else {
					//product.getInventoryInfo();
					/*convertHhItemToMozuProduct(item, product);
					productResource
					.updateProduct(product, product.getProductCode());*/
					updateProduct(product, productResource, item);
					// setProductAttributes(product);
				}
			}
		} catch (AddUpdateProductException e) {
			
			System.out.println(e);
		}
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
		
		product.setProductTypeId(_7);

		convertHhItemToMozuProduct(item, product);
		productResource.addProduct(product);
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
	}
	
	/**.
	 * 
	 */
	@Test
	public final void testFetchAttributes() {
		final String attributeName = "sample";
		final String attributeFqn =  TENANT + attributeName;
		try {
			final ApiContext apiContext =
					new MozuApiContext(Constants.tenantId, Constants.siteId);
			final AttributeResource attributeResource = 
					new AttributeResource(apiContext);
			com.mozu.api.contracts.productadmin.Attribute attribute = 
					new com.mozu.api.contracts.productadmin.Attribute();
			
			
			attribute = attributeResource.getAttribute(attributeFqn);
			
			if (attribute == null) {

		
				attribute = new Attribute();
				createNewAttribute(attributeName, attribute);
				final Attribute newAttribute = 
						attributeResource.addAttribute(attribute);
				/*if (newAttribute!=null) {
					System.out.println("Attribute
					 "+attributeFqn + " created successfully!!!! ");
				}*/
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception while adding attribute " + attributeFqn);
			
		}
	}
	
	/**
	 * @param attributeName.
	 * @param attribute.
	 */
	public void createNewAttribute(final String attributeName,
			final Attribute attribute) {
		
		setAttributeValues(attributeName, attribute);
		final AttributeLocalizedContent	attributeLocalizedContent
		    = new AttributeLocalizedContent();
		attributeLocalizedContent.setLocaleCode("en-US");
		attributeLocalizedContent.setName(attributeName);
		final List<com.mozu.api.contracts.productadmin.AttributeLocalizedContent> list
		    = new ArrayList<>();
		list.add(attributeLocalizedContent);
		attribute.setLocalizedContent(list);
		
	}

	/**
	 * @param attributeName.
	 * @param attribute.
	 */
	public void setAttributeValues(final String attributeName,
			final Attribute attribute) {
		attribute.setAdminName(attributeName);
		attribute.setInputType(TEXT_BOX);
		attribute.setDataType(STRING);
		attribute.setMasterCatalogId(master_catalog_id);
		attribute.setValueType(ADMIN_ENTERED);
		attribute.setIsExtra(false);
		attribute.setIsOption(false);
		attribute.setIsProperty(true);
		attribute.setAttributeCode(attributeName);
		
	}
	
	// @Test
	/**.
	 * 
	 */
	public final void testFetcgHhProductAttributesFromDb() {
		try {
			final Item item = hhDaoImpl.getItem("", "", "");
			final ProductResource productResource 
			    = new ProductResource(
			    		new MozuApiContext(Constants.tenantId, Constants.siteId));
			final Product product = productResource.getProduct(item.getId().getItem());

			if (product == null) {
				System.out.println("Product " 
						+ item.getId().getItem() + " is not existing ");
				
			} else {

				setProductAttributes(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Test
	/**.
	 *. @throws Exception
	 */
	public final void testFetchHhEcoFeesFromDb() throws Exception {
		/*final Date d1 = new java.util.Date(); 
		System.out.println(d1);*/
		final List<EcoFees> ecoFeesList = hhDaoImpl.getEcoFes();
		final int tenantId = Constants.tenantId;
		final int siteId = Constants.siteId;
		final MozuApiContext context = new MozuApiContext();
		context.setTenantId(tenantId);
		context.setSiteId(siteId);
		for (EcoFees e : ecoFeesList) {
			final JsonNode ec =
					getEntity(context,
							tenantId, siteId, e.getItem(), e.getProv());

			if (ec == null) {
				insertEntity(context, tenantId,
						siteId, e.getItem(), e.getProv(),
						new Double(e.getFeeAmt()).toString());
			} else {
		  /*	updateEntity(context, tenantId, siteId, e.getItem(), 
			 * e.getProv(), new Double(e.getFeeAmt()).toString(),
						null);*/
				/*System.out.println("updating entity with productCode "
						+ e.getItem() + " province : "
						+ e.getProv());*/
			}
		}
		/*final Date d2 = new java.util.Date(); 
		System.out.println(d2);*/
	}

	//@Test
	/**.
	 *. @throws Exception
	 */
	public final void testFetchHhProductDetails() throws Exception {
		/*final GlobalItem globalItem = new GlobalItem();
		final String itemId = "4466-443";*/
		final String batchId = "1";
		final String status = "INITIAL";
		/*Item item = hhDaoImpl.getItem(itemId, batchId,status);
		globalItem.setItem(item);*/
		hhDaoImpl.getItemsList(batchId, status);
		
		
	}
	
	/**
	 * @param context.
	 * @param tenantId.
	 * @param siteId.
	 * @param productCode.
	 * @param province.
	 * @return.
	 */
	public final JsonNode getEntity(
			final MozuApiContext context,
			final int tenantId, final int siteId, 
			final String productCode, final String province) {
		
		JsonNode jsonNode = null;
		try {
			
			final EntityResource entityResource = new EntityResource(context);
			/*String filterString = "(productCode eq '";
			filterString = filterString + productCode + 
			"') and (province eq '" + province + "')";

			System.out.println(filterString);*/

			//entityCollection = entityResource.getEntities(
			//Constants.FULL_PATH_NAME, null, null, filterString, null, null);
			jsonNode = entityResource.getEntity(
					Constants.FULL_PATH_NAME,
					createIdForEntityList(productCode, province));
			
		} catch (Exception e) {
			System.out.println("Exception while getting Entity");
			e.printStackTrace();
		}
		return jsonNode;
	}

	/*public void fetchEntityListFromDB() {
		MozuApiContext context = new MozuApiContext();
		context.setTenantId(Constants.tenantId);
		context.setSiteId(Constants.siteId);
		
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
			// JsonNode jsonNode = entityResource.getEntity(EHF_TEMPLATE + "@" +
			// "HomeH", "b87fb7634b6247abb82101f16996fbbd");
			// System.out.println(jsonNode.toString());
			// JsonNode jsonNode2 = jsonNode.get("productCode");
			// ObjectNode objectNode = (ObjectNode)jsonNode;
			// objectNode.put("productCode", "10003");
			String productCode = "10003", province = "ON";
			String filterString = "(productCode eq '";
			filterString = filterString + 
			productCode + "') and (province eq '" + province + "')";

			System.out.println(filterString);
			// EntityCollection entityCollection =
			// entityResource.getEntities(fullName, null, null, "(productCode eq
			// '10003') and (province eq 'ON')", null, null);
			EntityCollection entityCollection =
			 entityResource.getEntities(fullName, null, null, filterString, null,
					null);
			// (productCode eq ”10003”) and (province eq ”ON”)

			List<JsonNode> list = entityCollection.getItems();
			EntityContainerResource entityContainerResource = 
			new EntityContainerResource(context);
			entityContainerResource.getEntityContainer(Constants.FULL_PATH_NAME, "");
			System.out.println(list);
			
			 * entityResource.updateEntity(jsonNode,EHF_TEMPLATE + "@" +
			 * "HomeH", "b87fb7634b6247abb82101f16996fbbd"); JsonNode jsonNode3
			 * = entityResource.getEntity(EHF_TEMPLATE + "@" + "HomeH",
			 * "b87fb7634b6247abb82101f16996fbbd");
			 * System.out.println("updated JSON "+jsonNode3.toString());
			 
		} catch (Exception e) {
			System.out.println("Error processing Entity for : "
			 + fitmentNode.get(ITEM));
			e.printStackTrace();
		}

	}*/

	// @Test
	/**
	 * @param context.
	 * @param tenantId.
	 * @param siteId.
	 * @param productCode.
	 * @param province.
	 * @param feeAmount.
	 * @.throws Exception
	 */
	public void insertEntity(final MozuApiContext context,final int tenantId,
			final int siteId, final String productCode, 
			final String province, final String feeAmount)
			throws Exception {
		
		final EntityResource entityResource = new EntityResource(context);
		final ObjectMapper mapper = new ObjectMapper();
		final ObjectNode fitmentNode = mapper.getNodeFactory().objectNode();
		/*fitmentNode.put(Constants.ID,  createIdForEntityList(productCode,province));
		fitmentNode.put(Constants.PRODUCT_CODE, productCode);
		fitmentNode.put(Constants.PROVINCE, province);
		fitmentNode.put(Constants.FEE_AMT, feeAmount);*/
		setObjectNodeValues(fitmentNode, productCode, province, feeAmount);
		try {
			entityResource.insertEntity(fitmentNode, Constants.FULL_PATH_NAME);
			//System.out.println("Entity with product code : "
			//+ productCode+ " and province :  "+province+ " added successfully!!!!!");
		} catch (Exception e) {
			//System.out.println("Error while adding Entity for 
			//product code : " + productCode+ " and province : "+province);
			e.printStackTrace();
		}
	}

	/**
	 * @param fitmentNode.
	 * @param productCode.
	 * @param province.
	 * @param feeAmount.
	 */
	public void setObjectNodeValues(final ObjectNode fitmentNode,final String productCode, 
			final String province, final String feeAmount) {
		fitmentNode.put(Constants.ID,  createIdForEntityList(productCode,province));
		fitmentNode.put(Constants.PRODUCT_CODE, productCode);
		fitmentNode.put(Constants.PROVINCE, province);
		fitmentNode.put(Constants.FEE_AMT, feeAmount);
	}
	
	/**
	 * @param context.
	 * @param tenantId.
	 * @param siteId.
	 * @param productCode.
	 * @param province.
	 * @param feeAmount.
	 * @param fitmentNode1.
	 */
	public final void updateEntity(final MozuApiContext context,
			final int tenantId, final int siteId,
			final String productCode, final String province, final String feeAmount,
			final ObjectNode fitmentNode1) {
			
		final EntityResource entityResource = new EntityResource(context);
		try {
			
			final ObjectMapper mapper = new ObjectMapper();
			final ObjectNode fitmentNode = mapper.getNodeFactory().objectNode();
		
			final String id = createIdForEntityList(productCode, province); 
			fitmentNode.put(Constants.ID, id);
			fitmentNode.put(Constants.PRODUCT_CODE, productCode);
			fitmentNode.put(Constants.PROVINCE,province);
			fitmentNode.put(Constants.FEE_AMT, feeAmount);
			entityResource.updateEntity(fitmentNode, Constants.FULL_PATH_NAME, id);
			System.out.println("Enity with product code : "
			        + productCode + " and province : " 
					+ province + " updated successfully!!!!! ");
		} catch (Exception e) {
			System.out.println(
					"Error while updating Entity for productCode : "
			        + productCode + " and province : " + province);
			e.printStackTrace();
		}
	}

	//@Test
	public final void testFetchExtendedDescriptionFromDB() {
		try {
			final String productCode = "4466-444";
			final List<ExtDesc> extDescs = hhDaoImpl.geExtendedDescription(productCode);
			final ProductResource productResource = 
					new ProductResource(
							new MozuApiContext(
									Constants.tenantId,
									Constants.siteId));
			final Product product = productResource.getProduct(productCode);
			final int tenantId =
					Constants.tenantId;
			final int siteId = Constants.siteId;
			if (product != null) {
				if (extDescs != null && extDescs.size() != 0) {
					for (ExtDesc e : extDescs) {
						final String type = e.getType();
						final String description = e.getDescription();
						if (!isEmpty(description) 
								&& description.trim().length() != 0){
							if (!isEmpty(type) 
									&&	type.equalsIgnoreCase(
											Constants.MKTG)) {
								if (isProductPropertyExist(
										product.getProperties(),
										Constants.tenant_marketing_description)) {
									updateProductproperty(Constants.tenant_marketing_description, e.getDescription(),
											product, tenantId, siteId);
								} else {
									addProductProperty(Constants.tenant_marketing_description, e.getDescription(),
											product, tenantId, siteId);
								}
							} else if (!isEmpty(type) && type.equalsIgnoreCase(Constants.INGR)) {
								if (isProductPropertyExist(product.getProperties(), Constants.TENANT_INGREDIENTS)) {
									updateProductproperty(Constants.TENANT_INGREDIENTS, e.getDescription(), product,
											tenantId, siteId);
								} else {
									addProductProperty(Constants.TENANT_INGREDIENTS, e.getDescription(), product,
											tenantId, siteId);
								}
							} else if (!isEmpty(type) && type.equalsIgnoreCase(Constants.FB)) {
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

	protected final void setProductAttributes(final Product product) {
		final List<ProductAttribute> productAttributes = hhDaoImpl.getProductAttributesList();
		List<ProductProperty> productProperties = product.getProperties();
		if (productProperties != null && productProperties.size() != 0) {
			for (ProductAttribute p : productAttributes) {
				final ProductItemAttributes productItemAttributes = hhDaoImpl.getProductItemAttribute(p.getProductAttrId(),
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
				final ProductItemAttributes productItemAttributes = hhDaoImpl.getProductItemAttribute(p.getProductAttrId(),
						product.getProductCode());
				if (productItemAttributes != null && isStatusInitial(productItemAttributes.getStatus())) {
					addProductProperty(productItemAttributes, product);
				}
			}
			product.setProperties(productProperties);
		}
	}

	
	public static void convertItemToMozuProduct(final Item item, final Product product) {
		product.setProductCode(item.getId().getItem());
		final ProductLocalizedContent productLocalizedContent = new ProductLocalizedContent();
		productLocalizedContent.setLocaleCode("en-US");
		// productLocalizedContent.setProductName("29 Espresso Adelaide Swivel
		// Stool3");
		productLocalizedContent.setProductName(item.getItemDesc());
		product.setContent(productLocalizedContent);
		// product.setProductTypeId(7);

		product.setProductUsage("Standard");
		product.setMasterCatalogId(2);

		// product.getPr

		final ProductPrice price = new ProductPrice();
		price.setPrice(209.99);
		price.setIsoCurrencyCode("CAD");
		price.setSalePrice(209.99);
		product.setPrice(price);
		final List list = new ArrayList<ProductInCatalogInfo>();
		final ProductInCatalogInfo productInCatalogInfo = new ProductInCatalogInfo();
		productInCatalogInfo.setCatalogId(2);

		productInCatalogInfo.setPrice(price);

		productInCatalogInfo.setIsActive(true);
		// productInCatalogInfo.setCatalogId(2);

		list.add(productInCatalogInfo);
		product.setProductInCatalogs(list);

		final Measurement measurement = new Measurement();
		measurement.setUnit("lbs");
		measurement.setValue(new Double(2));
		product.setPackageWeight(measurement);

		product.setBaseProductCode(item.getHhCtrlBrandInd());

		product.setIsValidForProductType(false);

	}

	
	/**
	 * @param item.
	 * @param product.
	 */
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
		/*product.setProductCode(item.getId().getItem());
		final ProductLocalizedContent productLocalizedContent 
		    = new ProductLocalizedContent();
		productLocalizedContent.setLocaleCode("en-US");
		productLocalizedContent.setProductName(item.getItemDesc());
		product.setContent(productLocalizedContent);
		

		product.setProductUsage("Standard");
		product.setMasterCatalogId(2);
		final List list = new ArrayList<ProductInCatalogInfo>();
		final ProductInCatalogInfo productInCatalogInfo = new ProductInCatalogInfo();
		productInCatalogInfo.setCatalogId(2);

		productInCatalogInfo.setIsActive(true);
		

		list.add(productInCatalogInfo);
		product.setProductInCatalogs(list);

		final Measurement measurement = new Measurement();
		measurement.setUnit("lbs");
		measurement.setValue(new Double(2));
		product.setPackageWeight(measurement);*/
		
		/*final List<Brand> brandsList = item.getBrand();
		for (Brand b : brandsList) {
			ProductUtility.testTransformationFromHhBrandToKiboBrand(product, b);
		}*/
		
		/*if (item.getExtDesc() != null && item.getExtDesc().size() != 0) {

			for (ExtDesc e : item.getExtDesc()) {
				ProductUtility
				.testTransformationFromHhExtendedDescToKiboExtendedDesc(product, e);
			}
		}*/
		
		 
		/*if (item.getGtin() != null && item.getGtin().size() != 0) {
			for (Gtin g : item.getGtin()) {

				ProductUtility.transformHhProductGtinToKiboProductGtin(product, g);
			}
		}*/
		
		/*if (item.getItemLoc() != null && item.getItemLoc().size() != 0) {
			final ItemLoc itemLoc
			    = item.getItemLoc().get(0);
			ProductUtility
			.testTransformationFromHhItemLocToKiboItemLoc(product, itemLoc);
		}*/
		
		
		
		/*if (item.getItemRestricted() != null && item.getItemRestricted().size() != 0) {
			for (ItemRestricted i : item.getItemRestricted()) {
				ProductUtility
				.testTransformFromHhItemRestrictedToKiboItemRestricted(product, i);
			}
		}*/
       
		
		/*if (item.getRetailMsrp() != null && item.getRetailMsrp().size() != 0) {
			for (RetailMsrp r : item.getRetailMsrp()) {
				ProductUtility.testTransformationFromHhPriceToKiboPrice(product, r);
			}
		}*/
		
	              	/*for (ProductItemAttributes p : item.getProductItemAttributes()) {
			if (ProductUtility.isPropertyExists(
			product.getProperties(), p.getId().getProductAttrId())) {
				for (ProductProperty pp : product.getProperties()) {
					if (pp.getAttributeFQN().
					equalsIgnoreCase(p.getId().getProductAttrId())) {
						ProductUtility.
(pp, p.getAttributeValue());
					}
				}

			} else {
				ProductUtility
				.addProductProperty(product,
				 p.getAttributeValue(), p.getId().getProductAttrId());
			}
		}*/
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
			ProductUtility.transformHhBrandToKiboBrand(product, b);
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
				.transformFromHhItemRestrictedToKiboItemRestricted(product, i);
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
				ProductUtility.transformationFromHhPriceToKiboPrice(product, r);
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
	
	
	
	
	protected final void convertProductAttribute(
			final String attrFqnId, final ProductItemAttributes productItemAttributes,
			final Product product) {
		try {
			final List<ProductProperty> productProperties = product.getProperties();
			if (productProperties != null && productProperties.size() != 0) {
				for (ProductProperty p : productProperties) {
					if (p.getAttributeFQN().equalsIgnoreCase(attrFqnId)) {
						final ProductPropertyValue productPropertyValue = p.getValues().get(0);
						if (productPropertyValue instanceof ProductPropertyValue
								&& productPropertyValue.getContent() != null) {

							productPropertyValue.getContent().setStringValue(productItemAttributes.getAttributeValue());
						}
						productPropertyValue.setValue(productItemAttributes.getAttributeValue());
						final ProductPropertyResource productPropertyResource 
						= new ProductPropertyResource(
								new MozuApiContext(Constants.tenantId, Constants.siteId));
						productPropertyResource.updateProperty(p, product.getProductCode(), p.getAttributeFQN());
					}
				}
			}
			;
			productItemAttributes.setStatus("SUCCESS");
			hhDaoImpl.updateProductItemAttributes(productItemAttributes);
			System.out
					.println("Property " +
			productItemAttributes.getId().getProductAttrId() + " updated successfully ");
		} catch (Exception e) {

			productItemAttributes.setStatus("ERROR");
			hhDaoImpl.updateProductItemAttributes(productItemAttributes);
			System.out.println("Exception while updating property " + productItemAttributes.getId().getProductAttrId()
					+ " " + e.getCause());
		}

	}

	protected final void updateProductproperty(final String attributeFqn, final String attributeValue, final Product product,
			final int tenantId, final int siteId) {
		try {
			final List<ProductProperty> productProperties = product.getProperties();
			if (productProperties != null && productProperties.size() != 0) {
				for (ProductProperty p : productProperties) {
					if (p.getAttributeFQN().equalsIgnoreCase(attributeFqn)) {
						final ProductPropertyValue productPropertyValue = p.getValues().get(0);
						if (productPropertyValue instanceof ProductPropertyValue
								&& productPropertyValue.getContent() != null) {

							productPropertyValue.getContent().setStringValue(attributeValue);
						}
						// productPropertyValue.setValue(attributeValue);
						final ProductPropertyResource productPropertyResource = new ProductPropertyResource(
								new MozuApiContext(tenantId, siteId));
						productPropertyResource.updateProperty(p, product.getProductCode(), p.getAttributeFQN());
					}
				}
			}
			System.out.println(" property " + attributeFqn + " updated successfully");
		} catch (Exception e) {

			System.out.println("Exception while updating property " + attributeFqn + " ");
			e.printStackTrace();
		}

	}

	protected final void addProductProperty(final ProductItemAttributes productItemAttributes, final Product product) {
		try {
			final ProductProperty productProperty = new ProductProperty();
			productProperty.setAttributeFQN(productItemAttributes.getId().getProductAttrId());

			final ProductPropertyValue productPropertyValueAttr = new ProductPropertyValue();
			final List<ProductPropertyValue> productPropertyValue = new ArrayList<ProductPropertyValue>();
			productPropertyValueAttr.setValue(productItemAttributes.getAttributeValue());

			final ProductPropertyValueLocalizedContent content = new ProductPropertyValueLocalizedContent();
			content.setLocaleCode("En-US");
			content.setStringValue(productItemAttributes.getAttributeValue());
			productPropertyValueAttr.setContent(content);

			productPropertyValue.add(productPropertyValueAttr);
			productProperty.setValues(productPropertyValue);
			product.getProperties().add(productProperty);
			final ProductPropertyResource productPropertyResource = new ProductPropertyResource(
					new MozuApiContext(Constants.tenantId, Constants.siteId));
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

	protected final void addProductProperty(final String attributeFqn, final String attributeValue, final Product product, final int tenantId,
			final int siteId) {
		try {
			final ProductProperty productProperty = new ProductProperty();
			productProperty.setAttributeFQN(attributeFqn);

			final ProductPropertyValue productPropertyValue = new ProductPropertyValue();
			final List<ProductPropertyValue> productPropertyValuesList = new ArrayList<ProductPropertyValue>();
			
			final ProductPropertyValueLocalizedContent content = new ProductPropertyValueLocalizedContent();
			content.setLocaleCode("en-US");
			content.setStringValue(attributeValue);
			productPropertyValue.setContent(content);

			productPropertyValuesList.add(productPropertyValue);
			productProperty.setValues(productPropertyValuesList);
			product.getProperties().add(productProperty);
			final ProductPropertyResource productPropertyResource = new ProductPropertyResource(
					new MozuApiContext(tenantId, siteId));
			productPropertyResource.addProperty(productProperty, product.getProductCode());
			System.out.println("Property " + attributeFqn + " added successfully ");

		} catch (Exception e) {
			System.out.println("Exception while adding property " + attributeFqn + " ");
			e.printStackTrace();
		}
	}

	protected static boolean isProductPropertyExist(final List<ProductProperty> productProperties, final String produtFqn) {
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

	protected static boolean isStatusInitial(final String itemAttributeStatus) {
		boolean isStatusInitial = false;
		if (!isEmpty(itemAttributeStatus) && itemAttributeStatus.equalsIgnoreCase("INITIAL")) {
			isStatusInitial = true;
		}
		return isStatusInitial;
	}

	protected static boolean isEmpty(final String str) {
		if (str == null || str.length() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public final String createIdForEntityList( String productCode, String province){
		productCode = productCode.toLowerCase();
		productCode = productCode.replaceAll("\\W", ""); 
		province = province.toLowerCase();
		province = province.replaceAll("\\W", "-"); 
		return productCode+"-"+province;
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
