package com.homehardware.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

public class HhItemRestrictedDaoImpl implements HhItemRestrictedDao{

	@Autowired
	EntityManagerFactory entityManagerFactory;

	
	@Override
	public final List getItemRestricted(
			final String batchId,final String status,final String item) {
		final EntityManager entityManager =
				entityManagerFactory.createEntityManager();
			
		final Query query = entityManager.createQuery(
				"from ItemRestricted i where "
				+ "  i.batchId = :batchId"
				+ " and i.status = :status"
				+ " and i.item = :item");
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		query.setParameter("item", item);
		final List list = query.getResultList();
		entityManager.close();
		return list;
	}

}
