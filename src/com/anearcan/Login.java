package com.anearcan;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
    public String doLogin(@QueryParam("email") String email, @QueryParam("pw") String pwd){
        String response = "";
        User u = login(email, pwd);
        if(u != null){
            response = Utility.constructJSON("login",true, u);
        }else{
            response = Utility.constructJSON("login", false, "Incorrect Email or Password");
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
    private User login(String email, String pwd){
        System.out.println("Inside checkCredentials");
        User result = null;
        if(Utility.isNotNull(email) && Utility.isNotNull(pwd)){
            try {
                result = DBConnection.login(email, pwd);
                
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