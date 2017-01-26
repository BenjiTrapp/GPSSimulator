package faultInjection.fault_library.perturbation_strategies;

import gps.data.GPSData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StuckAtErrorStrategyTest {

    private static final String TEST_LONGITUDE = "1337";

    @Test
    public void shouldStayStuckAtDefaultValue(){
        // given
        StuckAtErrorStrategy s  = new StuckAtErrorStrategy();
        s.setStuckTime(250);
        GPSData.setLongitude(TEST_LONGITUDE);
        assertEquals(TEST_LONGITUDE, GPSData.getLongitude());

        // when
        s.notifyToPerturb();
        simulateGPSChanges();
        s.perturb();

        // then - the TEST_LONGITUDE must be ignored
        assertEquals(TEST_LONGITUDE, GPSData.getLongitude());
    }

    private void simulateGPSChanges() {
        new Thread(() -> {
            int x = 0;
            while (x < 50){
                GPSData.setLongitude("SPAM");
                x++;
            }
            //Spend some time to simulate an asynchronous manipulation of the GPS Data
            try {Thread.sleep(15);} catch (InterruptedException ignored) {}}).start();
    }
}