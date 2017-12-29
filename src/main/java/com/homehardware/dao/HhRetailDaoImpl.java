package com.homehardware.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HhRetailDaoImpl implements HhRetailDao {

	@Autowired
	EntityManagerFactory entityManagerFactory;

	
	@Override
	public final List getRetailForItem(
			final String batchId,final String status,
			final String productCode) {
		final EntityManager entityManager =
				entityManagerFactory.createEntityManager();
			
		final Query query = entityManager.createQuery(
				"from Retail r where "
				+ "  r.batchId = :batchId"
				+ " and r.status = :status"
				+" and r.item = :item");
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		query.setParameter("item", productCode);
		final List list = query.getResultList();
		entityManager.close();
		return list;
	}

}
