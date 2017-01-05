package gps.NMEA.utils;

import gps.NMEA.sentences.NMEASentenceTypes;
import org.jboss.byteman.contrib.bmunit.BMUnitRunner;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit-Test to prove the correctness of the calculation of the gps.NMEA Checksum.
 * This Test will be used for further Tests via Mutation Tests and may be changed
 * to see better effects
 *
 * @author Benjamin Trapp
 */
public class ChecksumUtilitiesTest {
    private final static String VALID_GGA_SENTENCE = "$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,*4E";
    private final static String VALID_RMC_SENTENCE = "$GPRMC,1911622,A,5336.93,N,1005.12,E,010.1,10.0,211113,,S";
    private final static String INVALID_CRC_GGA_SENTENCE = "$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,*42";
    private final static String INVALID_CRC_RMC_SENTENCE = "$GPRMC,1911622,A,5336.93,N,1005.12,E,010.1,10.0,211113,,S*42";
    private final static String INVALID_CRC_LENGTH_GGA_SENTENCE = "$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,*1234";
    private final static String INVALID_CRC_LENGTH_RMC_SENTENCE = "$GPRMC,1911622,A,5336.93,N,1005.12,E,010.1,10.0,211113,,S*1234";

    @Test
    public void testGetCRC() {
        // given
        String tmp = "$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,";

        // when
        String crcCalced = ChecksumUtilities.getCRC(tmp);

        // then
        assertEquals("4E", crcCalced);
    }

    @Test
    public void testCRCLen() {
        // given
        StringBuilder s;
        Random rnd = new Random();
        final int RANGE = 13;

        // when
        for (int i = 0; i < RANGE; i++) {
            s = new StringBuilder("$GPGGA,");
            String tmp = s.append(rnd.nextInt(10000)).append(",").toString();
            System.out.println(tmp);
            s.append("*")
             .append(ChecksumUtilities.getCRC(tmp));

            tmp = s.toString();
            System.out.println(tmp);
            // then
            assertTrue(ChecksumUtilities.isChecksumValid(s.toString()));
        }
    }

    @Test
    @Tag("HappyPath")
    public void testCheckCRC() {

        assertTrue(ChecksumUtilities.isChecksumValid(VALID_GGA_SENTENCE));
        assertTrue(ChecksumUtilities.isChecksumValid(VALID_RMC_SENTENCE + "*" + ChecksumUtilities.getCRC(VALID_RMC_SENTENCE)));
    }

    @Test
    @Tag("SadPath")
    public void invalidCRCChecksShouldFail(){
        assertFalse(ChecksumUtilities.isChecksumValid(INVALID_CRC_RMC_SENTENCE));
        assertFalse(ChecksumUtilities.isChecksumValid(INVALID_CRC_GGA_SENTENCE));
        assertFalse(ChecksumUtilities.isChecksumValid(INVALID_CRC_LENGTH_RMC_SENTENCE));
        assertFalse(ChecksumUtilities.isChecksumValid(INVALID_CRC_LENGTH_GGA_SENTENCE));
    }

    @Test
    @Tag("SadPath")
    public void invalidRandomCRCChecksShouldFail(){
        // given
        Random rnd = new Random();

        // when
        for (int i = 0; i < 9; i++)
            // then
            assertFalse(ChecksumUtilities.isChecksumValid("$T3ST,NM3@,Sentence,4711,%*" + (rnd.nextInt(9)) + i));
    }
}