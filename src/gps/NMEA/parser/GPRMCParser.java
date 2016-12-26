package gps.NMEA.parser;

/**
 * This Class is used to parse RMC-Sentences for further
 * usage in the telemetrie simulation
 * @author Benjamin Trapp
 *
 */
class GPRMCParser implements INMEASentenceParser
{
	private GPSPosition pos;
	
	/**
	 * Default Constructor 
	 */
	public GPRMCParser()
	{
		this.pos = new GPSPosition();
	}
	
	/**
	 * Default Constructor that uses a predefined
	 * GPSPosition class
	 * @param pos GPSPosition class to fill it with
	 * the parsed info
	 */
	public GPRMCParser(GPSPosition pos)
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
		pos.setLatitude(Double.parseDouble(tokens[3]));
		pos.setLongitude(Double.parseDouble(tokens[5]));
		pos.setVelocity(Double.parseDouble(tokens[7]));
		pos.setDirection(Double.parseDouble(tokens[8]));
		
		return pos;
	}
}
