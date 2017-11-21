package com.homehardware.dao;

import java.util.List;

import com.homehardware.beans.Store;
import com.homehardware.model.ExtDesc;
import com.homehardware.model.Item;
import com.homehardware.model.ProductAttribute;
import com.homehardware.model.ProductItemAttributes;

public interface HhDao {

	/*void saveEmployee(final Employee e);
	void saveItemDetails();*/
	public Store getStore();

	void getEcoFees();

	Item getItem();

	List<ProductAttribute> getProductAttributesList();
	
	//List<ProductAttribute> getProductAttributesList(String productCode);

	ProductItemAttributes getProductItemAttribute();

	ProductItemAttributes getProductItemAttribute(String attributeFqn, String itemId);
	
	

	void updateProductItemAttributes(ProductItemAttributes productItemAttributes);

	List<ExtDesc> geExtendedDescription(String item);
}
