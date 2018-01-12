package com.homehardware.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

public class HhDynamicAttributesDaoImpl implements HhDynamicAttributesDao {

	

	@Autowired
	EntityManagerFactory entityManagerFactory;

	
	@Override
	public final List getDynamicAttributes(
			final String batchId,final String status,final String productCode) {
		final EntityManager entityManager =
				entityManagerFactory.createEntityManager();
			
		final Query query = entityManager.createQuery(
				"from ItemDynAttr i where "
				+ "  i.batchId = :batchId"
				+ " and i.status = :status"
				+ " and i.item = :item");
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		query.setParameter("item", productCode);
		
		final List list = query.getResultList();
		entityManager.close();
		return list;
	}

	@Override
	public final List getDynamicAttributesInfo(
			final String batchId,final String status) {
		final EntityManager entityManager =
				entityManagerFactory.createEntityManager();
			
		final Query query = entityManager.createQuery(
				"from DynAttrInfo i where "
				+ "  i.batchId = :batchId"
				+ " and i.status = :status"
				);
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		
		final List list = query.getResultList();
		entityManager.close();
		return list;
	}
	
	@Override
	public final List getDynamicAttributesType(
			final String batchId,final String status) {
		final EntityManager entityManager =
				entityManagerFactory.createEntityManager();
			
		final Query query = entityManager.createQuery(
				"from AttrDefinition d where "
				+ "  d.batchId = :batchId"
				+ " and d.status = :status"
				);
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		
		final List list = query.getResultList();
		entityManager.close();
		return list;
	}


}
