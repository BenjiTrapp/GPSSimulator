package faultInjection.fault_library.perturbation_strategies

import annotations.MockClass
import gps.data.GPSData
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class StuckAtErrorStrategySpec extends Specification {

    private static final String TEST_LONGITUDE = "1337"

    def "shouldStayStuckAtDefaultValue"() {
        given:
        def s = new StuckAtErrorStrategy()
        s.setStuckTime(250)
        GPSData.setLongitude(TEST_LONGITUDE)
        TEST_LONGITUDE == GPSData.getLongitude()

        when:
        s.notifyToPerturb()
        simulateGPSChanges()
        s.perturb()

        then: "the TEST_LONGITUDE must be ignored"
        GPSData.getLongitude() != "SPAM"
    }

    @MockClass
    private void simulateGPSChanges() {
        new Thread() {
            Closure run = { ->
                int x = 0
                while (x < 50) {
                    GPSData.setLongitude("SPAM")
                    x++
                }
                //Spend some time to simulate an asynchronous manipulation of the GPS Data
                try {
                    sleep(15)
                } catch (InterruptedException ignored) {
                }
            }

        }.run()
    }
}
