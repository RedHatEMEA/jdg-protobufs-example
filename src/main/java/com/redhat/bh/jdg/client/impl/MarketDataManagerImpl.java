package com.redhat.bh.jdg.client.impl;

import java.util.List;

import org.infinispan.client.hotrod.ServerStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.bh.jdg.client.AbstractDataManager;
import com.redhat.bh.jdg.client.IDataManager;
import com.redhat.bh.jdg.common.ICommonProperties;
import com.redhat.bh.jdg.marshaller.mds.MDSMarshaller;
import com.redhat.bh.jdg.model.ComplexMarketPriceEntry;
import com.redhat.bh.jdg.model.MarketPriceEntry;

public class MarketDataManagerImpl extends AbstractDataManager implements
		IDataManager {
	private static Logger LOGGER = LoggerFactory
			.getLogger(MarketDataManagerImpl.class);

	public MarketDataManagerImpl() {
		super();

		registerProtobufMarshaller(
				getProperty(ICommonProperties.PROTOBUF_MDS_FILE_KEY),
				MDSMarshaller.class);

		registerProtobufMarshaller(ComplexMarketPriceEntry.class);
	}

	public Object getEntry(String key) {
		return this.getMarketDataCache().get(key);
	}

	public void putEntry(String key, Object value) {
		this.getMarketDataCache().put(key, value);

	}

	public void deleteEntry(String key) {
		this.getMarketDataCache().remove(key);
	}

	public int getCacheSize() {
		return this.getMarketDataCache().size();
	}

	public void clearCache() {
		this.getMarketDataCache().clear();
	}

	public List<Object> search(String field, String searchTerm, Class<?> clazz) {
		return search(getMarketDataCache(), field, searchTerm, clazz);
	}

	public List<Object> searchByType(Class<?> clazz) {
		return super.search(getMarketDataCache(), clazz);
	}

	public ServerStatistics getStats() {
		return getStats(getMarketDataCache());
	}

}
