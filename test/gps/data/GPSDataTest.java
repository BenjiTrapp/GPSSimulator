package gps.data;

import org.junit.jupiter.api.Test;

import static gps.data.GPSData.*;
import static gps.data.GPSDataEnumHolder.CardinalDirections.*;
import static gps.data.GPSDataEnumHolder.GPSFixTypes.GPS_FIX_3D;
import static gps.data.GPSDataEnumHolder.Modes.SIMULATION;
import static org.junit.jupiter.api.Assertions.*;

class GPSDataTest {

    @Test
    public void shouldReinitCorrectly() {
        // given - when
        GPSData.reinitialize();

        //then
        assertEquals(GPSDataEnumHolder.Status.A, GPSData.getStatus());
        assertEquals("53.557085", GPSData.getLatitude());
        assertEquals("10.023167", GPSData.getLongitude());
        assertEquals("003.0", GPSData.getVelocity());
        assertEquals("15", GPSData.getAltitude());
        assertEquals("4", GPSData.getSatellites());
        assertEquals("2.0", GPSData.getHDOP());
        assertEquals("2.4", GPSData.getVDOP());
        assertEquals("2.8", GPSData.getPDOP());
        assertEquals(8, GPSData.getQuality());
        assertEquals(314, GPSData.getCourse());
        assertEquals(SOUTH, GPSData.getNS());
        assertEquals(EAST, GPSData.getEW());
        assertEquals(SIMULATION, GPSData.getMode());
        assertEquals(GPS_FIX_3D, GPSData.getFixType());
    }

    @Test
    public void shouldIgnoreSetMethosWhenIsStucked(){
        // given
        GPSData.reinitialize();
        GPSData.stuckAtState(true);

        // when
        assertTrue(GPSData.isStuck());
        GPSData.setLongitude("123456789");
        GPSData.setLongitude("987654321");
        GPSData.setVelocity("-1");

        // then
        assertEquals("53.557085", GPSData.getLatitude());
        assertEquals("10.023167", GPSData.getLongitude());
        assertEquals("003.0", GPSData.getVelocity());
    }

    @Test
    public void shouldUpdateGPSDataWhenIsNotStuck(){
        // given
        GPSData.reinitialize();
        GPSData.stuckAtState(true);
        assertTrue(GPSData.isStuck());

        // when
        GPSData.stuckAtState(false);
        assertFalse(GPSData.isStuck());
        GPSData.setLatitude("123456789");
        GPSData.setLongitude("987654321");
        GPSData.setVelocity("-1");

        // then
        assertEquals("123456789", GPSData.getLatitude());
        assertEquals("987654321", GPSData.getLongitude());
        assertEquals("-1", GPSData.getVelocity());
    }

    @Test
    public void shouldNotUpdateGPSDataWhenDataIsEqualsToTheOldDataAndGPSIsNotStuck(){
        // given
        GPSData.stuckAtState(false);
        assertFalse(GPSData.isStuck());
        GPSData.setLatitude("123456789");
        GPSData.setLongitude("987654321");
        GPSData.setVelocity("-1");
        assertEquals("123456789", GPSData.getLatitude());
        assertEquals("987654321", GPSData.getLongitude());
        assertEquals("-1", GPSData.getVelocity());

        // when

        GPSData.setLatitude("123456789");
        GPSData.setLongitude("987654321");
        GPSData.setVelocity("-1");

        // then
        assertEquals("123456789", GPSData.getLatitude());
        assertEquals("987654321", GPSData.getLongitude());
        assertEquals("-1", GPSData.getVelocity());
    }
}