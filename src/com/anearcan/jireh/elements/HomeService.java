package com.anearcan.jireh.elements;


import java.util.GregorianCalendar;

/**
 * Created by Teddy on 10/10/2015.
 */
public class HomeService extends Service {
    //NOTE: serciveLocation = usersLocation

    public HomeService(User user, ServiceProvider serviceProvider, ServiceType serviceType, GregorianCalendar scheduledTime, double ratePerHour, boolean userProvidesTool) {
        super(user, serviceProvider, serviceType, scheduledTime, ratePerHour, userProvidesTool);
    }
}
