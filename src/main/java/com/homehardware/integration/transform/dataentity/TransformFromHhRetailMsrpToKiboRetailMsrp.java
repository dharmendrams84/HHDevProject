/**
 *  =======================================================================================
 *  Description : Transformation of HH retail price object to KIBO retail price object.
 *  Created By  : Ashwinee Salunkhe
 *  
 *  VERSION     MODIFIED BY               (MM/DD/YY)      Description
 *  ========================================================================================
 *  1.0         Ashwinee Salunkhe           11/17/17      Transformation of HH retail price 
 *                                                        object to KIBO retail price object.
 *  =========================================================================================
 */

package com.homehardware.integration.transform.dataentity;

import com.homehardware.model.RetailMsrp;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductPrice;

import java.util.ArrayList;
import java.util.List;

/**
 * Transformation class to transform from HH Item Retail to Kibo Item Retail.
 * 
 * @author Ashwinee Salunkhe
 * @created Nov 17, 2017
 */
public class TransformFromHhRetailMsrpToKiboRetailMsrp {
  
  /**
   * This method is transform HH Item Retail Price object to KIBO item retail price object.
   */
  public static void testTransformationFromHhPriceToKiboPrice(
      Product product,Location location,RetailMsrp retailMsrp) {
    
    // transform item loc product code obj to kibo product code
    product.setProductCode(retailMsrp.getItem());
    
    // transform location obj to kibo location
    location.setCode(retailMsrp.getStore().toString());
    
    // transform retail price obj to kibo retail price
    setRetailPrice(product,retailMsrp);
    
  }

  @SuppressWarnings({ "unchecked" })
  protected static void setRetailPrice(Product product, RetailMsrp retailMsrp) {
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
