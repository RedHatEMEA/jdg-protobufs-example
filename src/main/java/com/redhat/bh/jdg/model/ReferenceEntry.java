package com.redhat.bh.jdg.model;

import java.io.Serializable;

public class ReferenceEntry implements Serializable {

	/**
	 * Serializeable UID
	 */
	private static final long serialVersionUID = 3964784722626142609L;

	private String value;
	private String dataType;

	public ReferenceEntry(String value, String dataType) {
		this.value = value;
		this.dataType = dataType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/*
	 * Yes, it's an auto-generated method. No, I don't care
	 */
	@Override
	public String toString() {
		return "ReferenceEntry [value=" + value + ", dataType=" + dataType
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
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		ReferenceEntry other = (ReferenceEntry) obj;
		if (dataType == null) {
			if (other.dataType != null) {
				return false;
			}
		} else if (!dataType.equals(other.dataType)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

}
