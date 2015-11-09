package com.redhat.bh.jdg.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.infinispan.client.hotrod.ServerStatistics;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.bh.jdg.client.IDataManager;
import com.redhat.bh.jdg.client.impl.ReferenceDataManagerImpl;
import com.redhat.bh.jdg.model.AlternativeReferenceEntry;
import com.redhat.bh.jdg.model.ReferenceEntry;

public class ReferenceDataManagerImplTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReferenceDataManagerImplTest.class);

	private static IDataManager rdm;

	private static final String BASE_KEY_FORMAT = "%s.%s.%d";
	private static final String BASE_KEY = "object.key";
	private static final int MAX_OBJECT_COUNT = 100;
	private static final int NO_OBJECT_COUNT = 0;
	private static final String TEST_DATATYPE = "currency";
	private static final String TEST_VALUE = "000";

	@BeforeClass
	public static void setupClass() {
		LOGGER.info("Running setup...");
		rdm = new ReferenceDataManagerImpl();
		rdm.clearCache();
		try {
			// mdm.clearCache();
			LOGGER.info("Cache entries: " + rdm.getCacheSize());
		} catch (Exception e1) {
			LOGGER.error("Something 'dun broked");
		}

	}

	@After
	public void teardown() {
		LOGGER.info(String.format("%d objects left in cache after test run",
				rdm.getCacheSize()));
		clearCache();
		LOGGER.info("Teardown complete");
	}

	@Test
	public void testReadEmpty() {
		Object entry = rdm.getEntry(BASE_KEY);
		assertNull(entry);
		assertEquals(NO_OBJECT_COUNT, rdm.getCacheSize());
	}

	@Test
	public void testWrite() {
		writeEntries(MAX_OBJECT_COUNT, TEST_VALUE, TEST_DATATYPE);
		assertEquals(MAX_OBJECT_COUNT, rdm.getCacheSize());
	}

	@Test
	public void testStats() {
		writeEntries(MAX_OBJECT_COUNT, TEST_VALUE, TEST_DATATYPE);
		assertEquals(MAX_OBJECT_COUNT, rdm.getCacheSize());

		ServerStatistics stats = rdm.getStats();
		assertNotNull(stats);

		LOGGER.info("Statistics");
		for (String key : stats.getStatsMap().keySet()) {
			LOGGER.info("Key: " + key + "\t" + "Value: "
					+ stats.getStatistic(key));
		}
	}

	@Test
	public void testRead() {
		writeEntries(MAX_OBJECT_COUNT, TEST_VALUE, TEST_DATATYPE);

		List<Object> retrieved = new ArrayList<Object>();

		for (int i = 0; i < MAX_OBJECT_COUNT; i++) {
			String key = String.format(BASE_KEY_FORMAT, BASE_KEY,
					TEST_DATATYPE, i);
			retrieved.add(rdm.getEntry(key));
			LOGGER.info("GOT: " + key);
		}

		assertEquals(MAX_OBJECT_COUNT, rdm.getCacheSize());
		assertEquals(MAX_OBJECT_COUNT, retrieved.size());
	}

	@Test
	public void testDelete() {
		writeEntries(MAX_OBJECT_COUNT, TEST_VALUE, TEST_DATATYPE);

		for (int i = 0; i < MAX_OBJECT_COUNT; i++) {
			String key = String.format(BASE_KEY_FORMAT, BASE_KEY,
					TEST_DATATYPE, i);
			rdm.deleteEntry(key);

		}

		assertEquals(NO_OBJECT_COUNT, rdm.getCacheSize());
	}

	@Test
	public void testSearch() {
		String value1 = "000";
		String value2 = "002";
		String value3 = "004";

		String dt1 = "currency";
		String dt2 = "bench";
		String dt3 = "fees";

		writeEntries(10, value1, dt1);
		writeEntries(15, value2, dt2);
		writeEntries(20, value3, dt3);

		List<Object> results = rdm
				.search("value", value1, ReferenceEntry.class);
		assertEquals(10, results.size());

		results = rdm.search("value", value2, ReferenceEntry.class);
		assertEquals(15, results.size());

		results = rdm.search("value", value3, ReferenceEntry.class);
		assertEquals(20, results.size());
	}

	@Test
	public void testTypeSearch() {
		String value1 = "000";
		String value2 = "002";

		String dt1 = "currency";
		String dt2 = "bench";

		writeAlternativeEntries(60, value1, dt1);

		List<Object> results = rdm
				.searchByType(AlternativeReferenceEntry.class);
		assertEquals(60, results.size());

		writeEntries(40, value2, dt2);

		results = rdm.searchByType(AlternativeReferenceEntry.class);
		assertEquals(60, results.size());

	}

	private void writeEntries(int numEntries, String value, String dataType) {

		LOGGER.info("Adding Reference Entries");

		for (int i = 0; i < numEntries; i++) {
			String key = String.format(BASE_KEY_FORMAT, BASE_KEY, dataType, i);
			rdm.putEntry(key, new ReferenceEntry(value, dataType));
			LOGGER.info("PUT: " + key);
		}
	}

	private void writeAlternativeEntries(int numEntries, String value,
			String dataType) {

		LOGGER.info("Adding Alternative Reference Entries");

		for (int i = 0; i < numEntries; i++) {
			String key = String.format(BASE_KEY_FORMAT, BASE_KEY, dataType, i);
			rdm.putEntry(key, new AlternativeReferenceEntry(value, dataType,
					TEST_DATATYPE));
			LOGGER.info("PUT: " + key);
		}
	}

	private void clearCache() {
		try {
			rdm.clearCache();
			LOGGER.info("Cache entries: " + rdm.getCacheSize());
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
			writeEntries(i, TEST_VALUE, TEST_DATATYPE);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
