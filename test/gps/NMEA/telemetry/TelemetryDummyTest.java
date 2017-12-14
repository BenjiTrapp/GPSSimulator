package gps.NMEA.telemetry;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.parser.NMEAParser;
import gps.NMEA.exceptions.InvalidChecksumException;
import org.junit.Rule;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

class TelemetryDummyTest {
    @Mock private FileWriter fileWriterMock = mock(FileWriter.class);
    @Mock private NMEAParser nmeaParserMock = mock(NMEAParser.class);

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    @Tag("Integration Test")
    public void dummy() throws InvalidChecksumException, IOException {
        // given
        GPSPosition pos = new GPSPosition();
        pos.setLatitude(4711.0);

        TelemetryDummy dummy = new TelemetryDummy(fileWriterMock, nmeaParserMock);

        // when
        when(nmeaParserMock.parse(anyString())).thenReturn(pos);
        doNothing().when(fileWriterMock).write(anyString());
        dummy.write2File("test");

        // then
        verify(fileWriterMock).write(anyString());
        assertTrue(nmeaParserMock.parse(anyString()).getLatitude().equals(4711.0));
    }
}