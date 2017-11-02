package com.homehardware.dao;

import com.homehardware.beans.Employee;
import com.homehardware.model.ItemDynAttrId;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
@Transactional
public class HhDaoImpl implements HhDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ItemDynAttrId itemDynAttrId;
		/*
	 * (non-Javadoc)
	 * 
	 * @see com.homehardware.dao.HhDao#saveEmployee(com.homehardware.Employee)
	 */
	@Override
	public final void saveEmployee(final Employee e) {
		final Session session = sessionFactory.openSession();
		final org.hibernate.Transaction tx = session.beginTransaction();
		session.save(e);
		tx.commit();
		session.flush();
	}

	public final void setEmployeeDetails(final Employee employee) {
		employee.setEmail("nehab@gmail.com");
		employee.setFirstname("neha");
		employee.setLastname("Bhatia");
		employee.setTelephone("88877766");
	}
	
	@Override
	@Transactional
	public final void saveItemDetails() {
		setItemDynAttr();
		final Session session = sessionFactory.openSession();
		final org.hibernate.Transaction tx = session.beginTransaction();
		session.save(itemDynAttrId);
		tx.commit();
		session.flush();
	}
	
	
	public final void setItemDynAttr() {
		itemDynAttrId.setItem("PRD001");
		itemDynAttrId.setDynAttrDesc("test desc");
		
	}
}
