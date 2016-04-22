package com.anearcan;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.anearcan.jireh.elements.User;

public class Utility {
    /**
     * Null check Method
     * 
     * @param txt
     * @return
     */
    public static boolean isNotNull(String txt) {
        System.out.println("Inside isNotNull for "+ txt);
        return txt != null && txt.trim().length() >= 0 ? true : false;
    }
 
    /**
     * Method to construct JSON
     * 
     * @param tag
     * @param status
     * @return
     */
    public static String constructJSON(String tag, boolean status) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        	e.printStackTrace();
        }
        return obj.toString();
    }
 
    /**
     * Method to construct JSON
     * 
     * @param tag
     * @param status
     * @param user
     * @return
     */
    public static String constructJSON(String tag, boolean status, User user) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("email", user.getEmail());
            obj.put("password", user.getPassword());
            obj.put("dob", user.getDateOfBirth());
            obj.put("fullname", user.getFullname());
            obj.put("phone", user.getPhoneNumber());
            obj.put("rating", user.getCurrentAverageRating());
            obj.put("id", user.getID());
            
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        	e.printStackTrace();
        }
        System.out.println(obj.toString());
        return obj.toString();
    }
    
    
    /**
     * Method to construct JSON with Error Msg
     * 
     * @param tag
     * @param status
     * @param err_msg
     * @return
     */
    public static String constructJSON(String tag, boolean status,String err_msg) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("error_msg", err_msg);
        } catch (JSONException e) {
        	e.printStackTrace();
        }
        return obj.toString();
    }
 
    public static String dateToDatabaseFormat(Date date){
    	String dateString = "";
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
    	return sdf.format(date);
    }
    
}