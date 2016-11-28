package com.example.neelarora.todoapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.neelarora.todoapp", appContext.getPackageName());
    }

    @Test
    public  void test_addGeofence() throws Exception{


        //context = new MockContext();
        MainActivity main = new MainActivity();
        Context context =  main.getApplicationContext();
        GeofenceController.getInstance().init(context);
        GeofenceController controller = GeofenceController.getInstance();


        NamedGeofence geofence = new NamedGeofence();
        geofence.name = "TEST";
        geofence.latitude = 30.00;
        geofence.longitude = 30.00;
        geofence.radius = (float) 0x1.4p2;


        controller.addGeofence(geofence, main.geofenceControllerListener);
        assertEquals(1, controller.getNamedGeofences().size());
    }
}
