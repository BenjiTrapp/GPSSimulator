package gps.data;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GPSDataTest {
    @Test
    @Ignore
    public void testInitValues()
    {

        synchronized (this){
            GPSData.reinitialize();
            GPSData.reinitialize();
            GPSData.reinitialize();
            GPSData.reinitialize();
            assertEquals(GPSDataEnumHolder.Status.A, GPSData.getStatus());
            assertEquals("53.557085", GPSData.getLatitude());
            assertEquals("10.023167", GPSData.getLongitude());
            assertEquals(GPSDataEnumHolder.CardinalDirections.SOUTH, GPSData.getNS());
            assertEquals(GPSDataEnumHolder.CardinalDirections.EAST, GPSData.getEW());
            assertEquals("003.0", GPSData.getVelocity());
            assertEquals("15", GPSData.getAltitude());
            assertEquals(314, GPSData.getCourse());
            assertEquals("4", GPSData.getSatellites());
            assertEquals(8, GPSData.getQuality());
            assertEquals("2.0" , GPSData.getHDOP());
            assertEquals("2.4" , GPSData.getVDOP());
            assertEquals("2.8" , GPSData.getPDOP());
            assertEquals(GPSDataEnumHolder.Modes.SIMULATION, GPSData.getMode());
            assertEquals(GPSDataEnumHolder.GPSFixTypes.GPS_FIX_3D, GPSData.getFixType());
        }

    }

    @Test
    @Ignore
    public void testSetGet()
    {
        final int DURATION = 1000;

        for(int i = 0; i < DURATION; i++ )
        {
            GPSData.setCourse(i);
            assertEquals(i, GPSData.getCourse());

            GPSData.setAltitude(Integer.toString(i));
            assertEquals(Integer.toString(i), GPSData.getAltitude());

            GPSData.setLongitude(Integer.toString(i));
            assertEquals(Integer.toString(i), GPSData.getLongitude());

            GPSData.setLatitude(Integer.toString(i));
            assertEquals(Integer.toString(i), GPSData.getLatitude());
        }
    }}