package com.anearcan.jireh.elements;


import java.sql.Date;

/**
 * Created by Teddy on 10/10/2015.
 */
public class HomeService extends Service {
    //NOTE: serciveLocation = usersLocation

    public HomeService(User user, ServiceProvider serviceProvider, String serviceType, Date scheduledTime, double ratePerHour, boolean userProvidesTool) {
        super(user, serviceProvider, serviceType, scheduledTime, ratePerHour, userProvidesTool);
    }
}
