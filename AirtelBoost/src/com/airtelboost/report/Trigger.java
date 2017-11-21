package com.airtelboost.report;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

public class Trigger {

	public static LinkedHashMap<String, String> userDetails = new LinkedHashMap<String, String>();
	public static BufferedReader br = null;

	public static void loginValues(String credentials) {
		String[] keys = credentials.split(",");
		for (String val : keys) {
			String[] users = val.split("::");
			userDetails.put(users[0], users[1]);
		}
	}

	public Boolean loginValidation(String username, String password) {
		return password.equals(userDetails.get(username)) ? true : false;
	}

	public static LinkedHashMap<String, String> msisdnReport(String msisdn, String fileName) throws IOException {

		br = null;
		LinkedHashMap<String, String> msisdnDetails = new LinkedHashMap<String, String>();

		br = new BufferedReader(new FileReader(fileName));
		String str, header;
		header = br.readLine();
		msisdnDetails.put("headerTag", header);

		while ((str = br.readLine()) != null) {
			if (str.contains(msisdn)) {
				msisdnDetails.put(msisdn,
						((msisdnDetails.get(msisdn) == null) ? str : msisdnDetails.get(msisdn) + "#" + str));
			}
		}

		return msisdnDetails;

	}

}