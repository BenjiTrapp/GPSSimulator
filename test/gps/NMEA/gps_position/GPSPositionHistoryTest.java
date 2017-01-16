package gps.NMEA.gps_position;

import gps.NMEA.sentences.NMEASentenceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GPSPositionHistoryTest {

    private GPSPositionHistory gpsPositionHistory;
    private GPSPosition mockPos1, mockPos2, mockPos3, mockPos4;

    @BeforeEach
    public void setUp(){
        mockPos1 = mock(GPSPosition.class);
        mockPos2 = mock(GPSPosition.class);
        mockPos3 = mock(GPSPosition.class);
        mockPos4 = mock(GPSPosition.class);

        when(mockPos1.getAltitude()).thenReturn(1.0);
        when(mockPos2.getAltitude()).thenReturn(2.0);
        when(mockPos3.getAltitude()).thenReturn(3.0);
        when(mockPos4.getAltitude()).thenReturn(4.0);
    }

    @Test
    public void shouldAddAndGetAllTypesOfHistoricPositions(){
        // given
        gpsPositionHistory = new GPSPositionHistory(NMEASentenceTypes.GPGGA);

        // when
        gpsPositionHistory.addCurrentPosition(mockPos1);
        gpsPositionHistory.addLastPosition(mockPos2);
        gpsPositionHistory.addSecondLastPosition(mockPos3);
        gpsPositionHistory.addThirdLastPosition(mockPos4);

        // then
        assertEquals(new Double(1.0), gpsPositionHistory.getCurrentPosition().getAltitude());
        assertEquals(new Double(2.0), gpsPositionHistory.getLastPosition().getAltitude());
        assertEquals(new Double(3.0), gpsPositionHistory.getSecondLastPosition().getAltitude());
        assertEquals(new Double(4.0), gpsPositionHistory.getThirdLastPosition().getAltitude());
    }

    @Test
    public void shouldAddAllPosAndGetAllTypesOfHistoricPositions(){
        // given
        gpsPositionHistory = new GPSPositionHistory(NMEASentenceTypes.GPGGA);

        // when
        gpsPositionHistory.addPositions(mockPos1, mockPos2, mockPos3, mockPos4);

        // then
        assertEquals(new Double(1.0), gpsPositionHistory.getCurrentPosition().getAltitude());
        assertEquals(new Double(2.0), gpsPositionHistory.getLastPosition().getAltitude());
        assertEquals(new Double(3.0), gpsPositionHistory.getSecondLastPosition().getAltitude());
        assertEquals(new Double(4.0), gpsPositionHistory.getThirdLastPosition().getAltitude());
    }

    @Test
    public void shouldAddAndGetSentenceTypes(){
        // given
        gpsPositionHistory = new GPSPositionHistory(NMEASentenceTypes.GPGGA);

        // when
        gpsPositionHistory.addNMEASentenceType(NMEASentenceTypes.GPRMC);

        // then
        assertEquals(NMEASentenceTypes.GPRMC, gpsPositionHistory.getSentenceType());
    }

    @Test
    public void shouldPrintToString(){
        // given
        gpsPositionHistory = new GPSPositionHistory(NMEASentenceTypes.GPGGA);

        // when
        String result = gpsPositionHistory.toString();

        // then
        assertTrue(result.contains("Current Position: "));
        assertTrue(result.contains("Last Position: "));
        assertTrue(result.contains("Previous Last Position: "));
        assertTrue(result.contains("Third Last Position: "));
        assertTrue(result.contains("\n"));
    }
}