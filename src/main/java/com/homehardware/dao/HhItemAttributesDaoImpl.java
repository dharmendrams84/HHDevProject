package com.homehardware.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

public class HhItemAttributesDaoImpl implements HhItemAttributesDao{

	@Autowired
	EntityManagerFactory entityManagerFactory;

	
	@Override
	public final List getItemAttributes(
			final String batchId,final String status,final String productCode) {
		final EntityManager entityManager =
				entityManagerFactory.createEntityManager();
			
		final Query query = entityManager.createQuery(
				"from ProductItemAttributes p where "
				+ "  p.batchId = :batchId"
				+ " and p.status = :status"
				+ " and p.id.item = :item");
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		query.setParameter("item", productCode);
		
		final List list = query.getResultList();
		
		entityManager.close();
		return list;
	}
	
	
	@Override
	public final List getProdAttributes(
			final String batchId,final String status) {
		final EntityManager entityManager =
				entityManagerFactory.createEntityManager();
			
		final Query query = entityManager.createQuery(
				"from ProductAttr p where "
				+ "  p.batchId = :batchId"
				+ " and p.status = :status"
				);
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
				
		final List list = query.getResultList();
		
		entityManager.close();
		return list;
	}
	
	
	@Override
	public final List getProdItemAttributes(
			final String batchId,final String status,final String item) {
		final EntityManager entityManager =
				entityManagerFactory.createEntityManager();
			
		final Query query = entityManager.createQuery(
				"from ProductItemAttr p where "
				+ "  p.batchId = :batchId"
				+ " and p.status = :status"
				+ " and p.id.item = :item");
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		query.setParameter("item", item);
				
		final List list = query.getResultList();
		
		entityManager.close();
		return list;
	}

}
