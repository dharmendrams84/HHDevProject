package com.homehardware.dao;

import java.util.List;

public interface HhDaoObject {

	List getItemsList(String batchId, String status);
}
