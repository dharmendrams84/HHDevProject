package com.homehardware.utility;

import com.hh.integration.constants.Constant;
import com.homehardware.model.Promotion;
import com.homehardware.model.Retail;
import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.productadmin.PriceList;
import com.mozu.api.contracts.productadmin.PriceListEntry;
import com.mozu.api.contracts.productadmin.PriceListEntryPrice;
import com.mozu.api.resources.commerce.catalog.admin.PriceListResource;
import com.mozu.api.resources.commerce.catalog.admin.pricelists.PriceListEntryResource;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public final class PriceListUtility {
	
	protected static final Logger logger = Logger.getLogger(PriceListUtility.class);

	public static final String OVERRIDDEN_PRICE_MODE = "Overridden";
	public static final String NC = "NC";



	/**
	 * @param priceList.
	 */
	public void createNewPricelist(
			final PriceList priceList,final String priceListCode) {
		//priceList = new PriceList();
		priceList.setName(priceListCode);
		priceList.setEnabled(true);
		priceList.setFilteredInStorefront(false);
		priceList.setPriceListCode(priceListCode);
		//priceList.setPriceListSequence(0);
		priceList.setResolvable(true);
		priceList.setValidForAllSites(true);
		
	}
	
	
	/**
	 * @param priceListEntry.
	 */
	public void convertPriceListEntry(
			final PriceListEntry priceListEntry,final Retail retail,
			final Promotion promotion) {
	
		priceListEntry.setProductCode(retail.getItem());
		priceListEntry.setCurrencyCode("CAD");
		List<PriceListEntryPrice> priceListEntryPrices = new ArrayList<>();
		/*if(priceListEntryPrices==null){
			priceListEntryPrices = new ArrayList
		}*/
		final PriceListEntryPrice priceListEntryPrice = new PriceListEntryPrice();
		createNewPriceListEntryPrice(priceListEntryPrice,
				promotion,retail.getRetail().doubleValue());
		priceListEntryPrices.add(priceListEntryPrice);
		priceListEntry.setPriceEntries(priceListEntryPrices);
		
	}
	
	/**
	 * @param priceListEntryPrice.
	 */
	
	public void createNewPriceListEntryPrice(
			final PriceListEntryPrice priceListEntryPrice,final Promotion promotion,
			final double retailPrice) {
		
		/*priceListEntryPrice.setSalePriceMode("UseCatalog");
		priceListEntryPrice.setListPriceMode("UseCatalog");*/
		priceListEntryPrice.setSalePriceMode(OVERRIDDEN_PRICE_MODE); 
		if (promotion != null
				&& promotion.getPromoItemType() != null 
				&& promotion.getPromoItemType().length() != 0
				&& !promotion.getPromoItemType().equalsIgnoreCase(NC)) {
			
			priceListEntryPrice.setSalePrice(promotion.getPromoRetail());
			
		}
		priceListEntryPrice.setListPriceMode(OVERRIDDEN_PRICE_MODE);
		priceListEntryPrice.setListPrice(retailPrice);
		
		priceListEntryPrice.setMinQty(Constant.int_1);
		
	}
	
	/**
	 * @param promotions.
	 * @param item.
	 * @.return
	 */
	public final Promotion getPromotionForItem(
			final List<Promotion> promotions, final String item) {
		Promotion promotion = null;
		for (Promotion p : promotions) {
			if (p.getDItem().equalsIgnoreCase(item)) {
				promotion = p;
			}
		}
		return promotion;
	}
	
	
	
	/**
	 * @param priceList.
	 * @param apiContext.
	 * @.throws Exception
	 */
	public void addPriceList(
			final PriceList priceList, final ApiContext apiContext,
			final PriceListResource priceListResource,
			final String priceListCode) {
	
		try {
			createNewPricelist(priceList, priceListCode);
			/*
			 * final PriceListResource priceListResource = new
			 * PriceListResource(apiContext);
			 */
			priceListResource.addPriceList(priceList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @param list.
	 * @param promotions.
	 * @param apiContext.
	 */
	public void addOrUpdatePriceListEntries(
			final List list,
			final List<Promotion> promotions,final ApiContext apiContext,
			final String priceListCode) {
		try {
			for (Object o : list) {
				final String currencyCode = "CAD";
				final Retail retail = (Retail) o;
				PriceListEntry priceListEntry = null;
				final PriceListEntryResource priceListEntryResource 
					= new PriceListEntryResource(apiContext);
				final String item = retail.getItem();
				priceListEntry = priceListEntryResource
						.getPriceListEntry(retail.getStore(),
								retail.getItem(), currencyCode);
				final Promotion promotion = 
						getPromotionForItem(promotions, item);
				if (promotion == null) {
					logger.info("No Promotion found for product code " + item);
				}
				if (priceListEntry == null) {
					priceListEntry = new PriceListEntry();
					addNewPriceListEntry(priceListEntry, retail,
							promotion, priceListEntryResource,
							priceListCode);
				} else {
					convertPriceListEntry(priceListEntry, retail, promotion);
					priceListEntryResource
						.updatePriceListEntry(priceListEntry,
							priceListCode, item, currencyCode);
					logger.info("price list entry for item " + item + 
							 " updated successfully!!!!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param priceListEntry.
	 * @param retail.
	 * @param promotion.
	 * @param priceListEntryResource.
	 * @param priceListCode.
	 */
	public void addNewPriceListEntry(

			final PriceListEntry priceListEntry, 
			final Retail retail, final Promotion promotion,
			final PriceListEntryResource priceListEntryResource,
			final String priceListCode) {
		try {
			convertPriceListEntry(priceListEntry, retail, promotion);

			final PriceListEntry newPriceListEntry 
				= priceListEntryResource
				.addPriceListEntry(priceListEntry, priceListCode);
			if (newPriceListEntry != null) {
				logger.info(
						"price list entry for item "
								+ retail.getItem() 
								+ " added successfully!!!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	

}
