package com.anearcan;

import java.sql.Date;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.anearcan.jireh.elements.User;
//Path: http://localhost/<appln-folder-name>/register

@Path("/register")
public class Register {
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/register/doregister
    @Path("/doregister")  
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?id=pqrs&username=abc&password=xyz
    public String doRegister(@QueryParam("name") String fullname,
    		@QueryParam("dob") long dob,
    		@QueryParam("pw") String password,
    		@QueryParam("phone") String phoneNumber,
    		@QueryParam("email") String email){
        String response = "";
        System.out.println("Inside doLogin "+email+"  "+password+" Phone:"+phoneNumber);
        int retCode = registerUser(fullname,password,new Date(dob),phoneNumber,email);
        if(retCode == 0){
            response = Utility.constructJSON("register",true);
        }else if(retCode == 1){
            response = Utility.constructJSON("register",false, "You are already registered");
        }else if(retCode == 2){
            response = Utility.constructJSON("register",false, "Special Characters are not allowed in Username and Password");
        }else if(retCode == 3){
            response = Utility.constructJSON("register",false, "Error occured");
        }
        return response; 
    }
 
    private int registerUser(String fullname,String password, Date dateOfBirth, String phoneNumber, String email){
        System.out.println("Inside checkCredentials");
        int result = 3;
        if(Utility.isNotNull(email)/*&& Utility.isNotNull(age)*/){
            try {
            	User u = new User(fullname, password, dateOfBirth, phoneNumber, email);
                if(DBConnection.insertUser(u)){
                    System.out.println("RegisterUser if");
                    result = 0;
                }
            } catch(SQLException sqle){
                System.out.println("RegisterUSer catch sqle");
                //When Primary key violation occurs that means user is already registered
                if(sqle.getErrorCode() == 1062){
                    result = 1;
                } 
                //When special characters are used in name, username or password
                else if(sqle.getErrorCode() == 1064){
                    System.out.println(sqle.getErrorCode());
                    result = 2;
                }else{
                	System.out.println(sqle.getErrorCode());
                	System.out.println(sqle.getMessage());
                	result = 3;
                }
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("Inside checkCredentials catch e ");
                e.printStackTrace();
                result = 3;
            }
        }else{
            System.out.println("Inside checkCredentials else");
            System.out.println(email);
            result = 3;
        }
 
        return result;
    }
}
