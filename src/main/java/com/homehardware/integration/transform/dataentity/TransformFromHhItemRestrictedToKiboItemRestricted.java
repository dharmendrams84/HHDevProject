/**
 *  =======================================================================================
 *  Description : Transformation of HH item restricted object to KIBO item restricted object.
 *  Created By  : Ashwinee Salunkhe
 *  
 *  VERSION     MODIFIED BY               (MM/DD/YY)      Description
 *  ========================================================================================
 *  1.0         Ashwinee Salunkhe           11/17/17      Transformation of HH item restricted 
 *                                                        object to KIBO retail item restricted
 *                                                        object.
 *  =========================================================================================
 */

package com.homehardware.integration.transform.dataentity;

import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.ItemRestricted;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductProperty;

import java.util.List;

/**
 * Transformation class to transform from HH Promotion to Kibo Promotion.
 * 
 * @author Ashwinee Salunkhe
 * @created Nov 17, 2017
 */
public class TransformFromHhItemRestrictedToKiboItemRestricted {
  
  /**
   * This method is transform HH Item restricted object to KIBO item restricted object.
   */
  public static void testTransformFromHhItemRestrictedToKiboItemRestricted(
      Product product,Location location,ItemRestricted itemRestricted) {
    
    // transform item loc product code obj to kibo product code
    product.setProductCode(itemRestricted.getItem());
    
    // transform location obj to kibo location
    location.setCode(new Integer(itemRestricted.getStore()).toString());
    
    // transform item loca vendor product number obj to kibo vendor product number
    setProductWebsiteInd(product,itemRestricted);
    
    // transform item loca vendor product number obj to kibo vendor product number
    setProductEcommerceInd(product,itemRestricted);
        
  }

  protected static void setProductEcommerceInd(Product product, ItemRestricted itemRestricted) {
    // TODO Auto-generated method stub
    
    List<ProductProperty> productProperties;
    productProperties = product.getProperties();
    
    if (productProperties != null && productProperties.size() != 0) {
    
      if (TransformationOfProductProperties.isPropertyExists(productProperties, 
          HhProductAttributeFqnConstants.Hh_Ecomm_Ind_Attr_Fqn)) {
       
        productProperties.forEach(updateProductProperties -> {
          if (updateProductProperties.getAttributeFQN().equalsIgnoreCase(
              HhProductAttributeFqnConstants.Hh_Ecomm_Ind_Attr_Fqn)) {
            
            TransformationOfProductProperties.updateProductPropertiesAttribute(
                updateProductProperties,itemRestricted.getEcommerceInd());
          }
          
        });
       
      } else {
        TransformationOfProductProperties.addProductProperty(
            product,itemRestricted.getEcommerceInd(),
                HhProductAttributeFqnConstants.Hh_Ecomm_Ind_Attr_Fqn);
      }
           
    } else {
      
      TransformationOfProductProperties.addProductProperty(product,itemRestricted.getEcommerceInd(),
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
          if (updateProductProperties.getAttributeFQN().equalsIgnoreCase(
              HhProductAttributeFqnConstants.Hh_Website_Ind_Attr_Fqn)) {
            
            TransformationOfProductProperties.updateProductPropertiesAttribute(
                updateProductProperties,itemRestricted.getWebsiteInd());
          }
          
        });
       
      } else {
        TransformationOfProductProperties.addProductProperty(
            product,itemRestricted.getWebsiteInd(),
                HhProductAttributeFqnConstants.Hh_Website_Ind_Attr_Fqn);
      }
           
    } else {
      
      TransformationOfProductProperties.addProductProperty(product,itemRestricted.getWebsiteInd(),
          HhProductAttributeFqnConstants.Hh_Website_Ind_Attr_Fqn);
    }
    
  }
  
}
