package com.homehardware.dao;

import java.util.List;

public interface HhItemAttributesDao {

	List getItemAttributes(String batchId, String status, String productCode);

	List getProdAttributes(String batchId, String status);

	List getProdItemAttributes(String batchId, String status, String item);

}
