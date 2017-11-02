package com.homehardware.dao;

import com.homehardware.beans.Employee;


public interface HhDao {

	void saveEmployee(final Employee e);
	void saveItemDetails();
}
