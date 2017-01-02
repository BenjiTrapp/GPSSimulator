/**
 * 
 */
package test;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import faultInjection.pertubation.PertubationFunctions;
import gps.data.GPSData;

/**
 * @author Benjamin-Yves
 *
 */
public class TestPertubationFuncs extends TestCase
{
	private Random rnd = new Random();
	private int percentage = 10;

	@Test
	public void testRandomTriggerWithPercentage() throws InterruptedException
	{
		int tmp = -1;
		int bla = 0;

		for(int i = 0; i < 5; i++)
		{
			tmp = rnd.nextInt(500 * percentage) / 100;
			System.out.println(tmp);
			
			if(tmp <= percentage && tmp >= 0)
				System.out.println("###BAM"+ ++bla + "###");
			
			Thread.sleep(1000);
		}
	}

	@Test
	public void testRandomChar()
	{
		String test1 = "Dies ist ein Test";
		String test2 = "Dies ist ein sehr viel l�ngerer Test als der erste Test";
		
		String tmp1 = "";
		tmp1 = PertubationFunctions.randomASCIIChar(test1);
		System.out.println(tmp1);
		
		String tmp2 = "";
		tmp2 = PertubationFunctions.randomASCIIChar(test2);
		System.out.println(tmp2);
		
		Map<String,String> tmpMap = null;
		tmpMap = PertubationFunctions.getASCIIMap();
		assertFalse(tmpMap.isEmpty());
		
		assertEquals(tmp1, tmpMap.get(test1));
		assertEquals(tmp2, tmpMap.get(test2));
				
		//Random Test f�r 100 Zeichen	
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < 100; i++)
			System.out.println(PertubationFunctions.randomASCIIChar(sb.append(i).toString()));
	}
	
	@Test
	public void testRandomCharWithNMEAData()
	{
		String gga = "$GPGGA,182022,5334.16,N,1001.96,W,8.0,06,1.6,15.1,M,0,M,,*6F";
		String rmc = "$GPRMC,182023,A,5334.19,S,1001.96,W,010.0,277.0,181213,,S*76";
		
		String perturbedGGA = PertubationFunctions.randomASCIIChar(gga);
		String perturbedRMC = PertubationFunctions.randomASCIIChar(rmc);
		
		assertNotNull(perturbedGGA);
		assertNotNull(perturbedRMC);
		
		assertNotSame(gga, perturbedGGA);
		assertNotSame(rmc, perturbedRMC);
		System.out.println(perturbedGGA + "\n" + perturbedRMC );
	}
	
	@Test
	public void testRandomDash()
	{
		assertEquals("53.557085", GPSData.getLatitude());
		assertEquals("10.023167", GPSData.getLongitude());
	
		PertubationFunctions.randomDashCoordinates();
		
		assertEquals("63.557085", GPSData.getLatitude());
		assertEquals("20.023167", GPSData.getLongitude());
	}
}
