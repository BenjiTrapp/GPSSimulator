package gps.data;

import static gps.data.GPSDataEnumHolder.*;

/**
 * This class is final because there is only one gps.data.GPSData available for the GPS Generator
 * module
 * 
 * This class can be modified by different threads but its assumed that only the
 * DataGenTask (implemented as singleton) is working with this class. But for 
 * further changes or ideas f.e. simulation GPS-Jamming this class is implemented
 * thread save!
 * 
 * @author Benjamin Trapp
 */
public final class GPSData
{
	private static Status status;
	private static String latitude;
	private static String longitude;
	private static CardinalDirections ns;
	private static CardinalDirections ew;
	private static String velocity;
	private static String altitude;
	private static int course;
	private static String satellites;
	private static int quality;
	private static GPSFixTypes fixType;
	private static String pdop;
	private static String hdop;
	private static String vdop;
	private static Modes mode;
	
	static
	{
		reinitialize();
	}
	
	/**
	 * Set`s the Status (A=Active, V=Void)
	 * @return Enumeration type containing the status
	 */
	public static Status getStatus()
	{
		return status;
	}
	
	/**
	 * Set`s the Status (A=Active, V=Void)
	 * @param status the status that shall be set
	 */
	public static void setStatus(Status status)
	{
		if (GPSData.status != status)
			GPSData.status = status;
	}

	/**
	 * Get`s the geometric latitude back
	 * @return geometric latitude as String
	 */
	public synchronized static String getLatitude()
	{
		return latitude;
	}
	
	/**
	 * Set`s the geometric latitude back
	 */
	public synchronized static void setLatitude(String latitude)
	{
		if (GPSData.latitude != latitude)
			GPSData.latitude = latitude;
	}

	/**
	 * Get`s the geometric longitude back
	 * @return geometric longitude as String
	 */
	public synchronized static String getLongitude()
	{
		return longitude;
	}

	/**
	 * Set`s the geometric longitude back
	 */
	public synchronized static void setLongitude(String longitude)
	{
		if (GPSData.longitude != longitude)
			GPSData.longitude = longitude;
	}
	
	/**
	 * Get`s the Cardinal Direction for east or west back
	 * @return Enum with the cardinal direction east or west
	 */
	public static CardinalDirections getEW()
	{
		return ew;
	}

	/**
	 * Set`s the Cardinal Direction for east or west 
	 */
	public static void setEW(CardinalDirections ew)
	{
		if (ew == CardinalDirections.NORTH || ew == CardinalDirections.SOUTH)
			throw new IllegalArgumentException();
		
		if (GPSData.ew != ew)
			GPSData.ew = ew;
	}
	
	/**
	 * Get`s the Cardinal Direction for north or south back
	 * @return Enum with the cardinal direction north or south 
	 */
	public static CardinalDirections getNS()
	{
		return ns;
	}
	
	/**
	 * Set`s the Cardinal Direction for north or south 
	 */
	public static void setNS(CardinalDirections ns)
	{
		if (ns == CardinalDirections.EAST || ns == CardinalDirections.WEST)
			throw new IllegalArgumentException();
		
		if (GPSData.ns != ns)
			GPSData.ns = ns;
	}
	
	/**
	 * Get`s the velocity back 
	 * @return velocity as string 
	 */
	public synchronized static String getVelocity()
	{
		return velocity;
	}
	
	/**
	 * Set`s the velocity
	 * @param velocity
	 */
	public synchronized static void setVelocity(String velocity)
	{
		if (GPSData.velocity != velocity)
			GPSData.velocity = velocity;
	}
	
	/**
	 * Get's the altitude back
	 * @return altitude as String
	 */
	public synchronized static String getAltitude()
	{
		return altitude;
	}
	
	/**
	 * Set`s the altitude
	 */
	public synchronized static void setAltitude(String altitude)
	{
		if (GPSData.altitude != altitude)
			GPSData.altitude = altitude;
	}
	
