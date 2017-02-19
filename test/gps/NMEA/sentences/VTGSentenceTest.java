package gps.NMEA.sentences;

import gps.NMEA.utils.ChecksumUtilities;
import gps.data.GPSData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VTGSentenceTest {

    @BeforeEach
    public void setUp() {
        GPSData.reinitialize();
    }

    @AfterEach
    public void tearDown() {
        GPSData.reinitialize();
    }

    @Test
    void shouldCreateCorrectVTGInstance() {
        // given
        NMEASentence vtg = new VTGSentence();

        // when
        String result = vtg.getSentence();
        System.out.println(result);

        // then
        assertTrue(result.contains("$GPVTG,"));
        assertTrue(result.contains(",K,E*"));
        assertTrue(result.contains(",M,"));
        assertTrue(result.contains(",T,"));
    }

    @Test
    void shouldCreateCorrectChecksum() {
        // given
        NMEASentence vtg = new VTGSentence();

        // when
        String result = vtg.getSentence();

        // then
        assertTrue(ChecksumUtilities.isChecksumValid(result));
    }

    @Test
    void shouldCalculateVelocityCorrectlyInKnotsAndKMPerHour() {
        // given
        NMEASentence vtg = new VTGSentence();
        final double CONVERT_KNOTS_TO_KM_PER_HOUR = 1.852;
        final Double EXPECTED_SPEET_IN_KMH =  new Double(GPSData.getVelocity()) * CONVERT_KNOTS_TO_KM_PER_HOUR;

        // when
        String result = vtg.getSentence();

        // then
        assertTrue(result.contains("M,"  + GPSData.getVelocity() + ",N,"));
        assertTrue(result.contains("," + EXPECTED_SPEET_IN_KMH.toString() + ",K,"));
    }

    @Test
    void shouldCalculateCorrectCourseRelativeToTheTrueNorth() {
        // given
        NMEASentence vtg = new VTGSentence();
        final double TRACK_RELATIVE_TO_TRUE_NORTH = 0.014;
        final Double EXPECTED_COURSE_RELATIVE_TO_TRUE_NORTH = GPSData.getCourse() * TRACK_RELATIVE_TO_TRUE_NORTH;

        // when
        String result = vtg.getSentence();

        // then
        assertTrue(result.contains("," +  EXPECTED_COURSE_RELATIVE_TO_TRUE_NORTH +  ",T,"));
    }

    @Test
    void shouldCalculateCorrectCourseRelativeToTheMagneticNorth() {
        // given
        NMEASentence vtg = new VTGSentence();
        final double TRACK_RELATIVE_TO_MAGNETIC_NORTH = 0.017;
        final Double EXPECTED_COURSE_RELATIVE_TO_MAGNETIC_NORTH = GPSData.getCourse() * TRACK_RELATIVE_TO_MAGNETIC_NORTH;

        // when
        String result = vtg.getSentence();

        // then
        assertTrue(result.contains("," +  EXPECTED_COURSE_RELATIVE_TO_MAGNETIC_NORTH +  ",M,"));
    }
}