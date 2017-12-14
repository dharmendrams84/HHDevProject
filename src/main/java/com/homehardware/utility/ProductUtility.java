package com.homehardware.utility;

import com.hh.integration.constants.Constant;
import com.homehardware.constants.HhProductAttributeFqnConstants;

import com.homehardware.model.Brand;
import com.homehardware.model.DynAttrInfo;
import com.homehardware.model.ExtDesc;
import com.homehardware.model.Gtin;
import com.homehardware.model.Images;
import com.homehardware.model.Item;
import com.homehardware.model.ItemAffiliated;
import com.homehardware.model.ItemDynAttr;
import com.homehardware.model.ItemLoc;
import com.homehardware.model.ItemRestricted;
import com.homehardware.model.ProductItemAttributes;
import com.homehardware.model.RetailMsrp;
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
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public final class ProductUtility {


	
	
	protected static final Logger logger = Logger.getLogger(ProductUtility.class);

	/**
	 * transform related product.
	 */
	public static void transformHhRelatedProductToKiboRelatedProductCode(final Product product,
			final List<ItemAffiliated> affiliatedItems) {
		// TODO Auto-generated method stub
		final List<ProductProperty> productProperties = product.getProperties();
		// try {
		if (productProperties != null && productProperties.size() != 0) {
			if (isPropertyExists(productProperties,
					HhProductAttributeFqnConstants
					.Hh_Product_CrossSell_Attr_Fqn)) {
				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(
							HhProductAttributeFqnConstants
							.Hh_Product_CrossSell_Attr_Fqn)) {
						updateProductPropertiesAttributeForAffiliatedItem(
								updateProductProperties, product,
								affiliatedItems);
					}

				});
			} else {
				addProductPropertiesAttributeForAffiliatedItem(product,
						HhProductAttributeFqnConstants
						.Hh_Product_CrossSell_Attr_Fqn, affiliatedItems);
			}
		} else {
			addProductPropertiesAttributeForAffiliatedItem(product,
					HhProductAttributeFqnConstants
					.Hh_Product_CrossSell_Attr_Fqn, affiliatedItems);
		}

		/*
		 * } catch (Exception e) {
		 * 
		 * logger.error( " Not able to transfer related " +
		 * "(affiliated items) items due to cause = " + e.getMessage()); }
		 */

	}

	protected static void updateProductPropertiesAttributeForAffiliatedItem(
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
		/*
		 * } catch (Exception e) {
		 * logger.error(" Exception while updating property " +
		 * "product cross sell" + " Due to Exception = " + e.getCause()); throw
		 * e; }
		 */
	}

	protected static void addProductPropertiesAttributeForAffiliatedItem(final Product product,
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
		/*
		 * List<ProductProperty> productProperties = product.getProperties(); if
		 * (productProperties == null || productProperties.size() == 0) {
		 * productProperties = new ArrayList<ProductProperty>();
		 * product.setProperties(productProperties); }
		 * productProperties.add(productProperty);
		 * product.setProperties(productProperties);
		 */

		/*
		 * } catch (Exception e) { logger.error(
		 * " Exception while Adding property product cross sell " +
		 * " Due to Exception = " + e.getCause()); e.printStackTrace(); throw e;
		 * }
		 */

	}

	protected static void setProductProperties(
			final Product product, final ProductProperty productProperty) {
		List<ProductProperty> productProperties = product.getProperties();
		if (productProperties == null || productProperties.size() == 0) {
			productProperties = new ArrayList<ProductProperty>();
			product.setProperties(productProperties);
		}
		productProperties.add(productProperty);
		product.setProperties(productProperties);
	}

	protected static ProductPropertyValue createProductProperty(final String value) {
		final ProductPropertyValue productPropertyValue = new ProductPropertyValue();
		final ProductPropertyValueLocalizedContent content;
		content = new ProductPropertyValueLocalizedContent();
		content.setStringValue(value);
		productPropertyValue.setContent(content);
		return productPropertyValue;
	}

	/**
	 * @param product.
	 * @param brand.
	 */
	public static void testTransformationFromHhBrandToKiboBrand(
			final Product product, final Brand brand) {

		// transform product brand description obj to kibo product brand
		// description
		setProductBrandDescription(product, brand);

		// transform HH product brand code obj to kibo product brand code obj
		setProductBrandCode(product, brand);

		// transform product image id to kibo product image id
		setProductHomeExclusiveInd(product, brand);

		// transform product image id and locale to kibo product image id and
		// locale
		setProductImageIdAndProductLocale(product, brand);

	}

	protected static void setProductHomeExclusiveInd(final Product product, final Brand brand) {
		// TODO Auto-generated method stub

		final List<ProductProperty> productProperties;
		productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (isPropertyExists(productProperties,
					HhProductAttributeFqnConstants
					.Hh_Home_Exclusive_Ind_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(
								HhProductAttributeFqnConstants
								.Hh_Home_Exclusive_Ind_Attr_Fqn)) {

						updateProductPropertiesAttribute(
								updateProductProperties,
								brand.getHomeExclusiveInd());
					}

				});

			} else {
				addProductProperty(
						product, brand.getHomeExclusiveInd(),
						HhProductAttributeFqnConstants
						.Hh_Home_Exclusive_Ind_Attr_Fqn);
			}

		} else {

			addProductProperty(product	,brand
					.getHomeExclusiveInd(),
						HhProductAttributeFqnConstants
						.Hh_Home_Exclusive_Ind_Attr_Fqn);

		}

	}

	protected static void setProductBrandCode(final Product product, final Brand brand) {
		// TODO Auto-generated method stub

		final List<ProductProperty> productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (isPropertyExists(productProperties,
					HhProductAttributeFqnConstants.Hh_Brand_Code_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(
								HhProductAttributeFqnConstants
									.Hh_Brand_Code_Attr_Fqn)) {

						updateProductPropertiesAttribute(
									updateProductProperties,
								brand.getBrandCode());
					}

				});

			} else {
				addProductProperty(
						product, brand.getBrandCode(),
						HhProductAttributeFqnConstants
						.Hh_Brand_Code_Attr_Fqn);
			}

		} else {

			addProductProperty(
					product, brand.getBrandCode(),
					HhProductAttributeFqnConstants.Hh_Brand_Code_Attr_Fqn);

		}

	}

	protected static void setProductBrandDescription(final Product product, final Brand brand) {
		// TODO Auto-generated method stub

		final List<ProductProperty> productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (isPropertyExists(productProperties,
					HhProductAttributeFqnConstants.Hh_Brand_Desc_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(
								 HhProductAttributeFqnConstants
									.Hh_Brand_Desc_Attr_Fqn)) {

						updateProductPropertiesAttribute(
								 updateProductProperties,
								brand.getBrandDesc());
					}

				});

			} else {
				addProductProperty(
						product, brand.getBrandDesc(),
						HhProductAttributeFqnConstants
						.Hh_Brand_Desc_Attr_Fqn);
			}

		} else {

			
			addProductProperty(product,
					brand.getBrandDesc(),
					HhProductAttributeFqnConstants.Hh_Brand_Desc_Attr_Fqn);

		}

	}

	protected static void setProductImageIdAndProductLocale(
			final Product product, final Brand brand) {
		// TODO Auto-generated method stub
		final ProductLocalizedContent content = product.getContent();
		List<ProductLocalizedImage> productImages = content.getProductImages();
		if (productImages != null && productImages.size() != 0) {
			productImages.forEach(images -> {
				images.setId(brand.getImageId());

			});
		} else {
			productImages = new ArrayList<>();
			productImages.forEach(images -> {
				images.setId(brand.getImageId());

			});
			content.setProductImages(productImages);

		}
		content.setLocaleCode(brand.getLanguage());
	}

        /**
     * @param product.
     * @param extendedDesc.
     * @return.
     */
        public static 
	    Product 
		testTransformationFromHhExtendedDescToKiboExtendedDesc(
			final Product product,
			final ExtDesc extendedDesc) {

		/*
		 * // transform product code object to kibo product code obj
		 * transformHhProductCodeToKiboProductCode(product,extendedDesc);
		 */
		/*
		 * transform product content(language and full description) object to
		 * kibo product content(language and full description) obj
		 */
		transformHhProductContentToKiboProductContent(product, extendedDesc);

		// transform product description type object to kibo product description
		// type obj
		//transformHhProductDescTypeToKiboProductDescType(product, extendedDesc);

		return product;

	}

	private static void transformHhProductDescTypeToKiboProductDescType(final Product product,
			final ExtDesc extendedDesc) {
		// TODO Auto-generated method stub
		final List<ProductProperty> productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (isPropertyExists(productProperties,
					HhProductAttributeFqnConstants
					.Hh_Extended_Desc_Type_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(
								HhProductAttributeFqnConstants
								.Hh_Extended_Desc_Type_Attr_Fqn)) {

						
						updateProductPropertiesAttribute(
								updateProductProperties,
								extendedDesc.getType());
					}

				});

			} else {
				
				addProductProperty(product, extendedDesc.getType(),
						HhProductAttributeFqnConstants
						.Hh_Extended_Desc_Type_Attr_Fqn);
				/*addProductProperty(HhProductAttributeFqnConstants
						.Hh_Extended_Desc_Type_Attr_Fqn,
						 extendedDesc.getType(), product);*/
		        }

		} else {

			addProductProperty(
					product, extendedDesc.getType(),
					HhProductAttributeFqnConstants
					.Hh_Extended_Desc_Type_Attr_Fqn);

		}

	}

	protected static void transformHhProductContentToKiboProductContent(final Product product,
			final ExtDesc extendedDesc) {
		// TODO Auto-generated method stub
		if (extendedDesc != null && extendedDesc.getType().equalsIgnoreCase("Fb")) {
			final ProductLocalizedContent content = product.getContent();
			content.setLocaleCode(extendedDesc.getLanguage());
			content.setProductFullDescription(extendedDesc.getDescription());
			product.setContent(content);
		} else if (extendedDesc.getType().equalsIgnoreCase("Ingr")) {
			final String propertyName = "tenant~ingredients";
			
			addOrUpdateProperty(product, propertyName, extendedDesc.getDescription());
		} else if (extendedDesc.getType().equalsIgnoreCase("Mktg")) {
			final String propertyName = "tenant~marketing-description";
		
			addOrUpdateProperty(product, propertyName, extendedDesc.getDescription());
		}
	}
	
	
	protected static void addOrUpdateProperty(
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
	

	/*
	 * private static void transformHhProductCodeToKiboProductCode( Product
	 * product, ExtDesc extendedDesc) { // TODO Auto-generated method stub
	 * product.setProductCode(extendedDesc.getItem()); }
	 */

	/**
	 * @param product.
	 * @param gtin.
	 */
	public static void transformHhProductGtinToKiboProductGtin(
			final Product product, final Gtin gtin) {
		// TODO Auto-generated method stub
		final List<ProductProperty> productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (isPropertyExists(productProperties,
					HhProductAttributeFqnConstants.Hh_Gtin_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(
								HhProductAttributeFqnConstants
									.Hh_Gtin_Attr_Fqn)) {

						
						updateProductPropertiesAttribute(
								updateProductProperties,
								gtin.getGtin());
					}

				});

			} else {
				
				addProductProperty(product, gtin.getGtin(), 
						HhProductAttributeFqnConstants.Hh_Gtin_Attr_Fqn);
			}

		} else {

			
			addProductProperty(product, gtin.getGtin(),
					HhProductAttributeFqnConstants.Hh_Gtin_Attr_Fqn);

		}

	}

	/**
	 * @param product.
	 * @param itemLoc.
	 * @return.
	 */
	public static Product 
	      testTransformationFromHhItemLocToKiboItemLoc(
			final Product product, final ItemLoc itemLoc) {

		// transform item loc product code obj to kibo product code
		// product.setProductCode(itemLoc.getItem());

		// transform location obj to kibo location
		// location.setCode(itemLoc.getLoc());

		// transform item loca measurement obj to kibo measurement
		setProductMeasurment(product, itemLoc);

		// transform item loca vendor product number obj to kibo vendor product
		// number
		setProductVendorProductNumber(product, itemLoc);
		return product;

	}

	protected static void setProductVendorProductNumber(
			final Product product, final ItemLoc itemLoc) {
		// TODO Auto-generated method stub

		final List<ProductProperty> productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (isPropertyExists(productProperties,
					HhProductAttributeFqnConstants
					.Hh_VendorProductNumber_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(
								HhProductAttributeFqnConstants
								.Hh_VendorProductNumber_Attr_Fqn)) {
					
					        updateProductPropertiesAttribute(
						        updateProductProperties,
								itemLoc.getVpn());
					}

				});

			} else {
				
				addProductProperty(product, itemLoc.getVpn(),
						HhProductAttributeFqnConstants
						.Hh_VendorProductNumber_Attr_Fqn);
			}

		} else {

			
			addProductProperty(product, itemLoc.getVpn(),
					HhProductAttributeFqnConstants
					.Hh_VendorProductNumber_Attr_Fqn);
		}

	}

	protected static void setProductMeasurment(final Product product, final ItemLoc itemLoc) {
		
		// TODO Auto-generated method stub
		if (product.getPackageWeight() != null
				&& product.getPackageWeight().getValue() != null 
				&& Double.SIZE != 0) {
			product.getPackageWeight().setValue(itemLoc.getWeight());
			/*
			 * product.getPackageLength().setValue(value);
			 * product.getPackageHeight().setValue(value);
			 */ 
		} else {
			final Measurement measurement = new Measurement();
			measurement.setValue(itemLoc.getWeight());
			measurement.setUnit(itemLoc.getWeightUom());
			product.setPackageWeight(measurement);

		}

	}

	/**
	 * @param product.
	 * @param itemRestricted.
	 */
	public static void testTransformFromHhItemRestrictedToKiboItemRestricted(
			final Product product,
			final ItemRestricted itemRestricted) {

		// transform item loc product code obj to kibo product code
		// product.setProductCode(itemRestricted.getItem());

		// transform location obj to kibo location
		// location.setCode(new Integer(itemRestricted.getStore()).toString());

		// transform item loca vendor product number obj to kibo vendor product
		// number
		setProductWebsiteInd(product, itemRestricted);

		// transform item loca vendor product number obj to kibo vendor product
		// number
		setProductEcommerceInd(product, itemRestricted);

	}

	protected static void setProductEcommerceInd(
			final Product product, final ItemRestricted itemRestricted) {
		// TODO Auto-generated method stub

		final List<ProductProperty> productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (isPropertyExists(productProperties,
					HhProductAttributeFqnConstants.Hh_Ecomm_Ind_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(
								HhProductAttributeFqnConstants
									.Hh_Ecomm_Ind_Attr_Fqn)) {

						
						updateProductPropertiesAttribute(
								updateProductProperties,
								itemRestricted.getEcommerceInd());
					}

				});

			} else {
				addProductProperty(product, itemRestricted.getEcommerceInd(),
						HhProductAttributeFqnConstants
						.Hh_Ecomm_Ind_Attr_Fqn);
			}

		} else {

			
			addProductProperty(product, itemRestricted.getEcommerceInd(),
					HhProductAttributeFqnConstants.Hh_Ecomm_Ind_Attr_Fqn);
		}

	}

	protected static void setProductWebsiteInd(
			final Product product, final ItemRestricted itemRestricted) {
		// TODO Auto-generated method stub

		final List<ProductProperty> productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (isPropertyExists(productProperties,
					HhProductAttributeFqnConstants.Hh_Website_Ind_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(
								HhProductAttributeFqnConstants
									.Hh_Website_Ind_Attr_Fqn)) {

						
						updateProductPropertiesAttribute(
								updateProductProperties,
								itemRestricted.getWebsiteInd());
					}

				});

			} else {
				
				addProductProperty(product, itemRestricted.getWebsiteInd(),
						HhProductAttributeFqnConstants
						.Hh_Website_Ind_Attr_Fqn);
			}

		} else {

			addProductProperty(product, itemRestricted.getWebsiteInd(),
					HhProductAttributeFqnConstants.Hh_Website_Ind_Attr_Fqn);
		}

	}
	
	/**
	 * @param product.
	 * @param retailMsrp.
	 */
	public static void testTransformationFromHhPriceToKiboPrice(
		  	  final Product product,
				final RetailMsrp retailMsrp) {

		// transform retail price obj to kibo retail price
		setRetailPrice(product, retailMsrp);

	}

	/*@SuppressWarnings({ "unchecked" })*/
	protected static void setRetailPrice(final Product product, final RetailMsrp retailMsrp) {
		// TODO Auto-generated method stub
		ProductPrice productPrice = product.getPrice();
		if (productPrice == null) {
			productPrice = new ProductPrice();
		}
		final double d = retailMsrp.getRetailMsrp().doubleValue();
		productPrice.setPrice(d);
		productPrice.setSalePrice(d);
		product.setPrice(productPrice);

	}

