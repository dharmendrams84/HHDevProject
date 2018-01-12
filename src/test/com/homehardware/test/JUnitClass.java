package com.homehardware.test;

import com.hardware.constants.Constants;
import com.hh.integration.constants.Constant;
import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.dao.HhBrandDao;
import com.homehardware.dao.HhCategoryDao;
import com.homehardware.dao.HhDaoObject;
import com.homehardware.dao.HhDynamicAttributesDao;
import com.homehardware.dao.HhExtDescDao;
import com.homehardware.dao.HhGtinDao;
import com.homehardware.dao.HhItemAffiliatedDao;
import com.homehardware.dao.HhItemAttributesDao;
import com.homehardware.dao.HhItemImagesDao;
import com.homehardware.dao.HhItemLocDao;
import com.homehardware.dao.HhItemRestrictedDao;
import com.homehardware.dao.HhRetailDao;
import com.homehardware.dao.HhRetailDaoImpl;
import com.homehardware.model.AttrDefinition;
import com.homehardware.model.Brand;
import com.homehardware.model.ExtDesc;
import com.homehardware.model.Gtin;
import com.homehardware.model.Hierarchy;
import com.homehardware.model.Images;
import com.homehardware.model.Item;
import com.homehardware.model.ItemAffiliated;
import com.homehardware.model.ItemLoc;
import com.homehardware.model.ItemRestricted;
import com.homehardware.model.ProductAttr;
import com.homehardware.model.ProductItemAttr;
import com.homehardware.model.ProductItemAttributes;
import com.homehardware.model.Promotion;
import com.homehardware.model.Retail;
import com.homehardware.model.RetailMsrp;
import com.homehardware.processor.HhAffiliatedItemProcessor;
import com.homehardware.processor.HhCategoryProcessor;
import com.homehardware.processor.HhDynAttributeProcessor;
import com.homehardware.processor.HhExtDescProcessor;
import com.homehardware.processor.HhGtinProcessor;
import com.homehardware.processor.HhImagesProcessor;
import com.homehardware.processor.HhItemLocProcessor;
import com.homehardware.processor.HhItemProcessor;
import com.homehardware.processor.HhItemRestrictedProcessor;
import com.homehardware.processor.HhProdItemAttributeProcessor;
import com.homehardware.processor.HhProductBrandProcesser;
import com.homehardware.utility.AttributeUtility;
import com.homehardware.utility.PriceListUtility;
import com.homehardware.utility.ProductUtility;
import com.homehardware.utility.ProductUtilityOld;
import com.homehardware.utility.ProductUtilityOld.Status;
import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.core.Measurement;
import com.mozu.api.contracts.core.extensible.AttributeValueLocalizedContent;
import com.mozu.api.contracts.productadmin.Attribute;
import com.mozu.api.contracts.productadmin.AttributeInProductType;
import com.mozu.api.contracts.productadmin.AttributeLocalizedContent;
import com.mozu.api.contracts.productadmin.AttributeVocabularyValue;
import com.mozu.api.contracts.productadmin.AttributeVocabularyValueInProductType;
import com.mozu.api.contracts.productadmin.AttributeVocabularyValueLocalizedContent;
import com.mozu.api.contracts.productadmin.Category;
import com.mozu.api.contracts.productadmin.CategoryLocalizedContent;
import com.mozu.api.contracts.productadmin.CategoryLocalizedImage;
import com.mozu.api.contracts.productadmin.CategoryPagedCollection;
import com.mozu.api.contracts.productadmin.PriceList;
import com.mozu.api.contracts.productadmin.PriceListCollection;
import com.mozu.api.contracts.productadmin.PriceListEntry;
import com.mozu.api.contracts.productadmin.PriceListEntryCollection;
import com.mozu.api.contracts.productadmin.PriceListEntryPrice;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductInCatalogInfo;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductOption;
import com.mozu.api.contracts.productadmin.ProductOptionValue;
import com.mozu.api.contracts.productadmin.ProductPrice;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductType;
import com.mozu.api.contracts.productadmin.ProductVariationOption;
import com.mozu.api.contracts.productruntime.ProductOptionSelection;
import com.mozu.api.contracts.sitesettings.order.VocabularyValue;
import com.mozu.api.resources.commerce.catalog.admin.CategoryResource;
import com.mozu.api.resources.commerce.catalog.admin.PriceListResource;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.mozu.api.resources.commerce.catalog.admin.pricelists.PriceListEntryResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductOptionResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductVariationResource;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;*/

