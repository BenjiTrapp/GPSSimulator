package gps.NMEA.parser;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.parser.hardening_functions.HardeningStrategy;
import gps.NMEA.parser.hardening_functions.StuckAtErrorDetectionStrategy;
import gps.NMEA.utils.InvalidChecksumException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class NMEAParserTest {
    private NMEAParser parser;

    @BeforeEach
    public void setUp(){
        parser = new NMEAParser();
    }

    @Test
    @Tag("ExceptionalPath")
    public void shouldThrowInvalidChecksumException(){
        // given
        String INVALID_CRC_GGA_SENTENCE = "$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,*42";


        // when - then
        assertThrows(InvalidChecksumException.class, () -> parser.parse(INVALID_CRC_GGA_SENTENCE));
    }

    @Test
    @Tag("HappyPath")
    void shouldParseAndCreateCorrectGPSPosition() throws InvalidChecksumException {
        // given
        String VALID_RMC_SENTENCE = "$GPGGA,122627,5333.89,S,1001.66,E,8.0,03,1.7,150.0,M,0,M,,*52";

        // when
        GPSPosition result = parser.parse(VALID_RMC_SENTENCE);

        // then
        assertEquals(new Double(1001.66), result.getLatitude());
        assertEquals(new Double(122627), result.getTime());
        assertEquals(new Double(8.0000), result.getQuality());
        assertEquals(new Double(150.0000), result.getAltitude());
        assertNull(result.getLongitude());
        assertNull(result.getDirection());
        assertNull(result.getVelocity());
    }

    @Test
    @Tag("HappyPath")
    void shouldParseAndCreateCorrectGPSPositionAndRunHardeningStrategy() throws InvalidChecksumException {
        // given
        NMEAParser parserWithStrategy;
        String VALID_RMC_SENTENCE = "$GPGGA,122627,5333.89,S,1001.66,E,8.0,03,1.7,150.0,M,0,M,,*52";
        StuckAtErrorDetectionStrategy strategy = mock(StuckAtErrorDetectionStrategy.class);
        Set <HardeningStrategy> strategies = new HashSet<>();
        strategies.add(strategy);

        // when
        parserWithStrategy = new NMEAParser(strategies);
        when(strategy.isPerturbationDetected(any())).thenReturn(true);
        for( int i = 0; i < 4; i ++){parserWithStrategy.parse(VALID_RMC_SENTENCE);}

        // then
        verify(strategy, atLeastOnce()).isPerturbationDetected(any());
    }

    @Test
    @Tag("HappyPath")
    void shouldParseAndCreateCorrectGPSPositionAndNotRunHardeningStrategy() throws InvalidChecksumException {
        // given
        NMEAParser parserWithStrategy;
        String VALID_RMC_SENTENCE = "$GPGGA,122627,5333.89,S,1001.66,E,8.0,03,1.7,150.0,M,0,M,,*52";
        StuckAtErrorDetectionStrategy strategy = mock(StuckAtErrorDetectionStrategy.class);
        Set <HardeningStrategy> strategies = new HashSet<>();
        strategies.add(strategy);

        // when
        parserWithStrategy = new NMEAParser(strategies);
        when(strategy.isPerturbationDetected(any())).thenReturn(false);
        for( int i = 0; i < 42; i ++){parserWithStrategy.parse(VALID_RMC_SENTENCE);}

        // then
        verify(strategy, atLeastOnce()).isPerturbationDetected(any());
    }

    @Test
    @Tag("HappyPath")
    void shouldCreateTwoHistoryGPSPosWhenAtLeast4SentencesHaveBeenParsed() throws InvalidChecksumException {
        // given
        NMEAParser mockParer = spy(NMEAParser.class);
        String VALID_RMC_SENTENCE = "$GPGGA,122627,5333.89,S,1001.66,E,8.0,03,1.7,150.0,M,0,M,,*52";

        // when
        for( int i = 0; i < 4; i ++){mockParer.parse(VALID_RMC_SENTENCE);}

        // then
        verify(mockParer, times(2)).createHistoricGPSPositionSnapshot(any(),any());
    }

    @Test
    @Tag("HappyPath")
    void shouldCreateNoHistoryGPSPosWhenOnly3SentencesHaveBeenParsed() throws InvalidChecksumException {
        // given
        NMEAParser mockParer = spy(NMEAParser.class);
        String VALID_RMC_SENTENCE = "$GPGGA,122627,5333.89,S,1001.66,E,8.0,03,1.7,150.0,M,0,M,,*52";

        // when
        for( int i = 0; i < 2; i ++){mockParer.parse(VALID_RMC_SENTENCE);}

        // then
        verify(mockParer, never()).createHistoricGPSPositionSnapshot(any(),any());
    }
}