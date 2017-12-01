/**
 *  =======================================================================================
 *  Description : Transformation of HH GTIN object to KIBO GTIN object.
 *  Created By  : Ashwinee Salunkhe
 *  
 *  VERSION     MODIFIED BY               (MM/DD/YY)      Description
 *  ========================================================================================
 *  1.0         Ashwinee Salunkhe           11/17/17      Transformation of HH GTIN object
 *                                                        to KIBO retail GTIN  object.
 *  =========================================================================================
 */     

package com.homehardware.integration.transform.dataentity;

import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.Gtin;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductProperty;
import java.util.List;

/**
 * Transformation class used to transform HH gtin to Kibo gtin.
 * 
 * @author Ashwinee Salunkhe
 * @created Nov 17, 2017
 */

public class TransformFromHhGtinToKiboGtin {
  
  /**
   * This method is transform HH gtin object to KIBO gtin object.
   */  
  public Product testTransformationFromHhGtinToKiboGtin(Product product,Gtin gtin) {
    
    // transform product code object to kibo product code obj
    transformHhProductCodeToKiboProductCode(product,gtin);
    
    // transform product description type object to kibo product description type obj
    transformHhProductGtinToKiboProductGtin(product,gtin);
    
    return product;
    
  }

  private void transformHhProductGtinToKiboProductGtin(Product product, Gtin gtin) {
    // TODO Auto-generated method stub
    List<ProductProperty> productProperties;
    productProperties = product.getProperties();
    
    if (productProperties != null && productProperties.size() != 0) {
    
      if (TransformationOfProductProperties.isPropertyExists(productProperties, 
          HhProductAttributeFqnConstants.Hh_Gtin_Attr_Fqn)) {
       
        productProperties.forEach(updateProductProperties -> {
          if (updateProductProperties.getAttributeFQN().equalsIgnoreCase(
              HhProductAttributeFqnConstants.Hh_Gtin_Attr_Fqn)) {
            
            TransformationOfProductProperties.updateProductPropertiesAttribute(
                updateProductProperties,gtin.getGtin());
          }
          
        });
       
      } else {
        TransformationOfProductProperties.addProductProperty(
            product,gtin.getGtin(),
                HhProductAttributeFqnConstants.Hh_Gtin_Attr_Fqn);
      }
           
    } else {
      
      TransformationOfProductProperties.addProductProperty(product,gtin.getGtin(),
          HhProductAttributeFqnConstants.Hh_Gtin_Attr_Fqn);
      
    }
    
  }

  private void transformHhProductCodeToKiboProductCode(Product product, Gtin gtin) {
    // TODO Auto-generated method stub
    product.setProductCode(gtin.getItem());
  }
  
}
