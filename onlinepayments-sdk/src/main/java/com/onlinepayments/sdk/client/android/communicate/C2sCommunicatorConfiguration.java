/*
 * Copyright 2020 Global Collect Services B.V
 */

package com.onlinepayments.sdk.client.android.communicate;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import androidx.annotation.Nullable;

import com.onlinepayments.sdk.client.android.Util;
import com.onlinepayments.sdk.client.android.session.Session;

/**
 * Contains all configuration parameters needed for communicating with the Online Payments gateway.
 *
 * @deprecated In a future release, this class and its functions will become internal to the SDK.
 */
@Deprecated
public class C2sCommunicatorConfiguration implements Serializable {

	private static final long serialVersionUID = 4087796898189138740L;
	private static final String API_VERSION = "v1/";
	private static final String API_BASE = "client/";
	private static final String API_PATH = API_BASE + API_VERSION;
	private static final Pattern VERSION_PATTERN = Pattern.compile(".*(" + API_BASE + "v\\d\\/)");

	// C2SCommunicator related configuration variables
	private String clientSessionId;
	private String customerId;
	private String clientApiUrl;
	private String assetUrl;
	private boolean environmentIsProduction;

	// Merchant specified app-information
	private String appIdentifier;
	private String ipAddress;

	public boolean loggingEnabled = false;

	/**
	 * Constructor, creates the C2SCommunicatorConfiguration object.
	 *
	 * @param clientSessionId used for identifying the session on the Online Payments gateway
	 * @param customerId used for identifying the customer on the Online Payments gateway
	 * @param clientApiUrl the endpoint baseurl
	 * @param assetUrl the asset baseurl
	 * @param environmentIsProduction states if the environment is production
	 * @param appIdentifier used to create device metadata
	 * @param ipAddress used to create device metadata; may be null
	 *
	 */
	public C2sCommunicatorConfiguration(String clientSessionId, String customerId, String clientApiUrl, String assetUrl, boolean environmentIsProduction, String appIdentifier, String ipAddress) {
		this(clientSessionId, customerId, clientApiUrl, assetUrl, environmentIsProduction, appIdentifier, ipAddress, false);
	}


	/**
	 * Constructor, creates the C2SCommunicatorConfiguration object.
	 *
	 * @param clientSessionId used for identifying the session on the Online Payments gateway
	 * @param customerId used for identifying the customer on the Online Payments gateway
	 * @param clientApiUrl the endpoint baseurl
	 * @param assetUrl the asset baseurl
	 * @param environmentIsProduction states if the environment is production
	 * @param appIdentifier used to create device metadata
	 * @param ipAddress used to create device metadata; may be null
	 * @param loggingEnabled indicates whether requests and responses should be logged to the console; default is false; should be false in production
	 */
	public C2sCommunicatorConfiguration(String clientSessionId, String customerId, String clientApiUrl, String assetUrl, boolean environmentIsProduction, String appIdentifier, String ipAddress, boolean loggingEnabled) {

		if (clientSessionId == null) {
			throw new InvalidParameterException("Error creating C2SCommunicatorConfiguration, clientSessionId may not be null");
		}
		if (customerId == null) {
			throw new InvalidParameterException("Error creating C2SCommunicatorConfiguration, customerId may not be null");
		}
		if (clientApiUrl == null) {
			throw new InvalidParameterException("Error creating C2SCommunicatorConfiguration, clientApiUrl may not be null");
		}
		if (assetUrl == null) {
			throw new InvalidParameterException("Error creating C2SCommunicatorConfiguration, assetUrl may not be null");
		}
		if (appIdentifier == null) {
			throw new InvalidParameterException("Error creating C2SCommunicatorConfiguration, appIdentifier may not be null");
		}

		this.clientSessionId = clientSessionId;
		this.customerId = customerId;
		this.clientApiUrl = createClientUrl(clientApiUrl);
		this.assetUrl = assetUrl;
		this.environmentIsProduction = environmentIsProduction;
		this.appIdentifier = appIdentifier;
		this.ipAddress = ipAddress;
		this.loggingEnabled = loggingEnabled;
	}

	/**
	 * Convenience method for creating a {@link Session} without a given ipAddress.
	 *
	 * @param clientSessionId used for identifying the session on the Online Payments gateway
	 * @param customerId used for identifying the customer on the Online Payments gateway
	 * @param clientApiUrl the endpoint baseurl
	 * @param assetBaseUrl the asset baseurl
	 * @param environmentIsProduction states if the environment is production
	 * @param appIdentifier used to create device metadata
	 *
	 * @return initialised Session
	 *
	 * @deprecated use {@link Session#Session(String, String, String, String, boolean, String)} or {@link Session#Session(String, String, String, String, boolean, String, boolean)} instead.
	 */
	@Deprecated
	public static Session initWithClientSessionId(String clientSessionId, String customerId, String clientApiUrl, String assetBaseUrl, boolean environmentIsProduction, String appIdentifier) {
		return initSession(clientSessionId, customerId, clientApiUrl, assetBaseUrl, environmentIsProduction, appIdentifier, null);
	}


