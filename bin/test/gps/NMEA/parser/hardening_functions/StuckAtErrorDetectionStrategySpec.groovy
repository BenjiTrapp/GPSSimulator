package gps.NMEA.parser.hardening_functions

import gps.NMEA.gps_position.GPSPosition
import gps.NMEA.gps_position.GPSPositionHistory
import gps.NMEA.sentences.NMEASentenceTypes
import spock.lang.Shared
import spock.lang.Specification

class StuckAtErrorDetectionStrategySpec extends  Specification {
    @Shared private def current = new GPSPosition()
    @Shared private def last = new GPSPosition()
    @Shared private def second_last = new GPSPosition()
    @Shared private def third_last = new GPSPosition()
    @Shared private def gpsPositionHistory

    def setup() {
        gpsPositionHistory = new GPSPositionHistory(NMEASentenceTypes.GPGGA)
        current.setAltitude("123.5" as Double)
        current.setLongitude("456.5" as Double)
        current.setLatitude("789.5" as Double)

        last.setAltitude("123.4" as Double)
        last.setLongitude("456.4" as Double)
        last.setLatitude("789.4" as Double)

        second_last.setAltitude("123.4" as Double)
        second_last.setLongitude("456.4" as Double)
        second_last.setLatitude("789.4" as Double)

        third_last.setAltitude("123.4" as Double)
        third_last.setLongitude("456.4" as Double)
        third_last.setLatitude("789.4" as Double)
    }

    def "Should return true when all positions are equal"() {
        given:
        gpsPositionHistory.addCurrentPosition(current)
        gpsPositionHistory.addLastPosition(current)
        gpsPositionHistory.addSecondLastPosition(current)
        gpsPositionHistory.addThirdLastPosition(current)

        when:
        boolean result = new StuckAtErrorDetectionStrategy().isPerturbationDetected(gpsPositionHistory)

        then:
        assert result
    }

    def "Should return true when all positions are different"() {
        given:
        gpsPositionHistory.addCurrentPosition(current)
        gpsPositionHistory.addLastPosition(last)
        gpsPositionHistory.addSecondLastPosition(second_last)
        gpsPositionHistory.addThirdLastPosition(third_last)

        when:
        boolean result = new StuckAtErrorDetectionStrategy().isPerturbationDetected(gpsPositionHistory)

        then:
        assert !result
    }
}
