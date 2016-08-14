package com.anearcan;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.codehaus.jettison.json.JSONArray;
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
            obj.put("isAdmin", user.isAdmin());            
        } catch (JSONException e) {
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
			u.setAdmin(o.getBoolean("isAdmin"));
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
			sp.setUserID(o.getLong("id"));
			sp.setAvailabilityRadius(o.getDouble("availabilityRadius"));
			sp.setCurrentLocation(o.getString("currentLocation"));
			sp.setBankInfo(o.getString("bankInfo"));
			sp.setBusinessAddress(o.getString("businessAddress"));
			JSONArray servicesOffered = o.getJSONArray("servicesOffered");
			
			for(int i=0; i<servicesOffered.length()  ; i++){
				sp.addServicesOffered(servicesOffered.getString(i));
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
    public static String constructJSON(String tag, boolean status, String err_msg) {
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
    
    public static String constructJSON(String tag, boolean status, String err_msg, JSONObject objects)
    {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("error_msg", err_msg);
            obj.put("list", objects);
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
    
    /**
     * Calculate distance between 2 points on earth's surface using their long and lat coordinates. 
     * Haversine formula used as below
     * dlon = lon2 - lon1 
     * dlat = lat2 - lat1
     * a = (sin(dlat/2))^2 + cos(lat1) * cos(lat2) * (sin(dlon/2))^2
     * c = 2 * atan2( sqrt(a), sqrt(1-a) )
     * d = R * c (where R is the radius of the Earth)
     * Using  earth's radius of 6373km
     */
    public static double calculateDistanceInKM(double long1, double lat1, double long2, double lat2)
    {
    	double radiusOfEarthInKM = 6373.0;
    	double dlong = long2 - long1;
    	double dlat = lat2 - lat1;
    	
    	double a = Math.pow(Math.sin(dlat/2), 2) + (Math.cos(lat1)*Math.cos(lat2)*Math.pow(Math.sin(dlong/2), 2));
    	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = radiusOfEarthInKM * c;
    	return d;    	
    }
    
    /**
     * Calculate distance between 2 points on earth's surface using their long and lat coordinates. 
     * Haversine formula used as below
     * dlon = lon2 - lon1 
     * dlat = lat2 - lat1
     * a = (sin(dlat/2))^2 + cos(lat1) * cos(lat2) * (sin(dlon/2))^2
     * c = 2 * atan2( sqrt(a), sqrt(1-a) )
     * d = R * c (where R is the radius of the Earth)
     * 
     * Using earth's radius of 3961 miles.
     */
    public static double calculateDistanceInMiles(double long1, double lat1, double long2, double lat2)
    {
    	double radiusOfEarthInMiles = 3961.0;
    	double dlong = long2 - long1;
    	double dlat = lat2 - lat1;
    	
    	double a = Math.pow(Math.sin(dlat/2), 2) + (Math.cos(lat1)*Math.cos(lat2)*Math.pow(Math.sin(dlong/2), 2));
    	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = radiusOfEarthInMiles * c;
    	return d;    	
    }
}

