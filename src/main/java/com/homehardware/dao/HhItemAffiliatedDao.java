package com.homehardware.dao;

import java.util.List;

public interface HhItemAffiliatedDao {

	List getItemAffiliated(String batchId, String status, String item);

}
