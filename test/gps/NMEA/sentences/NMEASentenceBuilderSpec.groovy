package gps.NMEA.sentences

import gps.NMEA.utils.ChecksumUtilities
import spock.lang.Specification

import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.*

class NMEASentenceBuilderSpec extends Specification {

    def shouldCreateCorrectSentence(){
        given:
        def builder = new NMEASentenceBuilder(NMEASentenceTypes.GPGGA).append("Test")
                                                                      .append("string")
                                                                      .append("1234")
                                                                      .append("4711")
                                                                      .append("")
                                                                      .append("")
                                                                      .appendChecksum()

        when:
        def result = builder.build()

        then:
        that result, containsString("\$GPGGA,Test,string,1234,4711,,*72")
    }


    def shouldCreateCorrectSentenceWithMode(){
        given:
        def builder = new NMEASentenceBuilder(NMEASentenceTypes.GPGGA).append("Test")
                                                                      .append("string")
                                                                      .append("1234")
                                                                      .append("4711")
                                                                      .append("")
                                                                      .append("")
                                                                      .appendChecksum("SuperDuperMode")


        when:
        def result = builder.build()

        then:
        that result, containsString("\$GPGGA,Test,string,1234,4711,,SuperDuperMode*46")
    }


    def shouldCreateCorrectSentenceWithValidChecksum(){
        given:
        def builder = new NMEASentenceBuilder(NMEASentenceTypes.GPGGA).append("Test")
                                                                      .append("string")
                                                                      .append("1234")
                                                                      .append("4711")
                                                                      .append("")
                                                                      .append("SuperDuperMode")
                                                                      .appendChecksum()

        when:
        def result = builder.build()

        then:
        assert ChecksumUtilities.isChecksumValid(result)
    }
}
