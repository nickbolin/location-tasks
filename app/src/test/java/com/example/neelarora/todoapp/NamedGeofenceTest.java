package com.example.neelarora.todoapp;

import android.view.MenuItem;

import com.google.android.gms.location.Geofence;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 * Author: nbolin 11/26/2016
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NamedGeofenceTest {

    @Test
    public void test_geofence() throws Exception {
        NamedGeofence fence = new NamedGeofence();
        fence.name = "Test Location";
        fence.latitude = 37.2296;
        fence.longitude = -80.4139;
        fence.radius = (float)0.8516;

        Geofence result = fence.geofence();

        assertNotNull(result.getRequestId()); // verifies that the resulting Geofence has been
                                                // created and assigned a requestId
    }

    @Test
    public void test_compareTo_self() throws Exception { // test if a fence is equal to itself
        NamedGeofence blacksburg1 = new NamedGeofence();
        blacksburg1.name = "Blacksburg";
        blacksburg1.latitude = 37.2296;
        blacksburg1.longitude = -80.4139;
        blacksburg1.radius = (float)0.8516; // created a fence for Blacksburg

        assertEquals(blacksburg1.compareTo(blacksburg1), 0); // a given fence should be equal to itself

    }

    @Test
    public void test_compareTo_samePoint() throws Exception { // same points with different names
        NamedGeofence blacksburg1 = new NamedGeofence();
        blacksburg1.name = "Blacksburg";
        blacksburg1.latitude = 37.2296;
        blacksburg1.longitude = -80.4139;
        blacksburg1.radius = (float)0.8516; // created a fence for Blacksburg

        NamedGeofence blacksburg2 = new NamedGeofence();
        blacksburg2.name = "New Blacksburg";
        blacksburg2.latitude = 37.2296;
        blacksburg2.longitude = -80.4139;
        blacksburg2.radius = (float)0.8516; // create an identical fence for Blacksburg, but with a
        // different name

        assertNotEquals(blacksburg1.compareTo(blacksburg2), 0); // even though these are the same
        // geographic location, they should
        // not be equal
    }

    @Test
    public void test_compareTo_sameName() throws Exception { // two different locations, with same name
        NamedGeofence blacksburg1 = new NamedGeofence();
        blacksburg1.name = "Blacksburg";
        blacksburg1.latitude = 37.2296;
        blacksburg1.longitude = -80.4139;
        blacksburg1.radius = (float)0.8516; // created a fence for Blacksburg

        NamedGeofence blacksburg3 = new NamedGeofence();
        blacksburg3.name = "Blacksburg";
        blacksburg3.latitude = -37.2296;
        blacksburg3.longitude = 80.4139;
        blacksburg3.radius = (float)0.25; // created a fence for Blacksburg

        assertEquals(blacksburg1.compareTo(blacksburg3), 0); // though at different locations, they
                                                            // should be equal
    }
}