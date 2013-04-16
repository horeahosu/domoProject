package com.domo.selenium.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.internal.Base64Encoder;

public class WSCallerHelper {
	private FileUtil fileUtil;
	private static final List<String> NOT_OK_RESPONSE_CODES = new ArrayList<String>();

	static {
		NOT_OK_RESPONSE_CODES.add("401");
	}

	public WSCallerHelper() {
		fileUtil = new FileUtil();
	}

	/**
	 * Sends a request to a Web Service
	 * 
	 * @param wsURL
	 * @param fileName
	 * @return
	 */
	public String callWS(String wsURL, String fileName) {
		return this.callWS(fileName, wsURL, null, null);
	}

	/**
	 * Calls a Web Service that requires basic auth
	 * 
	 * @param wsURL
	 * @param fileName
	 * @param user
	 * @param pwd
	 * @return
	 */
	public String callWS(String wsURL, String fileName, String user, String pwd) {
		URL url;
		HttpURLConnection connection = null;

		try {
			String xmlRequest = fileUtil.getFileContentsAsString(fileName);
			url = new URL(wsURL);

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Length",
					String.valueOf(xmlRequest.length()));
			connection.setRequestProperty("Content-Type", "text/xml");
			connection.setRequestProperty("Connection", "Close");
			connection.setRequestProperty("SoapAction", "");
			connection.setDoOutput(true);

			connection.setUseCaches(false);

			if (user != null && pwd != null) {
				String auth = (user + ":" + pwd);
				String encoding = new Base64Encoder().encode(auth.getBytes());
				connection.setRequestProperty("Authorization", "Basic "
						+ encoding);
			}
			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(xmlRequest);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is;

			System.out.println("response code = "
					+ connection.getResponseCode());
			if (connection.getResponseCode() <= 400) {
				is = connection.getInputStream();
			} else {
				/* error from server */
				is = connection.getErrorStream();
			}

			if (NOT_OK_RESPONSE_CODES.contains(String.valueOf(connection
					.getResponseCode()))) {
				return String.valueOf(connection.getResponseCode());
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			System.out.println("response " + response.toString());
			return response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
