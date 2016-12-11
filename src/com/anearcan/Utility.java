package com.anearcan;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.anearcan.jireh.elements.Service;
import com.anearcan.jireh.elements.ServiceProvider;
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
    public static String constructJSONForUserLogin(String tag, boolean status, User user, ArrayList<User> admins) {
        JSONObject obj = new JSONObject();
        ArrayList<String> adminJSONList = new ArrayList<String>();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("email", user.getEmail());
            obj.put("password", user.getPassword());
            obj.put("dob", user.getDateOfBirth());
            obj.put("fullname", user.getFullname());
            obj.put("phoneNumber", user.getPhoneNumber());
            obj.put("rating", user.getCurrentAverageRating());
            obj.put("id", user.getID());
            obj.put("isAdmin", user.isAdmin()); 
            
            obj.put("asServiceProvider", false);
            
//            obj.put("admins", admins);
            for(int i=0; i<admins.size();i++){
            	adminJSONList.add(constructUserJSON(admins.get(i)).toString());
            }
            obj.put("admins", adminJSONList);
        } catch (JSONException e) {
        	e.printStackTrace();
        }
        System.out.println(obj.toString());
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
    public static String constructJSONForUserLogin(String tag, boolean status, User user, ArrayList<User> admins, ArrayList<ServiceProvider> splist) {
        JSONObject obj = new JSONObject();
        ArrayList<String> adminJSONList = new ArrayList<String>();
        ArrayList<String> serviceProviderJSONList = new ArrayList<String>();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("email", user.getEmail());
            obj.put("password", user.getPassword());
            obj.put("dob", user.getDateOfBirth());
            obj.put("fullname", user.getFullname());
            obj.put("phoneNumber", user.getPhoneNumber());
            obj.put("rating", user.getCurrentAverageRating());
            obj.put("id", user.getID());
            obj.put("isAdmin", user.isAdmin()); 
            
            obj.put("asServiceProvider", false);
            
            for(int i=0; i<admins.size();i++){
            	adminJSONList.add(constructUserJSON(admins.get(i)).toString());
            }
            obj.put("admins", adminJSONList);
            
            for(int i=0; i<splist.size();i++){
            	serviceProviderJSONList.add(constructServiceProviderJSON(splist.get(i)).toString());
            }
            obj.put("serviceProvidersList", serviceProviderJSONList);
        } catch (JSONException e) {
        	e.printStackTrace();
        }
        System.out.println(obj.toString());
        return obj.toString();
    }

    /**
     * Method to construct JSON
     * 
     * @param tag
     * @param status
     * @param sp
     * @return
     */
    public static String constructJSONForServiceProviderLogin(String tag, boolean status, ServiceProvider sp, ArrayList<User> admins) {
        JSONObject obj = new JSONObject();
        ArrayList<String> adminJSONList = new ArrayList<String>();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("email", sp.getEmail());
            obj.put("password", sp.getPassword());
            obj.put("dob", sp.getDateOfBirth());
            obj.put("fullname", sp.getFullname());
            obj.put("phoneNumber", sp.getPhoneNumber());
            obj.put("rating", sp.getCurrentAverageRating());
            obj.put("id", sp.getID());
            obj.put("isAdmin", sp.isAdmin());
            obj.put("currentLocation", sp.getCurrentLocation());
            
            obj.put("availabilityRadius", sp.getAvailabilityRadius());
            obj.put("bankInfo", sp.getBankInfo());
            obj.put("businessAddress", sp.getBusinessAddress());
            obj.put("servicesOffered", new JSONArray(sp.getServiceTypes()));
            obj.put("numberOfCancellations", sp.getNumberOfCancellations());
            obj.put("verificationId", sp.getVerificationID());
            obj.put("profilePhotoURL", sp.getPhoto());
            
            obj.put("asServiceProvider", true);
            
            for(int i=0; i<admins.size();i++){
            	adminJSONList.add(constructUserJSON(admins.get(i)).toString());
            }
            obj.put("admins", adminJSONList);

        } catch (JSONException e) {
        	e.printStackTrace();
        }
        System.out.println(obj.toString());
        return obj.toString();
    }

	public static String constructJSONForListOfUsers(String tag, boolean status,
			ArrayList<User> admins) {
		
		JSONObject obj = new JSONObject();

		ArrayList<String> adminJSONList = new ArrayList<String>();
        for(int i=0; i<admins.size();i++){
        	adminJSONList.add(constructUserJSON(admins.get(i)).toString());
        }
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
        	obj.put("admins", adminJSONList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        System.out.println(obj.toString());
        return obj.toString();
	}
    

	public static String constructJSONForListOfServiceProviders(String tag, boolean status,
			ArrayList<ServiceProvider> serviceProviders) {
		
		JSONObject obj = new JSONObject();

		ArrayList<String> serviceprovidersJSONList = new ArrayList<String>();
        for(int i=0; i<serviceProviders.size();i++){
        	serviceprovidersJSONList.add(constructServiceProviderJSON(serviceProviders.get(i)).toString());
        }
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
        	obj.put("serviceProvidersList", serviceprovidersJSONList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        System.out.println(obj.toString());
        return obj.toString();
	}
	
    public static JSONObject constructUserJSON(User user)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("email", user.getEmail());
            obj.put("password", user.getPassword());
            obj.put("dob", user.getDateOfBirth());
            obj.put("fullname", user.getFullname());
            obj.put("phoneNumber", user.getPhoneNumber());
            obj.put("rating", user.getCurrentAverageRating());
            obj.put("id", user.getID());
//            obj.put("id", u.getID());
//            obj.put("fullname", u.getFullname());
//            obj.put("password", u.getPassword());
//            obj.put("phoneNumber", u.getPhoneNumber());
//            obj.put("email", u.getEmail());
//            obj.put("dob", u.getDateOfBirth().getTime());
//            obj.put("currentLocation", u.getCurrentLocation().toString());
//            obj.put("averageRating", u.getCurrentAverageRating());
//            obj.put("isAdmin", u.isAdmin());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject constructServiceProviderJSON(ServiceProvider sp)
    {
        JSONObject obj = null;
        try {
            obj = constructUserJSON(sp);
            obj.put("email", sp.getEmail());
            obj.put("password", sp.getPassword());
            obj.put("dob", sp.getDateOfBirth());
            obj.put("fullname", sp.getFullname());
            obj.put("phoneNumber", sp.getPhoneNumber());
            obj.put("rating", sp.getCurrentAverageRating());
            obj.put("id", sp.getID());
            obj.put("isAdmin", sp.isAdmin());
            obj.put("currentLocation", sp.getCurrentLocation());
            
            obj.put("availabilityRadius", sp.getAvailabilityRadius());
            obj.put("bankInfo", sp.getBankInfo());
            obj.put("businessAddress", sp.getBusinessAddress());
            obj.put("servicesOffered", new JSONArray(sp.getServiceTypes()));
            obj.put("numberOfCancellations", sp.getNumberOfCancellations());
            obj.put("verificationId", sp.getVerificationID());
            obj.put("profilePhotoURL", sp.getPhoto());
//            obj.put("availabilityRadius",sp.getAvailabilityRadius());
//            obj.put("bankInfo",sp.getBankInfo());
//            obj.put("servicesOffered", new JSONArray(sp.getServiceTypes()));
//            obj.put("businessAddress", sp.getBusinessAddress());
//            obj.put("profilePictureURL", sp.getPhoto());
//            obj.put("numberOfCancellation", sp.getNumberOfCancellations());
//            obj.put("verificationId", sp.getVerificationId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
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
			sp.setPhoto(o.getString("profilePictureURL"));
			JSONArray servicesOffered = o.getJSONArray("servicesOffered");
			
			for(int i=0; i<servicesOffered.length(); i++){
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
//    	String dateString = "";
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

