package com.homehardware.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

public class HhExtDescDaoImpl implements HhExtDescDao{

	
	@Autowired
	EntityManagerFactory entityManagerFactory;

	
	@Override
	public final List getExtDesc(
			final String batchId,final String status,final String item) {
		final EntityManager entityManager =
				entityManagerFactory.createEntityManager();
			
		final Query query = entityManager.createQuery(
				"from ExtDesc e where "
				+ "  e.batchId = :batchId"
				+ " and e.status = :status"
				+ " and e.item = :item");
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		query.setParameter("item", item);
		final List list = query.getResultList();
		entityManager.close();
		return list;
	}



}
