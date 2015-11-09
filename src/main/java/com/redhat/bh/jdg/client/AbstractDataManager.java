package com.redhat.bh.jdg.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.client.hotrod.ServerStatistics;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.MessageMarshaller;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.protostream.annotations.ProtoSchemaBuilderException;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.bh.jdg.common.ICommonProperties;
import com.redhat.bh.jdg.model.ComplexMarketPriceEntry;

public abstract class AbstractDataManager {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractDataManager.class);

	private static final String PROTOFILE_MASK = "%s.proto";

	protected RemoteCacheManager cacheContainer;

	public AbstractDataManager() {

		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.addServers(getProperty(ICommonProperties.CLIENT_HOTROD_SERVER_LIST_KEY));
		builder.marshaller(new ProtoStreamMarshaller());
		cacheContainer = new RemoteCacheManager(builder.build());
	}

	protected String getProperty(String name) {
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader()
					.getResourceAsStream(ICommonProperties.PROPERTIES_FILE));
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		return props.getProperty(name);
	}

	protected String readResource(String resourcePath) throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				resourcePath);
		try {
			final Reader reader = new InputStreamReader(is, "UTF-8");
			StringWriter writer = new StringWriter();
			char[] buf = new char[1024];
			int len;
			while ((len = reader.read(buf)) != -1) {
				writer.write(buf, 0, len);
			}
			return writer.toString();
		} finally {
			is.close();
		}
	}

	@SuppressWarnings("unchecked")
	protected <T> void registerProtobufMarshaller(String protoFile,
			Class<?>... clazz) {
		try {

			// register the schemas with the client
			SerializationContext srcCtx = ProtoStreamMarshaller
					.getSerializationContext(cacheContainer);

			srcCtx.registerProtoFiles(FileDescriptorSource
					.fromResources(protoFile));

			for (Class<?> c : clazz) {
				registerMarshaller(c);
			}

			// register the schemas with the server too
			RemoteCache<String, String> metadataCache = cacheContainer
					.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
			
			metadataCache.put(protoFile, this.readResource(protoFile));

		} catch (Exception e1) {
			LOGGER.error("Oops!", e1);
		}
	}

	@SuppressWarnings("unchecked")
	protected <T> void registerProtobufMarshaller(Class<?>... clazz) {
		try {

			// register the schemas with the client
			SerializationContext serCtx = ProtoStreamMarshaller
					.getSerializationContext(this.cacheContainer);

			ProtoSchemaBuilder psb;

			for (Class<?> c : clazz) {

				psb = new ProtoSchemaBuilder();

				String protoFileName = String.format(PROTOFILE_MASK,
						c.getSimpleName());

				String generatedSchema = psb.fileName(protoFileName)
						.packageName(c.getSimpleName()).addClass(c)
						.build(serCtx);

				// register the schemas with the server too
				RemoteCache<String, String> metadataCache = cacheContainer
						.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
				
				metadataCache.put(protoFileName, generatedSchema);
			}

		} catch (Exception e1) {
			LOGGER.error("Oops!", e1);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> void registerMarshaller(Class<T> clazz) {
		try {
			SerializationContext srcCtx = ProtoStreamMarshaller
					.getSerializationContext(cacheContainer);

			T marshaller = clazz.newInstance();

			srcCtx.registerMarshaller((MessageMarshaller<T>) marshaller);
		} catch (Exception e1) {
			LOGGER.error("Oops!", e1);
		}
	}

	private RemoteCache<String, Object> getRemoteCache(String name) {
		return cacheContainer.getCache(name);
	}

	protected RemoteCache<String, Object> getReferenceDataCache() {
		return this
				.getRemoteCache(getProperty(ICommonProperties.RDS_CACHE_KEY));
	}

	protected RemoteCache<String, Object> getMarketDataCache() {
		return this
				.getRemoteCache(getProperty(ICommonProperties.MDS_CACHE_KEY));
	}

	public List<Object> search(RemoteCache cache, String field,
			String searchTerm, Class<?> clazz) {
		QueryFactory qf = Search.getQueryFactory(cache);
		Query query = qf.from(clazz).having(field).eq(searchTerm).toBuilder()
				.build();

		List results = query.list();
		return results;
	}

	public List<Object> search(RemoteCache cache, Class<?> clazz) {
		QueryFactory qf = Search.getQueryFactory(cache);
		Query query = qf.from(clazz).build();

		List results = query.list();
		return results;
	}

	public ServerStatistics getStats(RemoteCache cache) {
		return cache.stats();
	}

}
