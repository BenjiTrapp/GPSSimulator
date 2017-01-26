package faultInjection.fault_library.perturbation_strategies;

import gps.data.GPSData;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpoofedPositionStrategyTest {

    @Before
    public void setUp(){GPSData.reinitialize();}

    @Test
    public void shouldInjectDashToGPSData(){
        // given
        SpoofedPositionStrategy spoofedPositionStrategy = new SpoofedPositionStrategy();
        assertEquals("53.557085", GPSData.getLatitude());
        assertEquals("10.023167", GPSData.getLongitude());
        assertEquals("15", GPSData.getAltitude());

        // when
        spoofedPositionStrategy.notifyToPerturb();
        spoofedPositionStrategy.perturb();

        // then
        assertEquals("9475.557085", GPSData.getLatitude());
        assertEquals("9432.023167", GPSData.getLongitude());
        assertEquals("9437.0", GPSData.getAltitude());
    }

    @org.junit.Test
    public void shouldNOTInjectDashToGPSDataWhenCoordsAreStuck(){
        // given
        SpoofedPositionStrategy spoofedPositionStrategy = new SpoofedPositionStrategy(1337);
        spoofedPositionStrategy.setRetryCount(1);
        GPSData.stuckAtState(true);

        // when
        spoofedPositionStrategy.notifyToPerturb();
        spoofedPositionStrategy.perturb();

        // then - Nothing should happen
        assertEquals("53.557085", GPSData.getLatitude());
        assertEquals("10.023167", GPSData.getLongitude());
        assertEquals("150", GPSData.getAltitude());
    }

}