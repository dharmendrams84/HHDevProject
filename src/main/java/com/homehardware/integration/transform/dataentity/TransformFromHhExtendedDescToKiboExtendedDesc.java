/**
 *  ==============================================================================================
 *  Description : Transformation of HH Extended desc object to KIBO Extended desc object.
 *  Created By  : Ashwinee Salunkhe
 *  
 *  VERSION     MODIFIED BY               (MM/DD/YY)      Description
 *  ===============================================================================================
 *  1.0         Ashwinee Salunkhe           11/17/17      Transformation of HH Extended desc object
 *                                                        to KIBO retail Extended desc  object.
 *  ===============================================================================================
 */ 

package com.homehardware.integration.transform.dataentity;

import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.ExtDesc;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductProperty;
import java.util.List;

/**
 * Transformation class used to transform HH extended desc to Kibo extended desc.
 * 
 * @author Ashwinee Salunkhe
 * @created Nov 17, 2017
 */

public class TransformFromHhExtendedDescToKiboExtendedDesc {
  
  /**
   * This method is transform HH brand object to KIBO brand object.
   */  
  public Product testTransformationFromHhExtendedDescToKiboExtendedDesc(
      Product product,ExtDesc extendedDesc) {
    
    // transform product code object to kibo product code obj
    transformHhProductCodeToKiboProductCode(product,extendedDesc);
    
    /* transform product content(language and full description) 
    object to kibo product content(language and full description) obj */
    transformHhProductContentToKiboProductContent(product,extendedDesc);
    
    // transform product description type object to kibo product description type obj
    transformHhProductDescTypeToKiboProductDescType(product,extendedDesc);
   
    return product;
    
  }

  private void transformHhProductDescTypeToKiboProductDescType(
      Product product, ExtDesc extendedDesc) {
    // TODO Auto-generated method stub
    List<ProductProperty> productProperties;
    productProperties = product.getProperties();
    
    if (productProperties != null && productProperties.size() != 0) {
    
      if (TransformationOfProductProperties.isPropertyExists(productProperties, 
          HhProductAttributeFqnConstants.Hh_Extended_Desc_Type_Attr_Fqn)) {
       
        productProperties.forEach(updateProductProperties -> {
          if (updateProductProperties.getAttributeFQN().equalsIgnoreCase(
              HhProductAttributeFqnConstants.Hh_Extended_Desc_Type_Attr_Fqn)) {
            
            TransformationOfProductProperties.updateProductPropertiesAttribute(
                updateProductProperties,extendedDesc.getType());
          }
          
        });
       
      } else {
        TransformationOfProductProperties.addProductProperty(
            product,extendedDesc.getType(),
                HhProductAttributeFqnConstants.Hh_Extended_Desc_Type_Attr_Fqn);
      }
           
    } else {
      
      TransformationOfProductProperties.addProductProperty(product,extendedDesc.getType(),
          HhProductAttributeFqnConstants.Hh_Extended_Desc_Type_Attr_Fqn);
      
    }
    
  }

  private void transformHhProductContentToKiboProductContent(
      Product product, ExtDesc extendedDesc) {
    // TODO Auto-generated method stub
    if (extendedDesc != null) {
      ProductLocalizedContent content = product.getContent();
      content.setLocaleCode(extendedDesc.getLanguage());
      content.setProductFullDescription(extendedDesc.getDescription());
      product.setContent(content);
    }
  }

  private void transformHhProductCodeToKiboProductCode(
      Product product, ExtDesc extendedDesc) {
    // TODO Auto-generated method stub
    product.setProductCode(extendedDesc.getItem());
  }
  
}
