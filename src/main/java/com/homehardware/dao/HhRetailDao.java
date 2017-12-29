package com.homehardware.dao;

import java.util.List;


public interface HhRetailDao {

	List getRetailForItem(String batchId, String status,String productCode);

	
}
