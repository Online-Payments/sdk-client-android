package com.onlinepayments.sdk.client.android.model.surcharge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.onlinepayments.sdk.client.android.model.surcharge.response.Surcharge;
import com.onlinepayments.sdk.client.android.model.surcharge.response.SurchargeCalculationResponse;
import com.onlinepayments.sdk.client.android.model.surcharge.response.SurchargeResult;
import com.onlinepayments.sdk.client.android.testUtil.GsonHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Junit Testclass which tests surcharge calculation details response
 *
 * Copyright 2023 Global Collect Services B.V
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SurchargeCalculationResponseTest {

    private final SurchargeCalculationResponse scWithSurcharge = GsonHelper.fromResourceJson("scWithSurcharge.json", SurchargeCalculationResponse.class);
    private final SurchargeCalculationResponse scWithNoSurcharge = GsonHelper.fromResourceJson("scWithNoSurcharge.json", SurchargeCalculationResponse.class);

    @Test
    public void testEqualsSurchargeCalculationResponseWithSurcharge() {
        assertEquals(scWithSurcharge.getSurcharges().size(), 1);

        Surcharge surcharge = scWithSurcharge.getSurcharges().get(0);

        assertEquals(surcharge.getPaymentProductId(), 1);
        assertEquals(surcharge.getResult(), SurchargeResult.OK);
        assertEquals(surcharge.getNetAmount().getAmount(), Long.valueOf(1000));
        assertEquals(surcharge.getNetAmount().getCurrencyCodeString(), "EUR");
        assertEquals(surcharge.getSurchargeAmount().getAmount(), Long.valueOf(366));
        assertEquals(surcharge.getSurchargeAmount().getCurrencyCodeString(), "EUR");
        assertEquals(surcharge.getTotalAmount().getAmount(), Long.valueOf(1366));
        assertEquals(surcharge.getTotalAmount().getCurrencyCodeString(), "EUR");
        assertNotNull(surcharge.getSurchargeRate());
        assertEquals(surcharge.getSurchargeRate().getSurchargeProductTypeId(), "PAYMENT_PRODUCT_TYPE_ID");
        assertEquals(surcharge.getSurchargeRate().getSurchargeProductTypeVersion(), "1a2b3c-4d5e-6f7g8h-9i0j");
        assertEquals(surcharge.getSurchargeRate().getAdValoremRate(), 3.3, 0);
        assertEquals(surcharge.getSurchargeRate().getSpecificRate(), 333);
    }

    @Test
    public void testEqualsSurchargeCalculationResponseWithNoSurcharge() {
        assertEquals(scWithNoSurcharge.getSurcharges().size(), 1);

        Surcharge surcharge = scWithNoSurcharge.getSurcharges().get(0);

        assertEquals(surcharge.getPaymentProductId(), 2);
        assertEquals(surcharge.getResult(), SurchargeResult.NO_SURCHARGE);
        assertEquals(surcharge.getNetAmount().getAmount(), Long.valueOf(1000));
        assertEquals(surcharge.getNetAmount().getCurrencyCodeString(), "EUR");
        assertEquals(surcharge.getSurchargeAmount().getAmount(), Long.valueOf(0));
        assertEquals(surcharge.getSurchargeAmount().getCurrencyCodeString(), "EUR");
        assertEquals(surcharge.getTotalAmount().getAmount(), Long.valueOf(1000));
        assertEquals(surcharge.getTotalAmount().getCurrencyCodeString(), "EUR");
        assertNull(surcharge.getSurchargeRate());
    }
}
