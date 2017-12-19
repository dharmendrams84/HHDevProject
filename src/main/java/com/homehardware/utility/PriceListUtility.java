package com.homehardware.utility;

import com.hh.integration.constants.Constant;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.productadmin.PriceList;
import com.mozu.api.contracts.productadmin.PriceListEntry;
import com.mozu.api.contracts.productadmin.PriceListEntryPrice;
import com.mozu.api.resources.commerce.catalog.admin.PriceListResource;
import com.mozu.api.resources.commerce.catalog.admin.pricelists.PriceListEntryResource;

import java.util.ArrayList;
import java.util.List;


public final class PriceListUtility {

	/**
	 * @param priceList.
	 */
	public void createNewPricelist(
			final PriceList priceList) {
		//priceList = new PriceList();
		priceList.setName("PriceList");
		priceList.setEnabled(true);
		priceList.setFilteredInStorefront(false);
		priceList.setPriceListCode("PC002");
		//priceList.setPriceListSequence(0);
		priceList.setResolvable(true);
		priceList.setValidForAllSites(true);
		
	}
	
	
	/**
	 * @param priceListEntry.
	 */
	public void createNewPriceListEntry(
			final PriceListEntry priceListEntry) {
	
		priceListEntry.setProductCode("4466-430");
		priceListEntry.setCurrencyCode("CAD");
		//priceListEntry.setPriceListEntrySequence(Constant.int_7);
		final List<PriceListEntryPrice> priceListEntryPrices = new ArrayList<>();
		final PriceListEntryPrice priceListEntryPrice = new PriceListEntryPrice();
		createNewPriceListEntryPrice(priceListEntryPrice);
		priceListEntryPrices.add(priceListEntryPrice);
		priceListEntry.setPriceEntries(priceListEntryPrices);
		
	}
	
	/**
	 * @param priceListEntryPrice.
	 */
	
	public void createNewPriceListEntryPrice(
			final PriceListEntryPrice priceListEntryPrice) {
		
		priceListEntryPrice.setSalePriceMode("UseCatalog");
		priceListEntryPrice.setListPriceMode("UseCatalog");
		priceListEntryPrice.setMinQty(Constant.int_1);
	}
	
	/**
	 * @param priceList.
	 * @param apiContext.
	 * @.throws Exception
	 */
	public void addPriceList(
			final PriceList priceList, final ApiContext apiContext,
			final PriceListResource priceListResource) 
					throws Exception {

		createNewPricelist(priceList);
		/*final PriceListResource priceListResource = new PriceListResource(apiContext);*/
		priceListResource.addPriceList(priceList);
		final PriceListEntry priceListEntry = new PriceListEntry();
		createNewPriceListEntry(priceListEntry);
		final PriceListEntryResource priceListEntryResource
		    = new PriceListEntryResource(apiContext);
		priceListEntryResource
		.addPriceListEntry(priceListEntry, priceList.getPriceListCode());
	}
			
}
