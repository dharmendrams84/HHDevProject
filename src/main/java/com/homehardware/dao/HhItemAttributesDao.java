package com.homehardware.dao;

import java.util.List;

public interface HhItemAttributesDao {

	List getItemAttributes(String batchId, String status, String productCode);

}
