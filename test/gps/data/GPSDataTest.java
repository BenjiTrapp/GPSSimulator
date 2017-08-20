package gps.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static gps.data.GPSData.*;
import static gps.data.GPSDataEnumHolder.CardinalDirections.*;
import static gps.data.GPSDataEnumHolder.GPSFixTypes.GPS_FIX_3D;
import static gps.data.GPSDataEnumHolder.Modes.AUTONOMOUS;
import static gps.data.GPSDataEnumHolder.Modes.DIFFERENTIAL;
import static gps.data.GPSDataEnumHolder.Modes.SIMULATION;
import static gps.data.GPSDataEnumHolder.Status.*;
import static org.junit.jupiter.api.Assertions.*;

class GPSDataTest {

    @BeforeEach
    void setUp(){
        reinitialize();
    }

    @AfterEach
    void tearDown(){
        reinitialize();
    }

    @Test
    void shouldReinitializeCorrectly() {
        // given - when
        GPSData.reinitialize();

        //then
        assertEquals(A, GPSData.getStatus());
        assertEquals(SOUTH, GPSData.getNS());
        assertEquals(EAST, GPSData.getEW());
        assertEquals(SIMULATION, GPSData.getMode());
        assertEquals(GPS_FIX_3D, GPSData.getFixType());
    }

    @Test
    //@Ignore("Test")
    void shouldIgnoreSetMethodsWhenIsStucked(){
        // given
        GPSData.reinitialize();
        GPSData.stuckAtState(true);

        // when
        assertTrue(GPSData.isStuck());
        GPSData.setStatus(V); // Should be ignored

        // then
        assertEquals(V, GPSData.getStatus());
        assertTrue(GPSData.isStuck());
    }

    @Test
    void shouldUpdateGPSDataWhenIsNotStuck(){
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
    void shouldNotUpdateGPSDataWhenDataIsEqualsToTheOldDataAndGPSIsNotStuck(){
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

    @Test
    void shouldSetAndGetCourse(){
        // given
        int expected = 314;
        GPSData.setCourse(expected);

        // when
        int result = GPSData.getCourse();

        // then
        assertEquals(expected, result);
    }

    @Test
    void shouldSetAndGetLongitude(){
        // given
        String expected = "123";
        GPSData.setLongitude(expected);

        // when
        String result = GPSData.getLongitude();

        // then
        assertEquals(expected, result);
    }

    @Test
    void shouldSetAndGetCardinals(){
        // given
        GPSData.setNS(NORTH);
        GPSData.setEW(WEST);

        // when - then
        assertEquals(NORTH, GPSData.getNS());
        assertEquals(WEST, GPSData.getEW());
    }

    @Test
    void shouldSetAndGetModes(){
        // given
        GPSData.setMode(DIFFERENTIAL);

        // when - then
        assertEquals(DIFFERENTIAL, GPSData.getMode());
    }

    @Test
    void shouldSetAndGetFixType(){
        // given
        GPSData.setFixType(GPS_FIX_3D);

        // when - then
        assertEquals(GPS_FIX_3D, GPSData.getFixType());
    }


    @Test
    void shouldSetAndGetStatus(){
        // given
        GPSData.setStatus(V);

        // when - then
        assertEquals(V, GPSData.getStatus());
    }

    @Test
    void shouldSetAndGetLatitude(){
        // given
        String expected = "9475.557085";
        GPSData.setLatitude(expected);

        // when
        String result = GPSData.getLatitude();

        // then
        assertEquals(expected, result);
    }


    @Test
    void shouldSetAndGetStuckAtStateTRUE(){
        // given
        boolean expected = true;
        GPSData.stuckAtState(expected);

        // when
        boolean result = GPSData.isStuck();

        // then
        assertEquals(expected, result);
    }

    @Test
    void shouldSetAndGetStuckAtStateFALSE(){
        // given
        boolean expected = false;                                                                                                                                                ;
        GPSData.stuckAtState(expected);

        // when
        boolean result = GPSData.isStuck();

        // then
        assertEquals(expected, result);
    }
}