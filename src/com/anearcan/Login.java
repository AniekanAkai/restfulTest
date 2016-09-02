package com.anearcan;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.anearcan.jireh.elements.ServiceProvider;
import com.anearcan.jireh.elements.User;

//Path: http://localhost/<appln-folder-name>/login
@Path("/login")
public class Login {
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/login/dologin
    @Path("/dologin")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/login/dologin?email=abc&password=xyz
    public String doLogin(@QueryParam("asServiceProvider") String asServiceProvider, @QueryParam("email") String email, @QueryParam("pw") String pwd){
        String response = "";
        if(asServiceProvider.equalsIgnoreCase("Y")){
        	System.out.println("Logging in as service provider.");
        	ServiceProvider sp = serviceProviderLogin(email, pwd);
        	if(sp != null){
	            response = Utility.constructJSONForServiceProviderLogin("login",true, sp);
	        }else{
	            response = Utility.constructJSON("login", false, "Incorrect Email or Password");
	        }
        }else{
        	System.out.println("Logging in as user.");
	        User u = userLogin(email, pwd);
	        if(u != null){
	            response = Utility.constructJSONForUserLogin("login",true, u);
	        }else{
	            response = Utility.constructJSON("login", false, "Incorrect Email or Password");
	        }
        }
        return response;        
    }
 
    /**
     * Method to check whether the entered credential is valid
     * 
     * @param email
     * @param pwd
     * @return
     */
    private User userLogin(String email, String pwd){
        System.out.println("Inside checkCredentials");
        User result = null;
        if(Utility.isNotNull(email) && Utility.isNotNull(pwd)){
            try {
                result = DBConnection.userLogin(email, pwd);
                
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("Inside checkCredentials catch");
                e.printStackTrace();
                result = null;
            }
        }else{
            System.out.println("Inside checkCredentials else"+" email or password is null");
            result = null;           
        }
        return result;
    }
    
    private ServiceProvider serviceProviderLogin(String email, String pwd){
        System.out.println("Inside checkCredentials");
        ServiceProvider result = null;
        if(Utility.isNotNull(email) && Utility.isNotNull(pwd)){
            try {
                result = DBConnection.serviceProviderLogin(email, pwd);
                
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("Inside checkCredentials catch");
                e.printStackTrace();
                result = null;
            }
        }else{
            System.out.println("Inside checkCredentials else"+" email or password is null");
            result = null;           
        }
        return result;
    }

}