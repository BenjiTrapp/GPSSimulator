package faultInjection.pertubation.perturbation_functions.perturbation_strategies;

import gps.data.GPSData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StuckAtStrategyTest {

    private static final String TEST_LONGITUDE = "1337";

    @Test
    public void shouldStayStuckAtDefaultValue(){
        // given
        StuckAtStrategy s  = new StuckAtStrategy();
        s.setStuckedTime(150);
        GPSData.setLongitude(TEST_LONGITUDE);
        assertEquals(TEST_LONGITUDE, GPSData.getLongitude());


        // when
        s.notifyToPerturb();
            new Thread(() -> {
                GPSData.setLongitude(TEST_LONGITUDE);
                //Spend some time to simulate an asynchronous manipulation of the GPS Data
                try {Thread.sleep(50);} catch (InterruptedException ignored) {}
                GPSData.setLongitude(TEST_LONGITUDE);});
            s.perturb("bla");

        // then - the TEST_LONGITUDE must be overwritten by SPAMMING the "reinit" Method during perturbation
        assertEquals("10.023167", GPSData.getLongitude());
    }
}