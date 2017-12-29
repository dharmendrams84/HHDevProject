// default package
// Generated Dec 19, 2017 4:21:23 PM by Hibernate Tools 5.2.6.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Retail.
 * @see .Retail
 * @author Hibernate Tools
 */
@Stateless
public class RetailHome {

	private static final Log log = LogFactory.getLog(RetailHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Retail transientInstance) {
		log.debug("persisting Retail instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Retail persistentInstance) {
		log.debug("removing Retail instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Retail merge(Retail detachedInstance) {
		log.debug("merging Retail instance");
		try {
			Retail result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Retail findById(Integer id) {
		log.debug("getting Retail instance with id: " + id);
		try {
			Retail instance = entityManager.find(Retail.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
