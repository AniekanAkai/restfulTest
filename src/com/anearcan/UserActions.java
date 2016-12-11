package com.anearcan;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.anearcan.jireh.elements.User;


@Path("/user")
public class UserActions {

	@PUT
    // Path: http://localhost/<appln-folder-name>/user/update
    @Path("/update")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/user/update
    public String updateUser(String userJson) {
		System.out.println("Update user ");
		long id=0; 
		String key="";
		Object value = null;
		String result="";
		
		try {
			System.out.println("Update user webservice is in. "+ userJson);
			JSONObject o = new JSONObject(userJson);
			id = o.getLong("id");
			JSONArray updates = o.getJSONArray("updates");
			for(int i=0; i<updates.length(); i++){
				o = updates.getJSONObject(i);
				key = o.getString("key");
				value = o.get("value");
				if(DBConnection.updateUser(id, key, value)){
					System.out.println("User updated successfully");
					result = Utility.constructJSON("updateUser", true, "User updated successfully.");
					System.out.println(result);
				}else{
					System.out.println("User update failed");
					result = Utility.constructJSON("updateUser", false);
					System.out.println(result);
				}
			}			
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	@PUT
    // Path: http://localhost/<appln-folder-name>/user/delete
    @Path("/delete")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/user/delete
    public String deleteUser(String json) throws Exception {		
		String result="";		
		System.out.println("Delete user webservice is in.");
		System.out.println(json);
		JSONObject o = new JSONObject(json);
		if(DBConnection.deleteUser(o.getLong("id"))){
			DBConnection.addDeleteAccountFeedback(o);
			result = Utility.constructJSON("deleteUser", true, "User deleted successfully.");
		}else{
			result = Utility.constructJSON("deleteUser", false);
		}
		return result;
	}
	
	// HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/user/getAllAdmins
    @Path("/getAllAdmins")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/user/getAllAdmins
    public String getAllAdmins() throws Exception{
        String response = "";
        ArrayList<User> uList = DBConnection.getAllAdmins();
        if(!uList.isEmpty()){
            response = Utility.constructJSONForListOfUsers("AllAdmins",true, uList);
        }else{
            response = Utility.constructJSON("AllAdmins", false, "No administators in DB.");
        }
	    System.out.println(response); 
        return response;
    }
}
