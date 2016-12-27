/**
 * 
 */
package test;

import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;
import org.jboss.byteman.contrib.bmunit.BMUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;  

import static org.mockito.Mockito.*;  
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gps.data.GPSData;

import gps.NMEA.sentences.GGASentence;
import gps.NMEA.sentences.RMCSentence;
import gps.NMEA.parser.GPSPosition;
import gps.NMEA.parser.InvalidChecksumException;
import gps.NMEA.parser.NMEAParser;

/**
 * http://www.mastertheboss.com/byteman/byteman-advanced-tutorial
 * https://www.jboss.org/byteman
 * http://stackoverflow.com/questions/2276271/how-to-make-mock-to-void-methods-with-mockito
 * http://www.vogella.com/articles/Mockito/article.html
 * http://tutorials.jenkov.com/java-unit-testing/stub-mock-and-proxy-testing.html
 * 
 * http://www.youtube.com/watch?v=pog4Ne8y6Dc
 * @author Benjamin Trapp
 *
 */
@RunWith(MockitoJUnitRunner.class)
@IncludeCategory(BMUnitRunner.class)
public class TestNMEAParser extends TestCase
{
	private final static Logger LOG = LoggerFactory.getLogger(TestCheckSum.class);
	private static AtomicInteger tid = new AtomicInteger(1);
	private static boolean isSuccessful = false;
	
	private String validRMCSentence = "$GPRMC,205755,A,5335.17,N,1003.0,W,010.0,318.0,081213,,S*5A";
	private String validGGASentence = "$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,*4E";
	
	@Mock private GPSPosition mock = mock(GPSPosition.class);
	@Spy private NMEAParser spyNmeaParser = Mockito.spy(new NMEAParser(mock));
	@InjectMocks private RMCSentence rmc = new RMCSentence();
	@InjectMocks private GGASentence gga = new GGASentence();	
	
	static
	{
		PropertyConfigurator.configure("test.properties");
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		GPSData.reinitialize();
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
		if(isSuccessful)
			System.out.println("\nTests for Test-ID(" + tid.get() + ") successful!\n");
		else 
			System.out.println("\nTests for Test-ID(" + tid.get() + ") failed and is unsuccessful!\n");
	}

	@Test
	public void testSpyParser()
	{	
		GPSPosition mockPos = new GPSPosition();
		GPSPosition tmp = new GPSPosition();
		
		LOG.info("Prepareing the Mock Position");
		mockPos.setAltitude(100.0);
		mockPos.setDirection(123.0);
		mockPos.setLatitude(1234.5432);
		mockPos.setLongitude(1005.1234);
		
		LOG.info("Checking if the Mocked Position will be returned");
		
		try
		{
			doReturn(mockPos).when(spyNmeaParser).parse("TEST");
			tmp =  spyNmeaParser.parse("TEST");
		} catch (InvalidChecksumException e)
		{
			fail("No exception expected");
		}	
		
		assertEquals(100.0, tmp.getAltitude());
		assertEquals(123.0, tmp.getDirection());
		assertEquals(1234.5432, tmp.getLatitude());
		assertEquals(1005.1234, tmp.getLongitude());
		LOG.info("Mocked Position successfully returned and checked");
		isSuccessful = true;
	}
	
	@Test
	public void testParser()
	{
		GPSPosition gga = null; 
		GPSPosition rmc = null;
		NMEAParser parser = new NMEAParser();

		try
		{
			when(spyNmeaParser.parse(validGGASentence)).thenCallRealMethod();
			when(spyNmeaParser.parse(validRMCSentence)).thenCallRealMethod();
			
			gga = spyNmeaParser.parse(validGGASentence);
			rmc = spyNmeaParser.parse(validRMCSentence);
		} catch (InvalidChecksumException e)
		{
			fail("No exception expected");
		}
		
		assertNotNull(gga);
		assertNotNull(rmc);
				
		try
		{
			verify(spyNmeaParser).parse(validGGASentence);
			verify(spyNmeaParser).parse(validRMCSentence);
			LOG.info("Verification of the spy Parser done");
			
			gga = parser.parse(validGGASentence);
			rmc = parser.parse(validRMCSentence);
		} catch (InvalidChecksumException e)
		{
			fail("No exception expected");
		}
		
		//Check all important GGA Stuff
		assertEquals(5336.93, gga.getLatitude());
		assertEquals(1005.12, gga.getLongitude());
		assertEquals(15.4, gga.getAltitude());
		LOG.info("GGA Stuff successfull checked");
		
		//Check all important RMC Stuff
		assertEquals(5335.17, rmc.getLatitude());
		assertEquals(1003.00, rmc.getLongitude());
		assertEquals(318.00, rmc.getDirection());
		assertEquals(10.00, rmc.getVelocity());
		LOG.info("RMC Stuff successfull checked");
		isSuccessful = true;
	}
	
	@Test
	public void testException()
	{
		try
		{
			spyNmeaParser.parse("$GPRMC,DIES,IST,EIN,TEST,123,*23");
			fail("Exception wasn't triggered");
		} catch (InvalidChecksumException e)
		{
			assertNotNull(e);
			assertSame(InvalidChecksumException.class, e.getClass());
			LOG.info("Catched the " + e.getClass() + " (1/2)");
		} catch(Exception e)
		{
			fail("Catched the wrong exception");
			LOG.error("Catched the wrong exception of the type: " 
					+ e.getClass());
		}
		
		try
		{
			String invalidRmc = "�GPRMC,205755,A,5335.17,N,1003.0,W,010.0,318.0,081213,,S*5A";
			when(spyNmeaParser.parse(invalidRmc)).thenCallRealMethod();
			spyNmeaParser.parse(invalidRmc);
			fail("Exception wasn't triggered");
		} catch (InvalidChecksumException e)
		{
			assertNotNull(e);
			assertSame(InvalidChecksumException.class, e.getClass());
			LOG.info("Catched the " + e.getClass() + " (2/2)");
		}catch(Exception e)
		{
			fail("Catched the wrong exception");
			LOG.error("Catched the wrong exception of the type: " 
					+ e.getClass());
		}
		isSuccessful = true;
	}
	
	@Test
	public void testMockedException()
	{
		try
		{
			doThrow(new NullPointerException("TEST")).when(spyNmeaParser).parse("BAM");
			spyNmeaParser.parse("BAM");
			fail("Exception wasn't triggered");
		} catch (NullPointerException e)
		{
			assertNotNull(e);
			assertSame(NullPointerException.class, e.getClass());
			LOG.info("Catched the Mocked " + e.getClass());
		}catch(Exception e)
		{
			fail("Catched the wrong exception");
			LOG.error("Catched the wrong exception of the type: " 
					+ e.getClass());
		}
		isSuccessful = true;
	}
}
