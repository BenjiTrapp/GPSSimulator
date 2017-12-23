package gps.NMEA.sentences

import gps.NMEA.utils.ChecksumUtilities
import gps.data.GPSData
import spock.lang.Specification

import static gps.data.GPSDataEnumHolder.Status.*
import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.*
import static java.lang.Integer.parseInt

class GSASentenceSpec extends Specification {
    def setup(){ GPSData.reinitialize() }

    def cleanup(){ GPSData.reinitialize() }


    def "Should calculate correct GSA sentence"() {
        given:
        GPSData.setStatus(V)
        def gsaSentence = new GSASentence()

        when:
        def result = gsaSentence.getSentence()

        then:
        that result, containsString("\$GPGSA,,,,,,,,,,,,,,,,,*6E")
    }

    def "Should create correct GSA Sentence based on init GPS values"() {
        given:
        def gsaSentence = new GSASentence()

        when:
        def result = gsaSentence.getSentence()

        then:
        that result, containsString("\$GPGSA,A,3.0,")
        that result, containsString(",2.8,2.0,2.4*")
        assert ChecksumUtilities.isChecksumValid(result)
    }


    def "Should use default sentence when GPS data status is V"() {
        given:
        GSASentence gsaSentence = new GSASentence()
        GPSData.setStatus(V)

        when:
        def result = gsaSentence.sentence

        then:
        that result, equalTo("\$GPGSA,,,,,,,,,,,,,,,,,*6E")
        that result.split(",").toList(), hasSize(18)
    }


    def "Should have all 12 places filled when 12 Satellites are available"() {
        given:
        def gsaSentence = new GSASentence()
        GPSData.setSatellites("12")

        when:
        def result = gsaSentence.createSatellitePRNs()

        then:
        that result, equalTo("00,01,02,03,04,05,06,07,08,09,10,11,")
        that result.split(",").toList(), hasSize(12)
    }


    def shouldHaveA4PlacesFilledWhen4SatellitesAreAvailable() {
        given:
        def gsaSentence = new GSASentence()
        GPSData.setSatellites("4")
        int delimiterCnt = 0
        int digitCnt = 0

        when:
        String result = gsaSentence.createSatellitePRNs()

        then:
        that result, containsString(",")

        for (int i = 0; i < result.length(); i++) {
            if ("," as char == result.charAt(i)){delimiterCnt++}

            try {
                parseInt(String.valueOf(result.charAt(i)))
                digitCnt++
            } catch (ignored) {}
        }
        that delimiterCnt, is(12)
        that digitCnt, is(8) // must be twice, because every digit contains of two single digits
    }
}
