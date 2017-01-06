package faultInjection.pertubation.perturbation_functions.perturbation_strategies;

import gps.data.GPSData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StuckErrorStrategyTest {

    private static final String TEST_LONGITUDE = "1337";

    @Test
    public void shouldStayStuckAtDefaultValue(){
        // given
        StuckErrorStrategy s  = new StuckErrorStrategy();
        s.setStuckTime(150);
        GPSData.setLongitude(TEST_LONGITUDE);
        assertEquals(TEST_LONGITUDE, GPSData.getLongitude());


        // when
        s.notifyToPerturb();
            new Thread(() -> {
                GPSData.setLongitude(TEST_LONGITUDE);
                //Spend some time to simulate an asynchronous manipulation of the GPS Data
                try {Thread.sleep(50);} catch (InterruptedException ignored) {}});
        s.perturb();

        // then - the TEST_LONGITUDE must be overwritten by SPAMMING the "reinit" Method during perturbation
        assertEquals("1337", GPSData.getLongitude());
    }
}