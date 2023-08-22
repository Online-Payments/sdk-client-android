/*
 * Copyright 2020 Global Collect Services B.V
 */

package com.onlinepayments.sdk.client.android.model.paymentproduct.specificdata;

import java.io.Serializable;
import java.util.List;

/**
 * POJO which holds the payment product 302 specific properties.
 */
public class PaymentProduct302SpecificData implements Serializable {

	private static final long serialVersionUID = 4006738016411138300L;

	private List<String> networks;

	/**
	 * @deprecated In a future release, this constructor will become internal to the SDK.
	 */
	@Deprecated
	public PaymentProduct302SpecificData() {}

	public List<String> getNetworks() {
		return networks;
	}
}
