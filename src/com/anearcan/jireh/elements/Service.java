package com.anearcan.jireh.elements;



import java.sql.Date;

/**
 * Created by Teddy on 10/10/2015.
 */
public class Service{
    
	private long id;
    private User user; //User that requests the service.
    private ServiceProvider serviceProvider; //Service provider that would be supporting this.
    private String serviceType; //the type of service being created


    private Date scheduledTime; //Time user and service provider have settled on.
    private Date serviceStartTime;
    private Date serviceEndTime;

    private Review userReview;
    private Review serviceProviderReview;

    private String specialRequests;
    private String serviceLocation; //Location where user

    private double finalBalance; //Calculated from the ratePerHpur
    private double ratePerHour; //This is the price that would be shown.

    private boolean userProvidesTool = false; //Specifies if the user or the service provider would be providing tools.
    private boolean hasStartedService = false;
    private boolean hasServiceCompleted = false;

    private String status = "";//Pending Approval, Approved, Started, In Progress, Complete

    public Service(User user, ServiceProvider serviceProvider, String serviceType,
                   Date scheduledTime, double ratePerHour, boolean userProvidesTool) {
        this.user = user;
        this.serviceProvider = serviceProvider;
        this.serviceType = serviceType;
        this.scheduledTime = scheduledTime;
        this.ratePerHour = ratePerHour;
        this.userProvidesTool = userProvidesTool;
    }

    public Service() {
		// Auto-generated constructor stub
	}

	//Getters and Setters
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Date getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(Date serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public Date getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(Date serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public Review getUserReview() {
        return userReview;
    }

    public void setUserReview(Review userReview) {
        this.userReview = userReview;
    }

    public Review getServiceProviderReview() {
        return serviceProviderReview;
    }

    public void setServiceProviderReview(Review serviceProviderReview) {
        this.serviceProviderReview = serviceProviderReview;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(double finalBalance) {
        this.finalBalance = finalBalance;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public boolean isUserProvidesTool() {
        return userProvidesTool;
    }

    public void setUserProvidesTool(boolean userProvidesTool) {
        this.userProvidesTool = userProvidesTool;
    }

    public boolean isHasStartedService() {
        return hasStartedService;
    }

    public void setHasStartedService(boolean hasStartedService) {
        this.hasStartedService = hasStartedService;
    }

    public boolean isHasServiceCompleted() {
        return hasServiceCompleted;
    }

    public void setHasServiceCompleted(boolean hasServiceCompleted) {
        this.hasServiceCompleted = hasServiceCompleted;
    }

    //Pending Approval, Approved, Started, In Progress, Complete
    public String getStatus() {
        return status;
    }

    //Pending Approval, Approved, Started, In Progress, Complete
    public void setStatus(String status) {
        this.status = status;
    }

}
