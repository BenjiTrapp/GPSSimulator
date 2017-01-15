package gps.NMEA.parser.sentences;

import gps.NMEA.gps_position.GPSPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GPRMCParserTest {
    private String tokens[];

    @BeforeEach
    public void setUp(){
        tokens = new String[]{"GPRMC", "222637", "A", "5333.82", "N", "1001.96",
                              "E", "010.1", "84.0", "150117", "", "S*42"};

        for(int i = 0; i < tokens.length; i++)
            System.out.println("GPGGA Token " + i + ": " + tokens[i]);
    }

    @Test
    void shouldParseTokensArrayAndReturnCorrectGPSPosInfoObj() {
        // given
        GPRMCParser parser = new GPRMCParser();

        // when
        GPSPosition result = parser.parse(tokens);
        System.out.println(result);

        // then
        assertEquals(new Double(tokens[1]), result.getTime());
        assertEquals(new Double(tokens[5]), result.getLatitude());
        assertEquals(new Double(tokens[7]), result.getQuality());
        assertEquals(new Double(tokens[8]), result.getAltitude());
        assertNull(result.getVelocity());
        assertNull(result.getDirection());
    }
}