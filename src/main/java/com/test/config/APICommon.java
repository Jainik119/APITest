package com.test.config;

import com.relevantcodes.extentreports.LogStatus;
import com.test.report.ExtentManager;
import com.test.report.ExtentTestManager;
import com.test.report.listner.AnnotationTransformer;
import com.test.report.listner.TestListener;
import com.test.util.Constant;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


@Listeners({AnnotationTransformer.class, TestListener.class})
public class APICommon implements Constant{


    public APICommon() {
    }

    @BeforeMethod(alwaysRun = true)
    public void testStart(ITestResult result, Method method) throws Exception{
        ExtentTestManager.startTest("" + method.getName());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Test Start" );
    }

    @AfterMethod
    public void closeAllSession(ITestResult result) throws Exception{
        if (result.getStatus() == 2) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
            ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed");
        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
        } else {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
        }

        ExtentManager.getReporter().endTest(ExtentTestManager.getTest());
        ExtentManager.getReporter().flush();
        ExtentTestManager.getTest().log(LogStatus.INFO, "Session Closed");
    }

    public static String inputStreamToString(InputStream is) throws IOException {
        String line;
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        while ((line = rd.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }

    public static HttpResponse getData(String uri,Map<String, String> header) throws JSONException, IOException, URISyntaxException {
        @SuppressWarnings("deprecation") HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(new URI(uri));
        if (header != null){
            for (Map.Entry m:header.entrySet()){
                request.setHeader(m.getKey().toString(),m.getValue().toString());
            }
        }
        HttpResponse response = client.execute(request);
        return response;
    }

    public static HttpResponse postData(String uri, String data, String method, Map<String,String> header) throws JSONException, IOException, URISyntaxException {
        @SuppressWarnings("deprecation") HttpClient client = new DefaultHttpClient();
        HttpUriRequest request;
        switch (method.toUpperCase()) {
            case "PUT":
                request = new HttpPut(new URI(uri));
                ((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(data));
                break;
            case "DELETE":
                request = new HttpDelete(new URI(uri));
                break;
            case "POST":
            default:
                request = new HttpPost(new URI(uri));
                ((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(data));
                break;
        }
        if (header != null){
            for (Map.Entry m:header.entrySet()){
                request.setHeader(m.getKey().toString(),m.getValue().toString());
            }
        }
        HttpResponse response = client.execute(request);
        return response;
    }

    public static String response(int responseCode){
        String response = "";
        switch (responseCode) {
            case 200:
                response = "200 - Success";
                break;
            case 400:
                response = "400 - Bad Request";
                break;
            case 401:
                response = "401 - Unauthorized";
                break;
            case 403:
                response = "403 - Forbidden";
                break;
            case 404:
                response = "404 - Not Found";
                break;
            case 405:
                response = "405 - Method Not Allowed";
                break;
            case 406:
                response = "406 - Not Acceptable";
                break;
            case 500 :
                response = "500 - Internal Server Error";
                break;
            case 501:
                response = "501 - Not Implemented";
                break;
            case 412:
                response = "412 - Precondition Failed";
                break;
            case 415:
                response = "415 - Unsupported Media Type";
                break;
            case 301:
                response = "301 - Moved Permanently";
                break;
            default:
                response = "000 - Fail";
                break;
        }
        return response.toUpperCase();
    }

}
