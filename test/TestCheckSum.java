/**
 * 
 */
package test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;
import org.jboss.byteman.contrib.bmunit.BMUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gps.NMEA.utils.ChecksumUtilities;

/**
 * JUnit-Test to prove the correctness of the calculation of the gps.NMEA Checksum.
 * This Test will be used for further Tests via Mutation Tests and may be changed
 * to see better effects 
 * 
 * @author Benjamin Trapp
 *
 */
@RunWith(BMUnitRunner.class)
public class TestCheckSum extends TestCase
{
	private final static Logger LOG = LoggerFactory.getLogger(TestCheckSum.class);
	private static AtomicInteger tid = new AtomicInteger(1);
	private String validGGASentence = "$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,*4E";
	private String validRMCSentence = "$GPRMC,1911622,A,5336.93,N,1005.12,E,010.1,10.0,211113,,S";
	private String invalidCRCGGASentence = "$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,*42";
	private String invalidCRCRMCSentence = "$GPRMC,1911622,A,5336.93,N,1005.12,E,010.1,10.0,211113,,S*42";
	private String invalidCRCLengthGGASentence = "$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,*1234";
	private String invalidCRCLengthRMCSentence = "$GPRMC,1911622,A,5336.93,N,1005.12,E,010.1,10.0,211113,,S*1234";
	private static boolean isSuccessful = false;
	
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
	public void testGetCRC()
	{
		LOG.info("@TC: testGetCRC()");
		String tmp = "$GPGGA,1911504,5336.93,N,1005.12,E,8.0,01,1.2,15.4,M,0,M,,";
		String crcCalced = ChecksumUtilities.getCRC(tmp);
		
		LOG.info("gps.NMEA:\t\t"+ tmp);
		LOG.info("CRC-Calced:\t" + crcCalced);
		assertEquals("4E", crcCalced);
		isSuccessful = true;
	}
	
	@Test
	public void testCheckCRC()
	{
		LOG.info("@TC: testCheckCRC()");
		Random rnd = new Random();
		LOG.info("Checking valid GGA Sentence");
		assertTrue(ChecksumUtilities.isChecksumValid(validGGASentence));
		LOG.info("Checking valid RMC Sentence");
		assertTrue(ChecksumUtilities.isChecksumValid(validRMCSentence + "*" + ChecksumUtilities.getCRC(validRMCSentence)));
		LOG.info("Checking invalid RMC Sentence");
		assertFalse(ChecksumUtilities.isChecksumValid(invalidCRCRMCSentence));
		LOG.info("Checking invalid GGA Sentence");
		assertFalse(ChecksumUtilities.isChecksumValid(invalidCRCGGASentence));
		LOG.info("Checking invalid CRC length for RMC Sentence");
		assertFalse(ChecksumUtilities.isChecksumValid(invalidCRCLengthRMCSentence));
		LOG.info("Checking invalid CRC length for GGA Sentence");
		assertFalse(ChecksumUtilities.isChecksumValid(invalidCRCLengthGGASentence));
		
		LOG.info("Checking invalid Sentences @checkCRC");
		for(int i = 0; i < 9; i++)
			assertFalse(ChecksumUtilities.isChecksumValid("$T3ST,N�3@,Sentence,4711,%*" + (rnd.nextInt(9)) + i));
		
		isSuccessful = true;
	}
	
	@Test
	public void testCRCLen()
	{
		StringBuffer s = new StringBuffer("$GPGGA");
		Random rnd = new Random();
		final int RANGE = 13;
		
		LOG.info("Checking CRC validation for a Range of  " + RANGE + " different length");
		
		for(int i = 0; i < RANGE; i++)
		{
			s.append(rnd.nextInt(10000) + ",");
			s.append("*" + ChecksumUtilities.getCRC(s.toString()));
			assertTrue(ChecksumUtilities.isChecksumValid(s.toString()));
		}
		isSuccessful = true;
	}
	
	@Test
	public void testInvalidCnt()
	{
		final int RANGE = 10;
		ChecksumUtilities.resetInvalidCRCAmount(); //Assert a clean start
		
		LOG.info("Checking invalid Count");
		for(int i = 0; i < RANGE; i++)
			ChecksumUtilities.isChecksumValid("INVALID");
		
		assertEquals(RANGE, ChecksumUtilities.getInvalidCRCAmount());
		
		LOG.info("Checking invalid counter reset");
		ChecksumUtilities.resetInvalidCRCAmount();
		assertEquals(0, ChecksumUtilities.getInvalidCRCAmount());
		isSuccessful = true;
	}
}
