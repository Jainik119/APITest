package com.test.tc;

import com.relevantcodes.extentreports.LogStatus;
import com.test.config.APICommon;
import com.test.header.HeaderData;
import com.test.payload.APIPayload;
import com.test.report.ExtentTestManager;
import com.test.util.Constant;
import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IAMSandboxSAML extends APICommon implements Constant {

    public static String flowID = "";
    public static String code = "";


    @Test(priority = 1)
    public void giamOAuthCall() throws Exception {
        try {
            HttpResponse response = APICommon.getData(IAM_SANDBOX_OAUTH_BASEURL + "/idp/startSSO.ping?PartnerSpid=GiamSandboxSP", null);
            flowID = response.getHeaders("Location").toString().split("=")[1];
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed",response.getEntity().toString());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
            ExtentTestManager.getTest().log(LogStatus.FAIL, "Oops ! .. API have some issue ",e.getMessage());
        }
    }

    @Test(priority = 2)
    public void initial_GET_FlowId() throws Exception {
        try {
            HttpResponse response = APICommon.getData(IAM_SANDBOX_OAUTH_BASEURL + "/pf-ws/authn/flows/"+flowID, HeaderData.initialGET());
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed",response.getEntity().toString());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
            ExtentTestManager.getTest().log(LogStatus.FAIL, "Oops ! .. API have some issue ",e.getMessage());
        }
    }

    @Test(priority = 3)
    public void userCredentials() throws Exception {
        try {
            APIPayload payload = new APIPayload();
            HttpResponse response = APICommon.postData(REQUEST_OTP_BASEURL + "/pf-ws/authn/flows/"+flowID, payload.userCredential().toString(), "POST", HeaderData.userCredential());
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed",response.getEntity().toString());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
            ExtentTestManager.getTest().log(LogStatus.FAIL, "Oops ! .. API have some issue ",e.getMessage());
        }
    }

    @Test(priority = 4)
    public void resumeProcessing() throws Exception {
        try {
            HttpResponse response = APICommon.getData(IAM_SANDBOX_OAUTH_BASEURL + "/as/"+flowID+"/resume/as/authorization.ping", null);
            Document document = Jsoup.parse(response.getEntity().toString());
            Element ele = document.select("input").first();
            code = ele.attr("value");
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed",code);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
            ExtentTestManager.getTest().log(LogStatus.FAIL, "Oops ! .. API have some issue ",e.getMessage());
        }
    }
}
