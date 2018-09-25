package com.flctxx.meditation.utils;

import org.json.simple.JSONArray;

import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;

public class NetworkUtility {
	private static final Logger logger = Logger.getLogger(NetworkUtility.class);
	
    public static String doPostRequest(String urlString) {
        return doRequest(urlString, "POST");
    }

    public static String doGetRequest(String urlString) {
        return doRequest(urlString, "GET");
    }

    public static String postJson(String urlString, String jsonString) {
        try {
            HttpURLConnection conn = (HttpURLConnection)
                    (new URL(urlString)).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(conn.getOutputStream()));
            writer.write(jsonString);
            writer.flush();
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String response = rd.readLine();
            rd.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static JSONArray getJson(String urlString) {
        try {
            HttpURLConnection conn = (HttpURLConnection)
                    (new URL(urlString)).openConnection();
            conn.setDoInput(true);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line = null;
            StringBuffer response = new StringBuffer();
            boolean firstLine = true;
            while ((line = rd.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    response.append(line);
                } else {
                    response.append("\n" + line);
                }
            }
            rd.close();
            System.out.println(response.toString());
            return (JSONArray) (new JSONParser()).parse(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getJsonStr(String urlString) {
        try {
            HttpURLConnection conn = (HttpURLConnection)
                    (new URL(urlString)).openConnection();
            conn.setDoInput(true);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line = null;
            StringBuffer response = new StringBuffer();
            boolean firstLine = true;
            while ((line = rd.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    response.append(line);
                } else {
                    response.append("\n" + line);
                }
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method assumes that the response from some host contains only
     * one line of message
     * @param urlString
     * @return one line response from the host
     */
    private static String doRequest(String urlString, String method) {
        try {
            HttpURLConnection conn = (HttpURLConnection)
                    (new URL(urlString)).openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod(method);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String response = rd.readLine();
            rd.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String exhaustBufferedReader(BufferedReader rd) {
        String line;
        StringBuffer response = new StringBuffer();
        boolean firstLine = true;
        try {
            while ((line = rd.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    response.append(line);
                } else {
                    response.append("\n" + line);
                }
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

}
