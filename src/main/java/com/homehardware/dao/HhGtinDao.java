package com.homehardware.dao;

import java.util.List;

public interface HhGtinDao {

	List getGtin(String batchId, String status, String item);
}
