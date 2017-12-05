package com.homehardware.utility;

import com.homehardware.constants.HhProductAttributeFqnConstants;

import com.homehardware.model.Brand;
import com.homehardware.model.ExtDesc;
import com.homehardware.model.Gtin;
import com.homehardware.model.ItemAffiliated;
import com.homehardware.model.ItemLoc;
import com.homehardware.model.ItemRestricted;
import com.homehardware.model.RetailMsrp;
import com.mozu.api.contracts.core.Measurement;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductLocalizedImage;
import com.mozu.api.contracts.productadmin.ProductPrice;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
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
			if (ProductUtility.isPropertyExists(productProperties,
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

			if (ProductUtility.isPropertyExists(productProperties,
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

			if (ProductUtility.isPropertyExists(productProperties,
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
		transformHhProductDescTypeToKiboProductDescType(product, extendedDesc);

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
			}

		} else {

			addProductProperty(
					product, extendedDesc.getType(),
					HhProductAttributeFqnConstants
					.Hh_Extended_Desc_Type_Attr_Fqn);

		}

	}

	private static void transformHhProductContentToKiboProductContent(final Product product,
			final ExtDesc extendedDesc) {
		// TODO Auto-generated method stub
		if (extendedDesc != null) {
			final ProductLocalizedContent content = product.getContent();
			content.setLocaleCode(extendedDesc.getLanguage());
			content.setProductFullDescription(extendedDesc.getDescription());
			product.setContent(content);
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
		product.getPackageWeight().getValue();
		// TODO Auto-generated method stub
		if (product.getPackageWeight().getValue() != null && Double.SIZE != 0) {
			product.getPackageWeight().setValue(itemLoc.getWeight());
		} else {
			final Measurement measurement = new Measurement();
			measurement.setValue(itemLoc.getWeight());
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

	@SuppressWarnings({ "unchecked" })
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
		/*productPropertyValue.add(productPropertyValueAttr);
		productPropertyAttr.setValues(productPropertyValue);*/
		addProductPropertyValue(productPropertyValue,
				productPropertyValueAttr, productPropertyAttr);
		setProductProperties(product, productPropertyAttr);
		
		
	}

	/**
	 * @param productPropertyValue.
	 * @param productPropertyValueAttr.
	 * @param productPropertyAttr.
	 */
	public static void addProductPropertyValue(final List<ProductPropertyValue> productPropertyValue,
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
		if (productPropertyValue instanceof ProductPropertyValue 
				&& productPropertyValue.getContent() != null) {
			productPropertyValue.setValue(productPropertyAttrValue);
			productPropertyValue.getContent().setStringValue(productPropertyAttrValue);

		}

	}
  

}
