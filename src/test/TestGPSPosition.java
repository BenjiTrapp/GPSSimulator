package test;

import java.util.concurrent.atomic.AtomicInteger;
import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gps.NMEA.parser.GPSPosition;

public class TestGPSPosition extends TestCase
{
	private final static Logger LOG = LoggerFactory.getLogger(TestCheckSum.class);
	private static AtomicInteger tid = new AtomicInteger(1);
	private static boolean isSuccessful = false;
	
	static
	{
		PropertyConfigurator.configure("test.properties");
	}

	@Before
	public void setUp() throws Exception
	{
		isSuccessful = false;
		System.out.println("\n===========================================");
		System.out.println("Test-ID: " + tid.getAndIncrement() + " for Class: " + this.getClass().getName());
	}
	
	@After
	public void tearDown() throws Exception
	{
		if(isSuccessful)
			System.out.println("\nTests for Test-ID(" + tid.get() + ") successful!\n");
		else 
			System.out.println("\nTests for Test-ID(" + tid.get() + ") failed and is unsuccessful!\n");
	}
	
	@Test
	public void testGetterSetter()
	{
		GPSPosition gp1 = new GPSPosition();
		
		LOG.info("Setting GPSPosition stuff");
		gp1.setAltitude(123.321);
		gp1.setDirection(123.321);
		gp1.setFixed(false);
		gp1.setLatitude(123.321);
		gp1.setLongitude(123.321);
		gp1.setQuality(123.321);
		gp1.setTime(123.321);
		gp1.setVelocity(123.321);
		
		LOG.info("Getting the setted stuff back");
		assertEquals(123.321, gp1.getAltitude());
		assertEquals(123.321, gp1.getDirection());
		assertEquals(123.321, gp1.getLatitude());
		assertEquals(123.321, gp1.getLongitude());
		assertEquals(123.321, gp1.getQuality());
		assertEquals(123.321, gp1.getTime());
		assertEquals(123.321, gp1.getVelocity());
		assertFalse(gp1.isFixed());
		isSuccessful = true;
	}
	
	@Test
	public void testEquals()
	{
		GPSPosition gp1 = new GPSPosition();
		GPSPosition gp2 = new GPSPosition();
		GPSPosition gp3 = new GPSPosition();
		
		LOG.info("Preseting stuff for Equals-Test");
		gp1.setAltitude(10.0);
		gp1.setLatitude(100.0);
		gp1.setLongitude(12.0);
		
		gp3.setAltitude(11.0);
		gp3.setLatitude(111.0);
		gp3.setLongitude(22.0);
		
		gp2 = gp1;
		
		LOG.info("Checkig equal methods");
		assertEquals(true, gp1.equals(gp2));
		assertEquals(true, gp1.isEqual(gp2));
		assertEquals(false, gp1.equals(gp3));
		assertEquals(false, gp1.isEqual(gp3));
		assertEquals(true, gp1.equals(gp1));
		assertEquals(true, gp1.isEqual(gp1));
		assertEquals(false, gp2.equals(gp3));
		assertEquals(false, gp2.isEqual(gp3));
		isSuccessful = true;
	}
}
