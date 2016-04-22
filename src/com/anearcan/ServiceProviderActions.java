package com.anearcan;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anearcan.jireh.elements.ServiceProvider;

@Path("ServiceProvider")
public class ServiceProviderActions {

	@PUT
    // Path: http://localhost/<appln-folder-name>/user/update
    @Path("/update")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/user/update?email=abc&password=xyz
    public String updateServiceProvider() {
		
		long id=0; 
		String key=""; 
		Object value = null;
		
		String result="";		
		System.out.println("Update service provider webservice is in.");
		if(DBConnection.updateServiceProvider(id, key, value)){
			result = Utility.constructJSON("updateServiceProvider", true, "ServiceProvider updated successfully.");
		}else{
			result = Utility.constructJSON("updateServiceProvider", false);
		}
		return result;
	}
	
	
	@PUT
    // Path: http://localhost/<appln-folder-name>/service/create
    @Path("/create")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/user/update?email=abc&password=xyz
    public String createServiceProvider() {
		
		ServiceProvider sp = null;
		String result="";
		
		System.out.println("Insert Service Provider webservice is in.");
		try {
			if(DBConnection.insertServiceProvider(sp)){
				result = Utility.constructJSON("insertServiceProvider", true, "Service Provider created successfully.");
			}else{
				result = Utility.constructJSON("insertServiceProvider", false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
    // Query parameters are parameters: http://localhost/<appln-folder-name>/user/update?email=abc&password=xyz
    public String deleteServiceProvider() {
		
		long id=0; 
		
		String result="";		
		System.out.println("Delete service provider webservice is in.");
		if(DBConnection.deleteServiceProvider(id)){
			result = Utility.constructJSON("deleteServiceProvider", true, "Service Provider deleted successfully.");
		}else{
			result = Utility.constructJSON("deleteServiceProvider", false);
		}
		return result;
	}
}
