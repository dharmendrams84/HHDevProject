package com.homehardware.processor;

import java.util.List;

import org.springframework.stereotype.Component;

import com.homehardware.constants.HhProductAttributeFqnConstants;
import com.homehardware.model.ItemLoc;
import com.homehardware.utility.ProductUtility;
import com.mozu.api.contracts.core.Measurement;
import com.mozu.api.contracts.productadmin.Product;

@Component
public class HhItemLocProcessor {

	/**
	 * @param product.
	 * @param itemLocs.
	 */
	public void setProductItemLocation(final Product product, final List<ItemLoc> itemLocs) {
		if (itemLocs != null && itemLocs.size() != 0) {
			for (ItemLoc itemLoc : itemLocs) {
				setProductMeasurment(product, itemLoc);
				ProductUtility productutility = new ProductUtility();
				productutility.addOrUpdateProperty(product,
						HhProductAttributeFqnConstants
						.Hh_VendorProductNumber_Attr_Fqn,
						itemLoc.getVpn());
			}
		}
	}
	
	/**
	 * @param product.
	 * @param itemLoc.
	 */
	public  void setProductMeasurment(final Product product, final ItemLoc itemLoc) {
		
		// TODO Auto-generated method stub
		if (product.getPackageWeight() != null
				&& product.getPackageWeight().getValue() != null 
				&& Double.SIZE != 0) {
			product.getPackageWeight().setValue(itemLoc.getWeight());
			
		} else {
			final Measurement measurement = new Measurement();
			measurement.setValue(itemLoc.getWeight());
			measurement.setUnit(itemLoc.getWeightUom());
			product.setPackageWeight(measurement);

		}

	}

	
}
