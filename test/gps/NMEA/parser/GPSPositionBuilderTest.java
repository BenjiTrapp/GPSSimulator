package gps.NMEA.parser;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.gps_position.GPSPositionBuilder;
import org.junit.Test;

import java.util.Optional;

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
        assertEquals(Optional.of(0.1), Optional.of(result.getLatitude()));
    }

}