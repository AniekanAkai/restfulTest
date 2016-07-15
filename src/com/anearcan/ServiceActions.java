package com.anearcan;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.anearcan.jireh.elements.Service;


@Path("/service")
public class ServiceActions {

	@PUT
    // Path: http://localhost/<appln-folder-name>/user/update
    @Path("/update")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/user/update
    public String updateService(String serviceJson) {
		
		long id=0; 
		String key="";
		Object value = null;
		String result="";
		
		try {
			JSONObject o = new JSONObject(serviceJson);
			id = o.getLong("id");
			key = o.getString("key");
			value = o.get("value");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		System.out.println("Update service webservice is in. "+ serviceJson);
		
		if(DBConnection.updateService(id, key, value)){
			result = Utility.constructJSON("updateService", true, "Service updated successfully.");
		}else{
			result = Utility.constructJSON("updateService", false);
		}
		return result;
	}
	
	@PUT
    // Path: http://localhost/<appln-folder-name>/service/create
    @Path("/create")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/user/create
    public String createService(String serviceJson) {
		Service s = Utility.generateServiceFromJSON(serviceJson);
		
		String result="";
		
		System.out.println("Create service webservice is in.");
		try {
			if(DBConnection.insertService(s)){
				result = Utility.constructJSON("createService", true, "Service created successfully.");
			}else{
				result = Utility.constructJSON("createService", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@DELETE
    // Path: http://localhost/<appln-folder-name>/service/delete
    @Path("/delete")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/user/delete/<id>
    public String deleteService(@PathParam("id") long id) {
		
		String result="";		
		System.out.println("Delete service webservice is in.");
		if(DBConnection.deleteService(id)){
			result = Utility.constructJSON("deleteService", true, "Service delete successfully.");
		}else{
			result = Utility.constructJSON("deleteService", false);
		}
		return result;
	}
	
	
	@GET
    // Path: http://localhost/<appln-folder-name>/service/start
    @Path("/start")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/service/start/{id}
    public String startService(@QueryParam("id") long id) {
		
		String result="";
		
		System.out.println("Create service webservice is in.");
		try {
			if(DBConnection.startService(id)){
				result = Utility.constructJSON("startService", true, "Service started now successfully.");
			}else{
				result = Utility.constructJSON("startService", false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@GET
    // Path: http://localhost/<appln-folder-name>/service/end
    @Path("/end")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/service/end/{id}
    public String endService(@QueryParam("id") long id) {

		String result="";
		
		System.out.println("End service webservice is in.");
		try {
			if(DBConnection.endService(id)){
				result = Utility.constructJSON("endService", true, "Service started now successfully.");
			}else{
				result = Utility.constructJSON("endService", false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
