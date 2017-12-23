package gps.NMEA.sentences

import gps.NMEA.utils.ChecksumUtilities
import gps.data.GPSData
import spock.lang.Specification

import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.*

class VTGSentenceSpec extends Specification {
    def cleanup() { GPSData.reinitialize() }

    def shouldCreateCorrectVTGInstance() {
        given:
        def vtg = new VTGSentence()

        when:
        def result = vtg.getSentence()

        then:
        that result, containsString("\$GPVTG,")
        that result, containsString(",K,E*")
        that result, containsString(",M,")
        that result, containsString(",T,")
    }


    def shouldCreateCorrectChecksum() {
        given:
        def vtg = new VTGSentence()

        when:
        def result = vtg.getSentence()

        then:
        that ChecksumUtilities.isChecksumValid(result), is(true)
    }


    def shouldCalculateVelocityCorrectlyInKnotsAndKMPerHour() {
        given:
        def vtg = new VTGSentence()
        final def CONVERT_KNOTS_TO_KM_PER_HOUR = 1.852 as Double
        final def EXPECTED_SPEED_IN_KMH =  new Double(GPSData.getVelocity()) * CONVERT_KNOTS_TO_KM_PER_HOUR

        when:
        def result = vtg.getSentence()

        then:
        that result, containsString("M,"  + GPSData.getVelocity() + ",N,")
        that result, containsString("," + EXPECTED_SPEED_IN_KMH.toString() + ",K,")
    }


    def shouldCalculateCorrectCourseRelativeToTheTrueNorth() {
        given:
        def vtg = new VTGSentence()
        def final TRACK_RELATIVE_TO_TRUE_NORTH = 0.014 as Double
        def final EXPECTED_COURSE_RELATIVE_TO_TRUE_NORTH = GPSData.getCourse() * TRACK_RELATIVE_TO_TRUE_NORTH

        when:
        def result = vtg.getSentence()

        then:
        that result, containsString("," +  EXPECTED_COURSE_RELATIVE_TO_TRUE_NORTH +  ",T,")
    }


    def shouldCalculateCorrectCourseRelativeToTheMagneticNorth() {
        given:
        def vtg = new VTGSentence()
        final double TRACK_RELATIVE_TO_MAGNETIC_NORTH = 0.017
        final Double EXPECTED_COURSE_RELATIVE_TO_MAGNETIC_NORTH = GPSData.getCourse() * TRACK_RELATIVE_TO_MAGNETIC_NORTH

        when:
        def result = vtg.getSentence()

        then:
        that result, containsString("," +  EXPECTED_COURSE_RELATIVE_TO_MAGNETIC_NORTH +  ",M,")
    }
}
