// default package
// Generated Nov 14, 2017 11:46:53 AM by Hibernate Tools 5.2.6.Final

package com.homehardware.model;

import java.util.List;

public class GlobalItem implements java.io.Serializable {
	
	private Item item;
	
	private List<ItemAffiliated> itemAffiliated ;
	
	private List<ItemDynAttr> itemDynAttr;

	private List<ItemEcoFees> itemEcoFees;
	
	private List<ItemInventory> itemInventory;
	
	private List<ItemLoc> itemLoc;
	
	private ItemRestricted itemRestricted;
	
	private List<Promotion> promotion;
	
	private List<RetailMsrp> retailMsrp;
	
	private List<ExtDesc> extDesc;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<ItemAffiliated> getItemAffiliated() {
		return itemAffiliated;
	}

	public void setItemAffiliated(List<ItemAffiliated> itemAffiliated) {
		this.itemAffiliated = itemAffiliated;
	}

	public List<ItemDynAttr> getItemDynAttr() {
		return itemDynAttr;
	}

	public void setItemDynAttr(List<ItemDynAttr> itemDynAttr) {
		this.itemDynAttr = itemDynAttr;
	}

	public List<ItemEcoFees> getItemEcoFees() {
		return itemEcoFees;
	}

	public void setItemEcoFees(List<ItemEcoFees> itemEcoFees) {
		this.itemEcoFees = itemEcoFees;
	}

	public List<ItemInventory> getItemInventory() {
		return itemInventory;
	}

	public void setItemInventory(List<ItemInventory> itemInventory) {
		this.itemInventory = itemInventory;
	}

	public List<ItemLoc> getItemLoc() {
		return itemLoc;
	}

	public void setItemLoc(List<ItemLoc> itemLoc) {
		this.itemLoc = itemLoc;
	}

	public ItemRestricted getItemRestricted() {
		return itemRestricted;
	}

	public void setItemRestricted(ItemRestricted itemRestricted) {
		this.itemRestricted = itemRestricted;
	}

	public List<Promotion> getPromotion() {
		return promotion;
	}

	public void setPromotion(List<Promotion> promotion) {
		this.promotion = promotion;
	}

	public List<RetailMsrp> getRetailMsrp() {
		return retailMsrp;
	}

	public void setRetailMsrp(List<RetailMsrp> retailMsrp) {
		this.retailMsrp = retailMsrp;
	}
	
	
	public List<ExtDesc> getExtDesc() {
		return extDesc;
	}

	public void setExtDesc(List<ExtDesc> extDesc) {
		this.extDesc = extDesc;
	}
	
}
