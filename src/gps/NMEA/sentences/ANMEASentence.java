package gps.NMEA.sentences;

import java.text.SimpleDateFormat;

import gps.NMEA.utils.ChecksumUtilities;
import gps.data.GPSData;
/**
 * This abstract Class contains all necessary methods to 
 * create a gps.NMEA Sentence of a specific type
 * 
 * @author Benjamin Trapp
 */
public abstract class ANMEASentence
{
	/**
	 * This function gets the Name of the gps.NMEA Sentence back
	 * @return the gps.NMEA Sentence as String
	 */
	public abstract String getName();
	
	/**
	 * Gets the gps.NMEA Sentence of it`s specific back
	 * @return gps.NMEA Sentence of a specific type as String
	 */
	public abstract String getSentence();

	/**
	 * Appends the passed value to the passed StringBuffer and adds a "," 
	 * in front of the passed value
	 * @param buf Reference to the StringBuffer that shall be used
	 * @param value Double value that be appended to the passed StringBuffer
	 */
	protected void append(StringBuffer buf, double value)
	{
		if(buf == null)
			throw new IllegalArgumentException("the passed StringBuffer is null");
		
		buf.append("," + value);
	}

	/**
	 * Appends the passed value to the passed StringBuffer and adds a "," 
	 * in front of the passed value
	 * @param buf Reference to the StringBuffer that shall be used
	 * @param arg String that be appended to the passed StringBuffer
	 */
	protected void append(StringBuffer buf, String arg)
	{
		if(buf == null)
			throw new IllegalArgumentException("the passed StringBuffer is null");
		
		buf.append(",");
		buf.append(arg);
	}
	
	/**
	 * Appends the gps.NMEA Checksum to the buffer as a hex digit
	 * @param buf buffer that contains all needed values
	 */
	protected void appendCheckSum(StringBuffer buf)
	{
        assert  buf != null;
		assert !(buf.length() == 0);
		buf.append("*" + ChecksumUtilities.getCRC(buf.toString()));
	}
	
	/**
	 * Appends the gps.NMEA Checksum to the buffer as a hex digit but
	 * adds the mode that is passed as a string in front 
	 * @param buf buffer that contains all needed values
	 */
	protected void appendCheckSum(StringBuffer buf, String mode)
	{
		if(buf == null)
			throw new IllegalArgumentException("the passed StringBuffer is null");
		
		buf.append(mode);
		appendCheckSum(buf);
	}
	
	/**
	 * Get's a time stamp back in the format HHMMSS
	 * @return time stamp as String 
	 */
	public String getTimestamp()
	{
	
		SimpleDateFormat timeStamp = new SimpleDateFormat("HHmmss");
		
		return timeStamp.format(new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	/**
	 * Get's a date stamp back in the format ddMMYY
	 * @return time stamp as String 
	 */
	public String getDatestamp()
	{
		SimpleDateFormat dateStamp = new SimpleDateFormat("ddMMYY");

		return dateStamp.format(new java.sql.Timestamp(System.currentTimeMillis()));
	}

	/**
	 * Gets the gps.NMEA Latitude back, the data is already  transformed
	 * into Degree� Minutes' and Seconds''
	 * @return A transfered/recalculated version of the Latitude
	 */
	public synchronized static double getNMEALatitude()
	{
		double degree = (int)Double.parseDouble(GPSData.getLatitude());
		double minute = (int) (Double.parseDouble(GPSData.getLatitude()) * 60 - degree * 60);
		double second = (Double.parseDouble(GPSData.getLatitude())) * 60
				- (int) (Double.parseDouble((GPSData.getLatitude())) * 60);

		return (double) Math.round((degree * 100 + minute + second) * 100) / 100;
	}

	/**
	 * Gets the gps.NMEA Longitude back, the data is already  transformed
	 * into Degree� Minutes' and Seconds''
	 * @return A transfered/recalculated version of the Longitude
	 */
	public synchronized static double getNMEALongitude()
	{
		double degree = (int) Double.parseDouble(GPSData.getLongitude());
		double minute = (int) (Double.parseDouble(GPSData.getLongitude()) * 60 - degree * 60);
		double second = Double.parseDouble(GPSData.getLongitude()) * 60
				- (int) (Double.parseDouble(GPSData.getLongitude()) * 60);

		return (double) Math.round((degree * 100 + minute + second) * 100) / 100;
	}
}
