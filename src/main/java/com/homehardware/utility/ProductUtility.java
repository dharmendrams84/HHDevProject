package com.homehardware.utility;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.integration.transform.dataentity.TransformationOfProductProperties;
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

public class ProductUtility {

	protected static final Logger logger = Logger.getLogger(ProductUtility.class);

	public static void transformHhRelatedProductToKiboRelatedProductCode(Product product,
			List<ItemAffiliated> affiliatedItems) {
		// TODO Auto-generated method stub
		List<ProductProperty> productProperties;
		productProperties = product.getProperties();
		try {
			if (productProperties != null && productProperties.size() != 0) {

				if (TransformationOfProductProperties.isPropertyExists(productProperties,
						HhProductAttributeFqnConstants.Hh_Product_CrossSell_Attr_Fqn)) {

					productProperties.forEach(updateProductProperties -> {
						if (updateProductProperties.getAttributeFQN()
								.equalsIgnoreCase(HhProductAttributeFqnConstants.Hh_Product_CrossSell_Attr_Fqn)) {

							updateProductPropertiesAttributeForAffiliatedItem(updateProductProperties, product,
									affiliatedItems);

						}

					});

				} else {

					addProductPropertiesAttributeForAffiliatedItem(product,
							HhProductAttributeFqnConstants.Hh_Product_CrossSell_Attr_Fqn, affiliatedItems);
				}

			} else {
				addProductPropertiesAttributeForAffiliatedItem(product,
						HhProductAttributeFqnConstants.Hh_Product_CrossSell_Attr_Fqn, affiliatedItems);
			}

		} catch (Exception e) {

			logger.error(
					" Not able to transfer related " + "(affiliated items) items due to cause = " + e.getMessage());
			throw e;
		}

	}

	protected static void updateProductPropertiesAttributeForAffiliatedItem(ProductProperty updateProductProperties,
			Product product, List<ItemAffiliated> affiliatedItems) {
		try {
			List<ProductPropertyValue> productPropertyValueList = new ArrayList<>();
			for (ItemAffiliated a : affiliatedItems) {
				if (a.getItem().equals(product.getProductCode())) {
					productPropertyValueList.add(createProductProperty(a.getItemAffiliated()));
				}
			}
			updateProductProperties.setValues(productPropertyValueList);
		} catch (Exception e) {
			logger.error(" Exception while updating property " + "product cross sell" + " Due to Exception = "
					+ e.getCause());
			throw e;
		}
	}

	protected static void addProductPropertiesAttributeForAffiliatedItem(Product product, String productAttrFqn,
			List<ItemAffiliated> affiliatedItems) {
		try {
			ProductProperty productProperty = new ProductProperty();
			productProperty.setAttributeFQN(productAttrFqn);
			List<ProductPropertyValue> productPropertyValueList = new ArrayList<>();
			for (ItemAffiliated a : affiliatedItems) {
				if (a.getItem().equals(product.getProductCode())) {
					productPropertyValueList.add(createProductProperty(a.getItemAffiliated()));
				}
			}
			productProperty.setValues(productPropertyValueList);
			List<ProductProperty> productProperties = product.getProperties();
			if (productProperties == null || productProperties.size() == 0) {
				productProperties = new ArrayList<ProductProperty>();
				product.setProperties(productProperties);
			}
			productProperties.add(productProperty);
			product.setProperties(productProperties);

		} catch (Exception e) {
			logger.error(
					" Exception while Adding property product cross sell " + " Due to Exception = " + e.getCause());
			e.printStackTrace();
			throw e;
		}

	}

	protected static ProductPropertyValue createProductProperty(String value) {
		ProductPropertyValue productPropertyValue = new ProductPropertyValue();
		ProductPropertyValueLocalizedContent content = new ProductPropertyValueLocalizedContent();
		content.setStringValue(value);
		productPropertyValue.setContent(content);
		return productPropertyValue;
	}

