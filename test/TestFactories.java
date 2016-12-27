/**
 * 
 */
package test;

import java.util.concurrent.atomic.AtomicInteger;

import gps.ParserFactory;
import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gps.GPSGeneratorFactory;
import faultInjection.pertubation.PertubationFactory;
import gps.NMEA.parser.TelemetrieDummy;

/**
 * JUnit-Test to prove the correctness of the calculation of the gps.NMEA Checksum.
 * This Test will be used for further Tests via Mutation Tests and may be changed
 * to see better effects 
 * 
 * @author Benjamin Trapp
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TestFactories extends TestCase
{
	private final static Logger LOG = LoggerFactory.getLogger(TestFactories.class);
	private static AtomicInteger tid = new AtomicInteger(1);
	private static boolean isSuccessful = false;

	//Mocks
	@Spy private TelemetrieDummy spyTeleDummy = Mockito.spy(new TelemetrieDummy());
	
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
		isSuccessful = false;
		System.out.println("\n===========================================");
		System.out.println("Test-ID: " + tid.getAndIncrement() + " for Class: " + this.getClass().getName());
	}
	
	@After
	public void tearDown()
	{
		if(isSuccessful)
			System.out.println("\nTests for Test-ID(" + tid.get() + ") successful!\n");
		else 
			System.out.println("\nTests for Test-ID(" + tid.get() + ") failed and is unsuccessful!\n");
	}
	
	@Test
	public void testParserFactory()
	{
		ParserFactory pf = new ParserFactory();
		assertNotNull(pf);
		
		ParserFactory pf_spy = new ParserFactory(spyTeleDummy);
		assertNotNull(pf_spy);
		assertNotNull(pf_spy.createParserThread());
		isSuccessful = true;
	}
	
	@Test
	public void testGeneratorFactory()
	{
		GPSGeneratorFactory gf = new GPSGeneratorFactory();
		assertNotNull(gf);
		isSuccessful = true;
	}
	
	@Test 
	public void testParserFacException()
	{
		try
		{
			@SuppressWarnings("unused")
			ParserFactory pfNullDummy = new ParserFactory(null);
			fail("Exception wasn't triggered");
		} catch (NullPointerException e)
		{
			assertNotNull(e);
			assertSame(NullPointerException.class, e.getClass());
			LOG.info("Catched the expected Exception of type:  " + e.getClass());
		}catch(Exception e)
		{
			fail("Catched the wrong exception");
			LOG.error("Catched the wrong exception of the type: " 
					+ e.getClass());
		}
		
		isSuccessful = true;
	}
	
	@Test 
	public void testGeneratorFacException()
	{
		GPSGeneratorFactory gf = new GPSGeneratorFactory();
		
		try
		{
			gf.build(null, 0);
			fail("Exception wasn't triggered");
		} catch (NullPointerException e)
		{
			assertNotNull(e);
			assertSame(NullPointerException.class, e.getClass());
			LOG.info("Catched the expected Exception of type:  " + e.getClass());
		}catch(Exception e)
		{
			fail("Catched the wrong exception");
			LOG.error("Catched the wrong exception of the type: " 
					+ e.getClass());
		}
		
		try
		{
			gf.build(null, 0, null);
			fail("Exception wasn't triggered");
		} catch (NullPointerException e)
		{
			assertNotNull(e);
			assertSame(NullPointerException.class, e.getClass());
			LOG.info("Catched the expected Exception of type:  " + e.getClass());
		}catch(Exception e)
		{
			fail("Catched the wrong exception");
			LOG.error("Catched the wrong exception of the type: " 
					+ e.getClass());
		}
		
		isSuccessful = true;
	}
	
	@Test 
	public void testPerturbationFacException()
	{
		PertubationFactory pf = new PertubationFactory();
		
		try
		{
			pf.build(null);
			fail("Exception wasn't triggered");
		} catch (NullPointerException e)
		{
			assertNotNull(e);
			assertSame(NullPointerException.class, e.getClass());
			LOG.info("Catched the expected Exception of type:  " + e.getClass());
		}catch(Exception e)
		{
			fail("Catched the wrong exception");
			LOG.error("Catched the wrong exception of the type: " 
					+ e.getClass());
		}
		
		try
		{
			pf.build(null, 0, null);
			fail("Exception wasn't triggered");
		} catch (NullPointerException e)
		{
			assertNotNull(e);
			assertSame(NullPointerException.class, e.getClass());
			LOG.info("Catched the expected Exception of type:  " + e.getClass());
		}catch(Exception e)
		{
			fail("Catched the wrong exception");
			LOG.error("Catched the wrong exception of the type: " 
					+ e.getClass());
		}
		
		try
		{
			pf.build(null, 0, null, null);
			fail("Exception wasn't triggered");
		} catch (NullPointerException e)
		{
			assertNotNull(e);
			assertSame(NullPointerException.class, e.getClass());
			LOG.info("Catched the expected Exception of type:  " + e.getClass());
		}catch(Exception e)
		{
			fail("Catched the wrong exception");
			LOG.error("Catched the wrong exception of the type: " 
					+ e.getClass());
		}
		
		isSuccessful = true;
	}
}
