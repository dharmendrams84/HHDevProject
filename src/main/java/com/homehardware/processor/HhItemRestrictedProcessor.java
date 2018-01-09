package com.homehardware.processor;

import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.ItemRestricted;
import com.mozu.api.ApiContext;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class HhItemRestrictedProcessor {

	/**
	 * @param itemRestricteds.
	 * @param apiContext.
	 */
	public void transformHhItemRestricted(
			final List<ItemRestricted> itemRestricteds ,
			final ApiContext apiContext) {
		if (itemRestricteds != null && itemRestricteds.size() != 0) {
			for (ItemRestricted itemRestricted : itemRestricteds) {
				
				final String productCode = itemRestricted.getItem();
				PropertyUtility.addOrUpdateProperty(itemRestricted.getWebsiteInd(),
						HhProductAttributeFqnConstants
						.Hh_Website_Ind_Attr_Fqn, apiContext,productCode);
							
				PropertyUtility.addOrUpdateProperty(
						itemRestricted.getEcommerceInd(),
						HhProductAttributeFqnConstants
						.Hh_Ecomm_Ind_Attr_Fqn, apiContext,productCode);
			}
		}
	}
}
