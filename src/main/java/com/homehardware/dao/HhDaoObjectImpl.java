package com.homehardware.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import com.homehardware.model.Promotion;


@Repository
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
		entityManager.close();
		return list;
	}
	
	
	
	@Override
	public final List getRetail(
			final String batchId,final String status) {
		final EntityManager entityManager =
				entityManagerFactory.createEntityManager();
			
		final Query query = entityManager.createQuery(
				"from Retail r where "
				+ "  r.batchId = :batchId"
				+ " and r.status = :status");
		//query.setParameter("store", store);
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		final List list = query.getResultList();
		entityManagerFactory.close();
		return list;
	}
	
	
	@Override
	public final List<Promotion> getPromotion(final String batchId,
			final String status , final String store) {
		final EntityManager entityManager =
				entityManagerFactory.createEntityManager();
			
		final Query query = entityManager.createQuery(
				"from Promotion p where p.store = :store "
				+ " and p.batchId = :batchId"
				+ " and p.status = :status");
		query.setParameter("store", Integer.parseInt(store));
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		final List list = (List<Promotion>)query.getResultList();
		entityManagerFactory.close();
		return list;
	}


}
