package com.homehardware.dao;

import java.util.List;

public interface HhItemLocDao {

	List getItemLocs(String batchId, String status, String item);

	
}
