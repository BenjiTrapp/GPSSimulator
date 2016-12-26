/**
 * 
 */
package test;

import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gps.data.GPSData;
import gps.data.GPSDataEnumHolder.CardinalDirections;
import gps.data.GPSDataEnumHolder.GPSFixTypes;
import gps.data.GPSDataEnumHolder.Modes;
import gps.data.GPSDataEnumHolder.Status;


public class TestGPSData extends TestCase
{
	private static AtomicInteger tid = new AtomicInteger(1);
	private static boolean isSuccessful= false;
	private final static Logger LOG = LoggerFactory.getLogger(TestCheckSum.class);
	
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

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		GPSData.reinitialize();
		
		if(isSuccessful)
			System.out.println("\nTests for Test-ID(" + tid.get() + ") successful!\n");
		else 
			System.out.println("\nTests for Test-ID(" + tid.get() + ") failed and is unsuccessful!\n");
	}

	@Test
	public void testInitValues()
	{
		LOG.info("Checking getter for defaul init values");
		
		assertEquals(Status.A, GPSData.getStatus());
		assertEquals("53.557085", GPSData.getLatitude());
		assertEquals("10.023167", GPSData.getLongitude());
		assertEquals(CardinalDirections.SOUTH, GPSData.getNS());
		assertEquals(CardinalDirections.EAST, GPSData.getEW());
		assertEquals("003.0", GPSData.getVelocity());
		assertEquals("15", GPSData.getAltitude());
		assertEquals(314, GPSData.getCourse());
		assertEquals("4", GPSData.getSatellites());
		assertEquals(8, GPSData.getQuality());
		assertEquals("2.0" , GPSData.getHDOP());
		assertEquals("2.4" , GPSData.getVDOP());
		assertEquals("2.8" , GPSData.getPDOP());
		assertEquals(Modes.SIMULATION, GPSData.getMode());
		assertEquals(GPSFixTypes.GPS_FIX_3D, GPSData.getFixType());
		
		isSuccessful = true;
	}
	
	@Test
	public void testSetGet()
	{
		final int DURATION = 1000;
		LOG.info("Checking setter and getter (" + DURATION + ") times");
		
		for(int i = 0; i < DURATION; i++ )
		{
			GPSData.setCourse(i);
			assertEquals(i, GPSData.getCourse());
			
			GPSData.setAltitude(Integer.toString(i));
			assertEquals(Integer.toString(i), GPSData.getAltitude());
			
			GPSData.setLongitude(Integer.toString(i));
			assertEquals(Integer.toString(i), GPSData.getLongitude());
			
			GPSData.setLatitude(Integer.toString(i));
			assertEquals(Integer.toString(i), GPSData.getLatitude());
		}
		
		isSuccessful = true;
	}
}
