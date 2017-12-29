package com.homehardware.dao;

import java.util.List;

public interface HhDynamicAttributesDao {
	
	List getDynamicAttributes(String batchId, String status, String productCode);
	
	List getDynamicAttributesInfo(String batchId, String status);
	
	List getDynamicAttributesType(String batchId, String status);

}
