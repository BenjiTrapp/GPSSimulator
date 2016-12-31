package gps.NMEA.utils;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class ChecksumUtilities
{
	private static AtomicInteger invalidCRCs = new AtomicInteger();
	private static int checksumLength = 3;
	private final static Logger LOG = Logger.getLogger("ChecksumLogger");
	private static FileHandler fh = null;
	
	static
	{
		try
		{
			fh = new FileHandler("log/Checksum.log");
			LOG.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			LOG.setUseParentHandlers(false);
			LOG.setLevel(Level.FINE);
		} catch (SecurityException | IOException e)
		{
			e.printStackTrace();
		}
    }
	
	/**
	 * Calculates the checksum from a gps.NMEA Sentence and compares it
	 * with the passed gps.NMEA Sentence.
	 * 
	 * Important: The passed gps.NMEA Sentence must contain a checksum
	 * of the type *DD (D for a Hex digit). If the CRC has only one
	 * Hex digit it must have a leading 0.
	 * 
	 * @param nmeaSentence The gps.NMEA Sentence that shall be checked
	 * @return true if the checksum is valid, otherwise false
	 */
	public static synchronized boolean isChecksumValid(String nmeaSentence)
	{
		boolean isValid = true;
		int msglen = nmeaSentence.length();
		if(nmeaSentence.isEmpty())
			throw new InvalidParameterException();
		
		if(!nmeaSentence.startsWith("$"))
		{
			appendError("gps.NMEA Sentence is Invalid - Missing leading '$' (" + nmeaSentence + ")");
			isValid = false;
		}
		
		if (msglen > 4 && isValid)
		{
			if (nmeaSentence.charAt(msglen - checksumLength) == '*')
			{
				String chk_s = getCRC(nmeaSentence.substring(0, msglen - 3));

				isValid = nmeaSentence.substring(msglen - 2, msglen).equals(chk_s);
				LOG.info("Sentence: " + nmeaSentence + " is valid");
				return (isValid);
			} else
			{
				appendError("Sentence ("+ nmeaSentence +" ) +  has no checksum ");
				return false;
			}
		}
		
		if(isValid)
			appendError("message length > 4 characters for Sentence (" + nmeaSentence + ")");
		
		return false;
	}

	/**
	 * Creates the checksum from the passed gps.NMEA Sentence
	 * 
	 * @param nmeaSentence String containing the full gps.NMEA message without checksum
	 * @return String String that contains the calculated checksum
	 */
	public static synchronized String getCRC(String nmeaSentence)
	{
		assert nmeaSentence != null;
		assert !nmeaSentence.isEmpty();

        int chk = 0;
		String chk_s;

		for (int i = 1; i < nmeaSentence.length(); i++)
			chk ^= nmeaSentence.charAt(i);

		chk_s = Integer.toHexString(chk).toUpperCase();

		if(chk_s.length() < 2)
			chk_s = "0" + chk_s;
		
		LOG.info("Created CRC ("+ chk_s +")  for Sentence: " + nmeaSentence);		
		return chk_s;
	}
	
	/**
	 * Get`s the total amount of all invalid CRC checks back
	 * @return total amount of invalid CRC checks as integer
	 */
	public static synchronized int getInvalidCRCAmount()
	{
		return invalidCRCs.get();
	}
	
	/**
	 * (Re-)sets the CRC check counter back to zero
	 */
	public static synchronized void resetInvalidCRCAmount()
	{
		invalidCRCs.set(0);
	}
	
	/**
	 * Append an "ERROR" to the logger 
	 * @param error String to describe the error
	 */
	private static void appendError(String error)
	{
		invalidCRCs.incrementAndGet();
		LOG.log(Level.FINE,error);
	}
}