	public static void testTransformationFromHhBrandToKiboBrand(Product product, Brand brand) {

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

	protected static void setProductHomeExclusiveInd(Product product, Brand brand) {
		// TODO Auto-generated method stub

		List<ProductProperty> productProperties;
		productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (TransformationOfProductProperties.isPropertyExists(productProperties,
					HhProductAttributeFqnConstants.Hh_Home_Exclusive_Ind_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(HhProductAttributeFqnConstants.Hh_Home_Exclusive_Ind_Attr_Fqn)) {

						TransformationOfProductProperties.updateProductPropertiesAttribute(updateProductProperties,
								brand.getHomeExclusiveInd());
					}

				});

			} else {
				TransformationOfProductProperties.addProductProperty(product, brand.getHomeExclusiveInd(),
						HhProductAttributeFqnConstants.Hh_Home_Exclusive_Ind_Attr_Fqn);
			}

		} else {

			TransformationOfProductProperties.addProductProperty(product, brand.getHomeExclusiveInd(),
					HhProductAttributeFqnConstants.Hh_Home_Exclusive_Ind_Attr_Fqn);

		}

	}

	protected static void setProductBrandCode(Product product, Brand brand) {
		// TODO Auto-generated method stub

		List<ProductProperty> productProperties;
		productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (TransformationOfProductProperties.isPropertyExists(productProperties,
					HhProductAttributeFqnConstants.Hh_Brand_Code_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(HhProductAttributeFqnConstants.Hh_Brand_Code_Attr_Fqn)) {

						TransformationOfProductProperties.updateProductPropertiesAttribute(updateProductProperties,
								brand.getBrandCode());
					}

				});

			} else {
				TransformationOfProductProperties.addProductProperty(product, brand.getBrandCode(),
						HhProductAttributeFqnConstants.Hh_Brand_Code_Attr_Fqn);
			}

		} else {

			TransformationOfProductProperties.addProductProperty(product, brand.getBrandCode(),
					HhProductAttributeFqnConstants.Hh_Brand_Code_Attr_Fqn);

		}

	}

	protected static void setProductBrandDescription(Product product, Brand brand) {
		// TODO Auto-generated method stub

		List<ProductProperty> productProperties;
		productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (TransformationOfProductProperties.isPropertyExists(productProperties,
					HhProductAttributeFqnConstants.Hh_Brand_Desc_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(HhProductAttributeFqnConstants.Hh_Brand_Desc_Attr_Fqn)) {

						TransformationOfProductProperties.updateProductPropertiesAttribute(updateProductProperties,
								brand.getBrandDesc());
					}

				});

			} else {
				TransformationOfProductProperties.addProductProperty(product, brand.getBrandDesc(),
						HhProductAttributeFqnConstants.Hh_Brand_Desc_Attr_Fqn);
			}

		} else {

			TransformationOfProductProperties.addProductProperty(product, brand.getBrandDesc(),
					HhProductAttributeFqnConstants.Hh_Brand_Desc_Attr_Fqn);

		}

	}

	protected static void setProductImageIdAndProductLocale(Product product, Brand brand) {
		// TODO Auto-generated method stub
		ProductLocalizedContent content = product.getContent();
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

	public static Product testTransformationFromHhExtendedDescToKiboExtendedDesc(Product product,
			ExtDesc extendedDesc) {

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

	private static void transformHhProductDescTypeToKiboProductDescType(Product product, ExtDesc extendedDesc) {
		// TODO Auto-generated method stub
		List<ProductProperty> productProperties;
		productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (TransformationOfProductProperties.isPropertyExists(productProperties,
					HhProductAttributeFqnConstants.Hh_Extended_Desc_Type_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(HhProductAttributeFqnConstants.Hh_Extended_Desc_Type_Attr_Fqn)) {

						TransformationOfProductProperties.updateProductPropertiesAttribute(updateProductProperties,
								extendedDesc.getType());
					}

				});

			} else {
				TransformationOfProductProperties.addProductProperty(product, extendedDesc.getType(),
						HhProductAttributeFqnConstants.Hh_Extended_Desc_Type_Attr_Fqn);
			}

		} else {

			TransformationOfProductProperties.addProductProperty(product, extendedDesc.getType(),
					HhProductAttributeFqnConstants.Hh_Extended_Desc_Type_Attr_Fqn);

		}

	}

	private static void transformHhProductContentToKiboProductContent(Product product, ExtDesc extendedDesc) {
		// TODO Auto-generated method stub
		if (extendedDesc != null) {
			ProductLocalizedContent content = product.getContent();
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

	public static void transformHhProductGtinToKiboProductGtin(Product product, Gtin gtin) {
		// TODO Auto-generated method stub
		List<ProductProperty> productProperties;
		productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (TransformationOfProductProperties.isPropertyExists(productProperties,
					HhProductAttributeFqnConstants.Hh_Gtin_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(HhProductAttributeFqnConstants.Hh_Gtin_Attr_Fqn)) {

						TransformationOfProductProperties.updateProductPropertiesAttribute(updateProductProperties,
								gtin.getGtin());
					}

				});

			} else {
				TransformationOfProductProperties.addProductProperty(product, gtin.getGtin(),
						HhProductAttributeFqnConstants.Hh_Gtin_Attr_Fqn);
			}

		} else {

			TransformationOfProductProperties.addProductProperty(product, gtin.getGtin(),
					HhProductAttributeFqnConstants.Hh_Gtin_Attr_Fqn);

		}

	}

	public static Product testTransformationFromHhItemLocToKiboItemLoc(Product product, ItemLoc itemLoc) {

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

	protected static void setProductVendorProductNumber(Product product, ItemLoc itemLoc) {
		// TODO Auto-generated method stub

		List<ProductProperty> productProperties;
		productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (TransformationOfProductProperties.isPropertyExists(productProperties,
					HhProductAttributeFqnConstants.Hh_VendorProductNumber_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(HhProductAttributeFqnConstants.Hh_VendorProductNumber_Attr_Fqn)) {

						TransformationOfProductProperties.updateProductPropertiesAttribute(updateProductProperties,
								itemLoc.getVpn());
					}

				});

			} else {
				TransformationOfProductProperties.addProductProperty(product, itemLoc.getVpn(),
						HhProductAttributeFqnConstants.Hh_VendorProductNumber_Attr_Fqn);
			}

		} else {

			TransformationOfProductProperties.addProductProperty(product, itemLoc.getVpn(),
					HhProductAttributeFqnConstants.Hh_VendorProductNumber_Attr_Fqn);
		}

	}

	protected static void setProductMeasurment(Product product, ItemLoc itemLoc) {
		product.getPackageWeight().getValue();
		// TODO Auto-generated method stub
		if (product.getPackageWeight().getValue() != null && Double.SIZE != 0) {
			product.getPackageWeight().setValue(itemLoc.getWeight());
		} else {
			Measurement measurement = new Measurement();
			measurement.setValue(itemLoc.getWeight());
			product.setPackageWeight(measurement);

		}

	}

	public static void testTransformFromHhItemRestrictedToKiboItemRestricted(Product product,
			ItemRestricted itemRestricted) {

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

	protected static void setProductEcommerceInd(Product product, ItemRestricted itemRestricted) {
		// TODO Auto-generated method stub

		List<ProductProperty> productProperties;
		productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (TransformationOfProductProperties.isPropertyExists(productProperties,
					HhProductAttributeFqnConstants.Hh_Ecomm_Ind_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(HhProductAttributeFqnConstants.Hh_Ecomm_Ind_Attr_Fqn)) {

						TransformationOfProductProperties.updateProductPropertiesAttribute(updateProductProperties,
								itemRestricted.getEcommerceInd());
					}

				});

			} else {
				TransformationOfProductProperties.addProductProperty(product, itemRestricted.getEcommerceInd(),
						HhProductAttributeFqnConstants.Hh_Ecomm_Ind_Attr_Fqn);
			}

		} else {

			TransformationOfProductProperties.addProductProperty(product, itemRestricted.getEcommerceInd(),
					HhProductAttributeFqnConstants.Hh_Ecomm_Ind_Attr_Fqn);
		}

	}

	protected static void setProductWebsiteInd(Product product, ItemRestricted itemRestricted) {
		// TODO Auto-generated method stub

		List<ProductProperty> productProperties;
		productProperties = product.getProperties();

		if (productProperties != null && productProperties.size() != 0) {

			if (TransformationOfProductProperties.isPropertyExists(productProperties,
					HhProductAttributeFqnConstants.Hh_Website_Ind_Attr_Fqn)) {

				productProperties.forEach(updateProductProperties -> {
					if (updateProductProperties.getAttributeFQN()
							.equalsIgnoreCase(HhProductAttributeFqnConstants.Hh_Website_Ind_Attr_Fqn)) {

						TransformationOfProductProperties.updateProductPropertiesAttribute(updateProductProperties,
								itemRestricted.getWebsiteInd());
					}

				});

			} else {
				TransformationOfProductProperties.addProductProperty(product, itemRestricted.getWebsiteInd(),
						HhProductAttributeFqnConstants.Hh_Website_Ind_Attr_Fqn);
			}

		} else {

			TransformationOfProductProperties.addProductProperty(product, itemRestricted.getWebsiteInd(),
					HhProductAttributeFqnConstants.Hh_Website_Ind_Attr_Fqn);
		}

	}
	
	 public static void testTransformationFromHhPriceToKiboPrice(
		      Product product,RetailMsrp retailMsrp) {
		    
		    // transform retail price obj to kibo retail price
		    setRetailPrice(product,retailMsrp);
		    
		  }

		  @SuppressWarnings({ "unchecked" })
		  protected static void setRetailPrice(Product product, RetailMsrp retailMsrp) {
		    // TODO Auto-generated method stub
		    ProductPrice productPrice =  product.getPrice();
		    if(productPrice==null){
		    	productPrice = new ProductPrice();
		    }
		   double d =retailMsrp.getRetailMsrp().doubleValue();
		    productPrice.setPrice(d);
		    productPrice.setSalePrice(d);
		    product.setPrice(productPrice);
		    
		  
		  }

		  
		  @SuppressWarnings({ "unchecked" })
		  protected static void setRetailPrice1(Product product, RetailMsrp retailMsrp) {
		    // TODO Auto-generated method stub
		    List<ProductPrice> productPrice = (List<ProductPrice>) product.getPrice();
		    if (productPrice != null && productPrice.size() != 0) {
		      productPrice.forEach(price -> {
		        price.setMsrp(Double.parseDouble(retailMsrp.getRetailMsrp().toString()));
		      });
		       
		    } else {
		      productPrice = new ArrayList<ProductPrice>();
		      productPrice.forEach(price -> {
		        price.setMsrp(Double.parseDouble(retailMsrp.getRetailMsrp().toString()));
		      });
		      product.setPrice((ProductPrice) productPrice);
		    }
		    
		  }
		
}
