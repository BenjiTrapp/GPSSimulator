
import junit.framework.TestCase;
import org.junit.Test;
import gps.NMEA.gps_position.GPSPosition;

public class TestGPSPosition extends TestCase
{
	@Test
	public void testGetterSetter()
	{
		GPSPosition gp1 = new GPSPosition();
		
		gp1.setAltitude(123.321);
		gp1.setDirection(123.321);
		gp1.setFixed(false);
		gp1.setLatitude(123.321);
		gp1.setLongitude(123.321);
		gp1.setQuality(123.321);
		gp1.setTime(123.321);
		gp1.setVelocity(123.321);
		
		assertEquals(123.321, gp1.getAltitude());
		assertEquals(123.321, gp1.getDirection());
		assertEquals(123.321, gp1.getLatitude());
		assertEquals(123.321, gp1.getLongitude());
		assertEquals(123.321, gp1.getQuality());
		assertEquals(123.321, gp1.getTime());
		assertEquals(123.321, gp1.getVelocity());
		assertFalse(gp1.isFixed());
	}
	
	@Test
	public void testEquals()
	{
		GPSPosition gp1 = new GPSPosition();
		GPSPosition gp2;
		GPSPosition gp3 = new GPSPosition();
		
		gp1.setAltitude(10.0);
		gp1.setLatitude(100.0);
		gp1.setLongitude(12.0);
		
		gp3.setAltitude(11.0);
		gp3.setLatitude(111.0);
		gp3.setLongitude(22.0);
		
		gp2 = gp1;
		
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
