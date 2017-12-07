package com.homehardware.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.Transactional;



@Transactional
public class HhDaoObjectImpl implements HhDaoObject {

	@PersistenceUnit
	EntityManagerFactory entityManagerFactory;
	
	@Override
	public final List getItemsList(final String batchId,final String status) {
		final EntityManager entityManager =
				entityManagerFactory.createEntityManager();
			
		final Query query = entityManager.createQuery(
				"from Item i where i.id.batchId ="
				+ " :batchId and i.status = :status");
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		final List list = query.getResultList();
		
		return list;
	}
	
	
}
