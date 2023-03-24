package com.onlinepayments.sdk.client.android.testUtil;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Copyright 2017 Global Collect Services B.V
 *
 */
public class GsonHelper {

    private static final Gson gson = new Gson();

    public static <T> T fromResourceJson(String resource, Class<T> classOfT) {
        try (Reader reader = new InputStreamReader(GsonHelper.class.getClassLoader().getResourceAsStream(resource))) {
                return gson.fromJson(reader, classOfT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
