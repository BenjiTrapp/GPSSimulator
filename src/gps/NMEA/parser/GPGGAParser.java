package gps.NMEA.parser;

/**
 * This Class is used to parse GGA-Sentences for further
 * usage in the telemetrie simulation
 * @author Benjamin Trapp
 *
 */
class GPGGAParser implements INMEASentenceParser
{
	private GPSPosition pos;
	
	/**
	 * Default Constructor 
	 */
	public GPGGAParser()
	{
		this.pos = new GPSPosition();
	}
	
	/**
	 * Default Constructor that uses a predefined
	 * GPSPosition class
	 * @param pos GPSPosition class to fill it with
	 * the parsed info
	 */
	public GPGGAParser(GPSPosition pos)
	{
		if(pos == null)
			throw new NullPointerException();
		
		this.pos = pos;
	}
	
	@Override
	public GPSPosition parse(String[] tokens)
	{
		   if (tokens == null)
	            throw new NumberFormatException("null");
		   
		pos.setTime(Double.parseDouble(tokens[1]));
		pos.setLatitude(Double.parseDouble(tokens[2]));
		pos.setLongitude(Double.parseDouble(tokens[4]));
		pos.setQuality(Double.parseDouble(tokens[6]));
		pos.setAltitude(Double.parseDouble(tokens[9]));
		
		return pos;
	}
}
