package com.anearcan.jireh.elements;

import java.util.Calendar;
import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by Teddy on 10/10/2015.
 */
public class User {
	
	private long id = 0;
    private String password;
    private String fullname;
    private Date dateOfBirth;
    private String currentLocation;
    private String phoneNumber;
    private String email;
    private double averageRating; // calculated from coalition of all reviews on user.
    private ArrayList<Service> servicesRequested;
    private ArrayList<Review> reviewsOn;

    public User(String fullname, String password, Date dateOfBirth, String phoneNumber, String email) {
        this.password = password;
        this.fullname = fullname;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        servicesRequested = new ArrayList<Service>();
        reviewsOn = new ArrayList<Review>();
        averageRating = 0.0;
        currentLocation = "";
    }

    public User(long id, String fullname, String password, Date dateOfBirth, String phoneNumber, String email) {
        this.id = id;
    	this.password = password;
        this.fullname = fullname;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        servicesRequested = new ArrayList<Service>();
        reviewsOn = new ArrayList<Review>();
        averageRating = 0.0;
        currentLocation = "";
    }

    
    public User() {
    	this.password = "";
        this.fullname = "";
        this.dateOfBirth = new Date(0);
        this.phoneNumber = "";
        this.email = "";
        servicesRequested = new ArrayList<Service>();
        reviewsOn = new ArrayList<Review>();
        averageRating = 0.0;
        currentLocation = "";
	}

	public long getID(){
    	return id;
    }
    
    public void setID(long id){
    	this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*
       Calculate average on the reviews made on the user,
       i.e sum up rating value and divide by number of reviews made.
     */
    public double getCurrentAverageRating() {
        
        return averageRating;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
    public boolean addServiceRequested(Service serviceRequested){
        return servicesRequested.add(serviceRequested);
    }

    public boolean removeServiceRequested(Service serviceRequested){
        return servicesRequested.remove(serviceRequested);
    }

    public boolean addUserReview(Review userReview){
        return reviewsOn.add(userReview);
    }

    public boolean removeUserReview(Review userReview){
        return reviewsOn.remove(userReview);
    }

	public void setCurrentRating(double ratings) {
		averageRating = ratings;
		
	}
}
