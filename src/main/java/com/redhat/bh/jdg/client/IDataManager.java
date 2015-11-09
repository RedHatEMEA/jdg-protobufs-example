package com.redhat.bh.jdg.client;

import java.util.List;

import org.infinispan.client.hotrod.ServerStatistics;

public interface IDataManager {

	public Object getEntry(String key);

	public void putEntry(String key, Object value);

	public void deleteEntry(String key);

	public List<Object> search(String field, String searchTerm, Class<?> clazz);

	public List<Object> searchByType(Class<?> clazz);

	public int getCacheSize();

	public void clearCache();

	public ServerStatistics getStats();
}
