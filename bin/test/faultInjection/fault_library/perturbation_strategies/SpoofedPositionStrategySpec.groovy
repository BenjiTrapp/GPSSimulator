package faultInjection.fault_library.perturbation_strategies

import gps.data.GPSData
import spock.lang.Specification

class SpoofedPositionStrategySpec extends Specification {

    def setup() { GPSData.reinitialize() }
    def cleanup() { GPSData.reinitialize() }

    def "Should inject a dash to GPS-Data"() {
        given:
        def spoofedPositionStrategy = new SpoofedPositionStrategy()
        "53.557085" == GPSData.getLatitude()
        "10.023167" == GPSData.getLongitude()
        "15" == GPSData.getAltitude()

        when:
        spoofedPositionStrategy.notifyToPerturb()
        spoofedPositionStrategy.perturb()

        then:
        "9475.557085" == GPSData.getLatitude()
        "9432.023167" == GPSData.getLongitude()
        "9437.0" == GPSData.getAltitude()
    }

    def "Should NOT inject a dash to the GPS-Data when the coordinates are stuck"() {
        given:
        def spoofedPositionStrategy = new SpoofedPositionStrategy(1337)
        spoofedPositionStrategy.setRetryCount(1)
        GPSData.stuckAtState(true)

        when:
        spoofedPositionStrategy.notifyToPerturb()
        spoofedPositionStrategy.perturb()

        then: //Nothing should happen
        "53.557085" == GPSData.getLatitude()
        "10.023167" == GPSData.getLongitude()
        "15" == GPSData.getAltitude()
    }
}
