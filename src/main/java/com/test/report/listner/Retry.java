package com.test.report.listner;

import com.relevantcodes.extentreports.LogStatus;
import com.test.report.ExtentTestManager;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {                      //Check if test not succeed
                iTestResult.setStatus(ITestResult.FAILURE);  //Mark test as failed
         } else {
            iTestResult.setStatus(ITestResult.SUCCESS);      //If test passes, TestNG marks it as passed
        }
        return false;
    }

    public void extendReportsFailOperations(ITestResult iTestResult) {
        ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed","");
    }
}
