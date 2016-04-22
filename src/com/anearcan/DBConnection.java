package com.anearcan;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    /**
     * Method to check whether uname and pwd combination are correct
     * @param email
     * @param pwd
     * @return
     * @throws Exception
     */
    public static User login(String email, String pwd) throws Exception {
        boolean isUserAvailable = false;
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
            String query = "INSERT into JirehSQL.ServiceProviders(user_id, availabilityRadius, serviceTypeOffered, bankInfo) values("
                    + sp.getUserID() + "," + sp.getAvailabilityRadius() + ",'"+ sp.getServiceTypes()+"','"+ sp.getBankInfo() +"');";
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
}