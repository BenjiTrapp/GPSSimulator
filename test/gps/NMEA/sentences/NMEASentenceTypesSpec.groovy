package gps.NMEA.sentences

import spock.lang.Specification

import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.*

class NMEASentenceTypesSpec extends Specification {

    def shouldReturnRMCEnumName(){
        given:
        def RPMC_name

        when:
        RPMC_name = NMEASentenceTypes.GPRMC.toString()

        then:
        that RPMC_name, containsString("GPRMC")
    }

    def shouldReturnRMCEnumType(){
        given:
        def RPMC_type

        when:
        RPMC_type = NMEASentenceTypes.GPRMC.getSentenceType()

        then:
        that RPMC_type, containsString("\$GPRMC")
    }

    def shouldReturnGGAEnumName(){
        given:
        def GGA_name

        when:
        GGA_name = NMEASentenceTypes.GPGGA.toString()

        then:
        that GGA_name, containsString("GPGGA")
    }

    def shouldReturnGGAEnumType(){
        given:
        def GGA_type

        when:
        GGA_type = NMEASentenceTypes.GPGGA.getSentenceType()

        then:
        that GGA_type, containsString("\$GPGGA")
    }
}
