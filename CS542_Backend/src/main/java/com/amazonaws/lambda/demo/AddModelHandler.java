package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

public class AddModelHandler implements RequestStreamHandler {
	JSONParser parser = new JSONParser();
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
    	LambdaLogger logger = context.getLogger();
        logger.log("Loading Java Lambda handler of ProxyWithStream");
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        JSONObject responseJson = new JSONObject();
        String responseCode = "200";
        
        //	default value
        String year = "";
        String make = "";
        String model = "";
        String trim = "";
        String transmission = "";
        String engine = "";
        String drive_type = "";
        
        try {
        	JSONObject event = (JSONObject)parser.parse(reader);
        	logger.log(event.toString());
            if ( event.get("year") != null) {
                year = (String)event.get("year");
            }
            if ( event.get("make") != null) {
                make = (String)event.get("make");
            }
            if ( event.get("model") != null) {
                model = (String)event.get("model");
            }
            if ( event.get("trim") != null) {
                trim = (String)event.get("trim");
            }
            if ( event.get("transmission") != null) {
                transmission = (String)event.get("transmission");
            }
            if ( event.get("engine") != null) {
                engine = (String)event.get("engine");
            }
            if ( event.get("drive_type") != null) {
                drive_type = (String)event.get("drive_type");
            }

            //	Error handle
            if (year == "" || make == "" || model == "") {
            	throw new Exception("Invalid input to add new model");
            }
            
            addModel(year, make, model, trim, transmission, engine, drive_type, context);
            
            JSONObject responseBody = new JSONObject();
            responseBody.put("input", event.toString());

            responseJson.put("isBase64Encoded", false);
            responseJson.put("statusCode", responseCode);
            responseJson.put("body", responseBody.toString());  

        } catch(Exception pex) {
            logger.log(pex.toString());
        }

        logger.log(responseJson.toString());
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toString());  
        writer.close();
    }
	private void addModel(String year, String make, String model, String trim, String transmission, String engine,
			String drive_type, Context context) {
		LambdaLogger logger = context.getLogger();
		try {
    		String url = "jdbc:mysql://cardb.clnm8zsvchg3.us-east-2.rds.amazonaws.com:3306";
    	    String username = "calcAdmin";
    	    String dbpassword = "rootmasterpassword";

    	    Connection conn = DriverManager.getConnection(url, username, dbpassword);
    	    Statement stmt = conn.createStatement();
    	    
    	    //	Add new model
    	    String newModel = String.format("INSERT INTO innodb.Model (year, make, model, trim, transmission, engine, drive_type)"
    	    		+ " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
    	    		year, make, model, trim, transmission, engine, drive_type);
    	    stmt.executeUpdate(newModel);
    	    
    	    stmt.close();
    	    conn.close();

    	} catch (Exception e) {
    	    e.printStackTrace();
    	    logger.log("Caught exception: " + e.getMessage());
    	}
	}

}
