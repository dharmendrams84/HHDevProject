package com.homehardware.dao;

import java.util.List;

public interface HhBrandDao {

	List getBrands(String batchId, String status, String item);

}
