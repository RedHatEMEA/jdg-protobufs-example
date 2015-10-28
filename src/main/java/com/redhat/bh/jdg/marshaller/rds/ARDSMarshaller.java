package com.redhat.bh.jdg.marshaller.rds;

import java.io.IOException;

import org.infinispan.protostream.MessageMarshaller;

import com.redhat.bh.jdg.model.AlternativeReferenceEntry;

public class ARDSMarshaller implements
		MessageMarshaller<AlternativeReferenceEntry> {

	public Class<? extends AlternativeReferenceEntry> getJavaClass() {
		return AlternativeReferenceEntry.class;
	}

	public String getTypeName() {
		return "rds.AlternativeReferenceEntry";
	}

	public AlternativeReferenceEntry readFrom(ProtoStreamReader reader)
			throws IOException {
		String value = reader.readString("value");
		String dataType = reader.readString("dataType");
		String somethingElse = reader.readString("somethingElse");
		return new AlternativeReferenceEntry(value, dataType, somethingElse);
	}

	public void writeTo(ProtoStreamWriter writer, AlternativeReferenceEntry re)
			throws IOException {
		writer.writeString("value", re.getValue());
		writer.writeString("dataType", re.getDataType());
		writer.writeString("somethingElse", re.getSomethingElse());
	}
}
