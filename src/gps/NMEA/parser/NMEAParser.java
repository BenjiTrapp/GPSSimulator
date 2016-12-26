package gps.NMEA.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gps.NMEA.utils.ChecksumUtilities;

/**
 * This Class is the main class of the gps.NMEA Parser to combine the
 * sub-parsers. Also this class is used by the ParserFactory
 * 
 * @author Benjamin Trapp
 */
public class NMEAParser
{
	private static final Map<String, INMEASentenceParser> sentenceParsers = new HashMap<String, INMEASentenceParser>();
	private static final String LOG4J_PROPERTIES = "log4j.properties";
	private static AtomicBoolean dashed = new AtomicBoolean();
	private final static Logger logger = LoggerFactory.getLogger(NMEAParser.class);
	private final List<GPSPosition> gpsPos = new ArrayList<>();
	private GPSPosition lastPosition = null;
	private GPSPosition secondLastPosition = null;
	private GPSPosition thirdLastPosition = null;
	private AtomicInteger cnt = new AtomicInteger();
	
	/**
	 * Default Constructor
	 */
	public NMEAParser()
	{
		PropertyConfigurator.configure(LOG4J_PROPERTIES);
		sentenceParsers.put("GPGGA", new GPGGAParser());
		sentenceParsers.put("GPRMC", new GPRMCParser());
		dashed.set(false);
	}
	
	/**
	 * Used for IoC and testing. Use a mock object to gain
	 * further details of the parser
	 */
	public NMEAParser(GPSPosition pos)
	{
		PropertyConfigurator.configure(LOG4J_PROPERTIES);
		sentenceParsers.put("GPGGA", new GPGGAParser(pos));
		sentenceParsers.put("GPRMC", new GPRMCParser(pos));
		dashed.set(false);
	}
	
	/**
	 * Parses a passed gps.NMEA-Sentence
	 * @param line gps.NMEA-Sentence that shall be parsed
	 * @return GPSPosition that contains the parsed info
	 * @throws InvalidChecksumException in case of a malformed gps.NMEA-Sentence
	 */
	public GPSPosition parse(String line) throws InvalidChecksumException
	{
		GPSPosition newPosition = new GPSPosition();
		int size = -1;
		
		//This is the "main" Protection against the implemented faults
		if (!ChecksumUtilities.isChecksumValid(line))
		{
			logger.error("gps.NMEA-Sentence malformed (" + line + ")");
			cnt.incrementAndGet();
			throw new InvalidChecksumException();
		}

		String nmea = line.substring(1);
		String[] tokens = nmea.split(",");
		String type = tokens[0];
		
		if (!sentenceParsers.containsKey(type))
		{
			logger.error("Type of the gps.NMEA-Sentence is unknown or malformed");
			throw new RuntimeException();
		}

		newPosition = sentenceParsers.get(type).parse(tokens);

		if (hasDashed(newPosition) || dashed.get())
			logger.error("### Dash has been detected ###");

		if (isStuck(newPosition))
			logger.error("### Stuck-At Bug Detected ###");

		synchronized (gpsPos)
		{
			gpsPos.add(newPosition);
		}

		size = gpsPos.size();

		if (size > 3)
		{
			lastPosition = gpsPos.get(size - 1);
			secondLastPosition = gpsPos.get(size - 2);
			thirdLastPosition = gpsPos.get(size - 3);
		}
		
		cnt.set(0);
		return newPosition;
	}
	
	/**
	 * Function to check the received Sentence if a dash has occurred based on the data of the last position 
	 * @param newPosition  New position that shall be matched against the data of the last position
	 * @return true if a dash has been recognized otherwise false
	 */
	private synchronized boolean hasDashed(GPSPosition newPosition)
	{
		synchronized (gpsPos)
		{
			if (newPosition != null && lastPosition != null)
			{
				if ((newPosition.getLatitude() > (lastPosition.getLatitude() + 2.5))
						|| (newPosition.getLongitude() > lastPosition
								.getLongitude() + 2.5))
				{
					dashed.set(true);
					return true;
				}
			}
		}
   		return false;
	}
	
	/**
	 * This function checks based on the last three known positions if the receiver is stuck
	 * @param newPosition position that shall be matched against the last three known positions
	 * @return true if the receiver is stuck otherwise false
	 */
	private synchronized boolean isStuck(GPSPosition newPosition )
	{
		synchronized (gpsPos)
		{
			if (newPosition != null && lastPosition != null
					&& secondLastPosition != null && thirdLastPosition != null
					&& cnt.get() < 3)
				if (newPosition.isEqual(lastPosition)
						&& newPosition.isEqual(secondLastPosition)
						&& newPosition.isEqual(thirdLastPosition))
					return true;
		}
		return false;
	}
}
