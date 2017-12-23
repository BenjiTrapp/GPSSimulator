package gps.NMEA.utils

import spock.lang.Specification

import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.*

import static gps.NMEA.utils.ChecksumUtilities.getCRC
import static gps.NMEA.utils.ChecksumUtilities.isChecksumValid

class ChecksumUtilitiesSpec extends Specification{
    private final static def VALID_GGA_SENTENCE = "\$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,*4E" 
    private final static def VALID_RMC_SENTENCE = "\$GPRMC,1911622,A,5336.93,N,1005.12,E,010.1,10.0,211113,,S" 
    private final static def INVALID_CRC_GGA_SENTENCE = "\$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,*42" 
    private final static def INVALID_CRC_RMC_SENTENCE = "\$GPRMC,1911622,A,5336.93,N,1005.12,E,010.1,10.0,211113,,S*42" 
    private final static def INVALID_CRC_LENGTH_GGA_SENTENCE = "\$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,*1234" 
    private final static def INVALID_CRC_LENGTH_RMC_SENTENCE = "\$GPRMC,1911622,A,5336.93,N,1005.12,E,010.1,10.0,211113,,S*1234" 

    def testGetCRC() {
        given:
        def tmp = "\$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,," 

        expect:
        getCRC(tmp) == "4E"
    }


    def testCRCLen() {
        given:
        def s
        def rnd = new Random()
        final def RANGE = 13

        RANGE.times {
            s = new StringBuilder("\$GPGGA,")
            def tmp = s.append(rnd.nextInt(10000)).append(",").toString()
            s.append("*").append(getCRC(tmp))

            expect:
            assert isChecksumValid(s.toString())
        }
    }


    def testCheckCRC() {
        expect:
        that isChecksumValid(VALID_GGA_SENTENCE), is(true)
        that isChecksumValid(VALID_RMC_SENTENCE + "*" + getCRC(VALID_RMC_SENTENCE)), is(true)
    }


    def invalidCRCChecksShouldFail(){
        expect:
        that isChecksumValid(INVALID_CRC_RMC_SENTENCE), is(false)
        that isChecksumValid(INVALID_CRC_GGA_SENTENCE), is(false)
        that isChecksumValid(INVALID_CRC_LENGTH_RMC_SENTENCE), is(false)
        that isChecksumValid(INVALID_CRC_LENGTH_GGA_SENTENCE), is(false)
    }


    def shouldThrowAssertionErrorExceptionAtGetCRCMethod(){
        when:
        getCRC(sentence)

        then:
        thrown(expectation)

        where:
        expectation           || sentence
        NullPointerException  || null
        AssertionError        || ""
        AssertionError        || "GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,*4E"
        AssertionError        || "GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,4E"
    }


    def shouldThrowAssertionErrorExceptionAtIsChecksumValidMethod(){
        when:
        isChecksumValid(sentence)

        then:
        thrown(expectation)

        where:
        expectation               || sentence
        IllegalArgumentException  || null
        AssertionError            || ""
        AssertionError            || "\$GPGGA**4E"
        AssertionError            || "GPGGA,,123,321*4E"
    }
}
