/*
 * Copyright 2020 Global Collect Services B.V
 */

package com.onlinepayments.sdk.client.android.model.iin;

import java.io.Serializable;
import java.util.List;

/**
 * POJO that contains the response for IIN lookup.
 */
public class IinDetailsResponse implements Serializable {

	private static final long serialVersionUID = -4043745317792003304L;

	private String paymentProductId;
	private String countryCode;
	private boolean isAllowedInContext;
	private List<IinDetail> coBrands;
	private IinStatus status;
	private CardType cardType;

	/**
	 @deprecated In a future release, this constructor will become internal to the SDK.
	 This constructor is only called in case of an invalid IIN response.
	 */
	@Deprecated
	public IinDetailsResponse(IinStatus status) {
		paymentProductId = null;
		countryCode = null;
		isAllowedInContext = false;
		coBrands = null;
		this.status = status;
		this.cardType = CardType.CREDIT;
	}

	public String getPaymentProductId() {
		return paymentProductId;
	}

	public IinStatus getStatus() {
		return status;
	}

	public void setStatus(IinStatus status) {
		this.status = status;
	}

	public CardType getCardType() {
		return cardType;
	}

	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @deprecated In a future release, this function will be removed. Use {@link #getCountryCode()} instead.
	 */
	@Deprecated
	public String getCountryCodeString() {
		return countryCode;
	}

	public boolean isAllowedInContext() {
		return isAllowedInContext;
	}

	public List<IinDetail> getCoBrands() {
		return coBrands;
	}


	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}

		if (o == null || o.getClass() != getClass()) {
			return false;
		}

		IinDetailsResponse otherResponse = (IinDetailsResponse)o;
		return (status != null ? status.equals(otherResponse.status) : otherResponse.status == null) &&
				(coBrands != null ? coBrands.equals(otherResponse.coBrands) : otherResponse.coBrands == null) &&
				(countryCode != null ? countryCode.equals(otherResponse.countryCode) : otherResponse.countryCode == null) &&
				(paymentProductId != null ? paymentProductId.equals(otherResponse.paymentProductId) : otherResponse.paymentProductId == null) &&
				isAllowedInContext == otherResponse.isAllowedInContext();
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31 * hash + (status != null ? status.hashCode() : 0);
		hash = 31 * hash + (paymentProductId != null ? paymentProductId.hashCode() : 0);
		hash = 31 * hash + (countryCode != null ? countryCode.hashCode() : 0);
		hash = 31 * hash + (coBrands != null ? coBrands.hashCode() : 0);
		hash = 31 * hash + Boolean.valueOf(isAllowedInContext).hashCode();
		return hash;
	}
}
