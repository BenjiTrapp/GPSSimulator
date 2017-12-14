package gps.NMEA.parser

import gps.NMEA.exceptions.InvalidChecksumException
import gps.NMEA.gps_position.GPSPosition
import gps.NMEA.parser.hardening_functions.HardeningStrategy
import gps.NMEA.parser.hardening_functions.StuckAtErrorDetectionStrategy
import spock.lang.Specification

class NMEAParserSpec extends Specification {
    private NMEAParser parser
    private final static def INVALID_CRC_GGA_SENTENCE = '''$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,*42'''
    private final static def VALID_RMC_SENTENCE = '''$GPGGA,122627,5333.89,S,1001.66,E,8.0,03,1.7,150.0,M,0,M,,*52''' 

    def setup(){ parser = new NMEAParser() }

    def "Should throw InvalidChecksumException"(){
        when:
        parser.parse(INVALID_CRC_GGA_SENTENCE)
        then:
        thrown(InvalidChecksumException)
    }

    def "Should parse and create correct GPSPosition"() {
        when:
        GPSPosition result = parser.parse(VALID_RMC_SENTENCE)

        then:
        5333.89     == result.getLatitude()
        122627      == result.getTime()
        8.0000      == result.getQuality()
        150.0000    == result.getAltitude()
        1001.66     == result.getLongitude()
        result.getDirection() == null
        result.getVelocity() == null
    }

    def "shouldParseAndCreateCorrectGPSPositionAndRunHardeningStrategy"() {
        given:
        def parserWithStrategy
        def strategy = Mock(StuckAtErrorDetectionStrategy.class)
        def strategies = new HashSet<HardeningStrategy>()
        strategies.add(strategy)

        when:
        strategy.isPerturbationDetected(_) >> returnVal
        parserWithStrategy = new NMEAParser(strategies)
        for( int i = 0; i < 4; i ++){parserWithStrategy.parse(VALID_RMC_SENTENCE)}

        then:
        (1.._) * strategy.isPerturbationDetected(_) // must be invoked at least once
        0 * _

        where:
        returnVal | _
        true      | _
        false     | _

    }

    def "Should create two HistoryGPSPositions when at least four sentences have been parsed"() {
        given:
        def mockParer = Spy(NMEAParser.class)

        when:
        for( int i = 0; i < 4; i ++){mockParer.parse(VALID_RMC_SENTENCE)}

        then:
        2 * mockParer.createHistoricGPSPositionSnapshot(_,_)
    }



    def "Should create NO HistoryGPSPositions when at least four sentences have been parsed"() {
        given:
        def mockParer = Spy(NMEAParser.class)

        when:
        for( int i = 0; i < 2; i ++){mockParer.parse(VALID_RMC_SENTENCE)}

        then:
        0 * mockParer.createHistoricGPSPositionSnapshot(_,_)
    }
}
