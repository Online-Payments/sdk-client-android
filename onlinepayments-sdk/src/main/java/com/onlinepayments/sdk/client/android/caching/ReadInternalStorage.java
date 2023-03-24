package com.onlinepayments.sdk.client.android.caching;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.onlinepayments.sdk.client.android.configuration.Constants;
import com.onlinepayments.sdk.client.android.model.iin.IinDetailsResponse;

/**
 * This class is responsible for reading files on disk who act as cache for certain data
 *
 * Copyright 2020 Global Collect Services B.V
 *
 */
class ReadInternalStorage {

	// Tag for logging
	private static final String TAG = ReadInternalStorage.class.getName();

	// Context used for accessing files
	private Context context;

	public ReadInternalStorage(Context context) {
		this.context = context;
	}


	/**
	 * Reads all iinResponses from the cache on disk
	 */
	@SuppressWarnings("unchecked")
	public Map<String, IinDetailsResponse> getIinResponsesFromCache() {

		if (context == null) {
			throw new InvalidParameterException("Error getting response in cache, context may not be null");
		}

		// Create new map which contains all the iinResponses
		Map<String, IinDetailsResponse> iinResponses = new HashMap<>();

		// Check if the cachefile exists
		String directory = context.getFilesDir() + Constants.DIRECTORY_IINRESPONSES;
		File file = new File(directory, Constants.FILENAME_IINRESPONSE_CACHE);
		if (file.exists()) {
			// read the content and parse it to Map<String, IinDetailsResponse>
			try(FileInputStream fileInputStream = new FileInputStream(file);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
			) {
				iinResponses = (Map<String, IinDetailsResponse>)objectInputStream.readObject();
			} catch (StreamCorruptedException e) {
				Log.e(TAG, "Error getting List<PaymentProduct> from internal file ", e);
			} catch (IOException e) {
				Log.e(TAG, "Error getting List<PaymentProduct> from internal file ", e);
			} catch (ClassNotFoundException e) {
				Log.e(TAG, "Error getting List<PaymentProduct> from internal file ", e);
			}
		}

		return iinResponses;
	}



	public Drawable getLogoFromInternalStorage(String paymentProductId, Resources resources) {

		if (paymentProductId == null) {
			throw new InvalidParameterException("Error getting drawable from file, paymentProductId may not be null");
		}

		Drawable imageFromFile = null;

		// Check if the cachefile exists
		String directory = context.getFilesDir() + Constants.DIRECTORY_LOGOS;
		File file = new File(directory, Constants.FILENAME_LOGO_PREFIX + paymentProductId);
		if (file.exists()) {
			// read the content and parse it to BitmapDrawable
			try(FileInputStream fileInputStream = new FileInputStream(file)) {
				imageFromFile = new BitmapDrawable(resources, BitmapFactory.decodeStream(fileInputStream));

			} catch (IOException e) {
				Log.e(TAG, "Error getting drawable from file ", e);
			}
		}

		return imageFromFile;

	}


}
