package com.redhat.bh.jdg.marshaller.mds;

import java.io.IOException;

import org.infinispan.protostream.MessageMarshaller;

import com.redhat.bh.jdg.model.MarketPriceEntry;

public class MDSMarshaller implements MessageMarshaller<MarketPriceEntry> {

	public Class<? extends MarketPriceEntry> getJavaClass() {
		return MarketPriceEntry.class;
	}

	public String getTypeName() {
		return "mds.MarketPriceEntry";
	}

	public MarketPriceEntry readFrom(ProtoStreamReader reader)
			throws IOException {
		double price = reader.readDouble("price");
		String commodity = reader.readString("commodity");
		return new MarketPriceEntry(price, commodity);
	}

	public void writeTo(ProtoStreamWriter writer,
			MarketPriceEntry mpe) throws IOException {

		writer.writeDouble("price", mpe.getPrice());
		writer.writeString("commodity", mpe.getCommodity());
	}

}
