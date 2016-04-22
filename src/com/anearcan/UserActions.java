package com.anearcan;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

@Path("/user")
public class UserActions {

	@PUT
    // Path: http://localhost/<appln-folder-name>/user/update
    @Path("/update")
    // Produces JSON as response
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/user/update
    public String updateUser() {
		
		long id=0; 
		String key="";
		Object value = null;
		String result="";
		
		System.out.println("Update user webservice is in.");
		if(DBConnection.updateUser(id, key, value)){
			result = Utility.constructJSON("updateUser", true, "User updated successfully.");
		}else{
			result = Utility.constructJSON("updateUser", false);
		}
		return result;
	}
}
