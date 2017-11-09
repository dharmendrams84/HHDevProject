package com.homehardware.test;

import com.homehardware.beans.Store;
import com.homehardware.dao.HhDaoImpl;

import java.util.ArrayList;
import java.util.List;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.core.Address;
import com.mozu.api.contracts.location.Coordinates;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.location.LocationType;
import com.mozu.api.contracts.location.ShippingOriginContact;
import com.mozu.api.contracts.mzdb.EntityList;
import com.mozu.api.resources.commerce.admin.LocationResource;
import com.mozu.api.resources.platform.EntityListResource;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/homehardware/applicationContext.xml" })
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/homehardware/applicationContext.xml" })

public class JUnitTest {

	public JUnitTest() {

	}

	@Autowired
	private HhDaoImpl hhDaoImpl;
	
	@Test
	public void testFetchHHStoreObjectFromDB()throws Exception{
		hhDaoImpl.getStore();
		
	}

	//@Test
	public void testInsertOrUpdateLocationObjectIntoKibo() throws Exception {
		LocationResource locationResource = new LocationResource(new MozuApiContext(24094,35909)); 
		String locationCode = "hh-1014";
		Location location = locationResource.getLocation(locationCode);
		Store store = new Store();
		store.setStoreName(locationCode);
		if(location==null){
			location = new Location();
			convertStoreTOMozuLocation(location, store);
			locationResource.addLocation(location);
		}else{
			convertStoreTOMozuLocation(location, store);
			locationResource.updateLocation(location, locationCode);
		}
		
		
		
					
	}
	
	public void convertStoreTOMozuLocation(Location location , Store store){
		location.setPhone("3333333334");
		location.setName("Home Hardware Central Warehouse1");
		
		Address address = new Address();
		address.setAddress1("Clarke & Clarke Home Hardware Building Centre");
		address.setCountryCode("CA");
		address.setStateOrProvince("NL");
		address.setCityOrTown("Bell Island");
		address.setPostalOrZipCode("A0A 4H0");
		address.setAddressType("Commercial");
		address.setIsValidated(false);
		location.setAddress(address);
		location.setAllowFulfillmentWithNoStock(false);
		location.setCode(store.getStoreName());
		location.setIsDisabled(false);
		
		Coordinates coordinates= new Coordinates();
		coordinates.setLat(0.0);
		coordinates.setLng(0.0);
		location.setGeo(coordinates);
		
		location.setDescription("description");
		ShippingOriginContact shippingOriginContact = new ShippingOriginContact();
		shippingOriginContact.setFirstName("Alex");
		shippingOriginContact.setLastNameOrSurname("Graham");
		shippingOriginContact.setCompanyOrOrganization("HH");
		shippingOriginContact.setEmail("ag@hh.ca");
		shippingOriginContact.setPhoneNumber("1111111111");
		
		
		location.setShippingOriginContact(shippingOriginContact);
		
		List<LocationType> list = new ArrayList<LocationType>();
		LocationType locationType = new LocationType();
		locationType.setCode("WH1");
		locationType.setName("WareHouse");
		list.add(locationType);
		location.setLocationTypes(list);
		

	}

	//@Test
	public void testFetchHHEcoFeesFromDB() throws Exception{
		//hhDaoImpl.getEcoFees();
		EntityList entityList = new EntityList();
		entityList.setName("testName");
		EntityListResource entityListResource = new EntityListResource(new MozuApiContext(24094,35909));
		
		entityListResource.createEntityList(entityList);
		System.out.println("After creating entityList resource"+entityListResource);
	}

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}
}
