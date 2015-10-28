package com.redhat.bh.jdg.model;

import java.io.Serializable;

public class MarketPriceEntry implements Serializable {

	/**
	 * Serializeable UID
	 */
	private static final long serialVersionUID = 1874488008945905488L;

	private double price;
	private String commodity;

	public MarketPriceEntry(double price, String commodity) {
		this.price = price;
		this.commodity = commodity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	/*
	 * Yes, it's an auto-generated method. No, I don't care
	 */
	@Override
	public String toString() {
		return "MarketPriceEntry [price=" + price + ", commodity=" + commodity
				+ "]";
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
		MarketPriceEntry other = (MarketPriceEntry) obj;
		if (commodity == null) {
			if (other.commodity != null) {
				return false;
			}
		} else if (!commodity.equals(other.commodity)) {
			return false;
		}
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price)) {
			return false;
		}
		return true;
	}

}
