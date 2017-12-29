// default package
// Generated Dec 27, 2017 12:53:20 PM by Hibernate Tools 5.2.6.Final
package com.homehardware.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class ProductItemAttributes.
 * @see .ProductItemAttributes
 * @author Hibernate Tools
 */
@Stateless
public class ProductItemAttributesHome {

	private static final Log log = LogFactory.getLog(ProductItemAttributesHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(ProductItemAttributes transientInstance) {
		log.debug("persisting ProductItemAttributes instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(ProductItemAttributes persistentInstance) {
		log.debug("removing ProductItemAttributes instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public ProductItemAttributes merge(ProductItemAttributes detachedInstance) {
		log.debug("merging ProductItemAttributes instance");
		try {
			ProductItemAttributes result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ProductItemAttributes findById(ProductItemAttributesId id) {
		log.debug("getting ProductItemAttributes instance with id: " + id);
		try {
			ProductItemAttributes instance = entityManager.find(ProductItemAttributes.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
