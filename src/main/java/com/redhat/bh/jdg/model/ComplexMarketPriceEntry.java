package com.redhat.bh.jdg.model;

import java.io.Serializable;

import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;

@ProtoDoc("@Indexed")
public class ComplexMarketPriceEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2923738412445740295L;

	private double price;

	private String commodity;

	private int numAvailable;

	public ComplexMarketPriceEntry() {
		// TODO Auto-generated constructor stub
	}

	public ComplexMarketPriceEntry(double price, String commodity,
			int numAvailable) {
		this.price = price;
		this.commodity = commodity;
		this.numAvailable = numAvailable;
	}

	@ProtoDoc("@IndexedField(index = true)")
	@ProtoField(number = 1, required = true)
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@ProtoDoc("@IndexedField(index = true)")
	@ProtoField(number = 2, required = true)
	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	@ProtoDoc("@IndexedField(index = true)")
	@ProtoField(number = 3, required = true)
	public int getNumAvailable() {
		return numAvailable;
	}

	public void setNumAvailable(int numAvailable) {
		this.numAvailable = numAvailable;
	}

	/*
	 * Yes, it's an auto-generated method. No, I don't care
	 */
	@Override
	public String toString() {
		return "ComplexMarketPriceEntry [price=" + price + ", commodity="
				+ commodity + ", numAvailable=" + numAvailable + "]";
	}

	/*
	 * Yes, it's an auto-generated method. No, I don't care
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((commodity == null) ? 0 : commodity.hashCode());
		result = prime * result + numAvailable;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/*
	 * Yes, it's an auto-generated method. No, I don't care
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ComplexMarketPriceEntry other = (ComplexMarketPriceEntry) obj;
		if (commodity == null) {
			if (other.commodity != null) {
				return false;
			}
		} else if (!commodity.equals(other.commodity)) {
			return false;
		}
		if (numAvailable != other.numAvailable) {
			return false;
		}
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price)) {
			return false;
		}
		return true;
	}

}
