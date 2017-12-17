// default package
// Generated Dec 12, 2017 12:25:58 PM by Hibernate Tools 5.2.6.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Images.
 * @see .Images
 * @author Hibernate Tools
 */
@Stateless
public class ImagesHome {

	private static final Log log = LogFactory.getLog(ImagesHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Images transientInstance) {
		log.debug("persisting Images instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Images persistentInstance) {
		log.debug("removing Images instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Images merge(Images detachedInstance) {
		log.debug("merging Images instance");
		try {
			Images result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Images findById(Integer id) {
		log.debug("getting Images instance with id: " + id);
		try {
			Images instance = entityManager.find(Images.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
