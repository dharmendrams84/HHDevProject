package com.homehardware.utility;

import com.hh.integration.constants.Constant;
import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.Brand;
import com.homehardware.model.DynAttrInfo;
import com.homehardware.model.AttrDefinition;
import com.homehardware.model.ExtDesc;
import com.homehardware.model.Gtin;
import com.homehardware.model.Images;
import com.homehardware.model.Item;
import com.homehardware.model.ItemAffiliated;
import com.homehardware.model.ItemDynAttr;
import com.homehardware.model.ItemLoc;
import com.homehardware.model.ItemRestricted;
import com.homehardware.model.ProductItemAttributes;
import com.homehardware.model.Retail;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.content.Document;
import com.mozu.api.contracts.content.DocumentCollection;
import com.mozu.api.contracts.core.Measurement;
import com.mozu.api.contracts.productadmin.Attribute;
import com.mozu.api.contracts.productadmin.AttributeInProductType;
import com.mozu.api.contracts.productadmin.AttributeLocalizedContent;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductInCatalogInfo;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductLocalizedImage;
import com.mozu.api.contracts.productadmin.ProductPrice;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductType;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.AttributeResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.ProductTypeResource;
import com.mozu.api.resources.content.documentlists.DocumentResource;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public final class ProductUtility {

	
	protected static final Logger logger = Logger.getLogger(ProductUtility.class);	
	

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
	public void convertHhItemToMozuProduct(
			final Item item, final Product product) {
		final String status = "INITIAL";
		if (product.getProperties() == null 
				|| product.getProperties().size() == 0) {
			final List<ProductProperty> productProperties = new ArrayList<>();
			product.setProperties(productProperties);
		}
		setProductBasicProperties(item, product);
		
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
	public final void setDefaultPackageWeight(
			final Product product) {
		final Measurement measurement = new Measurement();
		measurement.setValue(1.0);
		measurement.setUnit("LBS");
		product.setPackageWeight(measurement);
	}
	
	/**
	 * @param product.
	 * @param affiliatedItems.
	 */
	public void setProductAffiliatedItems(final Product product,
			final List<ItemAffiliated> affiliatedItems) {
		
		// TODO Auto-generated method stub
		final List<ProductProperty> productProperties = product.getProperties();
		// try {
		if (productProperties != null /*&& productProperties.size() != 0*/) {
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
	}

	/**
	 * @param updateProductProperties.
	 * @param product.
	 * @param affiliatedItems.
	 */
	public void updateAffiliatedItem(
			final ProductProperty updateProductProperties, final Product product,
			final List<ItemAffiliated> affiliatedItems) {
		// try {
		final List<ProductPropertyValue> productPropertyValueList = new ArrayList<>();
		for (ItemAffiliated a : affiliatedItems) {
			if (a.getItem().equals(product.getProductCode())) {
				productPropertyValueList.add(
						createProductProperty(a.getItemAffiliated()));
			}
		}
		updateProductProperties.setValues(productPropertyValueList);
		
	}

	/**
	 * @param product.
	 * @param productAttrFqn.
	 * @param affiliatedItems.
	 */
	public void addProductAffiliatedItem(final Product product,
			final String productAttrFqn, final List<ItemAffiliated> affiliatedItems) {
		// try {
		final ProductProperty productProperty = new ProductProperty();
		productProperty.setAttributeFQN(productAttrFqn);
		final List<ProductPropertyValue> productPropertyValueList = new ArrayList<>();
		for (ItemAffiliated a : affiliatedItems) {
			if (a.getItem().equals(product.getProductCode())) {
				productPropertyValueList
				.add(createProductProperty(a.getItemAffiliated()));
			}
		}
		productProperty.setValues(productPropertyValueList);
		setProductProperties(product, productProperty);
		
	}

	public void addListProperty(String value,String productAttrFqn){
		final List<ProductPropertyValue> productPropertyValueList = new ArrayList<>();
		productPropertyValueList.add(createProductProperty(value));
		final ProductProperty productProperty = new ProductProperty();
		productProperty.setAttributeFQN(productAttrFqn);
	}
	
	/**
	 * @param value.
	 * @.return
	 */
	public ProductPropertyValue createProductProperty(final String value) {
		final ProductPropertyValue productPropertyValue = new ProductPropertyValue();
		final ProductPropertyValueLocalizedContent content;
		content = new ProductPropertyValueLocalizedContent();
		content.setStringValue(value);
		productPropertyValue.setContent(content);
		return productPropertyValue;
	}
	
	/**
	 * @param product.
	 * @param productProperty.
	 */
	public static void setProductProperties(
			final Product product, final ProductProperty productProperty) {
		List<ProductProperty> productProperties = product.getProperties();
		if (productProperties == null || productProperties.size() == 0) {
			productProperties = new ArrayList<ProductProperty>();
			product.setProperties(productProperties);
		}
		productProperties.add(productProperty);
		product.setProperties(productProperties);
	}
	/**
	 * @param productProperties.
	 * @param attributeFqn.
	 * @return.
	 */
	
	public static boolean isPropertyExists(
			final List<ProductProperty> productProperties,
		final String attributeFqn) {
		boolean isExist = false;
		for (ProductProperty p : productProperties) {
			if (p.getAttributeFQN().equalsIgnoreCase(attributeFqn)) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	/**
	 * @param item.
	 * @param product.
	 */
	public void setProductBrandList(final List<Brand> brandsList, final Product product) {
		
		for (Brand b : brandsList) {
			transformHhBrandToKiboBrand(product, b);
		}
	}

	/**
	 * @param product.
	 * @param brand.
	 */
	public void transformHhBrandToKiboBrand(
			final Product product, final Brand brand) {

		// transform product brand description obj to kibo product brand
		// description
		addOrUpdateProperty(product,
				HhProductAttributeFqnConstants.Hh_Brand_Desc_Attr_Fqn,
				brand.getBrandDesc());

		// transform HH product brand code obj to kibo product brand code obj
		addOrUpdateProperty(product, HhProductAttributeFqnConstants
				.Hh_Brand_Code_Attr_Fqn, brand.getBrandCode());

		// transform product image id to kibo product image id
		/*setProductHomeExclusiveInd(product, brand);*/
		addOrUpdateProperty(product, HhProductAttributeFqnConstants
				.Hh_Home_Exclusive_Ind_Attr_Fqn,
				brand.getHomeExclusiveInd());

		

	}


	protected void setProductBrandDescription(final Product product, final Brand brand) {
		// TODO Auto-generated method stub

		addOrUpdateProperty(product,
				HhProductAttributeFqnConstants.Hh_Brand_Desc_Attr_Fqn,
				brand.getBrandDesc());

	}

	public void addOrUpdateProperty(
			final Product product,
			final String propertyName,final String propertyValue) {
		if (isPropertyExists(product.getProperties(), propertyName)) {
			for (ProductProperty pp : product.getProperties()) {
				if (pp.getAttributeFQN().equalsIgnoreCase(propertyName)) {
					updateProductPropertiesAttribute(pp, propertyValue);
				}
			}

		} else {
			addProductProperty(product, propertyValue, propertyName);
			
		}
	}
	
	/**
	 * @param productPropertiesAction.
	 * @param productPropertyAttrValue.
	 */
	public void updateProductPropertiesAttribute(
			final ProductProperty productPropertiesAction,		
			final String productPropertyAttrValue) {
		// TODO Auto-generated method stub

		final ProductPropertyValue productPropertyValue
		    = productPropertiesAction.getValues().get(0);
		if (productPropertiesAction.getValues() != null 
				&& productPropertiesAction.getValues().size() != 0
				&& productPropertyValue instanceof ProductPropertyValue 
		) {
			if (productPropertyValue.getContent() != null) {
				productPropertyValue.getContent()
				        .setStringValue(productPropertyAttrValue);
			} else {
				final ProductPropertyValueLocalizedContent 
				    content = new ProductPropertyValueLocalizedContent();
				content.setStringValue(productPropertyAttrValue);
				productPropertyValue.setContent(content);
			}

		} else {
			final List<ProductPropertyValue> productPropertyValuesList
				= new ArrayList<>();
			productPropertyValuesList
			.add(createProductProperty(productPropertyAttrValue));
			productPropertyValue.setValue(productPropertyValue);
		}
		
	}

	/**
	 * @param productPropertyValue.
	 * @param productPropertyValueAttr.
	 * @param productPropertyAttr.
	 */
	public  void addProductPropertyValue(
			final List<ProductPropertyValue> productPropertyValue,
			final ProductPropertyValue productPropertyValueAttr,
			final ProductProperty productPropertyAttr) {
		productPropertyValue.add(productPropertyValueAttr);
		productPropertyAttr.setValues(productPropertyValue);
	}
	
	/**
	 * @param product.
	 * @param attributeValue.
	 * @param attributeFqn.
	 */
	public final void addProductProperty(final Product product,
			final String attributeValue, final String attributeFqn) {
		try {
			final ProductProperty productProperty = new ProductProperty();
			productProperty.setAttributeFQN(attributeFqn);

			final ProductPropertyValue productPropertyValue 
			    = new ProductPropertyValue();
			final List<ProductPropertyValue> productPropertyValuesList 
		            = new ArrayList<ProductPropertyValue>();
			
			setPropertyValueContent(productPropertyValuesList,
					productPropertyValue, productProperty, attributeValue);
			setPropertiesList(product, productProperty, productPropertyValuesList);
			

		} catch (Exception e) {
			System.out.println("Exception while adding property " + attributeFqn + " ");
			e.printStackTrace();
		}
	}
	
	/**
	 * @param productPropertyValue.
	 * @param productProperty.
	 * @param attributeValue.
	 */
	public final void setPropertyValueContent(
			final List<ProductPropertyValue> productPropertyValuesList,
			final ProductPropertyValue productPropertyValue,
			final ProductProperty productProperty,
			final String attributeValue) {

		final ProductPropertyValueLocalizedContent content
		    = new ProductPropertyValueLocalizedContent();

		content.setStringValue(attributeValue);
		productPropertyValue.setContent(content);
		if (productProperty.getAttributeFQN()
				.equalsIgnoreCase(HhProductAttributeFqnConstants
						.Hh_Product_Class)) {
			productPropertyValue.setValue(attributeValue);
		}

		productPropertyValuesList.add(productPropertyValue);
		productProperty.setValues(productPropertyValuesList);
	}

	/**
	 * @param product.
	 * @param productProperty.
	 * @param productPropertyValuesList.
	 */
	public final void setPropertiesList(final Product product, 
			final ProductProperty productProperty,
			final List<ProductPropertyValue> productPropertyValuesList) {
		
		productProperty.setValues(productPropertyValuesList);
		List<ProductProperty> productPropertiesList = product.getProperties();
		if (productPropertiesList != null && productPropertiesList.size() != 0) {
			product.getProperties().add(productProperty);
		} else {
			productPropertiesList = new ArrayList<ProductProperty>();
			productPropertiesList.add(productProperty);
			product.setProperties(productPropertiesList);
		}
	}
	
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
			addOrUpdateProperty(product, propertyName, extendedDesc.getDescription());
		}
	}
	
	/**
	 * @param gtins.
	 * @param product.
	 */
	public void setProductGtin(
			final List<Gtin> gtins, final Product product) {

		for (Gtin g : gtins) {
			addOrUpdateProperty(product,
					HhProductAttributeFqnConstants.Hh_Gtin_Attr_Fqn,
					g.getGtin());
		}

	}
	
	
	/**
	 * @param product.
	 * @param itemLocs.
	 */
	public void setProductItemLocation(final Product product, final List<ItemLoc> itemLocs) {
		if (itemLocs != null && itemLocs.size() != 0) {
			for (ItemLoc itemLoc : itemLocs) {
				setProductMeasurment(product, itemLoc);
				addOrUpdateProperty(product,
						HhProductAttributeFqnConstants
						.Hh_VendorProductNumber_Attr_Fqn,
						itemLoc.getVpn());
			}
		}
	}
	
	/**
	 * @param product.
	 * @param itemLoc.
	 */
	public  void setProductMeasurment(final Product product, final ItemLoc itemLoc) {
		
		// TODO Auto-generated method stub
		if (product.getPackageWeight() != null
				&& product.getPackageWeight().getValue() != null 
				&& Double.SIZE != 0) {
			product.getPackageWeight().setValue(itemLoc.getWeight());
			
		} else {
			final Measurement measurement = new Measurement();
			measurement.setValue(itemLoc.getWeight());
			measurement.setUnit(itemLoc.getWeightUom());
			product.setPackageWeight(measurement);

		}

	}
	
	/**
	 * @param itemRestricteds.
	 * @param product.
	 */
	public void setProductItemRestricted(
			final List<ItemRestricted> itemRestricteds, final Product product) {
		if (itemRestricteds != null && itemRestricteds.size() != 0) {
			for (ItemRestricted itemRestricted : itemRestricteds) {
				addOrUpdateProperty(product,
						HhProductAttributeFqnConstants
						.Hh_Website_Ind_Attr_Fqn,
						itemRestricted.getWebsiteInd());
				addOrUpdateProperty(product,
						HhProductAttributeFqnConstants
						.Hh_Ecomm_Ind_Attr_Fqn,
						itemRestricted.getEcommerceInd());
			}
		}
	}
	
	/**
	 * @param retails.
	 * @param product.
	 */
	public void setProductRetailMsrp(
			final List<Retail> retails, final Product product) {
		if (retails != null && retails.size() != 0) {
			for (Retail r : retails) {
				setRetailPrice(product, r);
			}
		}
	}
	
	protected  void setRetailPrice(final Product product, final Retail retail) {
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
	 * @param attributes.
	 * @param product.
	 */
	public void setProductItemAttributes(
			final List<ProductItemAttributes> attributes,
			final Product product) {
		for (ProductItemAttributes p : attributes) {
			addOrUpdateProperty(product, 
					p.getId().getProductAttrId(),
					p.getAttributeValue());
		}
	}
	
	/**
	 * @param product.
	 * @param images.
	 * @param productResource.
	 * @param apiContext.
	 */
	public void transformHhImageToKiboImage(final Product product,
               final List<Images> images,
               final ProductResource productResource,final ApiContext apiContext) {
		final ProductLocalizedContent content = product.getContent();
		List<ProductLocalizedImage> productLocalizedImages = content.getProductImages();
		if (productLocalizedImages == null) {
			productLocalizedImages = new ArrayList<ProductLocalizedImage>();
			product.getContent().setProductImages(productLocalizedImages);
		}
		
		if (images != null && images.size() != 0) {
			for (Images img : images) {
				final File file = new File(
						Constant.IMAGES_FOLDER_LOC
				        + img.getImageId() + ".jpg");
				if (file.exists()) { 
					uploadImages(file, apiContext,
								img, productLocalizedImages);
			        } else {
			        	logger.info("No image exists with image id " + img);
			        }
			}
			content.setProductImages(productLocalizedImages);
		}
         
        }
	
	/**
	 * @param file.
	 * @param apiContext.
	 * @param img.
	 * @param productLocalizedImages.
	 */
	public void uploadImages(
			final File file, final ApiContext apiContext, final Images img,
			final List<ProductLocalizedImage> productLocalizedImages) {
	        final String cmsId = saveFileToFileManager(
					file, new Integer(img.getImageId())
					.toString(), apiContext);

                final ProductLocalizedImage productLocalizedImage
				    = new ProductLocalizedImage();
		productLocalizedImage.setLocaleCode(Constant.LOCALE);
		productLocalizedImage.setCmsId(cmsId);
		productLocalizedImages.add(productLocalizedImage);
	}

	/**
	 * @param file.
	 * @param imageName.
	 * @param context.
	 * @return.
	 */
	public static String saveFileToFileManager(
			final File file,final String imageName,final ApiContext context) {
		Document retDoc = null;
		try {
			
			final DocumentResource docResource = new DocumentResource(context);
			
			final Document doc = new Document();
			
			createDocumentObject(doc, imageName, file);
			
			final Document newDoc = createOrUpdateDocument(docResource, doc, imageName);
			
			retDoc = docResource.getDocument(Constant.FILES_MOZU, newDoc.getId());
			
			final FileInputStream fin = new FileInputStream(file);
			docResource.updateDocumentContent(
					fin, Constant.FILES_MOZU,
					retDoc.getId(), Constant.EXTENSION_JPG);
			
			
		} catch (Exception e) {
			logger.error("Exception while uploading image "
					+ imageName);
			
		}
		return retDoc.getId();
	}

	/**
	 * @param docResource.
	 * @param doc.
	 * @param imageName.
	 * @return.
	 */
	public static Document createOrUpdateDocument(
			final DocumentResource docResource, final Document doc,
			final String imageName) {
		Document newDoc = null ;
		try {
			final DocumentCollection existingDoc 
			    = docResource.getDocuments(
			    		Constant.FILES_MOZU, "name eq " + imageName, null,
					1, 0, Boolean.TRUE, null);

			//Document newDoc = null;
			if (existingDoc == null || existingDoc.getItems().isEmpty()) {
				newDoc = docResource.createDocument(doc, Constant.FILES_MOZU);
				logger.info("Creating Document with name " + imageName);
			} else {
				newDoc = existingDoc.getItems().get(0);
			}
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Error while creating or updating document " + e.getMessage());
		}
		return newDoc;
	}

	/**
	 * @param doc.
	 * @param imageName.
	 * @param file.
	 */
	public static void createDocumentObject(
			final Document doc,final String imageName,final File file) {
		doc.setContentMimeType(Constant.IMAGE_JPG);
		doc.setExtension(Constant.EXTENSION_JPG);
		doc.setName(imageName);
		doc.setDocumentTypeFQN(Constant.IMAGE_MOZU);
		if (file.exists()) {
			final long length = Long.valueOf(file.length());
			doc.setContentLength(length);
			logger.info("Content length is " + length);
		}

	}

	/**
	 * @param item.
	 * @param apiContext.
	 */
	public void transformHhDynamicAttributes(final Product product,
			final List<ItemDynAttr> itemDynAttrs,
			final List<DynAttrInfo> dynAttrInfos,
			final List<AttrDefinition> dynAttrTypes,
			final ApiContext apiContext)  throws Exception {
		try {
			/*final List<ItemDynAttr> itemDynAttrs = item.getItemDynAttr();*/
			if (itemDynAttrs != null && itemDynAttrs.size() != 0) {
				for (ItemDynAttr id : itemDynAttrs) {
					final AttributeResource attributeResource 
					    = new AttributeResource(apiContext);
					final String attributeName 
					    = Constant.ATTRIBUTE + "_" + id.getDynAttrId();
					String attributeType = getDynAttrType(attributeName, dynAttrTypes);
					createDynamicAttribute(apiContext, 
							attributeResource, attributeName,attributeType);
					if (attributeType != null && !attributeType.equalsIgnoreCase("YesNo")
							&& !attributeType.equalsIgnoreCase("List")) {
						addOrUpdateDynamicAttribute(dynAttrInfos, attributeName, product);
					}
					
					/*for (DynAttrInfo dyn : item.getDynAttrInfo()) {
						if (attributeName
								.equalsIgnoreCase(
										Constant.ATTRIBUTE 
										+ "_" 
										+ dyn.getDynAttrId()
										)) {
							addOrUpdateProperty(
									product,
									Constant
									.TENANT + attributeName,
									dyn.getDynAttrDesc());
							System.out.println(
									"Added or Updated"
									+ " dynamic attribute " 
									+ Constant
									.TENANT
									+ attributeName 
									+ " sucessfully!!!!");
						}
					}*/
				}
			}
		} catch (Exception e) {
			System.out.println("Exception while transoforming dynamic attribute "
					+ e.getMessage());
			throw e ;
		}
	}
	
	/**
	 * @param item.
	 * @param attributeName.
	 */
	public void addOrUpdateDynamicAttribute(
			final List<DynAttrInfo> dynAttrInfos,
			 final String attributeName,
			final Product product) {
		for (DynAttrInfo dyn : dynAttrInfos) {
			if (attributeName
					.equalsIgnoreCase(Constant.ATTRIBUTE 
							+ "_" 
							+ dyn.getDynAttrId())) {
				addOrUpdateProperty(
						product,
						Constant
						.TENANT + attributeName,
						dyn.getDynAttrDesc());
				
			}
		}
	}

	/**
	 * @param apiContext.
	 * @param attributeResource.
	 * @param attributeName.
	 */
	public final void createDynamicAttribute(
			final ApiContext apiContext,
			final AttributeResource attributeResource,
			final String attributeName,String attributeType
			) throws Exception {
			
		try {
			com.mozu.api.contracts.productadmin.Attribute 
		        	attribute = attributeResource
		        	.getAttribute(Constant.TENANT + attributeName);
			if (attribute == null) {
				attribute = new Attribute();
				createNewAttribute(attributeName, attribute,attributeType);
				attribute = 
						attributeResource.addAttribute(attribute);
				
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
						+ attributeName + " added successfully " 
						+ " And Linked to product type "
						+ Constant.PRODUCT_TYPE);
			} else {
				logger.info("Dynamic attribute "
						+ attributeName + " already exists " 
						+ " And Linked to product type "
						+ Constant.PRODUCT_TYPE);
			}
		
			
			/*attrInProdType.setAttributeDetail(attribute);
			attrInProdType.setAttributeFQN(attribute.getAttributeFQN());
			attrInProdType.setIsAdminOnlyProperty(false);
			attrInProdType.setIsHiddenProperty(false);
			attrInProdType.setIsInheritedFromBaseType(false);
			attrInProdType.setIsMultiValueProperty(false);
			attrInProdType.setIsProductDetailsOnlyProperty(true);
			attrInProdType.setIsRequiredByAdmin(false);
			attrInProdType.setOrder(0);
			*/
			
			/*final List<AttributeInProductType> propertiesList 
			    = productType.getProperties();
			propertiesList.add(attrInProdType);
			productType.setProperties(propertiesList);
			productTypeResource.updateProductType(productType, 2);*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception while adding attribute " + attributeName);
			throw e;
		}

	}
	
	/**
	 * @param attributeName.
	 * @param attribute.
	 */
	public void createNewAttribute(final String attributeName,
			 final Attribute attribute,final String attributeType) {
		
		setAttributeValues(attributeName, attribute);
		setAttributeType(attribute, attributeType);
		final AttributeLocalizedContent	attributeLocalizedContent
		    = new AttributeLocalizedContent();
		attributeLocalizedContent.setLocaleCode(Constant.LOCALE);
		attributeLocalizedContent.setName(attributeName);
		final List<com.mozu.api.contracts.productadmin.AttributeLocalizedContent> list
		    = new ArrayList<>();
		list.add(attributeLocalizedContent);
		attribute.setLocalizedContent(list);
		
	}
	
	public String getDynAttrType(final String attributeName,final List<AttrDefinition> dynAttrTypes){
		String attributeType = null;
		for(AttrDefinition d: dynAttrTypes){
			if(attributeName.equalsIgnoreCase(Constant.ATTRIBUTE+"_"+d.getDynAttrId())){
				attributeType = d.getInputType();
				break;
			}
		}
		
		return attributeType;
	}
	
	
	/**
	 * @param attributeName.
	 * @param attribute.
	 */
	public  void setAttributeValues(final String attributeName,
			final Attribute attribute) {
		attribute.setAdminName(attributeName);
		attribute.setInputType(Constant.TEXT_BOX);
		attribute.setDataType(Constant.STRING);
		attribute.setMasterCatalogId(Constant.master_catalog_id);
		attribute.setValueType(Constant.ADMIN_ENTERED);
		attribute.setIsExtra(false);
		attribute.setIsOption(false);
		attribute.setIsProperty(true);
		attribute.setAttributeCode(attributeName);
		
	}
	
	public  void setAttributeType(final Attribute attribute, String type) {
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
		}else if (type.equalsIgnoreCase("Text")) {
			attribute.setInputType(Constant.TEXT_BOX);
			attribute.setDataType("String");
		}
	}

	
	/**
	 * @param attribute.
	 * @param attrInProdType.
	 */
	public  void createAttributeInProductType(
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
	

}
