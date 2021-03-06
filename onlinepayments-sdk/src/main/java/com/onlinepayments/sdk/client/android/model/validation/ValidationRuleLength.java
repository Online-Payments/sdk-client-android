package com.onlinepayments.sdk.client.android.model.validation;


import android.util.Log;

import com.onlinepayments.sdk.client.android.model.PaymentRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * Validation rule for length
 *
 * Copyright 2020 Global Collect Services B.V
 *
 */
public class ValidationRuleLength extends AbstractValidationRule {

	private static final long serialVersionUID = 6453263230504247824L;

	private static final String TAG = ValidationRuleLength.class.getName();

	private Integer minLength;
	private Integer maxLength;
	private Integer maskedMaxLength;

	public ValidationRuleLength(Integer minLength, Integer maxLength, String errorMessage, ValidationType type) {

		super(errorMessage, type);

		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	/**
	 * Validates that the text has the required length
	 * @param text, the text to be validated
     * @return True if the String is valid, false otherwise
	 * @deprecated use {@link #validate(PaymentRequest, String)} instead
     */
	@Override
	@Deprecated
	public boolean validate(String text) {
		Log.w(TAG, "This method is deprecated and should not be used! Use <validate(PaymentRequest paymentRequest, String)> instead.");

		// Check if textsize >= minLength && textsize <= maxLength
		return text.length() >= minLength && text.length() <= maxLength;
	}

	/**
	 * Validates that the value has the desired length
	 * @param paymentRequest The fully filled payment request that is ready for doing the payment
	 * @param fieldId The ID of the field to which to apply the current validator
	 * @return True if the value in the field with <code>fieldId</code> has the correct length; false
	 * if it is not of the correct length or the fieldId could not be found.
	 */
	@Override
	public boolean validate(PaymentRequest paymentRequest, String fieldId) {

		String text = paymentRequest.getValue(fieldId);

		// Text is allowed to be empty if the minimal required length is 0
		if (StringUtils.isEmpty(text) && minLength == 0) {
			return true;
		}

		if (text == null) {
			return false;
		}

		text = paymentRequest.getUnmaskedValue(fieldId, text);

		return text.length() >= minLength && text.length() <= maxLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public Integer getMaskedMaxLength(){
		return maskedMaxLength;
	}
}
