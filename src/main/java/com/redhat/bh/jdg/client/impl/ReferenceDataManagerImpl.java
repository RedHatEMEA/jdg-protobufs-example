package com.redhat.bh.jdg.client.impl;

import java.util.List;

import org.infinispan.client.hotrod.ServerStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.bh.jdg.client.AbstractDataManager;
import com.redhat.bh.jdg.client.IDataManager;
import com.redhat.bh.jdg.common.ICommonProperties;
import com.redhat.bh.jdg.marshaller.rds.ARDSMarshaller;
import com.redhat.bh.jdg.marshaller.rds.RDSMarshaller;
import com.redhat.bh.jdg.model.ReferenceEntry;

public class ReferenceDataManagerImpl extends AbstractDataManager implements
		IDataManager {
	private static Logger LOGGER = LoggerFactory
			.getLogger(ReferenceDataManagerImpl.class);

	public ReferenceDataManagerImpl() {
		super();
		
		registerProtobufMarshaller(
				getProperty(ICommonProperties.PROTOBUF_RDS_FILE_KEY),
				RDSMarshaller.class, ARDSMarshaller.class);
	}

	public Object getEntry(String key) {
		return this.getReferenceDataCache().get(key);
	}

	public void putEntry(String key, Object value) {
		this.getReferenceDataCache().put(key, value);

	}

	public void deleteEntry(String key) {
		this.getReferenceDataCache().remove(key);

	}

	public int getCacheSize() {
		return this.getReferenceDataCache().size();
	}

	public void clearCache() {
		this.getReferenceDataCache().clear();

	}

	public List<Object> search(String field, String searchTerm, Class<?> clazz) {
		return search(getReferenceDataCache(), field, searchTerm, clazz);
	}

	public List<Object> search(String field, String searchTerm) {
		return search(getReferenceDataCache(), field, searchTerm,
				ReferenceEntry.class);
	}

	public List<Object> searchByType(Class<?> clazz) {
		return search(getReferenceDataCache(), clazz);
	}

	public ServerStatistics getStats() {
		return getStats(getReferenceDataCache());
	}

}
