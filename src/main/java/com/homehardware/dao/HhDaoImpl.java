package com.homehardware.dao;

import com.homehardware.beans.Employee;
import com.homehardware.beans.Store;
import com.homehardware.model.EcoFees;
import com.homehardware.model.ExtDesc;
import com.homehardware.model.Item;
import com.homehardware.model.ItemAffiliated;
import com.homehardware.model.ProductAttribute;
import com.homehardware.model.ProductItemAttributes;
import com.homehardware.model.ProductItemAttributesId;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HhDaoImpl implements HhDao {

	protected static final Logger logger = Logger.getLogger(HhDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	
	/*
	 * @Autowired private ItemDynAttrId itemDynAttrId;
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.homehardware.dao.HhDao#saveEmployee(com.homehardware.Employee)
	 */
	/*
	 * @Override public final void saveEmployee(final Employee e) { final
	 * Session session = sessionFactory.openSession(); final
	 * org.hibernate.Transaction tx = session.beginTransaction();
	 * session.save(e); tx.commit(); session.flush(); }
	 */

	/**
	 * Sets all attribute values of employee object.
	 * 
	 */
	public final void setEmployeeDetails(final Employee employee) {
		employee.setEmail("nehab@gmail.com");
		employee.setFirstname("neha");
		employee.setLastname("Bhatia");
		employee.setTelephone("88877766");
	}

	// @Override
	/*
	 * public final void saveItemDetails() { setItemDynAttr(); final Session
	 * session = sessionFactory.openSession(); final org.hibernate.Transaction
	 * tx = session.beginTransaction(); session.save(itemDynAttrId);
	 * tx.commit(); session.flush(); }
	 * 
	 * 
	 * public final void setItemDynAttr() { //itemDynAttrId.setItem("PRD001");
	 * itemDynAttrId.setDynAttrDesc("test desc");
	 * 
	 * }
	 */

	@Override
	public final Store getStore() {
		Store store = new Store();
		final Session session = sessionFactory.openSession();
		store = (Store) session.get(Store.class, 1);
		session.close();
		System.out.println("store website url " + store.getStoreWebsiteUrl());
		return store;
	}

	@Override
	public final void getEcoFees() {

		final Session session = sessionFactory.openSession();
		final EcoFees ecoFees = (EcoFees) session.get(EcoFees.class, 1);
		System.out.println(ecoFees);
		session.close();

	}
	
	/*@Override
	public final Item getItem() {

		final Session session = sessionFactory.openSession();
		final Item item = (Item) session.get(Item.class, 1);
		System.out.println(item);
		session.close();
		//session.flush();
		return item;

	}*/
	
	
	@Override
	public final Item getItem(final String itemId,final String batchId,final String status) {

		final Session session = sessionFactory.openSession();
		final String queryString = "from Item i where i.id.item = :item "
				+ "and i.id.batchId = :batchId and i.status = :status";
		
		final Query query = session.createQuery(queryString);
		query.setParameter("item", itemId);
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		
		final List list = query.list();
		final Item item = (Item)list.get(0);
		/*
		ItemId itemIdObj = new ItemId();
		itemIdObj.setItem(itemId);
		itemIdObj.setBatchId(batchId);
		
		Item item = (Item) session.get(Item.class, itemIdObj);*/
		
		session.close();
		return item;
	}
	
	@Override
	public final List getItemsList(final String batchId,final String status) {

		final Session session = sessionFactory.openSession();
		final String queryString = 
				"from Item i where i.id.batchId = :batchId and i.status = :status";
		final Query query = session.createQuery(queryString);
	
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		
		final List list = query.list();
		
		return list;
	}
	
	@Override
	public final List<ItemAffiliated> getItemAffliated(
			final String itemId,final String batchId,final String status) {

		final Session session = sessionFactory.openSession();
		final String queryString = 
				"from ItemAffiliated i where i.item = :item and "
				+ "i.batchId = :batchId and i.status = :status";
		final Query query = session.createQuery(queryString);
		query.setParameter("item", itemId);
		query.setParameter("batchId", batchId);
		query.setParameter("status", status);
		
		final List<ItemAffiliated> list = query.list();
		session.close();
		return list;
	}
	
	@Override
	public final List<ProductAttribute> getProductAttributesList() {

		final Session session = sessionFactory.openSession();
		final Query query = session.createQuery("from ProductAttribute");
		final List<ProductAttribute> productAttributes = query.list();   
		//System.out.println(productAttribute.getAttributeName());
		session.close();
		//session.flush();
		return productAttributes;
	}
	
	
	/*@Override
	public final List<ProductAttribute> getProductAttributesList(String productCode) {

		final Session session = sessionFactory.openSession();
		Query query = session.createQuery
		("from ProductAttribute p where p.productAttrId = :productAttrId");
		query.setParameter("productAttrId", productCode);

		List<ProductAttribute> productAttributes = query.list();   
		//System.out.println(productAttribute.getAttributeName());
		session.close();
		//session.flush();
		return productAttributes;
	}

*/	
	@Override
	public final ProductItemAttributes getProductItemAttribute() {

		final Session session = sessionFactory.openSession();
		final ProductItemAttributesId productItemAttributesId
		    = new ProductItemAttributesId();
		productItemAttributesId.setItem("4466-443");
		productItemAttributesId.setProductAttrId("tenant~hh-consumer-item-desc");
		final ProductItemAttributes productItemAttributes 
		    = (ProductItemAttributes)session
		        .get(ProductItemAttributes.class, productItemAttributesId);
		System.out.println(productItemAttributes);
		session.close();
		//session.flush();
		return productItemAttributes;
	}
	
	
	@Override
	public final ProductItemAttributes 
		getProductItemAttribute(final String attributeFqn,final String itemId) {

		final Session session = sessionFactory.openSession();
		final ProductItemAttributesId productItemAttributesId 
		    = new ProductItemAttributesId();
		productItemAttributesId.setItem(itemId);
		productItemAttributesId.setProductAttrId(attributeFqn);
		final ProductItemAttributes productItemAttributes 
		    = (ProductItemAttributes)session
		        .get(ProductItemAttributes.class, productItemAttributesId);
		
		session.close();
		
		return productItemAttributes;
	}
	
	@Override
	public final void updateProductItemAttributes(
			final ProductItemAttributes productItemAttributes) {
		Transaction tx = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.update(productItemAttributes);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			
		} finally {
			session.close();
		}

	}
	
	
	@Override
	public final List<ExtDesc> geExtendedDescription(final String item) {
		List<ExtDesc> extDescs = new ArrayList<>();
		final Session session = sessionFactory.openSession();
		final Query query = session.createQuery("from ExtDesc e where e.item = :item");
		query.setParameter("item", item);

		extDescs = query.list();
		session.close();

		return extDescs;
	}
	
	@Override
	public final List<EcoFees> getEcoFes() {

		final Session session = sessionFactory.openSession();
		final Query query = session.createQuery("from EcoFees");
		final List<EcoFees> ecofees = query.list();   
		session.close();
		
		return ecofees;
	}
	
	
	@Override
	public final Item getProductDetailsFromDb() {

		final Session session = sessionFactory.openSession();
		final Item item = new Item();
		final String query 
		    = "from Item i left join ExtDesc e on i.item=e.item "
		    		+ "left join EcoFees c on i.item=c.item";
		
		final List<?> list = session.createQuery(query).list();

		for (int i = 0; i < list.size(); i++) {
			final Object[] row = (Object[]) list.get(i);
			/* EcoFees subject= (Subject )row[0];
			 Employee employee = (Employee)row[1];*/
		}
		session.createQuery(query);
		
		session.close();
		return item;
	}
}
