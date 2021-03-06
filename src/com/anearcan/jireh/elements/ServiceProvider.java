package com.anearcan.jireh.elements;

//import android.location.GpsStatus;
//import android.location.Location;

import java.util.ArrayList;
import java.sql.Date;

/**
 * Created by Teddy on 10/10/2015.
 */
public class ServiceProvider extends User {

	private String photo;
    private String location; //in JSON format
    private double availabiltyRadius=0;
    private long verificationid = 0; //ServiceProvider id(from DB) would be used here.
    private int numberOfCancellations;
    private String bankInfo;        //acc number, swift no, bank id etc
    private ArrayList<String> servicesOffered;
    private ArrayList<Service> servicesProvided; //get from service ClassID
    private ArrayList<Review> reviewsOnUser;      // get from service class userReview


    public ServiceProvider(String username, String fullname, Date dateOfBirth,
                           String phoneNumber, String email,String location, double availabiltyRadius,
                           String serviceproviderid,String bankInfo,ArrayList<String> servicesOffered,
                           String password){
    	
        super(fullname, password, dateOfBirth, phoneNumber, email);
        this.location = location;
        this.availabiltyRadius = availabiltyRadius;        
        this.bankInfo = bankInfo;
        this.servicesOffered = servicesOffered;
    }
    
    public ServiceProvider(User u, double availabiltyRadius, long verificationId,String bankInfo,ArrayList<String> servicesOffered){
        super(u.getFullname(), u.getPassword(), u.getDateOfBirth(), u.getPhoneNumber(), u.getEmail());
        this.availabiltyRadius = availabiltyRadius;
        this.verificationid = verificationId;
        this.bankInfo = bankInfo;
        this.servicesOffered = servicesOffered;
    }

    
    public ServiceProvider() {
    	super();
    	this.location = "";
        this.availabiltyRadius = 0;        
        this.bankInfo = "";
        this.servicesOffered = new ArrayList<String>();
	}
    
	//GPS location
    public String getBusinessAddress() {
        return location;
    }

    public void setBusinessAddress(String location) {
        this.location = location;
    }

    
    public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	//Service provider ID
    public long getVerificationID() {
        return verificationid;
    }

    public void setVerificationID(long serviceproviderId) {
        this.verificationid = serviceproviderId;
    }

    //User id
    public long getUserID() {
        return super.getID();
    }

    public void setUserID(long userid) {
       super.setID(userid);
    }    
    
    // number of Cancellations
    public int getNumberOfCancellations() {
        return numberOfCancellations;
    }

    public void setNumberOfCancellations(int numberOfCancellations) {
        this.numberOfCancellations = numberOfCancellations;
    }

    //bankInfo
    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    //Service being provided
    public void requestNewServiceTypetoOffer(ServiceType servicetype){
        if(!servicesOffered.contains(servicetype)){
            //Send request to Admin
        }
    }

    public boolean addServicesOffered(String servicetype) {
        if(!servicesOffered.contains(servicetype)){
            servicesOffered.add(servicetype);
        }
        return false;
    }
    
    public boolean removeServicesOffered(ServiceType servicetype) {
        return servicesOffered.remove(servicetype);
    }
    
    public ArrayList<String> getServiceTypes() {
		return servicesOffered;
	}
    

    public boolean addServiceProvided(Service serviceProvided){
        return servicesProvided.add(serviceProvided);
    }

    public boolean removeServiceProvided(Service serviceProvided){
        return servicesProvided.remove(serviceProvided);
    }

    public boolean addServiceProviderReview(Review review){
        return reviewsOnUser.add(review);
    }

    public boolean removeServiceProviderReview(Review review){
        return reviewsOnUser.remove(review);
    }
    

    public void setAvailabilityRadius(double radius){
        availabiltyRadius = radius;
    }

    public double getAvailabilityRadius(){
    	return availabiltyRadius;
    }

    public void makeCommentOnUser(){}
    public void specifyPreferenceToUser(){}
	

}
