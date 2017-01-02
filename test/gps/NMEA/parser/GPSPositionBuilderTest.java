package gps.NMEA.parser;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.gps_position.GPSPositionBuilder;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GPSPositionBuilderTest {

    @Test
    public void bla(){
        //given
        GPSPositionBuilder positionBuilder = new GPSPositionBuilder();

        //when
        GPSPosition result = positionBuilder
                .addLatitude(0.1)
                .build();

        //then
        assertNotNull(result);
        assertEquals(0.1, java.util.Optional.ofNullable(result.getLatitude()));
    }

}