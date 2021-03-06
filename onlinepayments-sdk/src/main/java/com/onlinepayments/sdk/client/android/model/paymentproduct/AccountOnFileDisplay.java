package com.onlinepayments.sdk.client.android.model.paymentproduct;

import java.io.Serializable;


/**
 * POJO that represents an AccountOnFile object
 * This class is filled by deserialising a JSON string from the GC gateway
 *
 * Copyright 2020 Global Collect Services B.V
 *
 */
public class AccountOnFileDisplay implements Serializable {

	private static final long serialVersionUID = -7793293988073972532L;

	private String attributeKey;
	private String mask;

	public AccountOnFileDisplay(String attributeKey, String mask) {
		this.attributeKey = attributeKey;
		this.mask = mask;
	}

	public String getKey() {
		return attributeKey;
	}

	public String getMask() {
		return mask;
	}

}
