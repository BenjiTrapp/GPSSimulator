package gps.NMEA.parser.sentences;

import gps.NMEA.gps_position.GPSPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GPGGAParserTest {
    private String tokens[];

    @BeforeEach
    public void setUp(){
        tokens = new String[]{"GPGGA", "215336", "5333.46", "N", "1001.53", "E",
                              "8.0", "02", "2.0", "149.8", "M", "O", "M", "*4D"};
    }

    @Test
    void shouldParseTokensArrayAndReturnCorrectGPSPosInfoObj() {
        // given
        GPGGAParser parser = new GPGGAParser();

        // when
        GPSPosition result = parser.parse(tokens);

        // then
        assertEquals(new Double(tokens[1]), result.getTime());
        assertEquals(new Double(tokens[4]), result.getLatitude());
        assertEquals(new Double(tokens[6]), result.getQuality());
        assertEquals(new Double(tokens[9]), result.getAltitude());
        assertNull(result.getVelocity());
        assertNull(result.getDirection());
    }
}