package com.homehardware.test;

import com.hardware.constants.Constants;
import com.hh.integration.constants.Constant;
import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.dao.HhBrandDao;
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
import com.homehardware.model.Brand;
import com.homehardware.model.ExtDesc;
import com.homehardware.model.Gtin;
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
import com.homehardware.processor.HhDynAttributeProcessor;
import com.homehardware.processor.HhExtDescProcessor;
import com.homehardware.processor.HhGtinProcessor;
import com.homehardware.processor.HhImagesProcessor;
import com.homehardware.processor.HhItemLocProcessor;
import com.homehardware.processor.HhItemProcessor;
import com.homehardware.processor.HhItemRestrictedProcessor;
import com.homehardware.processor.HhProdItemAttributeProcessor;
import com.homehardware.processor.HhProductBrandProcesser;
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
import com.mozu.api.contracts.productadmin.AttributeVocabularyValueLocalizedContent;
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
import com.mozu.api.contracts.productadmin.ProductType;
import com.mozu.api.resources.commerce.catalog.admin.PriceListResource;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.mozu.api.resources.commerce.catalog.admin.pricelists.PriceListEntryResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductOptionResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;*/

import org.apache.log4j.Logger;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.AttributeResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.ProductTypeResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/webapp/WEB-INF/spring/homehardware/spring-context.xml" })
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
	
	//@Test
	public void testFetchHhVarProductFromDb() throws Exception {
		try {
			
			final ApiContext apiContext
				= new MozuApiContext(Constants.tenantId,
						Constants.siteId);
			final ProductResource productResource = new ProductResource(apiContext);
			//final Product product = productResource.getProduct("4466-446");
			final String batchId ="1";
			final String status = Status.INITIAL.toString();
			final List<Item> list = hhDaoObjectImpl
					.getItemsList("1", Status.INITIAL.toString());
			final ProductUtility productUtility = new ProductUtility();
			if (!list.isEmpty()) {
				for (Item item : list) {
					final String productCode = item.getId().getItem();
					Product product 
						= productResource.getProduct(productCode);
					
						if (product == null) {
						logger.info("Going to add new product with product code "+productCode);
						product = new Product();
						product.setProductTypeId(Constant.int_5);
						productUtility.convertHhItemToMozuProduct(
								list.get(0), product);
						product.setProductUsage("Configurable");

						product.setProductCode(productCode);
						product
							= productResource.addProduct(product);
						logger.info("Product with product code "+productCode+ " added successfully!!!!");
						List images = hhItemImagesDao.getItemImages(batchId, status, productCode);
						hhImagesProcessor.transformHhImageToKiboImage(product, images, productResource, apiContext);
						productResource.updateProduct(product, productCode);
						addOrUpdateProductOption(productCode, apiContext);
						
					}else{
						logger.info("Before updating product!!!!");
						/*List images = hhItemImagesDao.getItemImages(batchId, status, productCode);
						hhImagesProcessor.transformHhImageToKiboImage(product, images, productResource, apiContext);
						productResource.updateProduct(product, productCode);*/
						//addOrUpdateProductOption(productCode, apiContext);
						
						final List dynAttrTypes 
							= hhDynamicAttributesDao
							.getDynamicAttributesType(batchId, status);
						
						List<ProductAttr> productAttributes = hhItemAttributesDao.getProdAttributes(batchId, status);
						List<ProductItemAttr> productItemAttributes = hhItemAttributesDao.getProdItemAttributes(batchId, status, productCode);
																
						hhProdItemAttributeProcessor.
							transformProdItemAttributes(
									productItemAttributes,
									productAttributes, 
									dynAttrTypes, apiContext);
						
						/*List<ProductItemAttributes> productItemAttributes = hhItemAttributesDao.getItemAttributes(batchId, status, productCode);
						HhProdItemAttributeProcessor.transformProductItemAttributes(productItemAttributes, apiContext);*/

						final List<ItemAffiliated> itemAffiliateds = hhItemAffiliatedDao.getItemAffiliated(batchId,
								status, productCode);

						if (!itemAffiliateds.isEmpty()) {
							hhAffiliatedItemProcessor.setProductAffiliatedItems(itemAffiliateds, apiContext,
							HhProductAttributeFqnConstants.Hh_Product_CrossSell_Attr_Fqn, productCode);
							logger.info("Product crossell transformed successfully!!!!");
						}

						List gtins = hhGtinDao.getGtin(batchId, status, productCode);
						
						Gtin gtin = (Gtin)gtins.get(0);
												
						String attributeFQN = "tenant~gtin" ;
						hhGtinProcessor.addOrUpdateGtin(gtin, apiContext,attributeFQN);
						
						final List<Brand> brandsList = hhBrandDao.getBrands(batchId, status, productCode);
						if (!brandsList.isEmpty()) {
							hhProductBrandProcesser.setProductBrandList(brandsList, apiContext);
							
						}
						
						//update product
						product = productResource.getProduct(productCode);
						List extDescs = hhExtDescDao.getExtDesc(batchId, status, productCode);
						hhExtDescProcessor.setProductExtDesc(extDescs, product);
						productResource.updateProduct(product, productCode);
						logger.info("Product "+productCode+ " extended description details processed successfully!!!!");
						
						product = productResource.getProduct(productCode);
						//update product 
						List itemLocs = hhItemLocDao.getItemLocs(batchId, status, productCode);
						hhItemLocProcessor.setProductItemLocation(product, itemLocs);
						productResource.updateProduct(product, productCode);
						logger.info("Product "+productCode+ " item location details processed successfully!!!!");
						
						List<ItemRestricted> itemRestricteds = hhItemRestrictedDao.getItemRestricted(batchId, status, productCode);
						hhItemRestrictedProcessor.transformHhItemRestricted(itemRestricteds, apiContext);
						logger.info("Product "+productCode+ " item restricted details processed successfully!!!!");
						
						
						List itemDynAttrs = hhDynamicAttributesDao.getDynamicAttributes(batchId, status, productCode);
						List dynAttrInfos = hhDynamicAttributesDao.getDynamicAttributesInfo(batchId, status);
						
						hhDynAttributeProcessor.transformHhDynamicAttributes(itemDynAttrs, dynAttrInfos, dynAttrTypes, apiContext,Constant.PRODUCT_TYPE);
					}
				}

			} else {
				logger.info("No Objects for batch id " + 1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	@Test
	public void testFetchHhProductFromDb() {
		
		try {
			/*final EntityManager entityManager = 
			    entityManagerFactory.createEntityManager();
*/
			
			
			final ApiContext apiContext
			    = new MozuApiContext(Constants.tenantId,
					Constants.siteId);
			
			
			
			final String batchId = "1";
			final String status = ProductUtilityOld.Status.INITIAL.toString();
			final List list = hhDaoObjectImpl.getItemsList(
					batchId, ProductUtilityOld.Status.INITIAL.toString());
			final ProductUtility productUtility = new ProductUtility();
			if (list != null && list.size() != 0) {
				for (Object o : list) {
					final Item item = (Item) o;
					final String productCode = item.getId().getItem();
					final ProductPropertyResource productPropertyResource
						= new ProductPropertyResource(apiContext);
					final List<ProductProperty> listproperty 
						= productPropertyResource.
							getProperties(productCode);
					
					
					//productPropertyResource.updateProperty(productProperty, productCode, attributeFQN);
					Product product = hhItemProcessor.
							addOrUpdateProduct(item, apiContext);
					
					if (product.getProperties() == null) {
						product.setProperties(
								new ArrayList<ProductProperty>());
					}
					final ProductResource productResource 
						= new ProductResource(apiContext);
					/*
					Product product = productResource
							.getProduct(productCode);
					if (product == null) {
						product = new Product();
						productUtility
						.createNewProduct(product, productResource, item);
					}
					if (product.getProperties() == null) {
						product.setProperties(
								new ArrayList<ProductProperty>());
					}*/
					
					//micro service
					List<ProductItemAttributes> productItemAttributes = hhItemAttributesDao.getItemAttributes(batchId, status, productCode);
					//productUtility.setProductItemAttributes(productItemAttributes, product);
					HhProdItemAttributeProcessor.transformProductItemAttributes(productItemAttributes, apiContext);
					
					final List<ItemAffiliated> itemAffiliateds 
						= hhItemAffiliatedDao.getItemAffiliated(batchId,
								status, productCode);
					
					if (!itemAffiliateds.isEmpty()) {
						hhAffiliatedItemProcessor.setProductAffiliatedItems(itemAffiliateds, apiContext,
								HhProductAttributeFqnConstants.Hh_Product_CrossSell_Attr_Fqn, productCode);

					}
					
					
					List itemDynAttrs = hhDynamicAttributesDao.getDynamicAttributes(batchId, status, productCode);
					List dynAttrInfos = hhDynamicAttributesDao.getDynamicAttributesInfo(batchId, status);
					final List dynAttrTypes = hhDynamicAttributesDao.getDynamicAttributesType(batchId, status);
					hhDynAttributeProcessor.transformHhDynamicAttributes(itemDynAttrs, dynAttrInfos, dynAttrTypes, apiContext,Constant.PRODUCT_TYPE);

					
					List gtins = hhGtinDao.getGtin(batchId, status, productCode);
					
					Gtin gtin = (Gtin)gtins.get(0);
					//productUtility.setProductGtin(gtins, product);
					
					String attributeFQN = "tenant~gtin" ;
					hhGtinProcessor.addOrUpdateGtin(gtin, apiContext,attributeFQN);
					
					
					
					
					
					
					//micro
					final List<Brand> brandsList = hhBrandDao.getBrands(batchId, status, productCode);
					if (!brandsList.isEmpty()) {
						//productUtility.setProductBrandList(brands, product);
						hhProductBrandProcesser.setProductBrandList(brandsList, apiContext);
					}
					
					
					
				
					
					/*final List<Retail> retailList 
						=  hhRetailDao
						.getRetailForItem(batchId, status,productCode);
					for(Retail retail : retailList ){
						productCode = retail.getItem();
						product = productResource.getProduct(productCode);
						if(product!=null){
						productUtility.setItemRetailPrice(product, retail);
						}
					}*/
					
					//update product
					/*final List<ItemAffiliated> itemAffiliateds 
					= hhItemAffiliatedDao.getItemAffiliated(batchId, status, productCode);
					
					if(!itemAffiliateds.isEmpty()){
						for(ItemAffiliated itemAffiliated :itemAffiliateds){
							productUtility.setProductAffiliatedItems(product, itemAffiliateds);
						}
					}*/
					
					
					product = productResource.getProduct(productCode);
					//update product
					List extDescs = hhExtDescDao.getExtDesc(batchId, status, productCode);
					//hhExtDescProcessor.set
					hhExtDescProcessor.setProductExtDesc(extDescs, product);
					productResource.updateProduct(product, productCode);
					
					product = productResource.getProduct(productCode);
					//update product 
					List itemLocs = hhItemLocDao.getItemLocs(batchId, status, productCode);
					hhItemLocProcessor.setProductItemLocation(product, itemLocs);
					productResource.updateProduct(product, productCode);
					//to check
					/*List itemRestricteds = hhItemRestrictedDao.getItemRestricted(batchId, status, productCode);
					productUtility.setProductItemRestricted(itemRestricteds, product);*/
					//nothing to do
					/*List retails = hhRetailDao.getRetailForItem(batchId, status,productCode);
					productUtility.setProductRetailMsrp(retails, product);
					*/
					product = productResource.getProduct(productCode);
					//update product
					List images = hhItemImagesDao.getItemImages(batchId, status, productCode);
					//productUtility.transformHhImageToKiboImage(product, images, productResource, apiContext);
					hhImagesProcessor.transformHhImageToKiboImage(product, images, productResource, apiContext);
					
					/*List itemDynAttrs = hhDynamicAttributesDao.getDynamicAttributes(batchId, status, productCode);
					List dynAttrInfos = hhDynamicAttributesDao.getDynamicAttributesInfo(batchId, status);
					final List dynAttrTypes = hhDynamicAttributesDao.getDynamicAttributesType(batchId, status);*/
					//productUtility.transformHhDynamicAttributes(product, itemDynAttrs,dynAttrInfos,dynAttrTypes, apiContext);
					productResource.updateProduct(product, productCode);
					logger.info("Product with product code "+productCode+" added or updated successfully!!!!");
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	//@Test
	public void testFetchDynamicAttribute() {
		
		try {
				
			final ApiContext apiContext
			    = new MozuApiContext(Constants.tenantId,
					Constants.siteId);
			AttributeResource attributeResource = new AttributeResource(apiContext);
		  Attribute attribute1 =	attributeResource.getAttribute("tenant~iswarrexchitem");
		  Attribute attribute2 =	attributeResource.getAttribute("tenant~islumberitem");
			Attribute attribute = new Attribute();
			createNewAttribute("attribute_29", attribute);
			
			attribute = attributeResource.addAttribute(attribute);
		   logger.info("After adding attribute "+attribute);
		   final ProductTypeResource productTypeResource
		    = new ProductTypeResource(apiContext);
		final ProductType productType 
		    = productTypeResource.getProductType(Constant.PRODUCT_TYPE);
		final AttributeInProductType attrInProdType
		    = new AttributeInProductType();
		createAttributeInProductType(attribute, attrInProdType);
		addAttributeInProductType(
				attrInProdType, productType, productTypeResource);
		logger.info("Dynamic attribute "
				+  " added successfully " 
				+ " And Linked to product type "
				+ Constant.PRODUCT_TYPE);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param attribute.
	 * @param attrInProdType.
	 */
	public static void createAttributeInProductType(
			final Attribute attribute,final AttributeInProductType attrInProdType) {
		
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
	public static void addAttributeInProductType(
			final AttributeInProductType attrInProdType,
			final ProductType productType,
			final ProductTypeResource productTypeResource) {
		try {
			final List<AttributeInProductType> 
			    propertiesList = productType.getProperties();
			propertiesList.add(attrInProdType);
			productType.setProperties(propertiesList);
			productTypeResource.updateProductType(productType, 2);
		} catch (Exception e) {
			logger.error("Exception while updating product type "
					+ productType + " error message "	+ e.getMessage());
		}
	}

	
	public static void createNewAttribute(final String attributeName,
			 final Attribute attribute) {
		
		setAttributeValues(attributeName, attribute);
		final AttributeLocalizedContent	attributeLocalizedContent
		    = new AttributeLocalizedContent();
		attributeLocalizedContent.setLocaleCode(Constant.LOCALE);
		attributeLocalizedContent.setName(attributeName);
		final List<com.mozu.api.contracts.productadmin.AttributeLocalizedContent> list
		    = new ArrayList<>();
		list.add(attributeLocalizedContent);
		attribute.setLocalizedContent(list);
		
	}
	
	
	public static void setAttributeValues(final String attributeName,
			final Attribute attribute) {
		attribute.setAdminName(attributeName);
		/*attribute.setInputType("YesNo");
		attribute.setDataType("Bool");*/
		/*attribute.setInputType("List");
		attribute.setDataType("String");
		attribute.setValueType("Predefined");*/
		
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
	public void createNewProduct(
			final Product product,final 
			ProductResource productResource,
			final Item item) throws Exception {
		
		product.setProductTypeId(Constant.int_7);

	//	convertHhItemToMozuProduct(item, product);
	       /* final Product newProduct
	            = productResource.addProduct(product);*/
		/*if (newProduct != null) {
			logger.info(
					"New Product with product code " + newProduct.getProductCode() + " created successfully!!!!!");
		}*/
	}
		
	public ProductOptionValue createProductOptionValue(){
		ProductOptionValue productOptionValue = new ProductOptionValue();
		AttributeVocabularyValue attributeVocabularyValue = new AttributeVocabularyValue();
		AttributeVocabularyValueLocalizedContent attributeValueLocalizedContent = new AttributeVocabularyValueLocalizedContent();
		attributeValueLocalizedContent.setLocaleCode(Constant.LOCALE);
		attributeValueLocalizedContent.setStringValue("Bronze");;
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
	public void addOrUpdateProductOption(
			final String productCode,
			final ApiContext apiContext) {
		try {
			final ProductOptionResource productOptionResource 
				= new ProductOptionResource(apiContext);
			
			final String attributeFqN = TENANT_FINISH;
			/*productOptionResource.deleteOption(productCode, attributeFqN);*/
			ProductOption productOption 
				= productOptionResource.getOption(productCode, attributeFqN);
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
			final ProductOption productOptionUpdated 
				= productOptionResource.updateOption(productOption, productCode,
					attributeFqN);
			logger.info("After updating productOption !!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
