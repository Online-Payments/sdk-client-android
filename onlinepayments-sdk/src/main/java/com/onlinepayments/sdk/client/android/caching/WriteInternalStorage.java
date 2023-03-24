package com.onlinepayments.sdk.client.android.caching;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.onlinepayments.sdk.client.android.configuration.Constants;
import com.onlinepayments.sdk.client.android.model.iin.IinDetailsResponse;


/**
 * This class is responsible for writing files on disk who act as cache for certain data
 *
 * Copyright 2020 Global Collect Services B.V
 *
 */
class WriteInternalStorage {

	// Tag for logging
	private static final String TAG = WriteInternalStorage.class.getName();

	// Context used for accessing files
	private Context context;


	// Used for reading the current cached data to merge cached items
	private ReadInternalStorage storage;


	public WriteInternalStorage(Context context) {
		this.context = context;
		storage = new ReadInternalStorage(context);
	}



	/**
	 * Stores the given iinResponses in the cache on disk
	 *
	 * @param partialCreditCardNumber, entered partial creditcardnumber
	 * @param iinResponse, the IinDetailsResponse which is added to the current cache
	 */
	public void storeIinResponseInCache(String partialCreditCardNumber, IinDetailsResponse iinResponse) {

		if (partialCreditCardNumber == null) {
			throw new InvalidParameterException("Error storing response in partialCreditCardNumber, context may not be null");
		}
		if (iinResponse == null) {
			throw new InvalidParameterException("Error storing response in iinResponse, iinResponse may not be null");
		}

		// Retrieve the currenct cached items and add the new one to it
		Map<String, IinDetailsResponse> currentCachedIinResponses = storage.getIinResponsesFromCache();

		// Overwrite old value if it exists
        currentCachedIinResponses.remove(partialCreditCardNumber);
		currentCachedIinResponses.put(partialCreditCardNumber, iinResponse);

		// Then write the currentCachedIinResponses to disk
		String directory = context.getFilesDir() + Constants.DIRECTORY_IINRESPONSES;
		File file = new File(directory, Constants.FILENAME_IINRESPONSE_CACHE);
		file.getParentFile().mkdirs();

		try(FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
		) {
			objectOutputStream.writeObject(currentCachedIinResponses);
        } catch (IOException e) {
			Log.e(TAG, "Error storing BasicPaymentProducts on internal device storage", e);
		}
	}

	/**
	 * Stores the given image on disk
	 *
	 * @param paymentProductId, used for creating the file on disk
	 * @param image, Drawable that is written to disl
	 */
	public void storeLogoOnInternalStorage(String paymentProductId, Drawable image) {

		if (paymentProductId == null) {
			throw new InvalidParameterException("Error storing drawable on disk, paymentProductId may not be null");
		}
		if (image == null) {
			throw new InvalidParameterException("Error storing drawable on disk, image may not be null");
		}

		// Write the Drawable to disk
		String directory = context.getFilesDir() + Constants.DIRECTORY_LOGOS;
		File file = new File(directory, Constants.FILENAME_LOGO_PREFIX + paymentProductId);
		file.getParentFile().mkdirs();

		try(FileOutputStream fileOutputStream = new FileOutputStream(file);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
		) {
			// Parse the image to byte[] and write it
			Bitmap bitmap = ((BitmapDrawable)image).getBitmap();
			bitmap.compress(CompressFormat.PNG, 0 , byteArrayOutputStream);
			byte[] bitmapdata = byteArrayOutputStream.toByteArray();

			fileOutputStream.write(bitmapdata);

        } catch (IOException e) {
			Log.e(TAG, "Error storing drawable on internal device storage", e);
		}
	}


}
