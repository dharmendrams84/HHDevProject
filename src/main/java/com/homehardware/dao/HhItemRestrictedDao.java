package com.homehardware.dao;

import java.util.List;

public interface HhItemRestrictedDao {

	List getItemRestricted(String batchId, String status, String item);

}
