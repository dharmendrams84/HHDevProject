package com.homehardware.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.taskdefs.LoadResource;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.homehardware.dao.HhDaoImpl;
import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.core.Address;
import com.mozu.api.contracts.core.AuditInfo;
import com.mozu.api.contracts.location.Coordinates;
import com.mozu.api.contracts.location.FulfillmentType;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.location.LocationAttribute;
import com.mozu.api.contracts.location.LocationType;
import com.mozu.api.contracts.location.ShippingOriginContact;
import com.mozu.api.contracts.tenant.Tenant;
import com.mozu.api.resources.commerce.admin.LocationResource;
import com.mozu.api.security.AuthTicket;
import com.sun.xml.bind.v2.schemagen.xmlschema.LocalAttribute;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/homehardware/applicationContext.xml" })
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/homehardware/applicationContext.xml" })

public class JUnitTest {

	public JUnitTest() {

	}

	@Autowired
	private HhDaoImpl hhDaoImpl;
	
	
	@Test
	public void testFetchHHStoreObjectFromDB() {
		hhDaoImpl.getStore();

	}

	@Test
	public void testInsertOrUpdateLocationObjectIntoKibo() throws Exception {
		LocationResource locationResource = new LocationResource(new MozuApiContext(24094,35909)); 
		Location location = new Location();
		location.setPhone("1111111111");
		location.setName("Home Hardware Central Warehouse");
		
		Address address1 = new Address();
		address1.setAddress1("Clarke & Clarke Home Hardware Building Centre");
		address1.setCountryCode("CA");
		address1.setStateOrProvince("NL");
		address1.setCityOrTown("Bell Island");
		address1.setPostalOrZipCode("A0A 4H0");
		address1.setAddressType("Commercial");
		address1.setIsValidated(false);
		location.setAddress(address1);
		location.setAllowFulfillmentWithNoStock(false);
		location.setCode("hh-1011");
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
		locationResource.addLocation(location);
		
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
