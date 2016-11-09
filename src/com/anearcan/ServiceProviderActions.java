package com.anearcan;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.anearcan.jireh.elements.ServiceProvider;

@Path("/serviceProvider")
public class ServiceProviderActions {

	@PUT
    // Path: http://localhost/<appln-folder-name>/serviceprovider/update
    @Path("/update")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/user/update
    public String updateServiceProvider(String serviceProviderJson) {
		
		long id=0; 
		String key="";
		Object value = null;
		String result="";
		
		try {
			JSONObject o = new JSONObject(serviceProviderJson);
			id = o.getLong("id");
			key = o.getString("key");
			value = o.get("value");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		System.out.println("Update service provider webservice is in.");
		if(DBConnection.updateServiceProvider(id, key, value)){
			result = Utility.constructJSON("updateServiceProvider", true, "ServiceProvider updated successfully.");
		}else{
			result = Utility.constructJSON("updateServiceProvider", false);
		}
		return result;
	}
	
	
	@PUT
    // Path: http://localhost/<appln-folder-name>/serviceProvider/create
    @Path("/create")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createServiceProvider(String serviceProviderJson) {
		
		ServiceProvider sp = Utility.generateServiceProviderFromJSON(serviceProviderJson);
		String result="";
		
		System.out.println("Insert Service Provider webservice is in.");
		try {
			if(DBConnection.insertServiceProvider(sp)){
				result = Utility.constructJSON("insertServiceProvider", true, "Service Provider created successfully.");
				//add new service provider request
				if(DBConnection.insertServiceProviderRequest(sp)){
					System.out.println("Service Provider request added successfully.");
				}else{
					System.out.println("Service Provider request not added.");
					result = Utility.constructJSON("insertServiceProvider", false, "Service Provider request not added.");
				}
			}else{
				result = Utility.constructJSON("insertServiceProvider", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@DELETE
    // Path: http://localhost/<appln-folder-name>/serviceprovider/delete
    @Path("/delete")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/service/delete/<id>
    public String deleteServiceProvider(@QueryParam("id")long id) {
		
		String result="";		
		System.out.println("Delete service provider webservice is in.");
		if(DBConnection.deleteServiceProvider(id)){
			result = Utility.constructJSON("deleteServiceProvider", true, "Service Provider deleted successfully.");
		}else{
			result = Utility.constructJSON("deleteServiceProvider", false);
		}
		return result;
	}
	
	@PUT
    // Path: http://localhost/<appln-folder-name>/serviceprovider/get
    @Path("/get")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/service/get
	//json contains the user id, with the current location.
	public String getServiceProviders(String json)
	{
		return json;
		
	}
	
}
