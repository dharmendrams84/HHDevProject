package com.homehardware.dao;

import java.util.List;

public interface HhDaoObject {

	List getItemsList(String batchId, String status);

	List getRetail(String batchId,String status);
	
	List getPromotion(String batchId,String status,String store);

	
}
