package com.redhat.bh.jdg.common;

public interface ICommonProperties {
	public static final String PROPERTIES_FILE = "jdg.properties";

	public static String CLIENT_HOTROD_SERVER_LIST_KEY = "client.hotrod.server_list";
	public static String MDS_CACHE_KEY = "client.hotrod.mds.cache";
	public static String RDS_CACHE_KEY = "client.hotrod.rds.cache";

	public static String PROTOBUF_RDS_FILE_KEY = "client.protobuf.rds.file";
	public static String PROTOBUF_MDS_FILE_KEY = "client.protobuf.mds.file";

}
