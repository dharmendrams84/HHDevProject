package com.homehardware.test;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/homehardware/applicationContext.xml" })
public class JUnitTest {

	public JUnitTest() {

	}

	@Autowired
	private HhDaoImpl hhDaoImpl;
	
	
	@Test
	public void testFetchHHStoreObjectFromDB() {
		//HhDaoImpl hhDaoImpl = new HhDaoImpl();
		hhDaoImpl.getStore();
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
