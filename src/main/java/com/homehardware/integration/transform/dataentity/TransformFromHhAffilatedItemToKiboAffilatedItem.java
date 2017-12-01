/**
 *  =======================================================================================
 *  Description : Transformation of HH Affilited Item object to KIBO Affilited Item object.
 *  Created By  : Ashwinee Salunkhe
 *  
 *  VERSION     MODIFIED BY               (MM/DD/YY)      Description
 *  ========================================================================================
 *  1.0         Ashwinee Salunkhe           11/17/17      Transformation of HH Affilited Item  
 *                                                        object to KIBO Affilited Item object.
 *  =========================================================================================
 */

package com.homehardware.integration.transform.dataentity;

import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.ItemAffiliated;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductProperty;

import java.util.List;
import org.apache.log4j.Logger;

/**
 * Transformation class used to transform from Hh Affilated Item to Kibo Affilated Item.
 * 
 * @author Ashwinee Salunkhe
 * @created Nov 17, 2017
 */

public class TransformFromHhAffilatedItemToKiboAffilatedItem {
  
  protected static final Logger logger = 
      Logger.getLogger(TransformFromHhAffilatedItemToKiboAffilatedItem.class);
  
  /**
   * This method is transform HH Product Code and Related Product Code in to Kibo obj.
   */  
  public Product testTransformationFromHhAffilatedItemsToKiboAffilatedItems(
      Product product,List<ItemAffiliated> affilatedItemList) {
        
    // transform related product code(Affilated Item) object to kibo related product code obj
    transformHhRelatedProductToKiboRelatedProductCode(product,affilatedItemList);
    
    return product;
     
  }

  public static void transformHhRelatedProductToKiboRelatedProductCode(
      Product product, List<ItemAffiliated> affiliatedItems) {
    // TODO Auto-generated method stub
    List<ProductProperty> productProperties;
    productProperties = product.getProperties();
    try {
      if (productProperties != null && productProperties.size() != 0) {
        
        if (TransformationOfProductProperties.isPropertyExists(productProperties, 
            HhProductAttributeFqnConstants.Hh_Product_CrossSell_Attr_Fqn)) {
         
          productProperties.forEach(updateProductProperties -> {
            if (updateProductProperties.getAttributeFQN().equalsIgnoreCase(
                HhProductAttributeFqnConstants.Hh_Product_CrossSell_Attr_Fqn)) {
              
              TransformationOfProductProperties.updateProductPropertiesAttributeForAffiliatedItem(
                  updateProductProperties,product,affiliatedItems);
              
            }
            
          });
         
        } else {
          
          TransformationOfProductProperties.addProductPropertiesAttributeForAffiliatedItem(
              product,HhProductAttributeFqnConstants.Hh_Product_CrossSell_Attr_Fqn,
              affiliatedItems);
        }
             
      } else {
        TransformationOfProductProperties.addProductPropertiesAttributeForAffiliatedItem(
            product,HhProductAttributeFqnConstants.Hh_Product_CrossSell_Attr_Fqn,
            affiliatedItems);
      }
      
    } catch (Exception e) {
       
      logger.error(" Not able to transfer related "
           + "(affiliated items) items due to cause = " + e.getCause());
      e.printStackTrace();
    }
       
  }
    
}
