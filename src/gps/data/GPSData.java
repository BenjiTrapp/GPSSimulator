package gps.data;

import java.util.Objects;

import static gps.data.GPSDataEnumHolder.*;
import static gps.data.GPSDataEnumHolder.CardinalDirections.*;
import static gps.data.GPSDataEnumHolder.GPSFixTypes.*;
import static gps.data.GPSDataEnumHolder.Modes.*;

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
public class GPSData
{
    private static final int MAX_AVAILABLE_SATELLITES = 12;
    private static final String NEW_LINE = "\n";
    private static Status status = Status.A;
	private static String latitude = "53.557085";
	private static String longitude = "10.023167";
	private static CardinalDirections ns = SOUTH;
	private static CardinalDirections ew = EAST;
	private static String velocity = "003.0";
	private static String altitude = "150";
	private static int course = 314;
	private static String satellites = "4";
	private static int quality = 8;
	private static GPSFixTypes fixType= GPS_FIX_3D;
	private static String pdop = "2.8";
	private static String hdop = "2.0";
	private static String vdop = "2.4";
	private static Modes mode = SIMULATION;
	
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

	public synchronized static GPSData getCurrentGPSData(){
		return GPSData.getCurrentGPSData();
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
		if (!Objects.equals(GPSData.latitude, latitude))
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
		if (!Objects.equals(GPSData.longitude, longitude))
			GPSData.longitude = longitude;
	}
	
	/**
	 * Get`s the Cardinal Direction for east or west back
	 * @return Enum with the cardinal direction east or west
	 */
	public synchronized static CardinalDirections getEW()
	{
		return ew;
	}

	/**
	 * Set`s the Cardinal Direction for east or west 
	 */
	public synchronized static void setEW(CardinalDirections ew)
	{
		if (ew == NORTH || ew == SOUTH)
			throw new IllegalArgumentException();
		
		if (GPSData.ew != ew)
			GPSData.ew = ew;
	}
	
	/**
	 * Get`s the Cardinal Direction for north or south back
	 * @return Enum with the cardinal direction north or south 
	 */
	public synchronized static CardinalDirections getNS()
	{
		return ns;
	}
	
	/**
	 * Set`s the Cardinal Direction for north or south 
	 */
	public synchronized static void setNS(CardinalDirections ns)
	{
		if (ns == EAST || ns == WEST)
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
		if (!GPSData.velocity.equals(velocity))
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
		if (!GPSData.altitude.equals(altitude))
			GPSData.altitude = altitude;
	}
	
	/**
	 * Get`s the Course back
	 * @return the Course in degree (gon or radial)
	 */
	public synchronized static int getCourse()
	{
		return course;
	}
	
	/**
	 * Set`s the Course in degree (gon or radial)
	 */
	public synchronized static void setCourse(int course)
	{
		if (GPSData.course != course)
			GPSData.course = course;
	}
	
	/**
	 * Get`s the amount of satellites back
	 * @return amount of satellites as string
	 */
	public synchronized static String getSatellites()
	{
		return satellites;
	}
	
	/**
	 * Set`s the amount of satellites
	 */
	public synchronized static void setSatellites(String  satellites)
	{
		int tmp = Integer.parseInt(satellites);
				
		if(tmp > MAX_AVAILABLE_SATELLITES || tmp < 0)
			throw new IllegalArgumentException();
		
		if (!GPSData.satellites.equals(satellites))
			GPSData.satellites = satellites;
	}
	
	/**
	 * Get`s the Quality back
	 * @return quality as String
	 */
	public synchronized static int getQuality()
	{
		return quality;
	}

	/**
	 * Set`s the quality
	 * @param quality quality as integer
	 */
	public synchronized static void setQuality(int quality)
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
		if (!GPSData.pdop.equals(pdop))
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
		if (!GPSData.hdop.equals(hdop))
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
		if (!GPSData.vdop.equals(vdop))
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
	public static synchronized void reinitialize()
	{
		setStatus(Status.A);
		setLatitude("53.557085");
		setLongitude("10.023167");
		setNS(SOUTH);
		setEW(EAST);
		setVelocity("003.0");
		setAltitude("15");
		setCourse(314);
		setSatellites("4");
		setQuality(8);
		setHDOP("2.0");
		setVDOP("2.4");
		setPDOP("2.8");
		setMode(SIMULATION);
		setFixType(GPS_FIX_3D);
	}

	public static synchronized String printCurrentData(){
		return ("Altitude: " + getAltitude()) + NEW_LINE +
				"Course: " + getCourse() + NEW_LINE +
				"EW: " + getEW() + NEW_LINE +
				"FixType: " + getFixType() + NEW_LINE +
				"Latitude: " + getLatitude() + NEW_LINE +
				"Longitude: " + getLongitude() + NEW_LINE +
				"HDOP: " + getHDOP() + NEW_LINE +
				"VDOP: " + getVDOP() + NEW_LINE +
				"PDOP: " + getPDOP() + NEW_LINE +
				"Mode: " + getMode() + NEW_LINE +
				"Satellites: " + getSatellites() + NEW_LINE +
				"Status: " + getStatus() + NEW_LINE +
				"Velocity: " + getVelocity();
	}
}
