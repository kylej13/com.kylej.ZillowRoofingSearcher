package com.kylej.zillowroofingsearcher.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.kylej.zillowroofingsearcher.utils.Utils;

public class AddressSearchRequest {

	private final String API_REQUEST_URL = "https://www.zillow.com/webservice/GetSearchResults.htm";

	private final String ENCODED_ADDRESS;
	private final int zipCode;

	public AddressSearchRequest(String address, int zipCode) {
		String tempEncodedAddress = "";
		try {
			tempEncodedAddress = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ENCODED_ADDRESS = tempEncodedAddress;
		this.zipCode = zipCode;
	}
	
	//public JSONObject execute() {
		
	//}

	public String buildRequestURL() {
		StringBuilder sb = new StringBuilder();
		sb.append(API_REQUEST_URL + "?");
		sb.append("zws-id=" + Utils.ZILLOW_API_ID + "&");
		sb.append("address=" + ENCODED_ADDRESS + "&");
		sb.append("citystatezip=" + zipCode);
		return sb.toString();
	}
}
