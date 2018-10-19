package com.kylej.zillowroofingsearcher.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kylej.zillowroofingsearcher.utils.Utils;

public class AddressSearchRequest {

	private final String API_REQUEST_URL = "https://www.zillow.com/webservice/GetSearchResults.htm";

	private final String ENCODED_ADDRESS;
	private final int zipCode;

	private String requestUrl;

	public AddressSearchRequest(String address, int zipCode) {
		String tempEncodedAddress = "";
		try {
			tempEncodedAddress = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ENCODED_ADDRESS = tempEncodedAddress;
		this.zipCode = zipCode;
		requestUrl = buildRequestURL();
		execute();
	}

	public JSONObject execute() {
		HttpsURLConnection con = null;
		String jsonString;
		JSONObject myResponse = null;
		try {
			URL url = new URL(requestUrl);
			con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			HttpGet request = new HttpGet(requestUrl);
			HttpResponse response;
			HttpClient client = HttpClientBuilder.create().build();

			response = client.execute(request);
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			String result = convertStreamToString(inputStream);
			// JSONArray results = new JSONArray(result);
			JSONObject results = new JSONObject(result);

		} catch (MalformedURLException e) {
			System.out.println("URL not found");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public String buildRequestURL() {
		StringBuilder sb = new StringBuilder();
		sb.append(API_REQUEST_URL + "?");
		sb.append("zws-id=" + Utils.ZILLOW_API_ID + "&");
		sb.append("address=" + ENCODED_ADDRESS + "&");
		sb.append("citystatezip=" + zipCode);
		return sb.toString();
	}
}
