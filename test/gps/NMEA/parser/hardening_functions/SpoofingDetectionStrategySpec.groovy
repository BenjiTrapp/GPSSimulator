package gps.NMEA.parser.hardening_functions

import gps.NMEA.gps_position.GPSPosition
import gps.NMEA.gps_position.GPSPositionHistory
import gps.NMEA.sentences.NMEASentenceTypes
import spock.lang.Shared
import spock.lang.Specification

class SpoofingDetectionStrategySpec extends  Specification {
    @Shared private def position1 = new GPSPosition()
    @Shared private def position2 = new GPSPosition()
    @Shared private def gpsPositionHistory
    private static final def SPOOFING_COORD_DASH = 42.0 as Double

    def setup() {
        gpsPositionHistory = new GPSPositionHistory(NMEASentenceTypes.GPGGA)
        position1.setAltitude("123.0" as Double)
        position1.setLongitude("456.0" as Double)
        position1.setLatitude("789" as Double)

        position2.setAltitude("123" + SPOOFING_COORD_DASH as Double)
        position2.setLongitude("456" + SPOOFING_COORD_DASH as Double)
        position2.setLatitude("789" + SPOOFING_COORD_DASH as Double)
    }

    def "Should throw AssertionErrorException when CurrentPosition is null"() {
        given:
        gpsPositionHistory.addLastPosition(position2)

        when:
        gpsPositionHistory.addCurrentPosition(null)

        then:
        thrown(AssertionError)
    }

    def "Should throw AssertionErrorException when last position is null"() {
        given:
        gpsPositionHistory.addCurrentPosition(position2)

        when:
        gpsPositionHistory.addLastPosition(null)
        then:
        thrown(AssertionError)
    }


    def "Should return true when position is spoofed upper boundary"() {
        given:
        gpsPositionHistory.addCurrentPosition(position2)
        gpsPositionHistory.addLastPosition(position1)

        when:
        boolean result = new SpoofingDetectionStrategy().isPerturbationDetected(gpsPositionHistory)

        then:
        assert result
    }


    def "Should return false when position is NOT spoofed"() {
        given:
        String tiny_progress = ".5"
        position2.setAltitude("123" + tiny_progress as Double)
        position2.setLongitude("456" + tiny_progress as Double)
        position2.setLatitude("789" + tiny_progress as Double)

        gpsPositionHistory.addCurrentPosition(position1)
        gpsPositionHistory.addLastPosition(position2)

        when:
        boolean result = new SpoofingDetectionStrategy().isPerturbationDetected(gpsPositionHistory)

        then:
        assert !result
    }
}