	/**
	 * Convenience method for creating a {@link Session} with a given ipAddress.
	 *
	 * @param clientSessionId used for identifying the session on the Online Payments gateway
	 * @param customerId used for identifying the customer on the Online Payments gateway
	 * @param clientApiUrl the endpoint baseurl
	 * @param assetBaseUrl the asset baseurl
	 * @param environmentIsProduction states if the environment is production
	 * @param appIdentifier used to create device metadata
	 * @param ipAddress used to create device metadata
	 *
	 * @return initialised Session
	 *
	 * @deprecated use {@link Session#Session(String, String, String, String, boolean, String)} or {@link Session#Session(String, String, String, String, boolean, String, boolean)} instead.
	 */
	@Deprecated
	public static Session initWithClientSessionId(String clientSessionId, String customerId, String clientApiUrl, String assetBaseUrl, boolean environmentIsProduction, String appIdentifier, String ipAddress) {
		return initSession(clientSessionId, customerId, clientApiUrl, assetBaseUrl, environmentIsProduction, appIdentifier, ipAddress);
	}

	private static Session initSession(String clientSessionId, String customerId, String clientApiUrl, String assetBaseUrl, boolean environmentIsProduction, String appIdentifier, String ipAddress) {
		C2sCommunicatorConfiguration configuration = new C2sCommunicatorConfiguration(clientSessionId, customerId, clientApiUrl, assetBaseUrl, environmentIsProduction, appIdentifier, ipAddress, false);
		return initSession(clientSessionId, configuration);
	}

	private static Session initSession(String clientSessionId, C2sCommunicatorConfiguration configuration) {
		C2sCommunicator communicator = C2sCommunicator.getInstance(configuration);

		Session session = Session.getInstance(communicator);
		session.setClientSessionId(clientSessionId);
		return session;
	}

	public String getClientSessionId() {
		return clientSessionId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public boolean environmentIsProduction() {
		return environmentIsProduction;
	}

	public String getAppIdentifier() {
		return appIdentifier;
	}

	/**
	 * @return may return null
	 */
	@Nullable
	public String getIpAddress() {
		return ipAddress;
	}


	public String getBaseUrl() {
		return clientApiUrl;
	}

	public String getAssetUrl() {
		return assetUrl;
	}

	/**
	 * Utility method for setting whether request/response logging should be enabled or not.
	 *
	 * @param enableLogging boolean indicating whether request/response logging should be enabled or not
	 */
	public void setLoggingEnabled(boolean enableLogging) {
		this.loggingEnabled = enableLogging;
	}

	/**
	 * Checks whether request/response logging is enabled or not.
	 *
	 * @return a boolean indicating whether request/response logging is enabled or not
	 */
	public boolean getLoggingEnabled() {
		return loggingEnabled;
	}

	/**
	 * Returns a map of metadata of the device this SDK is running on.
	 * The map contains the SDK version, OS, OS version and screen size.
	 *
	 * @param context used for retrieving device metadata
	 *
	 * @return a Map containing key/values of metadata
	 */
	public Map<String, String> getMetadata(Context context) {
		return Util.getMetadata(context, appIdentifier, ipAddress);
	}

	private String createClientUrl(String clientApiUrl) {

		// The URL must always end with a slash
		if(!clientApiUrl.endsWith("/")) {
			clientApiUrl = clientApiUrl + "/";
		}

		// Check if the URL is correct
		if(clientApiUrl.toLowerCase(Locale.ROOT).endsWith(API_PATH)) {
			return clientApiUrl;
		}

		// Add the version if it is missing
		if(clientApiUrl.toLowerCase(Locale.ROOT).endsWith(API_BASE)) {
			return clientApiUrl + API_VERSION;
		}

		// Check if the wrong version is set
		Matcher versionMatcher = VERSION_PATTERN.matcher(clientApiUrl.toLowerCase(Locale.ROOT));
		if (versionMatcher.matches()) {
			String version = versionMatcher.group(1);
			throw new InvalidParameterException("This version of the Online Payments is only compatible with '" + API_PATH + "', you supplied: '" + version + "'");
		}

		// Add the complete API path to the provided URL
		return clientApiUrl + API_PATH;
	}
}
