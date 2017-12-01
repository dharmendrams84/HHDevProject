/**
 *  =======================================================================================
 *  Description : Transformation of HH Changed Images object to KIBO Changed Images object.
 *  Created By  : Ashwinee Salunkhe
 *  
 *  VERSION     MODIFIED BY               (MM/DD/YY)      Description
 *  =========================================================================================
 *  1.0         Ashwinee Salunkhe           11/17/17      Transformation of HH Changed Images 
 *                                                        object to KIBO Changed Images object.
 *  ==========================================================================================
 */

package com.homehardware.integration.transform.dataentity;

import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.ChangedImages;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductLocalizedImage;
import com.mozu.api.contracts.productadmin.ProductProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Transformation class used to transform HH Changed Images to Kibo Changed Images.
 * 
 * @author Ashwinee Salunkhe
 * @created Nov 17, 2017
 */

public class TransformFromHhChangedImagesToKiboChangedImages {
  
  /**
   * This method is transform HH brand object to KIBO brand object.
   */
  public Product testTransformationFromHhChangedImageToKiboChangedImage(
      Product product, ChangedImages changedImages) {
    
    // transform product code object to kibo product code obj
    transformHhProductCodeToKiboProductCode(product, changedImages);
    
    // transform product image object to kibo product image obj
    transformHhImageToKiboImage(product, changedImages);
    
    // transform product furniture indicator object to kibo product furniture
    // indicator obj
    transformHhFurnitureIndToKiboFurnitutreInd(product, changedImages);
    
    return product;
    
  }
  
  private void transformHhFurnitureIndToKiboFurnitutreInd(
      Product product, ChangedImages changedImages) {
    // TODO Auto-generated method stub
    
    List<ProductProperty> productProperties;
    productProperties = product.getProperties();
    
    if (productProperties != null && productProperties.size() != 0) {
      
      if (TransformationOfProductProperties.isPropertyExists(productProperties,
          HhProductAttributeFqnConstants.Hh_Furniture_Ind_Attr_Fqn)) {
        
        productProperties.forEach(updateProductProperties -> {
          if (updateProductProperties.getAttributeFQN()
              .equalsIgnoreCase(HhProductAttributeFqnConstants.Hh_Furniture_Ind_Attr_Fqn)) {
            
            TransformationOfProductProperties.updateProductPropertiesAttribute(
                updateProductProperties,
                changedImages.getFurnitureInd());
          }
          
        });
        
      } else {
        TransformationOfProductProperties.addProductProperty(
            product, changedImages.getFurnitureInd(),
            HhProductAttributeFqnConstants.Hh_Furniture_Ind_Attr_Fqn);
      }
      
    } else {
      
      TransformationOfProductProperties.addProductProperty(product, changedImages.getFurnitureInd(),
          HhProductAttributeFqnConstants.Hh_Furniture_Ind_Attr_Fqn);
      
    }
    
  }
  
  private void transformHhImageToKiboImage(Product product, ChangedImages changedImages) {
    // TODO Auto-generated method stub
    if (changedImages != null) {
      ProductLocalizedContent content = product.getContent();
      List<ProductLocalizedImage> productImages = content.getProductImages();
      if (productImages != null && productImages.size() != 0) {
        productImages.forEach(images -> {
          images.setId(changedImages.getImageId());
          images.setSequence(changedImages.getImageSeqNbr());
          images.setMediaType(changedImages.getImageType());
          
        });
      } else {
        productImages = new ArrayList<>();
        productImages.forEach(images -> {
          images.setId(changedImages.getImageId());
          images.setSequence(changedImages.getImageSeqNbr());
          images.setMediaType(changedImages.getImageType());
          
        });
      }
    }
    
  }
  
  private void transformHhProductCodeToKiboProductCode(
      Product product, ChangedImages changedImages) {
    // TODO Auto-generated method stub
    product.setProductCode(changedImages.getItem());
  }
  
}
