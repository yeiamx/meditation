package com.flctxx.meditation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.mockito.Mockito;

import com.flctxx.meditation.AbstractTest;
import com.flctxx.meditation.utils.NetworkUtility;

public abstract class AbstractTest extends Mockito {
    protected static String urlStarter;
    protected static String postUrl;
    protected static String getUrl;
    
    public static String doPostWithJsonAsParam(String urlString, String jsonString) {
        try {
            HttpURLConnection conn = (HttpURLConnection)
                    (new URL(urlString)).openConnection();
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(conn.getOutputStream()));
            writer.write(jsonString);
            writer.flush();
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            return NetworkUtility.exhaustBufferedReader(rd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    @BeforeClass
    public static void getUrlStarter() {
        Properties properties = new Properties();
        try {
            String filepath = AbstractTest.class.
                    getClassLoader().getResource("test.conf").getPath();
            if (filepath.contains("%"))
                filepath = URLDecoder.decode(filepath, "UTF-8");
            properties.loadFromXML(new FileInputStream(new File(filepath)));
            urlStarter = (String) properties.get("url-starter");
            logger.info("Current API Test is using url-starter: " + urlStarter);
        } catch (IOException e) {
            e.printStackTrace();
            urlStarter = "http://localhost:8080";
            logger.info("Test configuration file is not found, " +
                    "using default [http://localhost:8080]");
        }
    }
    
    private static final Logger logger = Logger.getLogger(AbstractTest.class);
}