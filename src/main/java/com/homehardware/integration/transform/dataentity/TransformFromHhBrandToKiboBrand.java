/**
 *  ====================================================================================
 *  Description : Transformation of HH brand object to KIBO brand object.
 *  Created By  : Ashwinee Salunkhe
 *  
 *  VERSION     MODIFIED BY               (MM/DD/YY)      Description
 *  ====================================================================================
 *  1.0         Ashwinee Salunkhe           11/17/17      Transformation of HH brand 
 *                                                        object to KIBO brand object.
 *  ====================================================================================
 */

package com.homehardware.integration.transform.dataentity;

import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.Brand;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductLocalizedImage;
import com.mozu.api.contracts.productadmin.ProductProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Transformation class used to transform from Hh Brand to Kibo Brand.
 * 
 * @author Ashwinee Salunkhe
 * @created Nov 17, 2017
 */

public class TransformFromHhBrandToKiboBrand {
  /**
   * This method is transform HH brand object to KIBO brand object.
   */
  public static Product testTransformationFromHhBrandToKiboBrand(Product product, Brand brand) {
    
    // transform product brand description obj to kibo product brand description
    setProductBrandDescription(product, brand);
    
    // transform HH product brand code obj to kibo product brand code obj
    setProductBrandCode(product, brand);
    
    // transform product image id to kibo product image id
    setProductHomeExclusiveInd(product, brand);
    
    // transform product image id and locale to kibo product image id and
    // locale
    setProductImageIdAndProductLocale(product, brand);
    
    return product;
    
  }
  
  protected static void setProductHomeExclusiveInd(Product product, Brand brand) {
    // TODO Auto-generated method stub
    
    List<ProductProperty> productProperties;
    productProperties = product.getProperties();
    
    if (productProperties != null && productProperties.size() != 0) {
    
      if (TransformationOfProductProperties.isPropertyExists(productProperties, 
          HhProductAttributeFqnConstants.Hh_Home_Exclusive_Ind_Attr_Fqn)) {
       
        productProperties.forEach(updateProductProperties -> {
          if (updateProductProperties.getAttributeFQN().equalsIgnoreCase(
              HhProductAttributeFqnConstants.Hh_Home_Exclusive_Ind_Attr_Fqn)) {
            
            TransformationOfProductProperties.updateProductPropertiesAttribute(
                updateProductProperties,brand.getHomeExclusiveInd());
          }
          
        });
       
      } else {
        TransformationOfProductProperties.addProductProperty(product,brand.getHomeExclusiveInd(),
                HhProductAttributeFqnConstants.Hh_Home_Exclusive_Ind_Attr_Fqn);
      }
           
    } else {
      
      TransformationOfProductProperties.addProductProperty(product,brand.getHomeExclusiveInd(),
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
          if (updateProductProperties.getAttributeFQN().equalsIgnoreCase(
              HhProductAttributeFqnConstants.Hh_Brand_Code_Attr_Fqn)) {
            
            TransformationOfProductProperties.updateProductPropertiesAttribute(
                updateProductProperties, brand.getBrandCode());
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
          if (updateProductProperties.getAttributeFQN().equalsIgnoreCase(
              HhProductAttributeFqnConstants.Hh_Brand_Desc_Attr_Fqn)) {
            
            TransformationOfProductProperties.updateProductPropertiesAttribute(
                updateProductProperties,brand.getBrandDesc());
          }
          
        });
       
      } else {
        TransformationOfProductProperties.addProductProperty(product,brand.getBrandDesc(),
                HhProductAttributeFqnConstants.Hh_Brand_Desc_Attr_Fqn);
      }
           
    } else {
      
      TransformationOfProductProperties.addProductProperty(product,brand.getBrandDesc(),
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
    } else  {
      productImages = new ArrayList<>();
      productImages.forEach(images -> {
        images.setId(brand.getImageId());
     
      });
      content.setProductImages(productImages);
      
    }
    content.setLocaleCode(brand.getLanguage()); 
  }
  
}