import org.apache.log4j.Logger;
import org.apache.tools.ant.types.resources.comparators.Date;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.AttributeResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.ProductTypeResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.attributes.AttributeVocabularyValueResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/homehardware/spring-context.xml" })
public final class JUnitClass {

	public static final String TENANT_FINISH = "tenant~finish";

	protected static final Logger logger = Logger.getLogger(JUnitClass.class);

	@Autowired
	private HhDaoObject hhDaoObjectImpl;

	@Autowired
	private HhRetailDao hhRetailDao;

	@Autowired
	private HhItemAffiliatedDao hhItemAffiliatedDao;

	@Autowired
	private HhBrandDao hhBrandDao;

	@Autowired
	private HhExtDescDao hhExtDescDao;

	@Autowired
	private HhGtinDao hhGtinDao;

	@Autowired
	private HhItemLocDao hhItemLocDao;

	@Autowired
	private HhItemRestrictedDao hhItemRestrictedDao;

	@Autowired
	private HhItemAttributesDao hhItemAttributesDao;

	@Autowired
	private HhItemImagesDao hhItemImagesDao;

	@Autowired
	private HhDynamicAttributesDao hhDynamicAttributesDao;

	@Autowired
	private HhGtinProcessor hhGtinProcessor;

	@Autowired
	private HhProductBrandProcesser hhProductBrandProcesser;

	@Autowired
	private HhProdItemAttributeProcessor HhProdItemAttributeProcessor;

	@Autowired
	private HhDynAttributeProcessor hhDynAttributeProcessor;

	@Autowired
	private HhAffiliatedItemProcessor hhAffiliatedItemProcessor;

	@Autowired
	private HhImagesProcessor hhImagesProcessor;

	@Autowired
	private HhItemLocProcessor hhItemLocProcessor;

	@Autowired
	private HhExtDescProcessor hhExtDescProcessor;

	@Autowired
	private HhItemProcessor hhItemProcessor;

	@Autowired
	private HhItemRestrictedProcessor hhItemRestrictedProcessor;

	@Autowired
	private HhProdItemAttributeProcessor hhProdItemAttributeProcessor;
	
	
	@Autowired
	private HhCategoryDao hhCategoryDao;

