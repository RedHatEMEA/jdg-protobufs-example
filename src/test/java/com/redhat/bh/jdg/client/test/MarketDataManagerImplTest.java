package com.redhat.bh.jdg.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.bh.jdg.client.IDataManager;
import com.redhat.bh.jdg.client.impl.MarketDataManagerImpl;
import com.redhat.bh.jdg.model.MarketPriceEntry;

public class MarketDataManagerImplTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MarketDataManagerImplTest.class);

	private static IDataManager mdm;

	private static final String BASE_KEY_FORMAT = "%s.%s.%d";
	private static final String BASE_KEY = "object.key";
	private static final int MAX_OBJECT_COUNT = 100;
	private static final int NO_OBJECT_COUNT = 0;
	private static final String TEST_COMMODITY = "oil";

	@BeforeClass
	public static void setupClass() {
		LOGGER.info("Running setup...");
		mdm = new MarketDataManagerImpl();
		mdm.clearCache();
		try {
			// mdm.clearCache();
			LOGGER.info("Cache entries: " + mdm.getCacheSize());
		} catch (Exception e1) {
			LOGGER.error("Something 'dun broked");
		}

	}

	@After
	public void setupTest() {
		clearCache();
		LOGGER.info("Teardown complete");
	}

	@Test
	public void testReadEmpty() {
		Object entry = mdm.getEntry(BASE_KEY);
		assertNull(entry);
		assertEquals(NO_OBJECT_COUNT, mdm.getCacheSize());
	}

	@Test
	public void testWrite() {
		writeEntries(MAX_OBJECT_COUNT);
		assertEquals(MAX_OBJECT_COUNT, mdm.getCacheSize());
	}

	@Test
	public void testRead() {
		writeEntries(MAX_OBJECT_COUNT, TEST_COMMODITY);

		List<Object> retrieved = new ArrayList<Object>();

		for (int i = 0; i < MAX_OBJECT_COUNT; i++) {
			String key = String.format(BASE_KEY_FORMAT, BASE_KEY,
					TEST_COMMODITY, i);
			retrieved.add(mdm.getEntry(key));
			LOGGER.info("GOT: " + key);
		}

		assertEquals(MAX_OBJECT_COUNT, mdm.getCacheSize());
		assertEquals(MAX_OBJECT_COUNT, retrieved.size());
	}

	@Test
	public void testDelete() {
		writeEntries(MAX_OBJECT_COUNT, TEST_COMMODITY);

		for (int i = 0; i < MAX_OBJECT_COUNT; i++) {
			String key = String.format(BASE_KEY_FORMAT, BASE_KEY,
					TEST_COMMODITY, i);
			mdm.deleteEntry(key);

		}

		assertEquals(NO_OBJECT_COUNT, mdm.getCacheSize());
	}

	@Test
	public void testSearch() {
		String beans = "beans";
		String oil = "oil";
		String rubber = "rubber";

		writeEntries(50, beans);
		writeEntries(100, oil);
		writeEntries(200, rubber);

		List<Object> results = mdm.search("commodity", beans);
		assertEquals(50, results.size());
	}

	private void writeEntries(int numEntries, String commodity) {
		for (int i = 0; i < numEntries; i++) {
			String key = String.format(BASE_KEY_FORMAT, BASE_KEY, commodity, i);
			mdm.putEntry(key, new MarketPriceEntry((i * numEntries), commodity));
			LOGGER.info("PUT: " + key);
		}
	}

	private void writeEntries(int numEntries) {
		writeEntries(numEntries, TEST_COMMODITY);
	}

	private void clearCache() {
		try {
			mdm.clearCache();
			LOGGER.info("Cache entries: " + mdm.getCacheSize());
		} catch (Exception e1) {
			LOGGER.error("Something 'dun broked");
		}

	}

}
