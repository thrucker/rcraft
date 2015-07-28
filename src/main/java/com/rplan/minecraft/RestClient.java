package com.rplan.minecraft;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class RestClient {
	public static String login(String username, String password) throws IOException {
        URL url = new URL("https://rplan.com/login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        byte[] bytes = (username + ":" + password).getBytes("UTF-8");
        String auth = Base64.getEncoder().encodeToString(bytes);
        conn.setRequestProperty("Authorization", "Basic " + auth);
        conn.setRequestMethod("POST");

        if (conn.getResponseCode() != 200) {
            throw new IOException(conn.getResponseMessage());
        }

        String cookie = conn.getHeaderField("Set-Cookie");
//        Pattern p = Pattern.compile("SID=\\(.+\\);");
//        Matcher m = p.matcher(cookie);
//        return m.group(1);
        int x = cookie.indexOf(';');
        return cookie.substring(4, x);
    }

	public static String get(URL url, String authCookie) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Cookie", "cookie-accepted=1; SID=" + authCookie);
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		// Buffer the result into a string
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();

		return sb.toString();
	}
}
