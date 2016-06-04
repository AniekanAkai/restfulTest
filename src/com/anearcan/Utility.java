package com.anearcan;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.anearcan.jireh.elements.Service;
import com.anearcan.jireh.elements.ServiceProvider;
import com.anearcan.jireh.elements.ServiceType;
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
    
    /*
     * Method to generate user from JSON
     * 
     */
    public static User generateUserFromJSON(String json){
    	User u = new User();
    	try {
			JSONObject o = new JSONObject(json);
			u = new User(o.getLong("id"),
					o.getString("fullname"), 
					o.getString("password"), 
					new Date(o.getLong("dob")), 
					o.getString("phoneNumber"),
					o.getString("email"));
			u.setCurrentLocation(o.getString("currentLocation"));
			u.setCurrentRating(o.getDouble("averageRating"));
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
    	
    	return u;
    }
    
    /*
     * Method to generate service provider from JSON
     * 
     */
    public static ServiceProvider generateServiceProviderFromJSON(String json){
    	ServiceProvider sp = new ServiceProvider();
    	try {
			JSONObject o = new JSONObject(json);
			sp = new ServiceProvider();
			sp.setUserID(o.getLong("userid"));
			sp.setAvailabilityRadius(o.getDouble("radius"));
			sp.setCurrentLocation(o.getString("currentLocation"));
			sp.setBankInfo(o.getString("bankInfo"));
			String[] servicesOffered = o.getString("servicesOffered").split(";");
			for(int i=0; i<servicesOffered.length; i++){
				sp.addServicesOffered(servicesOffered[i]);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return sp;
    }
    
    /*
     * Method to generate service from JSON
     * 
     */
    public static Service generateServiceFromJSON(String json){
    	Service s = new Service();
    	try {
			JSONObject o = new JSONObject(json);
			s = new Service(new User(),
					new ServiceProvider(),
					o.getString("serviceType"),
					new Date(o.getLong("scheduledTime")),
					o.getDouble("ratePerHour"),
					o.getBoolean("userProvidesTool"));
			s.getUser().setID(o.getLong("userid"));
			s.getServiceProvider().setID(o.getLong("serviceproviderid"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return s;
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