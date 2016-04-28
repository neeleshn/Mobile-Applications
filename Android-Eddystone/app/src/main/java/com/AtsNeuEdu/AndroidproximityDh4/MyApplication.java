package com.AtsNeuEdu.AndroidproximityDh4;

import android.app.Application;

import com.AtsNeuEdu.AndroidproximityDh4.estimote.BeaconID;
import com.AtsNeuEdu.AndroidproximityDh4.estimote.BeaconNotificationsManager;
import com.estimote.sdk.EstimoteSDK;

public class MyApplication extends Application {

    private boolean beaconNotificationsEnabled = false;

    @Override
    public void onCreate() {
        super.onCreate();

        EstimoteSDK.initialize(getApplicationContext(), "androidproximity-dh4", "xxx");

        // uncomment to enable debug-level logging
        // it's usually only a good idea when troubleshooting issues with the Estimote SDK
//        EstimoteSDK.enableDebugLogging(true);
    }

    public void enableBeaconNotifications() {
        if (beaconNotificationsEnabled) { return; }

        BeaconNotificationsManager beaconNotificationsManager1 = new BeaconNotificationsManager(this);
        beaconNotificationsManager1.addNotification(
                new BeaconID("XXXX", 1, 0),
                "Kick Off Room",
                "Bye Kick Off Room");
        beaconNotificationsManager1.startMonitoring();

        BeaconNotificationsManager beaconNotificationsManager2 = new BeaconNotificationsManager(this);
        beaconNotificationsManager2.addNotification(
                new BeaconID("XXXX", 1, 0),
                "Room 201",
                "201 Bye");
        beaconNotificationsManager2.startMonitoring();

        BeaconNotificationsManager beaconNotificationsManager3 = new BeaconNotificationsManager(this);
        beaconNotificationsManager3.addNotification(
                new BeaconID("XXXX", 1, 0),
                "Room 202",
                "202 Bye");
        beaconNotificationsManager3.startMonitoring();

        beaconNotificationsEnabled = true;
    }

    public boolean isBeaconNotificationsEnabled() {
        return beaconNotificationsEnabled;
    }

}
