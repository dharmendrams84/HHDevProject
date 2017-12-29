package com.homehardware.dao;

import java.util.List;

public interface HhItemImagesDao {

	List getItemImages(String batchId, String status, String item);

}
