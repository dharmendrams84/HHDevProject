/**
 *  ====================================================================================
 *  Description : Transformation of HH promotion object to KIBO promotion object.
 *  Created By  : Ashwinee Salunkhe
 *  
 *  VERSION     MODIFIED BY               (MM/DD/YY)      Description
 *  =====================================================================================
 *  1.0         Ashwinee Salunkhe           11/17/17      Transformation of HH promotion 
 *                                                        object to KIBO promotion object.
 *  =====================================================================================
 */

package com.homehardware.integration.transform.dataentity;

import com.hardware.constants.Utility;
import com.homehardware.model.Promotion;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.productadmin.Discount;
import com.mozu.api.contracts.productadmin.DiscountCondition;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductPrice;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

/**
 * Transformation class to transform from HH Promotion to Kibo Promotion.
 * 
 * @author Ashwinee Salunkhe
 * @created Nov 17, 2017
 */
public class TransformFromHhPromotionToKiboPromotion {
  
  /**
   * This method is used to transform HH promotion object to KIBO promotion obj.
   */
  public static void testTransformationFromHhPromotionToKiboPromotion(
      Product product, Promotion promotion/*,Location location*/) {
    
    // set start date
    setStartDateAndEndDate(product, promotion);
        
    // set promo retail and  marketing retail
    setRetailPriceAndMarketingPrice(product,promotion);
    
    // set Item code
    product.setProductCode(promotion.getDItem());
    
    // set location code
   // location.setCode(new Integer(promotion.getStore()).toString());
    
  }
  
  @SuppressWarnings("unchecked")
  protected static void setRetailPriceAndMarketingPrice(Product product,Promotion promotion) {
    // TODO Auto-generated method stub
    List<ProductPrice> productPrice = (List<ProductPrice>) product.getPrice();
    if (productPrice != null && productPrice.size() != 0) {
      productPrice.forEach(price -> {
        price.setMsrp(Double.parseDouble(promotion.getMarketingRetail().toString()));
        price.setPrice(Double.parseDouble(promotion.getPromoRetail().toString()));
      });
       
    } else {
      productPrice = new ArrayList<ProductPrice>();
      productPrice.forEach(price -> {
        price.setMsrp(Double.parseDouble(promotion.getMarketingRetail().toString()));
        price.setPrice(Double.parseDouble(promotion.getPromoRetail().toString()));
      });
      product.setPrice((ProductPrice) productPrice);
    }
    
  }

  @SuppressWarnings({ "unchecked" })
  protected static void setStartDateAndEndDate(Product product, Promotion promotion) {
    // TODO Auto-generated method stub
    
    List<Discount> discountList = product.getApplicableDiscounts();
    if (discountList != null && discountList.size() != 0) {
      discountList.forEach(discountListAction -> {
      
        List<DiscountCondition> discountCondition = 
            (List<DiscountCondition>) discountListAction.getConditions();
        if (discountCondition != null && discountCondition.size() != 0) {
          discountCondition.forEach(discountConditionAction -> {
            discountConditionAction.setStartDate(Utility.convertDateToDateTime(promotion.getStartDate())); 
            discountConditionAction.setExpirationDate(Utility.convertDateToDateTime(promotion.getEndDate()));
          });
          
        } else {
          discountCondition = new ArrayList<>();
          discountCondition.forEach(discountConditionAction -> {
            discountConditionAction.setStartDate(Utility.convertDateToDateTime(promotion.getStartDate())); 
            discountConditionAction.setExpirationDate(Utility.convertDateToDateTime(promotion.getEndDate()) );
          });
          discountListAction.setConditions((DiscountCondition) discountCondition);
        }
      });
      
    } else {
      discountList = new ArrayList<>();
      discountList.forEach(discountListAction -> {
        List<DiscountCondition> discountCondition = 
            (List<DiscountCondition>) discountListAction.getConditions();
        discountCondition.forEach(discountConditionAction -> {
          discountConditionAction.setStartDate(Utility.convertDateToDateTime(promotion.getStartDate())); 
          discountConditionAction.setExpirationDate(Utility.convertDateToDateTime(promotion.getEndDate()));
        });
      });
      product.setApplicableDiscounts(discountList);
    }
    
  }
  
}
