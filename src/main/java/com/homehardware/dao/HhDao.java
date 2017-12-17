package com.homehardware.dao;

import com.homehardware.beans.Store;
import com.homehardware.model.EcoFees;
import com.homehardware.model.ExtDesc;
import com.homehardware.model.Item;
import com.homehardware.model.ItemAffiliated;
import com.homehardware.model.ProductAttribute;
import com.homehardware.model.ProductItemAttributes;

import java.util.List;

public interface HhDao {

	/*void saveEmployee(final Employee e);
	void saveItemDetails();*/
	public Store getStore();

	void getEcoFees();

	//Item getItem();

	List<ProductAttribute> getProductAttributesList();
	
	//List<ProductAttribute> getProductAttributesList(String productCode);

	ProductItemAttributes getProductItemAttribute();

	ProductItemAttributes getProductItemAttribute(String attributeFqn, String itemId);
	
	

	void updateProductItemAttributes(ProductItemAttributes productItemAttributes);

	List<ExtDesc> geExtendedDescription(String item);
	
	public List<EcoFees> getEcoFes();

	Item getProductDetailsFromDb();

	public Item getItem(String itemId,
			String batchId ,String status);

	List<ItemAffiliated> getItemAffliated(String itemId, String batchId ,String status);

	List getItemsList(String batchId, String status);

	/*List<ItemAffiliated> getItemDynAttribute(String itemId, String batchId, String status);*/
}
