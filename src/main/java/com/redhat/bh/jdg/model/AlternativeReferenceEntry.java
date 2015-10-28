package com.redhat.bh.jdg.model;

import java.io.Serializable;

public class AlternativeReferenceEntry implements Serializable {

	/**
	 * Serializeable UID
	 */
	private static final long serialVersionUID = 3964784722626142609L;

	private String value;
	private String dataType;
	private String somethingElse;

	public AlternativeReferenceEntry(String value, String dataType,
			String somethingElse) {
		this.value = value;
		this.dataType = dataType;
		this.somethingElse = somethingElse;
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

	public String getSomethingElse() {
		return somethingElse;
	}

	public void setSomethingElse(String somethingElse) {
		this.somethingElse = somethingElse;
	}

	/*
	 * Yes, it's an auto-generated method. No, I don't care
	 */
	@Override
	public String toString() {
		return "AlternativeReferenceEntry [value=" + value + ", dataType="
				+ dataType + ", somethingElse=" + somethingElse + "]";
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
		result = prime * result
				+ ((somethingElse == null) ? 0 : somethingElse.hashCode());
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
		AlternativeReferenceEntry other = (AlternativeReferenceEntry) obj;
		if (dataType == null) {
			if (other.dataType != null) {
				return false;
			}
		} else if (!dataType.equals(other.dataType)) {
			return false;
		}
		if (somethingElse == null) {
			if (other.somethingElse != null) {
				return false;
			}
		} else if (!somethingElse.equals(other.somethingElse)) {
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
