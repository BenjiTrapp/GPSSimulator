package gps.NMEA.sentences

import gps.data.GPSData
import spock.lang.Specification

import static gps.data.GPSDataEnumHolder.Status.*
import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.*

class GGASentenceSpec extends Specification {

    def cleanup(){ GPSData.reinitialize() }

    def "Should use default sentence when status is V"() {
        given:
        GPSData.setStatus(V)
        def ggaSentence = new GGASentence()

        when:
        def result = ggaSentence.getSentence()

        then:
        that result, containsString("\$GPGGA")
        that result, containsString(",,,,,,,,,,,,,*7A")
    }

    def shouldBuildValidGGASentenceWhenStatusIsA() {
        given:
        A == GPSData.getStatus()
        def ggaSentence = new GGASentence()

        when:
        def result = ggaSentence.getSentence()

        then:
        that result, containsString("GPGGA")
        that result, containsString("5333.43")
        that result, containsString("1001.39")
        that result, containsString("8.0,")
        that result, containsString("2.0,")
        that result, containsString("4,")
        that result, containsString("S")
        that result, containsString("E")
        that result, containsString(",M,0,M,,")
        that result, containsString("*")
    }

    def shouldCreateValidGGASentence() {
        given:
        def sentence

        when:
        sentence = new GGASentence()

        then:
        that sentence, is(not(null))
        sentence instanceof  NMEASentence
        sentence instanceof  GGASentence
    }
}
