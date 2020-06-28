package gps.NMEA.sentences

import gps.data.GPSData
import spock.lang.Specification
import spock.lang.Stepwise

import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.*
import static gps.data.GPSDataEnumHolder.Status.*

@Stepwise
class RMCSentenceSpec extends Specification{
    def cleanup(){ GPSData.reinitialize() }


    def "Should use default sentence when status is v"() {
        given:
        GPSData.setStatus(V)
        def ggaSentence = new RMCSentence()

        when:
        def result = ggaSentence.getSentence()
        println result

        then:
        that result, containsString("\$GPRMC")
        that result, containsString(",V,,,,,,,,,,N*31")
    }


    def "Should build valid GGA sentence when status is A"() {
        given:
        A == GPSData.getStatus()
        def rmcSentence = new RMCSentence()

        when:
        def result = rmcSentence.getSentence()

        then:
       that result, containsString("\$GPRMC")
       that result, containsString("5333.43")
       that result, containsString("1001.39")
       that result, containsString("A,")
       that result, containsString("003.0,")
       that result, containsString("314.0")
       that result, containsString(rmcSentence.getDatetime())
       that result, containsString("S")
       that result, containsString("E")
       that result, containsString(",,S")
       that result, containsString("*")
    }


    def shouldCreateValidGGASentence() {
        when:
        def sentence = new RMCSentence()

        then:
        that sentence, is(not(null))
        sentence instanceof  NMEASentence
        sentence instanceof  RMCSentence
    }
}
