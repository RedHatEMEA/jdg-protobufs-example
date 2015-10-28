package com.redhat.bh.jdg.marshaller.rds;

import java.io.IOException;

import org.infinispan.protostream.MessageMarshaller;

import com.redhat.bh.jdg.model.ReferenceEntry;

public class RDSMarshaller implements MessageMarshaller<ReferenceEntry> {

	public Class<? extends ReferenceEntry> getJavaClass() {
		return ReferenceEntry.class;
	}

	public String getTypeName() {
		return "rds.ReferenceEntry";
	}

	public ReferenceEntry readFrom(ProtoStreamReader reader) throws IOException {
		String value = reader.readString("value");
		String dataType = reader.readString("dataType");
		return new ReferenceEntry(value, dataType);
	}

	public void writeTo(ProtoStreamWriter writer, ReferenceEntry re)
			throws IOException {
		writer.writeString("value", re.getValue());
		writer.writeString("dataType", re.getDataType());
	}
}
