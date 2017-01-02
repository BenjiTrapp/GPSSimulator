package gps.NMEA.sentences;

import gps.NMEA.utils.ChecksumUtilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NMEASentenceBuilderTest {

    @Test
    public void shouldCreateCorrectSentence(){
        // given
        NMEASentenceBuilder builder = new NMEASentenceBuilder(NMEASentenceTypes.GPGGA).append("Test")
                .append("string")
                .append("1234")
                .append("4711")
                .append("")
                .append("")
                .appendChecksum();;


        // when
        String result = builder.build();

        // then
        assertEquals("$GPGGA,Test,string,1234,4711,,*72", result);
    }

    @Test
    public void shouldCreateCorrectSentenceWithMode(){
        // given
        NMEASentenceBuilder builder = new NMEASentenceBuilder(NMEASentenceTypes.GPGGA).append("Test")
                .append("string")
                .append("1234")
                .append("4711")
                .append("")
                .append("")
                .appendChecksum("SuperDuperMode");


        // when
        String result = builder.build();

        // then
        assertEquals("$GPGGA,Test,string,1234,4711,,SuperDuperMode*46", result);
    }

    @Test
    public void shouldCreateCorrectSentenceWithValidChecksum(){
        // given
        NMEASentenceBuilder builder = new NMEASentenceBuilder(NMEASentenceTypes.GPGGA).append("Test")
                .append("string")
                .append("1234")
                .append("4711")
                .append("")
                .append("SuperDuperMode")
                .appendChecksum();


        // when
        String result = builder.build();
        System.out.println(result);
        // then
        assertTrue(ChecksumUtilities.isChecksumValid(result));
    }
}