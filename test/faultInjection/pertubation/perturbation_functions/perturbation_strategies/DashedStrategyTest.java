package faultInjection.pertubation.perturbation_functions.perturbation_strategies;

import gps.data.GPSData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DashedStrategyTest {

    @Test
    public void shouldInjectDashToGPSData(){
        GPSData.reinitialize();

        // given
        DashedStrategy dashedStrategy = new DashedStrategy();
        assertEquals("53.557085", GPSData.getLatitude());
        assertEquals("10.023167", GPSData.getLongitude());
        assertEquals("15", GPSData.getAltitude());

        // when
        dashedStrategy.notifyToPerturb();
        dashedStrategy.perturb("bla");

        // then
        assertEquals("137.557085", GPSData.getLatitude());
        assertEquals("94.023167", GPSData.getLongitude());
        assertEquals("99.0", GPSData.getAltitude());
    }

}