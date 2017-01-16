package gps.NMEA.gps_position;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GPSPositionBuilderTest {

    @Test
    public void shouldCreateValidGPSPostion(){
        //given
        GPSPositionBuilder positionBuilder = new GPSPositionBuilder();

        //when
        GPSPosition result = positionBuilder
                .addLatitude(0.1)
                .addDirection(1.0)
                .addVelocity(42.0)
                .addFixed(true)
                .build();

        //then
        assertNotNull(result);
        assertEquals(new Double(0.1), result.getLatitude());
    }

    @Test
    public void shouldAddAndGetAllValuesBack(){
        //given
        GPSPositionBuilder positionBuilder = new GPSPositionBuilder();

        //when
        GPSPosition result = positionBuilder.addLatitude(0.1)
                                            .addTime(1337.2)
                                            .addQuality(4.0)
                                            .addLongitude(21.0)
                                            .addDirection(1.0)
                                            .addVelocity(42.0)
                                            .addFixed(true)
                                            .build();

        //then
        assertEquals(new Double(1337.2), result.getTime());
        assertEquals(new Double(0.1), result.getLatitude());
        assertEquals(new Double(21.0), result.getLongitude());
        assertEquals(new Double(1.), result.getDirection());
        assertEquals(new Double(4.), result.getQuality());
        assertEquals(new Double(42.0), result.getVelocity());
        assertEquals(true, result.isFixed());
    }

}