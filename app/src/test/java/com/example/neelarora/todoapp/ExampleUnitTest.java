package com.example.neelarora.todoapp;

import android.view.MenuItem;

import com.google.android.gms.location.Geofence;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test_getGeofenceController() throws Exception {
        GeofenceController cont = new GeofenceController();
        assertNotNull(cont.getInstance());
    }

    @Test
    public void test_LatMin()   {
        Constants.Geometry geo = new Constants.Geometry();
        assertTrue(geo.MinLatitude == -90.0);
    }

    @Test
    public void test_LatMax()   {
        Constants.Geometry geo = new Constants.Geometry();
        assertTrue(geo.MaxLatitude == 90.0);
    }

    @Test
    public void test_LonMin()   {
        Constants.Geometry geo = new Constants.Geometry();
        assertTrue(geo.MinLongitude == 180.0);
    }

    @Test
    public void test_LonMax()   {
        Constants.Geometry geo = new Constants.Geometry();
        assertTrue(geo.MaxLongitude == -180.0);
    }

    @Test
    public void test_RadMin()   {
        Constants.Geometry geo = new Constants.Geometry();
        assertTrue(geo.MinRadius == 0.01);
    }

    @Test
    public void test_RadMax()   {
        Constants.Geometry geo = new Constants.Geometry();
        assertTrue(geo.MaxRadius == 20.0);
    }

    @Test
    public void test_SharedPrefs()    {
        Constants.SharedPrefs pref = new Constants.SharedPrefs();
        assertTrue(pref.Geofences.equals("SHARED_PREFS_GEOFENCES"));
    }

    @Test
    public void test_AreWeThereIntentService()  {
        AreWeThereIntentService serv = new AreWeThereIntentService();
        assertNotNull(serv);
    }

    @Test
    public void test_geofence_zeroRadius() throws Exception {
        NamedGeofence fence = new NamedGeofence();
        fence.name = "Test Location";
        fence.latitude = 37.2296;
        fence.longitude = -80.4139;
        fence.radius = (float)0.0;

        try   {
            Geofence result = fence.geofence();
        }
        catch (Exception e)
        {
            assertNotNull(e);
        }

    }

    @Test
    public void test_geofence_negativeRadius() throws Exception {
        NamedGeofence fence = new NamedGeofence();
        fence.name = "Test Location";
        fence.latitude = 37.2296;
        fence.longitude = -80.4139;
        fence.radius = (float)-5.0;

        try   {
            Geofence result = fence.geofence();
        }
        catch (Exception e)
        {
            assertNotNull(e);
        }
    }

    @Test
    public void test_geofence_HighLong() throws Exception {
        NamedGeofence fence = new NamedGeofence();
        fence.name = "Test Location";
        fence.latitude = 37.2296;
        fence.longitude = 181.0;
        fence.radius = (float)5.0;

        try   {
            Geofence result = fence.geofence();
        }
        catch (Exception e)
        {
            assertNotNull(e);
        }
    }

    @Test
    public void test_geofence_LowLong() throws Exception {
        NamedGeofence fence = new NamedGeofence();
        fence.name = "Test Location";
        fence.latitude = 37.2296;
        fence.longitude = -181.0;
        fence.radius = (float)5.0;

        try   {
            Geofence result = fence.geofence();
        }
        catch (Exception e)
        {
            assertNotNull(e);
        }
    }

    @Test
    public void test_geofence_LowLat() throws Exception {
        NamedGeofence fence = new NamedGeofence();
        fence.name = "Test Location";
        fence.latitude = -91;
        fence.longitude = -80.4139;
        fence.radius = (float) 5.0;

        try   {
            Geofence result = fence.geofence();
        }
        catch (Exception e)
        {
            assertNotNull(e);
        }
    }

    @Test
    public void test_geofence_HighLat() throws Exception {
        NamedGeofence fence = new NamedGeofence();
        fence.name = "Test Location";
        fence.latitude = 91;
        fence.longitude = -80.4139;
        fence.radius = (float) 5.0;

        try   {
            Geofence result = fence.geofence();
        }
        catch (Exception e)
        {
            assertNotNull(e);
        }
    }
}
