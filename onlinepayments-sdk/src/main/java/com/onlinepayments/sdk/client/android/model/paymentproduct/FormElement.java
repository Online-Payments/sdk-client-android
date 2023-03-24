package com.onlinepayments.sdk.client.android.model.paymentproduct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * POJO that represents a Formelement object
 * This class is filled by deserialising a JSON string from the GC gateway
 * The Formelements are used for determining whether it is a list or text input field
 * In case of list, it also has values inside the valuemapping
 *
 * Copyright 2020 Global Collect Services B.V
 *
 */
public class FormElement implements Serializable {

	private static final long serialVersionUID = 7081218270681792356L;


	public enum ListType {
		@SerializedName("text")
		TEXT,

		@SerializedName("list")
		LIST,

		@SerializedName("currency")
		CURRENCY,

		@SerializedName("date")
		DATE,

		@SerializedName("boolean")
		BOOLEAN,
		;
	}

	private ListType type;
	private List<ValueMap> valueMapping = new ArrayList<>();


	public ListType getType(){
		return type;
	}

	/**
	 * @deprecated
	 * Do not use this method. This method is intented for a temporary internal fix, and will be removed when no longer required.
	 *
	 * @param type the new type for this FormElement
	 */
	public void setType(ListType type) {
		this.type = type;
	}

	public List<ValueMap> getValueMapping(){
		return valueMapping;
	}
}
