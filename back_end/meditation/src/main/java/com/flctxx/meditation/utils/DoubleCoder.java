package com.flctxx.meditation.utils;

import java.io.IOException;
import java.net.*;

public class DoubleCoder {

    public static String encode(String code) throws IOException {
        return URLEncoder.encode(URLEncoder.encode(code, "UTF-8"), "UTF-8");
    }

    public static String decode(String code) throws IOException {
        return URLDecoder.decode(URLDecoder.decode(code, "UTF-8"), "UTF-8");
    }
}
