/**
 *  =======================================================================================
 *  Description : Transformation of HH item loc object to KIBO item restricted object.
 *  Created By  : Ashwinee Salunkhe
 *  
 *  VERSION     MODIFIED BY               (MM/DD/YY)      Description
 *  ========================================================================================
 *  1.0         Ashwinee Salunkhe           11/17/17      Transformation of HH item loc object
 *                                                        to KIBO retail item loc  object.
 *  =========================================================================================
 */                               
 

package com.homehardware.integration.transform.dataentity;

import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.ItemLoc;
import com.mozu.api.contracts.core.Measurement;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductProperty;

import java.util.List;

/**
 * Transformation class used to transform HH Item Loc to Kibo Item Loc.
 * 
 * @author Ashwinee Salunkhe
 * @created Nov 17, 2017
 */

public class TransformFromHhItemLocToKiboItemLoc {
  
  /**
   * This method is transform HH Item loc object to KIBO item loc object.
   * @return. 
   */
  public static Product testTransformationFromHhItemLocToKiboItemLoc(
      Product product ,ItemLoc itemLoc) {
    
    // transform item loc product code obj to kibo product code
   // product.setProductCode(itemLoc.getItem());
    
    // transform location obj to kibo location
    //location.setCode(itemLoc.getLoc());
        
    // transform item loca measurement obj to kibo measurement
    setProductMeasurment(product,itemLoc);
    
    // transform item loca vendor product number obj to kibo vendor product number
    setProductVendorProductNumber(product,itemLoc);
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
          if (updateProductProperties.getAttributeFQN().equalsIgnoreCase(
              HhProductAttributeFqnConstants.Hh_VendorProductNumber_Attr_Fqn)) {
            
            TransformationOfProductProperties.updateProductPropertiesAttribute(
                updateProductProperties,itemLoc.getVpn());
          }
          
        });
       
      } else {
        TransformationOfProductProperties.addProductProperty(product,itemLoc.getVpn(),
                HhProductAttributeFqnConstants.Hh_VendorProductNumber_Attr_Fqn);
      }
           
    } else {
      
      TransformationOfProductProperties.addProductProperty(product,itemLoc.getVpn(),
          HhProductAttributeFqnConstants.Hh_VendorProductNumber_Attr_Fqn);
    }
    
  }

  protected static void setProductMeasurment(Product product, ItemLoc itemLoc) {
    product.getPackageWeight().getValue();
    // TODO Auto-generated method stub
    if (product.getPackageWeight().getValue() != null 
        && Double.SIZE != 0) {
      product.getPackageWeight().setValue(itemLoc.getWeight());
    } else {
      Measurement measurement = new Measurement();
      measurement.setValue(itemLoc.getWeight());
      product.setPackageWeight(measurement);

    }
     
  }

  
}