/*	@SuppressWarnings({ "unchecked" })
	protected static void setRetailPrice1(final Product product, final RetailMsrp retailMsrp) {
		// TODO Auto-generated method stub
		List<ProductPrice> productPrice = (List<ProductPrice>) product.getPrice();
		if (productPrice != null && productPrice.size() != 0) {
			productPrice.forEach(price -> {
				price.setMsrp(Double
						.parseDouble(
							retailMsrp.getRetailMsrp().toString()));
			});

		} else {
			productPrice = new ArrayList<ProductPrice>();
			productPrice.forEach(price -> {
				price.setMsrp(Double.parseDouble(
						retailMsrp.getRetailMsrp().toString()));
			});
			product.setPrice((ProductPrice) productPrice);
		}

	}
*/
	/**
	 * @param productProperties.
	 * @param attributeFqn.
	 * @return.
	 */
	public static boolean isPropertyExists(final List<ProductProperty> productProperties,
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
	 * @param product.
	 * @param productPropertyAttrValue.
	 * @param productAttrFqn.
	 */
	/*
	public static void addProductProperty(
			final Product product,
			final String productPropertyAttrValue,final String productAttrFqn) {
		
			
		final ProductProperty productPropertyAttr = new ProductProperty();
		productPropertyAttr.setAttributeFQN(productAttrFqn);

		final ProductPropertyValue productPropertyValueAttr = new ProductPropertyValue();
		productPropertyValueAttr.setValue(productPropertyAttrValue);

		final ProductPropertyValueLocalizedContent content 
		    = new ProductPropertyValueLocalizedContent();
		content.setStringValue(productPropertyAttrValue);
		productPropertyValueAttr.setContent(content);

		final List<ProductPropertyValue> productPropertyValue
		    = new ArrayList<ProductPropertyValue>();
		productPropertyValue.add(productPropertyValueAttr);
		productPropertyAttr.setValues(productPropertyValue);
		addProductPropertyValue(productPropertyValue,
				productPropertyValueAttr, productPropertyAttr);
		setProductProperties(product, productPropertyAttr);
		
		
	}*/

	/**
	 * @param productPropertyValue.
	 * @param productPropertyValueAttr.
	 * @param productPropertyAttr.
	 */
	public static void addProductPropertyValue(
			final List<ProductPropertyValue> productPropertyValue,
			final ProductPropertyValue productPropertyValueAttr,
			final ProductProperty productPropertyAttr) {
		productPropertyValue.add(productPropertyValueAttr);
		productPropertyAttr.setValues(productPropertyValue);
	}

	/**
	 * @param productPropertiesAction.
	 * @param productPropertyAttrValue.
	 */
	public static void updateProductPropertiesAttribute(
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
		//System.out.println("property "+productPropertyValue+ " updated successfully!!");
	}
  
	/**
	 * @param product.
	 * @param attributeValue.
	 * @param attributeFqn.
	 */
	public static final void addProductProperty(final Product product,
			final String attributeValue, final String attributeFqn) {
		try {
			final ProductProperty productProperty = new ProductProperty();
			productProperty.setAttributeFQN(attributeFqn);

			final ProductPropertyValue productPropertyValue 
			    = new ProductPropertyValue();
			final List<ProductPropertyValue> productPropertyValuesList 
		            = new ArrayList<ProductPropertyValue>();
			/*

			final ProductPropertyValueLocalizedContent content
			    = new ProductPropertyValueLocalizedContent();
			
			content.setStringValue(attributeValue);
			productPropertyValue.setContent(content);

			productPropertyValuesList.add(productPropertyValue);
			productProperty.setValues(productPropertyValuesList);*/
			
			/*List<ProductProperty> productPropertiesList = product.getProperties();
			if (productPropertiesList != null && productPropertiesList.size() != 0) {
				product.getProperties().add(productProperty);
			} else {
				productPropertiesList = new ArrayList<ProductProperty>();
				productPropertiesList.add(productProperty);
				product.setProperties(productPropertiesList);
			}*/
			setPropertyValueContent(productPropertyValuesList,
					productPropertyValue, productProperty, attributeValue);
			setPropertiesList(product, productProperty, productPropertyValuesList);
			

		} catch (Exception e) {
			System.out.println("Exception while adding property " + attributeFqn + " ");
			e.printStackTrace();
		}
	}
	
	/**
	 * @param product.
	 * @param productPropertyValue.
	 * @param productProperty.
	 * @param attributeValue.
	 */
	/*public static final void addProductPropertyValuesList(final Product product,
			final ProductPropertyValue productPropertyValue,
			final ProductProperty productProperty,
			final String attributeValue) {
		
		final List<ProductPropertyValue> productPropertyValuesList 
		    = new ArrayList<ProductPropertyValue>();

		final ProductPropertyValueLocalizedContent content 
		    = new ProductPropertyValueLocalizedContent();

		content.setStringValue(attributeValue);
		productPropertyValue.setContent(content);

		productPropertyValuesList.add(productPropertyValue);
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
	
*/	
	/**
	 * @param product.
	 * @param productProperty.
	 * @param productPropertyValuesList.
	 */
	public static final void setPropertiesList(final Product product, 
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
	 * @param productPropertyValue.
	 * @param productProperty.
	 * @param attributeValue.
	 */
	public static final void setPropertyValueContent(
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
		productLocalizedContent.setLocaleCode(Constant.LOCALE);
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
			testTransformationFromHhBrandToKiboBrand(product, b);
		}
	}
	
	/**
	 * @param item.
	 * @param product.
	 */
	protected void setProductExtDesc(final Item item, final Product product) {
		if (item.getExtDesc() != null && item.getExtDesc().size() != 0) {

			for (ExtDesc e : item.getExtDesc()) {
				testTransformationFromHhExtendedDescToKiboExtendedDesc(product, e);
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
				transformHhProductGtinToKiboProductGtin(product, g);
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
			testTransformationFromHhItemLocToKiboItemLoc(product, itemLoc);
		}
	}
	
	/**
	 * @param item.
	 * @param product.
	 */
	protected void setProductItemRestricted(final Item item, final Product product) {
		if (item.getItemRestricted() != null && item.getItemRestricted().size() != 0) {
			for (ItemRestricted i : item.getItemRestricted()) {
				testTransformFromHhItemRestrictedToKiboItemRestricted(product, i);
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
				testTransformationFromHhPriceToKiboPrice(product, r);
			}
		}
	}
	
	/**
	 * @param item.
	 * @param product.
	 */
	protected void setProductItemAttributes(final Item item, final Product product) {
		for (ProductItemAttributes p : item.getProductItemAttributes()) {
			if (isPropertyExists(
							product.getProperties(),
							p.getId().getProductAttrId())) {
				for (ProductProperty pp : product.getProperties()) {
					if (pp.getAttributeFQN()
							.equalsIgnoreCase(
									p.getId()
									.getProductAttrId())) {
						updateProductPropertiesAttribute(
								pp, p.getAttributeValue());
					}
				}

			} else {
				addProductProperty(
						product, p.getAttributeValue(), 
						p.getId().getProductAttrId());
			}
		}
	}
	
	
	
	/**
	 * @param product.
	 * @param productResource.
	 * @param item.
	 * @param apiContext.
	 * @.throws Exception
	 */
	public  void createNewProduct(
			final Product product,final 
			ProductResource productResource,
			final Item item, final ApiContext apiContext) throws Exception {
		
		product.setProductTypeId(Constant.int_7);

		convertHhItemToMozuProduct(item, product);
		
		/*transformHhImageToKiboImage(
				product, item.getImages(),
				productResource, apiContext);*/
		//transformHhDynamicAttributes(product, item, apiContext);
		//final Product newProduct = productResource.addProduct(product);
		/*if (newProduct != null) {
			logger.info("New Product with product code " + newProduct.getProductCode()
					+ " created successfully!!!!!");
		}*/
	}
	
	/**
	 * @param product.
	 * @param productResource.
	 * @param item.
	 * @.throws Exception
	 */
	public void updateProduct(
			final Product product,final 
			ProductResource productResource,
			final Item item,final ApiContext apiContext) throws Exception {
		convertHhItemToMozuProduct(item, product);
		/*transformHhImageToKiboImage(product, item.getImages(), productResource, apiContext);
		transformHhDynamicAttributes(product,item, apiContext);*/
		productResource.updateProduct(product, product.getProductCode());
	}
	
	
	/**
	 * @param product.
	 * @param images.
	 * @param productResource.
	 * @param apiContext.
	 */
	public static void transformHhImageToKiboImage(final Product product,
               final List<Images> images,
               final ProductResource productResource,final ApiContext apiContext) {
		final ProductLocalizedContent content = product.getContent();
		List<ProductLocalizedImage> productLocalizedImages = content.getProductImages();
		if (productLocalizedImages == null) {
			productLocalizedImages = new ArrayList<ProductLocalizedImage>();
		}
		product.getContent().setProductImages(productLocalizedImages);
		if (images != null && images.size() != 0) {
			for (Images img : images) {
				final File file = new File(
						Constant.IMAGES_FOLDER_LOC
				        + img.getImageId() + ".jpg");
				if (file.exists()) { 
					/*
				        final String cmsId = saveFileToFileManager(
						file, new Integer(img.getImageId())
						.toString(), apiContext);

				

					final ProductLocalizedImage productLocalizedImage
					    = new ProductLocalizedImage();
					productLocalizedImage.setLocaleCode(Constant.LOCALE);
					productLocalizedImage.setCmsId(cmsId);
					productLocalizedImages.add(productLocalizedImage);
					*/
					uploadImages(file, apiContext, img, productLocalizedImages);
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
	public static void uploadImages(
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
			/*doc.setContentMimeType("image/jpg");
			doc.setExtension("jpg");
			doc.setName(imageName);
			doc.setDocumentTypeFQN("image@mozu");
			if (file.exists()) {
				final long length = Long.valueOf(file.length());
				doc.setContentLength(length);
				logger.info("Content length is " + length);
			}*/
			createDocumentObject(doc, imageName, file);
			/*final DocumentCollection existingDoc 
			    = docResource.getDocuments("files@mozu", "name eq " + imageName, null,
					1, 0, Boolean.TRUE, null);
			Document newDoc = null;
			if (existingDoc == null || existingDoc.getItems().isEmpty()) {
				newDoc = docResource.createDocument(doc, "files@mozu");
				logger.info("Creating Document with name " + imageName);
			} else {
				newDoc = existingDoc.getItems().get(0);
			}*/
			final Document newDoc = createOrUpdateDocument(docResource, doc, imageName);
			
			retDoc = docResource.getDocument(Constant.FILES_MOZU, newDoc.getId());
			//Document newDoc = docResource.createDocument(doc, "files@mozu");
			final FileInputStream fin = new FileInputStream(file);
			docResource.updateDocumentContent(
					fin, Constant.FILES_MOZU, retDoc.getId(), "jpg");
			
			
		} catch (Exception e) {
			System.out.println("Exception while uploading image "
					+ imageName);
			e.printStackTrace();
		}
		return retDoc.getId();
	}
	
	/**
	 * @param doc.
	 * @param imageName.
	 * @param file.
	 */
	public static void createDocumentObject(
			final Document doc,final String imageName,final File file) {
		doc.setContentMimeType("image/jpg");
		doc.setExtension("jpg");
		doc.setName(imageName);
		doc.setDocumentTypeFQN("image@mozu");
		if (file.exists()) {
			final long length = Long.valueOf(file.length());
			doc.setContentLength(length);
			logger.info("Content length is " + length);
		}

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
			e.printStackTrace();
		}
		return newDoc;
	}
	/*
	private Document saveFileToFileManager(File file, String imageName, ApiContext context, String recType)
			throws Exception {
		DocumentResource docResource = new DocumentResource(context);
		
		Document doc = new Document();
		doc.setContentMimeType("image/jpg");
		doc.setExtension("jpg");
		doc.setName(imageName);

		logger.info("Got image at " + imageName + " to process.");

		if (file.exists()) {
			long length = Long.valueOf(file.length());
			doc.setContentLength(length);
			logger.info("Content length is " + length);
		}

		doc.setDocumentTypeFQN("image@mozu");
		Document newDoc = null;
		DocumentCollection existingDoc = null;
		
		
		try {
			existingDoc = docResource.getDocuments(
			Constants.FILES_AT_MOZU, Constants.QUERY_NAME_EQ + imageName, null,
					1, 0, Boolean.TRUE, null);
			
			if (existingDoc == null || existingDoc.getItems().isEmpty()) {
				newDoc = docResource.createDocument(doc, "files@mozu");
				logger.info("Creating Document with name " + imageName);
			} else {
				newDoc = existingDoc.getItems().get(0);
			}
			
			if("U".equalsIgnoreCase(recType)) {
				docResource.deleteDocument("files@mozu", newDoc.getId());
				logger.info("Deleted existing document with name " + imageName);
				
				newDoc = docResource.createDocument(doc, "files@mozu");
				logger.info("Created new document with name " + imageName);
			}
		} catch (Exception e) {
			logger.error("Error occurred while uploading the image. ", e);
			throw e;
		}

		String docid = newDoc.getId();

		FileInputStream fin = new FileInputStream(file);
		docResource.updateDocumentContent(fin, "files@mozu", docid, "image/jpg");
		Document retDoc = docResource.getDocument("files@mozu", docid);

		// File's job is done. delete it.
		file.delete();

		logger.info("Image '" + imageName + "' uploaded");
		return retDoc;
	}
*/
	/**
	 * @param apiContext.
	 * @param attributeResource.
	 * @param attributeName.
	 */
	public static final void createDynamicAttribute(
			final ApiContext apiContext,
			final AttributeResource attributeResource,
			final String attributeName) throws Exception {
			
		try {
			com.mozu.api.contracts.productadmin.Attribute 
		        	attribute = attributeResource
		        	.getAttribute(Constant.TENANT + attributeName);
			if (attribute == null) {
				attribute = new Attribute();
				createNewAttribute(attributeName, attribute);
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
				System.out.println("Dynamic attribute "
						+ attributeName + " added successfully " 
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
	
	/**
	 * @param attributeName.
	 * @param attribute.
	 */
	public static void setAttributeValues(final String attributeName,
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
	
	
	/**
	 * @param item.
	 * @param product.
	 * @param apiContext.
	 */
	public void addOrUpdateProductInMozu(
			final Item item, final  ApiContext apiContext) {
		try {
			final ProductResource productResource = new ProductResource(apiContext);
			Product product = productResource.getProduct(item.getId().getItem());

		        if (product == null) {
				product = new Product();

				createNewProduct(product, productResource, item, apiContext);
				transformHhImageToKiboImage(
						product, item.getImages(),
						productResource, apiContext);
				transformHhDynamicAttributes(product, item, apiContext);
				final Product newProduct = productResource.addProduct(product);
				if (newProduct != null) {
					/*logger.info("New Product with product code "
							+ newProduct.getProductCode()
							+ " created successfully!!!!!");*/
				}
			} else {

				updateProduct(product, productResource, item,apiContext);
				/*transformHhImageToKiboImage(
						product, item.getImages(),
						productResource, apiContext);

				
				transformHhDynamicAttributes(product, item, apiContext);*/
				
				productResource.updateProduct(product, product.getProductCode());
				System.out.println(
						"Product with product code "
								+ item.getId().getItem()
								+ " updated successfully!!!!");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(
					"Exception while adding or updating product in Mozu "
							+ e.getMessage());
		}
		System.out.println(
				"Product with product code "
						+ item.getId().getItem()
						+ " added successfully!!!!");
	}
	
	/**
	 * @param item.
	 * @param apiContext.
	 */
	public void transformHhDynamicAttributes(final Product product,
			final Item item,final ApiContext apiContext)  throws Exception {
		try {
			final List<ItemDynAttr> itemDynAttrs = item.getItemDynAttr();
			if (itemDynAttrs != null && itemDynAttrs.size() != 0) {
				for (ItemDynAttr id : itemDynAttrs) {
					final AttributeResource attributeResource 
					    = new AttributeResource(apiContext);
					final String attributeName 
					    = Constant.ATTRIBUTE + "_" + id.getDynAttrId();

					createDynamicAttribute(apiContext, 
							attributeResource, attributeName);
					addorupdateDynamicAttribute(item, attributeName, product);
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
		}catch(Exception e){
			System.out.println("Exception while transoforming dynamic attribute "
					+ e.getMessage());
			throw e ;
		}
	}
	
	/**
	 * @param item.
	 * @param attributeName.
	 */
	public void addorupdateDynamicAttribute(
			final Item item, final String attributeName,
			final Product product) {
		for (DynAttrInfo dyn : item.getDynAttrInfo()) {
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
		}
	}
}
