/**
 *  =======================================================================================
 *  Description : Transformation of Product properties common methods.
 *  Created By  : Ashwinee Salunkhe
 *  
 *  VERSION     MODIFIED BY               (MM/DD/YY)      Description
 *  ========================================================================================
 *  1.0         Ashwinee Salunkhe           11/17/17      Transformation of HH Affilited Item  
 *                                                        object to KIBO Affilited Item object.
 *  =========================================================================================
 */

package com.homehardware.integration.transform.dataentity;

import com.homehardware.model.ItemAffiliated;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductProperty;
import com.mozu.api.contracts.productadmin.ProductPropertyValue;
import com.mozu.api.contracts.productadmin.ProductPropertyValueLocalizedContent;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Transformation class for common methods.
 * 
 * @author Ashwinee Salunkhe
 * @created Nov 17, 2017
 */

public class TransformationOfProductProperties {
  
  protected static final Logger logger = Logger.getLogger(TransformationOfProductProperties.class);
  
  protected static void updateProductPropertiesAttribute(ProductProperty productPropertiesAction,
      String productPropertyAttrValue) {
    // TODO Auto-generated method stub
    try {
      ProductPropertyValue productPropertyValue = productPropertiesAction.getValues().get(0);
      if (productPropertyValue instanceof ProductPropertyValue 
          && productPropertyValue.getContent() != null) {
        productPropertyValue.setValue(productPropertyAttrValue);
        productPropertyValue.getContent().setStringValue(productPropertyAttrValue);
        
      }
    } catch (Exception e) {
      logger.error(" Exception while updating property "
          + "value" + productPropertyAttrValue
          + " Due to Exception = " + e.getCause());
      
    }
  }
  
  protected static void addProductProperty(Product product,
      String productPropertyAttrValue, String productAttrFqn) {
    try { 
      ProductProperty productPropertyAttr = new ProductProperty();
      productPropertyAttr.setAttributeFQN(productAttrFqn);
      
      ProductPropertyValue productPropertyValueAttr = new ProductPropertyValue();
      productPropertyValueAttr.setValue(productPropertyAttrValue);
      
      ProductPropertyValueLocalizedContent content = new ProductPropertyValueLocalizedContent();
      content.setStringValue(productPropertyAttrValue);
      productPropertyValueAttr.setContent(content);
      
      List<ProductPropertyValue> productPropertyValue = new ArrayList<ProductPropertyValue>();
      productPropertyValue.add(productPropertyValueAttr);
      productPropertyAttr.setValues(productPropertyValue);
      
      List<ProductProperty> productProperties = product.getProperties();
      productProperties.add(productPropertyAttr);
      
      product.setProperties(productProperties);
      
    } catch (Exception e) {
      logger.error(" Exception while Adding property "
          + "value" + productAttrFqn
          + " Due to Exception = " + e.getCause());
    }
  }
  
  static boolean isPropertyExists(List<ProductProperty> productProperties, String attributeFqn) {
    boolean isExist = false;
    for (ProductProperty p : productProperties) {
      if (p.getAttributeFQN().equalsIgnoreCase(attributeFqn)) {
        isExist = true;
        break;
      }
    }
    return isExist;
  }

  protected static void addProductPropertiesAttributeForAffiliatedItem(Product product,
      String productAttrFqn,List<ItemAffiliated> affiliatedItems) {
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
      productProperties.add(productProperty);
      product.setProperties(productProperties);
      
    } catch (Exception e) {
      logger.error(" Exception while Adding property product cross sell "
          + " Due to Exception = " + e.getCause());      
    }
    
  }
  
  protected static void updateProductPropertiesAttributeForAffiliatedItem(
      ProductProperty updateProductProperties,
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
      logger.error(" Exception while updating property "
          + "product cross sell" + " Due to Exception = " + e.getCause());
    }
  }
  
  protected static ProductPropertyValue createProductProperty(String value) {
    ProductPropertyValue productPropertyValue = new ProductPropertyValue();
    ProductPropertyValueLocalizedContent content = new ProductPropertyValueLocalizedContent();
    content.setStringValue(value);
    productPropertyValue.setContent(content);
    return productPropertyValue;
  }

}
