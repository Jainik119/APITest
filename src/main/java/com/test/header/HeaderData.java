package com.test.header;

import org.apache.commons.codec.binary.Base64;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HeaderData {

    public static Map<String, String> requestOTPHeader(){
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type","application/json");
        map.put("Connection","keep-alive");
        map.put("Accept-Encoding","gzip,deflate,br");
        map.put("appName","giam");
        map.put("appKey","");
        return map;
    }

    public static Map<String, String> initialGET(){
        Map<String, String> map = new HashMap<>();
        map.put("X-XSRF-Header","PingFederate");
        return map;
    }

    public static Map<String, String> userCredential(){
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type","application/vnd.pingidentity.checkUsernamePassword+json");
        map.put("X-XSRF-Header","PingFederate");
        return map;
    }

    public static Map<String, String> exchangeAuthCode(){
        Map<String, String> map = new HashMap<>();
        String auth = "john@123" + ":" + "john@123";
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);
        map.put("Authorization", authHeader);
        return map;
    }

}
