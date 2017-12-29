package com.homehardware.dao;

import java.util.List;

public interface HhExtDescDao {

	List getExtDesc(String batchId, String status, String item);
}
