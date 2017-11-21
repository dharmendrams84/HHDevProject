package com.homehardware.dao;

import com.homehardware.beans.Employee;
import com.homehardware.beans.Store;
import com.homehardware.model.EcoFees;
import com.homehardware.model.ExtDesc;
import com.homehardware.model.Item;
import com.homehardware.model.ProductAttribute;
import com.homehardware.model.ProductItemAttributes;
import com.homehardware.model.ProductItemAttributesId;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
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
		try {
			final Session session = sessionFactory.openSession();
			store = (Store) session.get(Store.class, 1);
			//final JSONObject jsonObject = new JSONObject(store.getRegularHours());
			//final JSONObject regularHours = (JSONObject) jsonObject.get("regularHours");
			//regularHours.get("friday");
			System.out.println("store website url " + store.getStoreWebsiteUrl());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return store;
	}

	@Override
	public final void getEcoFees() {

		final Session session = sessionFactory.openSession();
		final EcoFees ecoFees = (EcoFees) session.get(EcoFees.class, 1);
		System.out.println(ecoFees);

	}
	
	@Override
	public final Item getItem() {

		final Session session = sessionFactory.openSession();
		final Item item = (Item) session.get(Item.class, 1);
		System.out.println(item);
		session.close();
		//session.flush();
		return item;

	}
	
	
	@Override
	public final List<ProductAttribute> getProductAttributesList() {

		final Session session = sessionFactory.openSession();
		Query query = session.createQuery("from ProductAttribute");
		List<ProductAttribute> productAttributes = query.list();   
		//System.out.println(productAttribute.getAttributeName());
		session.close();
		//session.flush();
		return productAttributes;
	}
	
	
	/*@Override
	public final List<ProductAttribute> getProductAttributesList(String productCode) {

		final Session session = sessionFactory.openSession();
		Query query = session.createQuery("from ProductAttribute p where p.productAttrId = :productAttrId");
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
		ProductItemAttributesId productItemAttributesId = new ProductItemAttributesId();
		productItemAttributesId.setItem("4466-443");
		productItemAttributesId.setProductAttrId("tenant~hh-consumer-item-desc");
		ProductItemAttributes productItemAttributes  = (ProductItemAttributes)session.get(ProductItemAttributes.class, productItemAttributesId);
		System.out.println(productItemAttributes);
		session.close();
		//session.flush();
		return productItemAttributes;
	}
	
	
	@Override
	public final ProductItemAttributes getProductItemAttribute(String attributeFqn,String itemId) {

		final Session session = sessionFactory.openSession();
		ProductItemAttributesId productItemAttributesId = new ProductItemAttributesId();
		productItemAttributesId.setItem(itemId);
		productItemAttributesId.setProductAttrId(attributeFqn);
		ProductItemAttributes productItemAttributes  = (ProductItemAttributes)session.get(ProductItemAttributes.class, productItemAttributesId);
		
		session.close();
		
		return productItemAttributes;
	}
	
	@Override
	public final void updateProductItemAttributes(ProductItemAttributes productItemAttributes) {
		Transaction tx = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.update(productItemAttributes);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}
	
	
	@Override
	public final List<ExtDesc> geExtendedDescription(String item){
		List<ExtDesc> extDescs = new ArrayList<>();
		final Session session = sessionFactory.openSession();
		Query query = session.createQuery("from ExtDesc e where e.item = :item");
		query.setParameter("item", item);

		extDescs = query.list();   
		session.close();
		
		return extDescs;
	}

}
