package com.anearcan;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.codehaus.jettison.json.JSONObject;

import com.anearcan.jireh.elements.ServiceProvider;
import com.anearcan.jireh.elements.User;
import com.anearcan.jireh.elements.Service;
 
public class DBConnection {
    /**
     * Method to create DB Connection
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("finally")
    public static Connection createConnection() throws Exception {
        Connection con = null;
        try {
            Class.forName(Constants.dbClass);
            con = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPwd);
        } catch (Exception e) {
            throw e;
        } finally {
            return con;
        }
    }
   
    
    public static ServiceProvider serviceProviderLogin(String email, String pwd) throws Exception
    {

        Connection dbConn = null;
        User u = null;
        ServiceProvider sp = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //First get the user's information.
            Statement stmt = dbConn.createStatement();
            String query = "SELECT * FROM JirehSQL.Users WHERE email = '" + email
                    + "' AND password=" + "'" + pwd + "'";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                u = new User(rs.getString("fullname"), rs.getString("password"), new java.sql.Date(new java.util.Date().getTime()),//rs.getDate("dateOfBirth"), 
                		Long.toString(rs.getLong("phoneNumber")), rs.getString("email"));
                u.setCurrentLocation(rs.getString("currentLocation"));
                u.setID(rs.getInt("id"));
                if(rs.getString("isAdmin").equals("Y")){
                	u.setAdmin(true);
                }
                System.out.println(rs.getInt("id"));
            }
            
            //Use user's information to get Service provider's information.
            query = "SELECT * FROM JirehSQL.ServiceProviders WHERE user_id = " + u.getID();
            System.out.println(query);
            rs = stmt.executeQuery(query);
            while (rs.next()) {
            	
            	if(rs.getString("requestStatus").equals("Approved")){
	            	sp = new ServiceProvider(u, rs.getDouble("availabilityRadiusinkm"), rs.getLong("id"), rs.getString("bankInfo"), 
	            			new ArrayList<String>());
	            	
	    			sp.setUserID(rs.getLong("user_id"));

	    			sp.setNumberOfCancellations(rs.getInt("numberOfCancellations"));
	    			sp.setBusinessAddress(rs.getString("businessAddress"));
	    			sp.setPhoto(rs.getString("profilePictureURL"));
	    			String[] servicesOffered = rs.getString("serviceTypeOffered").split(",");
	    			
	    			
	    			for(int i=0; i<servicesOffered.length; i++){
	    				sp.addServicesOffered(servicesOffered[i]);
	    			}
            	}else{
            		sp = null;
            		System.out.println("User's service provider request has not been approved.");
            	}
            }
            
        } catch (SQLException sqle) {
        	
            sqle.printStackTrace();
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return sp;
    }
    
    /**
     * Method to check whether uname and pwd combination are correct
     * @param email
     * @param pwd
     * @return
     * @throws Exception
     */
    public static User userLogin(String email, String pwd) throws Exception {

        Connection dbConn = null;
        User u = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "SELECT * FROM JirehSQL.Users WHERE email = '" + email
                    + "' AND password=" + "'" + pwd + "'";
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                u = new User(rs.getString("fullname"), rs.getString("password"), new java.sql.Date(new java.util.Date().getTime()),//rs.getDate("dateOfBirth"), 
                		Long.toString(rs.getLong("phoneNumber")), rs.getString("email"));
                u.setCurrentLocation(rs.getString("currentLocation"));
                u.setID(rs.getInt("id"));
                if(rs.getString("isAdmin").equals("Y")){
                	u.setAdmin(true);
                }
                System.out.println(rs.getInt("id"));
            }
        } catch (SQLException sqle) {
        	
            sqle.printStackTrace();
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return u;
    }

    
    /**
     * Method to check whether uname and pwd combination are correct
     * @param email
     * @param pwd
     * @return
     * @throws Exception
     */
    public static ArrayList<User> getAllAdmins() throws Exception {
        
        ArrayList<User> admins = new ArrayList<User>();
        Connection dbConn = null;
        User u = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "SELECT * FROM JirehSQL.Users WHERE isAdmin = 'Y'";
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                u = new User(rs.getString("fullname"), rs.getString("password"), new java.sql.Date(new java.util.Date().getTime()),//rs.getDate("dateOfBirth"), 
                		Long.toString(rs.getLong("phoneNumber")), rs.getString("email"));
                u.setCurrentLocation(rs.getString("currentLocation"));
                u.setID(rs.getInt("id"));
                if(rs.getString("isAdmin").equals("Y")){
                	u.setAdmin(true);
                }
                System.out.println(rs.getInt("id"));
                admins.add(u);
            }
        } catch (SQLException sqle) {
        	
            sqle.printStackTrace();
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return admins;
    }
    
    public static boolean insertUser(User u) throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Inserting user");
            System.out.println(u.getPhoneNumber());
            Statement stmt = dbConn.createStatement();
            String query = "INSERT into JirehSQL.Users(fullname,password,dateOfBirth,phoneNumber,email) values('"
                    + u.getFullname() + "','" + u.getPassword() + "','"+ Utility.dateToDatabaseFormat(u.getDateOfBirth())
                    +"',"+Integer.parseInt(u.getPhoneNumber()) + ",'"+ u.getEmail() +"');";
            System.out.println(query);
            int result = stmt.executeUpdate(query);
            
            System.out.println("Inserting user " + result);
            //When record is successfully inserted
            if (result>=0) {
            	insertStatus = true;
            	System.out.println("User inserted.");
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
    }
        
    public static boolean insertServiceProvider(ServiceProvider sp) throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Inserting Service Provider");
            System.out.println(sp.getPhoneNumber());
            Statement stmt = dbConn.createStatement();
            String query = "INSERT into JirehSQL.ServiceProviders(user_id, availabilityRadiusinkm, serviceTypeOffered, bankInfo, businessAddress, profilePictureURL) values("
                    + sp.getUserID() + "," + sp.getAvailabilityRadius() + ",'"+ sp.getServiceTypes()+"','"+ sp.getBankInfo() +"','"+ sp.getBusinessAddress() +"','"+ sp.getPhoto() +"');";
            System.out.println(query);
            int result = stmt.executeUpdate(query);
            
            System.out.println("Inserting Service provider " + result);
            //When record is successfully inserted
            if (result>=0) {
            	insertStatus = true;
            	System.out.println("Service Provider inserted.");
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
    }
        
    public static boolean insertService(Service s) throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Inserting service");
            Statement stmt = dbConn.createStatement();
            String query = "INSERT into JirehSQL.Services(user_id, serviceprovider_id, scheduledTime, serviceLocation, ratePerHour) values("
                    + s.getUser().getID() + ","+ s.getServiceProvider().getID() + ",'" + s.getScheduledTime() + "','"+ s.getServiceLocation()+"',"+ s.getRatePerHour() +");";
            System.out.println(query);
            int result = stmt.executeUpdate(query);
            
            System.out.println("Inserting new service " + result);
            //When record is successfully inserted
            if (result >= 0) {
            	insertStatus = true;
            	System.out.println("Service inserted.");
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
    }
    
    protected static boolean updateUser(long id, String columnName, Object value)
    {
    	boolean updateStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Updating User");
            Statement stmt = dbConn.createStatement();
            String query = "";
            if(value instanceof Integer || value instanceof Long || value instanceof Double){
            	query = String.format("UPDATE JirehSQL.Users SET %s=%d where id=%d", columnName, value, id);
            }else if(value instanceof Date){
            	query = String.format("UPDATE JirehSQL.Users SET %s='%s' where id=%d", columnName, Utility.dateToDatabaseFormat((Date) value), id);
            }else{
            	query = String.format("UPDATE JirehSQL.Users SET %s='%s' where id=%d", columnName, value, id);
            }
            
            System.out.println(query);
            int result = stmt.executeUpdate(query);
            
            
            System.out.println("Updating User " + result);
            //When record is successfully inserted
            if (result>=0) {
            	updateStatus = true;
            	System.out.println("User updated.");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            if (dbConn != null) {
                try {
					dbConn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
            throw e;
        } finally {
            if (dbConn != null) {
            	try {
					dbConn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        }
        return updateStatus;
    } 
    
    protected static boolean updateService(long id, String columnName, Object value)
    {
    	boolean updateStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Updating Service");
            String query = "";
            Statement stmt = dbConn.createStatement();
            if(value instanceof Integer || value instanceof Long || value instanceof Double){
            	query = String.format("UPDATE JirehSQL.Services SET %s=%d where id=%d", columnName, value, id);
            }else if(value instanceof Date){
            	query = String.format("UPDATE JirehSQL.Services SET %s='%s' where id=%d", columnName, Utility.dateToDatabaseFormat((Date) value), id);
            }else{
            	query = String.format("UPDATE JirehSQL.Services SET %s='%s' where id=%d", columnName, value, id);
            }
//            String query = String.format("UPDATE JirehSQL.Service SET user_id=%d, serviceprovider_id=%d, scheduledTime=%d, serviceLocation=%s, ratePerHour=%d where id=%s", 
//            		s.getUser().getID(), s.getServiceProvider().getID(), s.getScheduledTime(), s.getServiceLocation(), s.getRatePerHour(), s.getId());
            System.out.println(query);
            int result = stmt.executeUpdate(query);
            
            
            System.out.println("Updating service " + result);
            //When record is successfully inserteds
            if (result>=0) {
            	updateStatus = true;
            	System.out.println("Service updated.");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            if (dbConn != null) {
                try {
					dbConn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
            throw e;
        } finally {
            if (dbConn != null) {
            	try {
					dbConn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        }
        return updateStatus;
    } 
    
    protected static boolean updateServiceProvider(long id, String columnName, Object value)
    {
    	boolean updateStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Updating Service Provider");
            String query = "";
            Statement stmt = dbConn.createStatement();
            if(value instanceof Integer || value instanceof Long || value instanceof Double){
            	query = String.format("UPDATE JirehSQL.ServiceProviders SET %s=%d where id=%d", columnName, value, id);
            }else if(value instanceof Date){
            	query = String.format("UPDATE JirehSQL.ServiceProviders SET %s='%s' where id=%d", columnName, Utility.dateToDatabaseFormat((Date) value), id);
            }else{
            	query = String.format("UPDATE JirehSQL.ServiceProviders SET %s='%s' where id=%d", columnName, value, id);
            }

            System.out.println(query);
            int result = stmt.executeUpdate(query);
            
            System.out.println("Updating service provider " + result);
            //When record is successfully inserteds
            if (result>=0) {
            	updateStatus = true;
            	System.out.println("Service provider updated.");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            if (dbConn != null) {
                try {
					dbConn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
            throw e;
        } finally {
            if (dbConn != null) {
            	try {
					dbConn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        }
        return updateStatus;
    }
	
    protected static boolean deleteUser(long id)
    {
    	boolean deleteStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Deleting User");
            Statement stmt = dbConn.createStatement();
            String query = "";
            query = String.format("DELETE FROM JirehSQL.Users where id=%d", id);
            
            System.out.println(query);
            int result = stmt.executeUpdate(query);
            
            
            System.out.println("Delete User " + result);
            //When record is successfully deleted.
            if (result>=0) {
            	deleteStatus = true;
            	System.out.println("User deleted.");
            }
        } catch (SQLException sqle) {
        	deleteStatus = false;
        	sqle.printStackTrace();            
        } catch (Exception e) {
        	deleteStatus = false;
        	if (dbConn != null) {
                try {
					dbConn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
            throw e;
        } finally {
            if (dbConn != null) {
            	try {
					dbConn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        }
        return deleteStatus;
    }
    
    protected static boolean deleteService(long id) {
    	boolean deleteStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Deleting Service");
            Statement stmt = dbConn.createStatement();
            String query = "";
            query = String.format("DELETE FROM JirehSQL.Services where id=%d", id);
            
            System.out.println(query);
            int result = stmt.executeUpdate(query);
            
            
            System.out.println("Delete Service " + result);
            //When record is successfully deleted.
            if (result>=0) {
            	deleteStatus = true;
            	System.out.println("Service deleted.");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            if (dbConn != null) {
                try {
					dbConn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
            throw e;
        } finally {
            if (dbConn != null) {
            	try {
					dbConn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        }
        return deleteStatus;
	}
	
    protected static boolean deleteServiceProvider(long id) {
		boolean deleteStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Deleting Service Provider");
            Statement stmt = dbConn.createStatement();
            String query = "";
            query = String.format("DELETE FROM JirehSQL.ServiceProviders where id=%d", id);
            
            System.out.println(query);
            int result = stmt.executeUpdate(query);
            
            
            System.out.println("Delete Service provider " + result);
            //When record is successfully deleted.
            if (result>=0) {
            	deleteStatus = true;
            	System.out.println("Service provider deleted.");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            if (dbConn != null) {
                try {
					dbConn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
            throw e;
        } finally {
            if (dbConn != null) {
            	try {
					dbConn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        }
        return deleteStatus;
	}
    
    protected static boolean startService(long id)
    {
    	return updateService(id, "serviceStartTime", new java.util.Date());
    }
    
    protected static boolean endService(long id)
    {
    	return updateService(id, "serviceEndTime", new java.util.Date());
    }
    
    protected static boolean addDeleteAccountFeedback(JSONObject o) throws Exception{
    	boolean status = false;
    	Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Inserting feedback");
            Statement stmt = dbConn.createStatement();
            String query = "INSERT into JirehSQL.DeletedUsersFeedback(email, reason, timeOfDeletion) values('"
                    + o.getString("email") + "','"+ o.getString("reason") + "','" + o.getString("time") + "')";
            System.out.println(query);
            int result = stmt.executeUpdate(query);
            
            System.out.println("Inserting new feedback " + result);
            //When record is successfully inserted
            if (result >= 0) {
            	status = true;
            	System.out.println("Feedback inserted.");
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
    	return status;
    }

	
     protected static boolean insertServiceProviderRequest(ServiceProvider sp) throws SQLException {
    	 
    	 boolean insertStatus = false;
    	 long serviceProviderId = -1;
         Connection dbConn = null;
         try {
             try {
                 dbConn = DBConnection.createConnection();
             } catch (Exception e) {
                 e.printStackTrace();
             }
             System.out.println("Inserting Service Provider Request");
             Statement stmt = dbConn.createStatement();
             //Getting the service provider ID
             String query = "SELECT id FROM JirehSQL.ServiceProviders WHERE user_id=" + sp.getUserID();
             //System.out.println(query);
             ResultSet rs = stmt.executeQuery(query);
             
             while (rs.next()) {
            	 serviceProviderId = rs.getInt("id");
            	 System.out.println("Returned Id "+serviceProviderId);
             }
             
             query = "INSERT into JirehSQL.serviceProviderRequests(serviceProviderId) values("
                     + serviceProviderId +");";
             System.out.println(query);
             int result = stmt.executeUpdate(query);
             
             System.out.println("Inserting Service provider request" + result);
             //When record is successfully inserted
             if (result>=0) {
             	insertStatus = true;
             	System.out.println("Service Provider request inserted.");
             }
         } catch (SQLException sqle) {
             throw sqle;
         } catch (Exception e) {
             if (dbConn != null) {
                 dbConn.close();
             }
             throw e;
         } finally {
             if (dbConn != null) {
                 dbConn.close();
             }
         }
         return insertStatus;		
	}
     
     public static ArrayList<ServiceProvider> getServiceProvidersInArea(User u) throws SQLException{
    	 
    	 ArrayList<ServiceProvider> spList = new ArrayList<ServiceProvider>();
    	 
         Connection dbConn = null;
         
         try {
             try {
                 dbConn = DBConnection.createConnection();
             } catch (Exception e) {
                 e.printStackTrace();
             }
             Statement stmt = dbConn.createStatement();
             String query = "select ServiceProviders.id, ServiceProviders.user_id, ServiceProviders.businessAddress, "
             		+ "ServiceProviders.availabilityRadiusinKM, Users.currentLocation, Users.fullname, Users.email, "
             		+ "ServiceProviders.numberOfCancellations, ServiceProviders.serviceTypeOffered, ServiceProviders.bankInfo, "
             		+ "ServiceProviders.profilePictureURL from ServiceProviders INNER JOIN Users on ServiceProviders.user_id=Users.id "
             		+ "where ServiceProviders.requestStatus='Approved';";
             System.out.println(query);
             ResultSet rs = stmt.executeQuery(query);
             while (rs.next()) {
            	 double userLongitude = Double.parseDouble(u.getCurrentLocation().split(",")[1]);
            	 double userLatitude = Double.parseDouble(u.getCurrentLocation().split(",")[0]);
            	 
            	 double serviceProviderLongitude = Double.parseDouble(rs.getString("currentLocation").split(",")[1]);
            	 double serviceProviderLatitude = Double.parseDouble(rs.getString("currentLocation").split(",")[0]);
            	 
            	 double distanceBetweenFromUser = Utility.calculateDistanceInKM(userLongitude, userLatitude, serviceProviderLongitude, serviceProviderLatitude);
            	 
            	 //If the user is in the service provider's radius, show service provider on map
            	 if(distanceBetweenFromUser <=rs.getDouble("availabilityRadiusinkm")){
            		 ServiceProvider sp = new ServiceProvider(new User(), 
                			 rs.getDouble("availabilityRadiusinkm"),
                			 rs.getLong("id"),
                			 rs.getString("bankInfo"), 
                			 new ArrayList<String>());
    	            	
        			sp.setUserID(rs.getLong("user_id"));
        			sp.setNumberOfCancellations(rs.getInt("numberOfCancellations"));
        			sp.setBusinessAddress(rs.getString("businessAddress"));
        			sp.setPhoto(rs.getString("profilePictureURL"));
        			sp.setCurrentLocation(rs.getString("currentLocation"));
        			sp.setFullname(rs.getString("fullname"));
        			sp.setEmail(rs.getString("email"));
        			String[] servicesOffered = rs.getString("serviceTypeOffered").split(",");
        			
        			for(int i=0; i<servicesOffered.length; i++){
        				sp.addServicesOffered(servicesOffered[i]);
        			}
        			spList.add(sp);
            	 }
             }
         } catch (SQLException sqle) {
             sqle.printStackTrace();
         } catch (Exception e) {
             if (dbConn != null) {
                 dbConn.close();
             }
             throw e;
         } finally {
             if (dbConn != null) {
                 dbConn.close();
             }
         }
    	 
    	 return spList;
     }
}
