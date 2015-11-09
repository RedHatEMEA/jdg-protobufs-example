package com.redhat.bh.jdg.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.infinispan.client.hotrod.ServerStatistics;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.bh.jdg.client.IDataManager;
import com.redhat.bh.jdg.client.impl.MarketDataManagerImpl;
import com.redhat.bh.jdg.model.ComplexMarketPriceEntry;
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
		LOGGER.info(String.format("%d objects left in cache after test run", mdm.getCacheSize()));
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
	public void testComplexWrite() {
		writeComplexEntries(MAX_OBJECT_COUNT, "badgers", 7);
		assertEquals(MAX_OBJECT_COUNT, mdm.getCacheSize());
	}

	@Test
	public void testStats() {
		writeEntries(MAX_OBJECT_COUNT);
		assertEquals(MAX_OBJECT_COUNT, mdm.getCacheSize());

		ServerStatistics stats = mdm.getStats();
		assertNotNull(stats);

		LOGGER.info("Statistics");
		for (String key : stats.getStatsMap().keySet()) {
			LOGGER.info("Key: " + key + "\t" + "Value: "
					+ stats.getStatistic(key));
		}
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

		List<Object> results = mdm.search("commodity", beans,
				MarketPriceEntry.class);
		assertEquals(50, results.size());
	}

	@Test
	public void testComplexSearch() {
		String beans = "beans";
		String oil = "oil";
		String rubber = "rubber";

		writeComplexEntries(5, beans, 5);
		writeComplexEntries(10, oil, 10);
		writeComplexEntries(20, rubber, 20);

		List<Object> results = mdm.search("commodity", beans,
				ComplexMarketPriceEntry.class);
		assertEquals(5, results.size());

		List<Object> results2 = mdm.searchByType(ComplexMarketPriceEntry.class);

		assertEquals(35, results2.size());

	}

	private void writeEntries(int numEntries, String commodity) {
		for (int i = 0; i < numEntries; i++) {
			String key = String.format(BASE_KEY_FORMAT, BASE_KEY, commodity, i);
			mdm.putEntry(key, new MarketPriceEntry((i * numEntries), commodity));
			LOGGER.info("PUT: " + key);
		}
	}

	private void writeComplexEntries(int numEntries, String commodity,
			int numAvailable) {

		Random r = new Random();

		for (int i = 0; i < numEntries; i++) {

			String key = String.format(BASE_KEY_FORMAT, BASE_KEY, commodity, i);

			mdm.putEntry(key, new ComplexMarketPriceEntry((i * numEntries),
					commodity, r.nextInt(numAvailable)));

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

	/**
	 * Soak Test. Do not run.
	 */
	@Test
	@Ignore
	public void testSoak() {
		for (int i = 0; i < 100; i++) {
			writeEntries(i, TEST_COMMODITY);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
