package com.homehardware.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.homehardware.model.Hierarchy;


public class HhCategoryDaoImpl implements HhCategoryDao{

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@Override
	public final List getHhCategory(
			final String batchId,final String status) {
		final EntityManager entityManager =
				entityManagerFactory.createEntityManager();
			
		final Query query = entityManager.createQuery(
				"from Hierarchy c where "
				+ "  c.batchId = :batchId"
				+ " and c.status = :status");
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		
		final List list = query.getResultList();
		entityManager.close();
		return list;
	}

}