	/**
	 * Get`s the Course back
	 * @return the Course in degree (gon or radial)
	 */
	public static int getCourse()
	{
		return course;
	}
	
	/**
	 * Set`s the Course in degree (gon or radial)
	 */
	public static void setCourse(int course)
	{
		if (GPSData.course != course)
			GPSData.course = course;
	}
	
	/**
	 * Get`s the amount of satellites back
	 * @return amount of satellites as string
	 */
	public static String getSatellites()
	{
		return satellites;
	}
	
	/**
	 * Set`s the amount of satellites
	 */
	public static void setSatellites(String  satellites)
	{
		int tmp = Integer.parseInt(satellites);
				
		if(tmp > 12 || tmp < 0)
			throw new IllegalArgumentException();
		
		if (!GPSData.satellites.equals(satellites))
			GPSData.satellites = satellites;
	}
	
	/**
	 * Get`s the Quality back
	 * @return quality as String
	 */
	public static int getQuality()
	{
		return quality;
	}

	/**
	 * Set`s the quality
	 * @param quality quality as integer
	 */
	public static void setQuality(int quality)
	{
		if (GPSData.quality != quality)
			GPSData.quality = quality;
	}
	
	/**
	 * Get`s the Positional Dilution of Precision back
	 * @return Positional Dilution of Precision
	 */
	public synchronized static String getPDOP()
	{
		return pdop;
	}
	
	/**
	 * Set`s the Positional Dilution of Precision 
	 */
	public synchronized static void setPDOP(String pdop)
	{
		if (GPSData.pdop != pdop)
			GPSData.pdop = pdop;
	}
	
	/**
	 * Get`s the Horizontal Dilution of Precision back
	 * @return Horizontal Dilution of Precision
	 */
	public synchronized static String getHDOP()
	{
		return hdop;
	}
	
	/**
	 * Set`s the Horizontal Dilution of Precision 
	 * @return Horizontal Dilution of Precision
	 */
	public synchronized static void setHDOP(String hdop)
	{
		if (GPSData.hdop != hdop)
			GPSData.hdop = hdop;
	}
	
	/**
	 * Get`s the Vertical Dilution of Precision back
	 * @return Vertical Dilution of Precision
	 */
	public synchronized static String getVDOP()
	{
		return vdop;
	}
	
	/**
	 * Set`s the Vertical Dilution of Precision 
	 */
	public synchronized static void setVDOP(String vdop)
	{
		if (GPSData.vdop != vdop)
			GPSData.vdop = vdop;
	}
	
	/**
	 * Get`s the mode back 
	 * @return mode as enumeration
	 */
	public synchronized static Modes getMode()
	{
		return mode;
	}
	
	/**
	 * Set`s the mode depending on the passed enumeration type 
	 */
	public synchronized static void setMode(Modes mode)
	{
		if (GPSData.mode != mode)
			GPSData.mode = mode;
	}
	
	/**
	 * Get`s the fix type back 
	 * @return mode as enumeration
	 */
	public synchronized static GPSFixTypes getFixType()
	{
		return fixType;
	}

	/**
	 * Set`s the fix type depending on the passed enumeration type 
	 */
	public synchronized static void setFixType(GPSFixTypes fixType)
	{
		if (GPSData.fixType != fixType)
			GPSData.fixType = fixType;
	}
	
	/**
	 * Function to reinitialize the GPS data  
	 */
	public static void reinitialize()
	{
		status = Status.A;
		latitude = "53.557085";
		longitude = "10.023167";
		ns = CardinalDirections.SOUTH;
		ew = CardinalDirections.EAST;
		velocity = "003.0";
		altitude = "15";
		course = 314;
		satellites = "4";
		quality = 8;
		hdop = "2.0";
		vdop = "2.4";
		pdop = "2.8";
		mode = Modes.SIMULATION;
		fixType = GPSFixTypes.GPS_FIX_3D;
	}
}
