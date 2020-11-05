package com.test.payload;

import com.test.config.LoadPropertiesFiles;
import org.json.JSONObject;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class APIPayload {

    public JSONObject requestOTP(){
        JSONObject data = new JSONObject();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        String userid = "Automation_user_"+dateFormat.format(new Date());
        try {
            LoadPropertiesFiles.saveProperties(userid);
            data = new JSONObject();
            data.put("userName", userid);
            data.put("firstName", "test");
            data.put("lastName", "Aksudov");
            data.put("email", "sadik.maksudov@firstdata.com");
            data.put("serviceAccount", "gi-rw-001");
            data.put("serviceAccountPassword", "Welcome@123");
            data.put("template", "1");
            data.put("stubCode", "123");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return data;
    }

    public JSONObject deleteUser(){
        JSONObject data = new JSONObject();
        try {
            Properties p = LoadPropertiesFiles.loadProperties();
            data = new JSONObject();
            data.put("userName", p.getProperty("userName"));
            data.put("serviceAccount", "gi-rw-001");
            data.put("serviceAccountPassword", "Welcome@123");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return data;
    }

    public JSONObject userCredential(){
        JSONObject data = new JSONObject();
        try {
            data = new JSONObject();
            data.put("username", "sadik.maksudov@first.com");
            data.put("password", "Welcome123");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return data;
    }
}
