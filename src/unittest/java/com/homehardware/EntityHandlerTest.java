package com.homehardware;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.mzdb.EntityCollection;
import com.mozu.api.resources.platform.EntityListResource;
import com.mozu.api.resources.platform.entitylists.EntityResource;
import com.mozu.api.utils.JsonUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/homehardware/servlet-context.xml" })
public class EntityHandlerTest {

	private static final Logger logger = LoggerFactory.getLogger(EntityHandlerTest.class);

	Integer tenantId = 0;
	Integer siteId = 0;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		tenantId = new Integer("17120");
		siteId = null;
	}

	@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void installIntSetupSchemaTest() {
		//TODO implement
	}

	//@Test
	public void cleanupEntity() {
		//TODO implement
	}

	//@Test
	public void getEntity() {
		//TODO implement
	}

	private void runCleanup(final String entityName, final String key) throws Exception {
		final EntityResource entityResource = new EntityResource(
				new MozuApiContext(tenantId));

		final EntityCollection entities = entityResource.getEntities(
				entityName, 200, 0, null, null, null);

		for(JsonNode entity : entities.getItems()) {
			if (entity.has(key)) {
				final String id  = entity.get(key).asText();
				logger.info("Deleting id: " + id + " from " + entityName);
				entityResource.deleteEntity(entityName, id);
			}
		}
	}

	//@Test
	public void deleteEntityList() {
		//TODO implement
	}
}