	//@Test
	public void testFetchHhVarProductFromDb() throws Exception {
		try {

			final ApiContext apiContext = new MozuApiContext(Constants.tenantId, Constants.siteId);

			/*
			 * String attributeFQN = Constant.TENANT+"finish";
			 * ProductOptionResource productOptionResource = new
			 * ProductOptionResource(apiContext); ProductOption productOption =
			 * productOptionResource.getOption("4466-447",
			 * Constant.TENANT+"finish"); ProductOptionSelection
			 * productOptionSelection = new ProductOptionSelection();
			 * 
			 * AttributeVocabularyValueResource attrVocResource = new
			 * AttributeVocabularyValueResource(apiContext);
			 * AttributeVocabularyValue attributeVocabularyValue =
			 * attrVocResource.getAttributeVocabularyValue(Constant.TENANT+
			 * "finish", "Gold");
			 * attributeVocabularyValue.setProductName("44664448");
			 * //attrVocResource.updateAttributeVocabularyValue(
			 * attributeVocabularyValue, attributeFQN,
			 * attributeVocabularyValue.getValue().toString());
			 * 
			 * 
			 * ProductVariationResource productVariationResource = new
			 * ProductVariationResource(apiContext);
			 * productVariationResource.getProductVariation("4466-447",
			 * Constant.TENANT+"finish");
			 * 
			 */

			logger.info("After getting ProductOption!!!!!!");
			final ProductResource productResource = new ProductResource(apiContext);
			// final Product product = productResource.getProduct("4466-446");
			final String batchId = "1";
			final String status = Status.INITIAL.toString();
			final List<Item> list = hhDaoObjectImpl.getItemsList("1", Status.INITIAL.toString());
			final ProductUtility productUtility = new ProductUtility();
			final int productTypeCode = Constant.int_5;
			if (!list.isEmpty()) {
				for (Item item : list) {
					if (item.getProductClass() == 1731) {
						final String productCode = item.getId().getItem();
						Product product = productResource.getProduct(productCode);

						if (product == null) {
							logger.info("Going to add new product with product code " + productCode);
							product = new Product();
							product.setProductTypeId(productTypeCode);
							productUtility.convertHhItemToMozuProduct(list.get(0), product);
							product.setProductUsage("Configurable");

							product.setProductCode(productCode);
							product = productResource.addProduct(product);
							logger.info("Product with product code " + productCode + " added successfully!!!!");
							List images = hhItemImagesDao.getItemImages(batchId, status, productCode);
							hhImagesProcessor.transformHhImageToKiboImage(product, images, productResource, apiContext);
							productResource.updateProduct(product, productCode);

							final List dynAttrTypes = hhDynamicAttributesDao.getDynamicAttributesType(batchId, status);

							List<ProductAttr> productAttributes = hhItemAttributesDao.getProdAttributes(batchId,
									status);
							List<ProductItemAttr> productItemAttributes = hhItemAttributesDao
									.getProdItemAttributes(batchId, status, productCode);

							hhProdItemAttributeProcessor.transformProdItemAttributes(productItemAttributes,
									productAttributes, dynAttrTypes, apiContext, productTypeCode);

						} else {
							logger.info("Before updating product!!!!");
							/*
							 * List images =
							 * hhItemImagesDao.getItemImages(batchId, status,
							 * productCode);
							 * hhImagesProcessor.transformHhImageToKiboImage(
							 * product, images, productResource, apiContext);
							 * productResource.updateProduct(product,
							 * productCode);
							 */
							// addOrUpdateProductOption(productCode,
							// apiContext);

							final List dynAttrTypes = hhDynamicAttributesDao.getDynamicAttributesType(batchId, status);

							List<ProductAttr> productAttributes = hhItemAttributesDao.getProdAttributes(batchId,
									status);
							List<ProductItemAttr> productItemAttributes = hhItemAttributesDao
									.getProdItemAttributes(batchId, status, productCode);

							hhProdItemAttributeProcessor.transformProdItemAttributes(productItemAttributes,
									productAttributes, dynAttrTypes, apiContext, productTypeCode);

						}
					}
				}

			} else {
				logger.info("No Objects for batch id " + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//@Test
	public void testFetchHhProductFromDb() {

		try {
			/*
			 * final EntityManager entityManager =
			 * entityManagerFactory.createEntityManager();
			 */

			final ApiContext apiContext = new MozuApiContext(Constants.tenantId, Constants.siteId);

			final String batchId = "1";
			final String status = ProductUtilityOld.Status.INITIAL.toString();
			final List list = hhDaoObjectImpl.getItemsList(batchId, ProductUtilityOld.Status.INITIAL.toString());
			final ProductUtility productUtility = new ProductUtility();
			if (list != null && list.size() != 0) {
				for (Object o : list) {
					final Item item = (Item) o;
					if (item.getProductClass() == 1730) {
						final String productCode = item.getId().getItem();
						final ProductPropertyResource productPropertyResource = new ProductPropertyResource(apiContext);
						final List<ProductProperty> listproperty = productPropertyResource.getProperties(productCode);

						// productPropertyResource.updateProperty(productProperty,
						// productCode, attributeFQN);
						Product product = hhItemProcessor.addOrUpdateProduct(item, apiContext);

						if (product.getProperties() == null) {
							product.setProperties(new ArrayList<ProductProperty>());
						}
						final ProductResource productResource = new ProductResource(apiContext);
						/*
						 * Product product = productResource
						 * .getProduct(productCode); if (product == null) {
						 * product = new Product(); productUtility
						 * .createNewProduct(product, productResource, item); }
						 * if (product.getProperties() == null) {
						 * product.setProperties( new
						 * ArrayList<ProductProperty>()); }
						 */

						List itemDynAttrs = hhDynamicAttributesDao.getDynamicAttributes(batchId, status, productCode);
						List dynAttrInfos = hhDynamicAttributesDao.getDynamicAttributesInfo(batchId, status);
						final List<AttrDefinition> dynAttrTypes = hhDynamicAttributesDao
								.getDynamicAttributesType(batchId, status);
						// hhDynAttributeProcessor.transformHhDynamicAttributes(itemDynAttrs,
						// dynAttrInfos, dynAttrTypes,
						// apiContext,Constant.PRODUCT_TYPE);
						List<ProductAttr> productAttributes = hhItemAttributesDao.getProdAttributes(batchId, status);
						// List<ProductItemAttr> productItemAttributes =
						// hhItemAttributesDao.getProdItemAttributes(batchId,
						// status, productCode);
						/*
						 * final List dynAttrTypes = hhDynamicAttributesDao
						 * .getDynamicAttributesType(batchId, status);
						 */
						List<ProductItemAttr> productItemAttr = hhItemAttributesDao.getProdItemAttributes(batchId,
								status, productCode);

						hhProdItemAttributeProcessor.transformProdItemAttributes(productItemAttr, productAttributes,
								dynAttrTypes, apiContext, Constant.PRODUCT_TYPE);

						// micro service
						List<ProductItemAttributes> productItemAttributes = hhItemAttributesDao
								.getItemAttributes(batchId, status, productCode);
						// productUtility.setProductItemAttributes(productItemAttributes,
						// product);
						HhProdItemAttributeProcessor.transformProductItemAttributes(productItemAttributes, apiContext);

						final List<ItemAffiliated> itemAffiliateds = hhItemAffiliatedDao.getItemAffiliated(batchId,
								status, productCode);

						if (!itemAffiliateds.isEmpty()) {
							hhAffiliatedItemProcessor.setProductAffiliatedItems(itemAffiliateds, apiContext,
									HhProductAttributeFqnConstants.Hh_Product_CrossSell_Attr_Fqn, productCode);

						}

						List gtins = hhGtinDao.getGtin(batchId, status, productCode);

						Gtin gtin = (Gtin) gtins.get(0);
						// productUtility.setProductGtin(gtins, product);

						String attributeFQN = "tenant~gtin";
						hhGtinProcessor.addOrUpdateGtin(gtin, apiContext, attributeFQN);

						// micro
						final List<Brand> brandsList = hhBrandDao.getBrands(batchId, status, productCode);
						if (!brandsList.isEmpty()) {
							// productUtility.setProductBrandList(brands,
							// product);
							hhProductBrandProcesser.setProductBrandList(brandsList, apiContext);
						}

						/*
						 * final List<Retail> retailList = hhRetailDao
						 * .getRetailForItem(batchId, status,productCode);
						 * for(Retail retail : retailList ){ productCode =
						 * retail.getItem(); product =
						 * productResource.getProduct(productCode);
						 * if(product!=null){
						 * productUtility.setItemRetailPrice(product, retail); }
						 * }
						 */

						// update product
						/*
						 * final List<ItemAffiliated> itemAffiliateds =
						 * hhItemAffiliatedDao.getItemAffiliated(batchId,
						 * status, productCode);
						 * 
						 * if(!itemAffiliateds.isEmpty()){ for(ItemAffiliated
						 * itemAffiliated :itemAffiliateds){
						 * productUtility.setProductAffiliatedItems(product,
						 * itemAffiliateds); } }
						 */

						product = productResource.getProduct(productCode);
						// update product
						List extDescs = hhExtDescDao.getExtDesc(batchId, status, productCode);
						// hhExtDescProcessor.set
						hhExtDescProcessor.setProductExtDesc(extDescs, product);
						productResource.updateProduct(product, productCode);

						product = productResource.getProduct(productCode);
						// update product
						List itemLocs = hhItemLocDao.getItemLocs(batchId, status, productCode);
						hhItemLocProcessor.setProductItemLocation(product, itemLocs);
						productResource.updateProduct(product, productCode);
						// to check
						/*
						 * List itemRestricteds =
						 * hhItemRestrictedDao.getItemRestricted(batchId,
						 * status, productCode);
						 * productUtility.setProductItemRestricted(
						 * itemRestricteds, product);
						 */
						// nothing to do
						/*
						 * List retails = hhRetailDao.getRetailForItem(batchId,
						 * status,productCode);
						 * productUtility.setProductRetailMsrp(retails,
						 * product);
						 */
						product = productResource.getProduct(productCode);
						// update product
						List images = hhItemImagesDao.getItemImages(batchId, status, productCode);
						// productUtility.transformHhImageToKiboImage(product,
						// images, productResource, apiContext);
						hhImagesProcessor.transformHhImageToKiboImage(product, images, productResource, apiContext);

						/*
						 * List itemDynAttrs =
						 * hhDynamicAttributesDao.getDynamicAttributes(batchId,
						 * status, productCode); List dynAttrInfos =
						 * hhDynamicAttributesDao.getDynamicAttributesInfo(
						 * batchId, status); final List dynAttrTypes =
						 * hhDynamicAttributesDao.getDynamicAttributesType(
						 * batchId, status);
						 */
						// productUtility.transformHhDynamicAttributes(product,
						// itemDynAttrs,dynAttrInfos,dynAttrTypes, apiContext);
						productResource.updateProduct(product, productCode);
						logger.info("Product with product code " + productCode + " added or updated successfully!!!!");
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// @Test
	public void testFetchDynamicAttribute() {

		try {

			final ApiContext apiContext = new MozuApiContext(Constants.tenantId, Constants.siteId);
			AttributeResource attributeResource = new AttributeResource(apiContext);
			Attribute attribute1 = attributeResource.getAttribute("tenant~iswarrexchitem");
			Attribute attribute2 = attributeResource.getAttribute("tenant~islumberitem");
			Attribute attribute = new Attribute();
			createNewAttribute("attribute_29", attribute);

			attribute = attributeResource.addAttribute(attribute);
			logger.info("After adding attribute " + attribute);
			final ProductTypeResource productTypeResource = new ProductTypeResource(apiContext);
			final ProductType productType = productTypeResource.getProductType(Constant.PRODUCT_TYPE);
			final AttributeInProductType attrInProdType = new AttributeInProductType();
			createAttributeInProductType(attribute, attrInProdType);
			addAttributeInProductType(attrInProdType, productType, productTypeResource);
			logger.info("Dynamic attribute " + " added successfully " + " And Linked to product type "
					+ Constant.PRODUCT_TYPE);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param attribute.
	 * @param attrInProdType.
	 */
	public static void createAttributeInProductType(final Attribute attribute,
			final AttributeInProductType attrInProdType) {

		attrInProdType.setAttributeDetail(attribute);
		attrInProdType.setAttributeFQN(attribute.getAttributeFQN());
		attrInProdType.setIsAdminOnlyProperty(false);
		attrInProdType.setIsHiddenProperty(false);
		attrInProdType.setIsInheritedFromBaseType(false);
		attrInProdType.setIsMultiValueProperty(false);
		attrInProdType.setIsProductDetailsOnlyProperty(true);
		attrInProdType.setIsRequiredByAdmin(false);
		attrInProdType.setOrder(0);

	}

	/**
	 * @param attrInProdType.
	 * @param productType.
	 * @param productTypeResource.
	 */
	public static void addAttributeInProductType(final AttributeInProductType attrInProdType,
			final ProductType productType, final ProductTypeResource productTypeResource) {
		try {
			final List<AttributeInProductType> propertiesList = productType.getProperties();
			propertiesList.add(attrInProdType);
			productType.setProperties(propertiesList);
			productTypeResource.updateProductType(productType, 2);
		} catch (Exception e) {
			logger.error("Exception while updating product type " + productType + " error message " + e.getMessage());
		}
	}

	public static void createNewAttribute(final String attributeName, final Attribute attribute) {

		setAttributeValues(attributeName, attribute);
		final AttributeLocalizedContent attributeLocalizedContent = new AttributeLocalizedContent();
		attributeLocalizedContent.setLocaleCode(Constant.LOCALE);
		attributeLocalizedContent.setName(attributeName);
		final List<com.mozu.api.contracts.productadmin.AttributeLocalizedContent> list = new ArrayList<>();
		list.add(attributeLocalizedContent);
		attribute.setLocalizedContent(list);

	}

	public static void setAttributeValues(final String attributeName, final Attribute attribute) {
		attribute.setAdminName(attributeName);
		/*
		 * attribute.setInputType("YesNo"); attribute.setDataType("Bool");
		 */
		/*
		 * attribute.setInputType("List"); attribute.setDataType("String");
		 * attribute.setValueType("Predefined");
		 */

		attribute.setMasterCatalogId(Constant.master_catalog_id);
		attribute.setValueType(Constant.ADMIN_ENTERED);
		attribute.setIsExtra(false);
		attribute.setIsOption(false);
		attribute.setIsProperty(true);
		attribute.setAttributeCode(attributeName);

	}

	public void setAttributeType(final Attribute attribute, String type) {
		if (type.equalsIgnoreCase("YesNo")) {
			attribute.setInputType("YesNo");
			attribute.setDataType("Bool");
		} else if (type.equalsIgnoreCase("TextArea")) {
			attribute.setInputType("TextArea");
			attribute.setDataType("String");
		} else if (type.equalsIgnoreCase("List")) {
			attribute.setInputType("List");
			attribute.setDataType("String");
			attribute.setValueType("Predefined");
		}
	}

	/**
	 * @param product.
	 * @param productResource.
	 * @param item.
	 * 
	 */
	public void createNewProduct(final Product product, final ProductResource productResource, final Item item)
			throws Exception {

		product.setProductTypeId(Constant.int_7);

		// convertHhItemToMozuProduct(item, product);
		/*
		 * final Product newProduct = productResource.addProduct(product);
		 */
		/*
		 * if (newProduct != null) { logger.info(
		 * "New Product with product code " + newProduct.getProductCode() +
		 * " created successfully!!!!!"); }
		 */
	}

	public ProductOptionValue createProductOptionValue() {
		ProductOptionValue productOptionValue = new ProductOptionValue();
		AttributeVocabularyValue attributeVocabularyValue = new AttributeVocabularyValue();
		AttributeVocabularyValueLocalizedContent attributeValueLocalizedContent = new AttributeVocabularyValueLocalizedContent();
		attributeValueLocalizedContent.setLocaleCode(Constant.LOCALE);
		attributeValueLocalizedContent.setStringValue("Bronze");
		;
		attributeVocabularyValue.setContent(attributeValueLocalizedContent);
		attributeVocabularyValue.setValue("Bronze");
		productOptionValue.setAttributeVocabularyValueDetail(attributeVocabularyValue);
		productOptionValue.setValue("Bronze");
		return productOptionValue;
	}

	/**
	 * @param productCode.
	 * @param apiContext.
	 */
	// @Test
	public void addOrUpdateProductOption() {
		try {

			String dateString = "10/01/2018";
			String[] strArray = dateString.split("/");

			final String productCode = "4466-447";
			final ApiContext apiContext = new MozuApiContext(Constants.tenantId, Constants.siteId);

			final AttributeResource attributeResource = new AttributeResource(apiContext);
			Attribute attribute1 = attributeResource.getAttribute("tenant~sampleattribute");
			logger.info("found attribute!!!!!");

			final int productTypeCode = Constant.int_5;
			Attribute attributeNew = new Attribute();
			attributeNew.setAdminName("textAttr");
			attributeNew.setAttributeCode("textAttr");
			attributeNew.setDataType("DateTime");
			attributeNew.setInputType("Date");

			// attribute.setDataType(Constant.STRING);
			attributeNew.setMasterCatalogId(Constant.master_catalog_id);
			attributeNew.setValueType(Constant.ADMIN_ENTERED);
			attributeNew.setIsExtra(false);
			attributeNew.setIsOption(false);
			attributeNew.setIsProperty(true);
			attributeResource.addAttribute(attributeNew);
			// attribute.setAttributeCode(attributeName);

			logger.info("Attribute Added successfullt !!!!");

			ProductPropertyResource productPropertyResource = new ProductPropertyResource(apiContext);
			ProductProperty productProperty = productPropertyResource.getProperty(productCode, TENANT_FINISH);
			String value = "Gold";
			/*
			 * final AttributeResource attributeResource = new
			 * AttributeResource(apiContext);
			 */
			final Attribute attribute = attributeResource.getAttribute(TENANT_FINISH);
			/*
			 * List<AttributeVocabularyValue> vocabularyValues =
			 * attribute.getVocabularyValues(); AttributeVocabularyValue
			 * attributeVocabularyValue = vocabularyValues.get(0);
			 * attributeVocabularyValue.setValueSequence(0);
			 * attributeVocabularyValue.getContent().setStringValue("Silver");
			 * attributeVocabularyValue.setValue("Silver");
			 * vocabularyValues.add(attributeVocabularyValue);
			 * attributeResource.updateAttribute(attribute, TENANT_FINISH);
			 * logger.info("After updating attribute");
			 */
			AttributeVocabularyValueResource attributeVocabularyValueResource = new AttributeVocabularyValueResource(
					apiContext);

			AttributeVocabularyValue attributeVocabularyValue = AttributeUtility.createAttributeVocabularyValue(value);
			AttributeVocabularyValue attributeVocabularyValueNew = attributeVocabularyValueResource
					.addAttributeVocabularyValue(attributeVocabularyValue, TENANT_FINISH);

			final ProductTypeResource productTypeResource = new ProductTypeResource(apiContext);
			final ProductType productType = productTypeResource.getProductType(productTypeCode);

			final AttributeInProductType attributeInProductType = productType.getOptions().get(0);
			attributeInProductType.getVocabularyValues().add(AttributeUtility.createAttrVocInProdType("Gold"));

			final ProductType productTypeupdated = productTypeResource.updateProductType(productType, productTypeCode);

			logger.info("After getting product property!!!!!  " + attributeVocabularyValueNew);

			final ProductOptionResource productOptionResource = new ProductOptionResource(apiContext);

			final String attributeFqN = TENANT_FINISH;
			/* productOptionResource.deleteOption(productCode, attributeFqN); */
			ProductOption productOption = productOptionResource.getOption(productCode, attributeFqN);
			if (productOption == null) {
				productOption = new ProductOption();
				productOption.setAttributeFQN(attributeFqN);
				productOption = productOptionResource.addOption(productOption, productCode);
				logger.info("After Adding productOption " + attributeFqN + " !!!!! ");
			}

			List<ProductOptionValue> values = productOption.getValues();
			if (values == null) {
				values = new ArrayList<ProductOptionValue>();
				productOption.setValues(values);
			}
			final ProductOptionValue productOptionValue = createProductOptionValue();
			values.add(productOptionValue);
			final ProductOption productOptionUpdated = productOptionResource.updateOption(productOption, productCode,
					attributeFqN);
			logger.info("After updating productOption !!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param productCode.
	 * @param apiContext.
	 */
	//@Test
	public void testFetcHhCategory() {
		try {
			
		    List<Hierarchy> list = hhCategoryDao.getHhCategory("1", "INITIAL");
		    
			final ApiContext apiContext 
				= new MozuApiContext(Constants.tenantId, Constants.siteId);
			new HhCategoryProcessor().transformHhHierarchyToKiboCategory(list, apiContext);
			} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFetchHhFrenchProductFromDb() {

		try {
			final ApiContext apiContext 
					= new MozuApiContext(Constants.tenantId, Constants.siteId);
			String batchId = "1";
			String status = "INITIAL";
			List<Item> listItems = hhDaoObjectImpl.getItemsList("1", "INITIAL");
			ProductResource productResource = new ProductResource(apiContext);
			for (Item item : listItems) {
				//hhItemProcessor.addOrUpdateProduct(item, apiContext);
				
				String productCode = item.getId().getItem();
				Product product = productResource.getProduct(productCode);
				List<Images> images = hhItemImagesDao.getItemImages(batchId, status, productCode);
				hhImagesProcessor.transformHhImageToKiboImage(product, images, productResource, apiContext);
				productResource.updateProduct(product, productCode);
			}
			
			/*final ProductResource productResource
				= new ProductResource(apiContext);
			final String productCode =  "4466448";
			Product product = productResource.getProduct(productCode);
			List<Item> listItems = hhDaoObjectImpl.getItemsList("1", "INITIAL");
			ProductInCatalogInfo productInCatalogInfo =
					hhItemProcessor.convertProdCatalogInfo(listItems.get(0), null);
			//ProductInCatalogInfo productInCatalogInfo =	product.getProductInCatalogs().get(1);
			
			Product product2 = productResource.getProduct("4466-447");
			product2.getProductInCatalogs().add(productInCatalogInfo);
			productInCatalogInfo.getPrice().setPrice(0.0);
			product.getProductInCatalogs().add(productInCatalogInfo);
			productResource.updateProduct(product, productCode);*/
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
