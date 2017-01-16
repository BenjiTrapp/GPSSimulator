package gps.NMEA.gps_position;

import gps.NMEA.gps_position.GPSPosition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GPSPositionTest
{
	@Test
	public void shouldGetCorrectValuesAfterSettingThem()
	{
		// given
		GPSPosition gp1 = new GPSPosition();

		// when
		gp1.setAltitude(123.321);
		gp1.setDirection(123.321);
		gp1.setFixed(false);
		gp1.setLatitude(123.321);
		gp1.setLongitude(123.321);
		gp1.setQuality(123.321);
		gp1.setTime(123.321);
		gp1.setVelocity(123.321);

		// then
		assertEquals(new Double(123.321), gp1.getAltitude());
		assertEquals(new Double(123.321), gp1.getDirection());
		assertEquals(new Double(123.321), gp1.getLatitude());
		assertEquals(new Double(123.321), gp1.getLongitude());
		assertEquals(new Double(123.321), gp1.getQuality());
		assertEquals(new Double(123.321), gp1.getTime());
		assertEquals(new Double(123.321), gp1.getVelocity());
		assertFalse(gp1.isFixed());
	}



    @Test
	public void shouldBeEqualWhenBaseValuesAreEqual()
	{
		// given
		GPSPosition gp1 = new GPSPosition();
		GPSPosition gp2;
		GPSPosition gp3 = new GPSPosition();


		// when positions 1 and 3 are different
		gp1.setAltitude(10.0);
		gp1.setLatitude(100.0);
		gp1.setLongitude(12.0);
		gp1.setDirection(1.0);

		gp3.setAltitude(11.0);
		gp3.setLatitude(111.0);
		gp3.setLongitude(22.0);
		gp3.setVelocity(100.0);

		gp2 = gp1; // and position 1 equals position 2

		// then
		assertEquals(true, gp1.isBasicPositionEqual(gp2));
		assertEquals(true, gp1.isEqual(gp2));
		assertEquals(false, gp1.isBasicPositionEqual(gp3));
		assertEquals(false, gp1.isEqual(gp3));
		assertEquals(true, gp2.isBasicPositionEqual(gp1));
		assertEquals(true, gp1.isEqual(gp1));
		assertEquals(false, gp2.equals(gp3));
		assertEquals(false, gp2.isEqual(gp3));
	}


    @Test
    public void shouldBeOnlyEqualWhenCompleteObjectsAreEqual()
    {
        // given
        GPSPosition gp1 = new GPSPosition();
        GPSPosition gp2;
        GPSPosition gp3 = new GPSPosition();


        // when positions 1 and 3 are different
        gp1.setAltitude(10.0);
        gp1.setLatitude(100.0);
        gp1.setLongitude(12.0);
        gp1.setQuality(32.0);
        gp1.setDirection(42.0);
        gp1.setVelocity(4711.0);
        gp1.setTime(1337.0);
        gp1.setFixed(false);

        gp3.setAltitude(11.0);
        gp3.setLatitude(111.0);
        gp3.setLongitude(22.0);
        gp3.setLongitude(32.0);
        gp3.setDirection(4711.0);
        gp3.setTime(1337.1);
        gp3.setFixed(true);
        gp2 = gp1; // and position 1 equals position 2

        // then
        assertEquals(true, gp1.equals(gp2));
        assertEquals(true, gp1.isEqual(gp2));
        assertEquals(false, gp1.equals(gp3));
        assertEquals(false, gp1.isEqual(gp3));
        assertEquals(true, gp2.equals(gp1));
        assertEquals(true, gp1.isEqual(gp1));
        assertEquals(false, gp2.equals(gp3));
        assertEquals(false, gp2.isEqual(gp3));
    }
}
