package com.test.tc;

import com.relevantcodes.extentreports.LogStatus;
import com.test.config.APICommon;
import com.test.header.HeaderData;
import com.test.payload.APIPayload;
import com.test.report.ExtentTestManager;
import com.test.util.Constant;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RequestOTPTest extends APICommon implements Constant {

    @Test(priority = 1)
    public void requestOTPCall() throws Exception {
        try {
            APIPayload payload = new APIPayload();
            HttpResponse response = APICommon.postData(REQUEST_OTP_BASEURL + "/giam-auth-1/v1.2/requestOTP", payload.requestOTP().toString(), "POST", HeaderData.requestOTPHeader());
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 201);
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed",response.getEntity().toString());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
            ExtentTestManager.getTest().log(LogStatus.FAIL, "Oops ! .. API have some issue ",e.getMessage());
        }
    }
    @Test(priority = 2)
    public void deleteUserCall() throws Exception {
        try {
            APIPayload payload = new APIPayload();
            HttpResponse response = APICommon.postData(REQUEST_OTP_BASEURL + "/giam-auth-1/v1.2/deleteUser", payload.deleteUser().toString(), "POST", HeaderData.requestOTPHeader());
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed",response.getEntity().toString());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
            ExtentTestManager.getTest().log(LogStatus.FAIL, "Oops ! .. API have some issue ",e.getMessage());
        }
    }

}
