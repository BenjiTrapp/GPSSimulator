package gps.generator;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.TimerTask;

import gps.data.GPSDataEnumHolder;
import gps.data.GPSData;
import gps.generator.GPSGenEnumHolder.AngleUnits;
import gps.generator.GPSGenEnumHolder.Modes;
import gps.generator.GPSGenEnumHolder.Patterns;

/**
 * This Class is used for the GPS-Data Generation and is implemented as 
 * Singleton to avoid multiple manipulation of the GPS-Data. Use this 
 * Class in combination with a Timer. Also there are plenty getter and
 * setter for all important things like changing the generation mode of
 * the data or angle unit
 *  
 * @author Benjamin Trapp
 */
public class DataGenTask extends TimerTask
{
	private Random rnd = new Random();
	private DecimalFormat format = new DecimalFormat();
	private Modes latitudeMode, longitudeMode,altitudeMode,velocityMode,dopsMode = null;
	private AngleUnits angleUnit = AngleUnits.GON;
	private boolean isInvalid = false;
	private static DataGenTask instance = null;
	
	/**
	 * This Constructor is private, coming due through the
	 * Singleton-Pattern
	 */
	private DataGenTask()
	{
		latitudeMode = Modes.ASCENDING;
	    longitudeMode = Modes.ASCENDING;
	    altitudeMode = Modes.RANDOM;
	    angleUnit = AngleUnits.GON;
	    velocityMode = Modes.RANDOM;
	    dopsMode = Modes.RANDOM;
	}
	
	/**
	 * Get`s an Instance of the DataGenTask back (Singleton)
	 * @return Returns a Instance of the DataGenTask
	 */
	public static DataGenTask getInstance()
	{
		return instance == null? instance = new DataGenTask():instance;
	}
	
	@Override
	public void run()
	{
		generateRandomData();
	}
	
	/**
	 * Function to toggle between the two statuses A(active) and V(void)
	 */
	public void toggleStatus()
	{
		GPSDataEnumHolder.Status tmp = GPSData.getStatus();
		if (tmp == GPSDataEnumHolder.Status.A)
			{tmp = GPSDataEnumHolder.Status.V; System.out.println("Toggled the status to Void");}
		else
			{tmp = GPSDataEnumHolder.Status.A; System.out.println("Toggled the status to Active");}
	}
	
	/**
	 * Function to set the Status (V=Void, A=Active)
	 * @param status Enumeration of the type Status 
	 */
	public void setStatus(GPSDataEnumHolder.Status status)
	{
		GPSData.setStatus(status);
	}
	
	/**
	 * Get`s the Status back
	 * @return Status of the gps.data.GPSData (V=Void, A=Active)
	 */
	public GPSDataEnumHolder.Status getStatus()
	{
		return GPSData.getStatus();
	}
	
	/**
	 * Gets the current Mode for the Latitude back 
	 * @return Mode Returns the Mode(ASCENDING,DESCENDING, RANDOM) 
	 *         for the latitude
	 */
	public GPSGenEnumHolder.Modes getLatitude()
	{
		return latitudeMode;
	}
	/**
	 * Sets the current Mode for the Latitude 
	 * @param latitudeMode the Mode (ASCENDING,DESCENDING, RANDOM) that will be set for the latitude 
	 */
	public void setLatitude(GPSGenEnumHolder.Modes latitudeMode)
	{
		this.latitudeMode = latitudeMode;
	}

	/**
	 * Gets the current Mode for the Longitude back 
	 * @return Mode Returns the Mode(ASCENDING,DESCENDING, RANDOM) 
	 *         for the longitude
	 */
	public GPSGenEnumHolder.Modes getLongitude()
	{
		return longitudeMode;
	}
	
	/**
	 * Sets the current Mode for the Longitude
	 * @param longitudeMode the Mode(ASCENDING,DESCENDING, RANDOM) that will be set for the longitude
	 */
	public void setLongitude(GPSGenEnumHolder.Modes longitudeMode)
	{
		this.longitudeMode = longitudeMode;
	}
	
	/**
	 * Gets the current Mode for the altitude back 
	 * @return Mode Returns the Mode(ASCENDING,DESCENDING, RANDOM) 
	 *         for the altitude
	 */
	public GPSGenEnumHolder.Modes getAltitude()
	{
		return altitudeMode;
	}

	/**
	 * Sets the current Mode for the Altitude 
	 * @param The mode (ASCENDING,DESCENDING, RANDOM) that will be set for the altitude
	 */
	public void setAltitude(GPSGenEnumHolder.Modes altitudeMode)
	{
		this.altitudeMode = altitudeMode;
	}
	/**
	 * Gets the current Mode for the Velocity back 
	 * @return Mode Returns the Mode(ASCENDING,DESCENDING, RANDOM) 
	 *         for the Velocity
	 */
	public GPSGenEnumHolder.Modes getVelocity()
	{
		return velocityMode;
	}
	
	/**
	 * Sets the current Mode for the Velocity 
	 *@param The mode (ASCENDING,DESCENDING, RANDOM) that will be set for the velocity
	 */
	public void setVelocity(GPSGenEnumHolder.Modes velocityMode)
	{
		this.velocityMode = velocityMode;
	}
	
	/**
	 * Gets the current Mode for all three Dilutions of Point back 
	 * @return Mode Returns the Mode(ASCENDING,DESCENDING, RANDOM) 
	 *         for the three Dilutions of Point
	 */
	public GPSGenEnumHolder.Modes getDops()
	{
		return dopsMode;
	}

	/**
	 * Sets the current Mode for all three Dilutions of Point back 
	 * @param The mode (ASCENDING,DESCENDING, RANDOM) that will be set for the 
	 * three Dilutions of Point 
	 */
	public void setDops(GPSGenEnumHolder.Modes dopsMode)
	{
		this.dopsMode = dopsMode;
	}
	
	/**
	 * Gets the currently used Angle Unit Back
	 * @return Enumeration of the supported AngleUnits (RADIAL, GON)
	 */
	public AngleUnits getAngleUnit()
	{
		return angleUnit;
	}
	
	/**
	 * Sets a Angle Unit that shall be used 
	 * @param angleUnit Enumeration of the supported AngleUnits (RADIAL, GON)
	 */
	public void setAngleUnit(AngleUnits angleUnit)
	{
		this.angleUnit = angleUnit;
	}

	/**
	 * Generates random GPS Data for more details take a look at
	 * http://www.gpsinformation.org/dale/nmea.htm
	 */
	private void generateRandomData()
	{
		generateRandomLatitude(latitudeMode);
		generateRandomLongitude(longitudeMode);
		generateRandomSatellites();
		generateRandomAltitude(altitudeMode);
		generateRandomCardinalDirections();
		generateRandomCourse(angleUnit);
		generateRandomVelocity(velocityMode);
		generateRandomDOPs(dopsMode);
	}
	
	/**
	 * Generates random values for the latitude depending on the passed mode
	 * @param mode Mode for the generation of the gps.data.GPSData (ASCNECING,DESCENDING,RANDOM)
	 */
	private void generateRandomLatitude(GPSGenEnumHolder.Modes mode)
	{
		GPSData.setLatitude(randomDoubleDigit(GPSData.getLatitude(), mode, Patterns.LATITUDE));
	}
	
	/**
	 * Generates random values for the longitude depending on the passed mode
	 * @param mode Mode for the generation of the gps.data.GPSData (ASCNECING,DESCENDING,RANDOM)
	 */
	private void generateRandomLongitude(GPSGenEnumHolder.Modes mode)
	{
		GPSData.setLongitude(randomDoubleDigit(GPSData.getLongitude(),mode, Patterns.LONGITUDE));
	}
	
	/**
	 * Generates random values for the velocity depending on the passed mode
	 * @param mode Mode for the generation of the gps.data.GPSData (ASCNECING,DESCENDING,RANDOM)
	 */
	private void generateRandomVelocity(GPSGenEnumHolder.Modes mode)
	{
		GPSData.setVelocity(randomDoubleDigit(GPSData.getLongitude(),mode, Patterns.VELOCITY));
	}
	
	/**
	 * Generates random values for the altitude depending on the passed mode
	 * @param mode Mode for the generation of the gps.data.GPSData (ASCNECING,DESCENDING,RANDOM)
	 */
	private void generateRandomAltitude(GPSGenEnumHolder.Modes mode)
	{
		GPSData.setAltitude(randomDoubleDigit(GPSData.getAltitude(),mode, Patterns.ALTITUDE));
	}
	
	/**
	 * Generates random values for the three Dilutions of Point depending on the passed mode
	 * for more details: http://de.wikipedia.org/wiki/Dilution_of_Precision
	 * @param mode Mode for the generation of the gps.data.GPSData (ASCNECING,DESCENDING,RANDOM)
	 */
	private void generateRandomDOPs(GPSGenEnumHolder.Modes mode)
	{
		GPSData.setVDOP(randomDoubleDigit(GPSData.getVDOP(),mode, Patterns.DOP));
		GPSData.setHDOP(randomDoubleDigit(GPSData.getHDOP(),mode, Patterns.DOP));
		GPSData.setPDOP(randomDoubleDigit(GPSData.getPDOP(),mode, Patterns.DOP));
	}
	
	/**
	 * Generates a random amount of "currently" available satellites (0 to 12)
	 */
	private void generateRandomSatellites()
	{
		int tmp = rnd.nextInt(12);
		StringBuilder s = new StringBuilder();
		
		if(tmp < 10)
			s.append("0" + tmp);
		else 
			s.append(tmp);
		
		if(tmp < 3)
			GPSData.setFixType(GPSDataEnumHolder.GPSFixTypes.GPS_FIX_UNKNOWN);
		
		if(tmp == 3)
			GPSData.setFixType(GPSDataEnumHolder.GPSFixTypes.GPS_FIX_2D);

		if(tmp >= 4)
			GPSData.setFixType(GPSDataEnumHolder.GPSFixTypes.GPS_FIX_3D);
		
		GPSData.setSatellites(s.toString());
	}
	
	/**
	 * Generates based on a randomly calculated digit a random 
	 * cardinal direction
	 */
	private void generateRandomCardinalDirections()
	{
		int tmp = rnd.nextInt(100);
		
		if(tmp >= 0  && tmp < 25)GPSData.setNS(GPSDataEnumHolder.CardinalDirections.NORTH);
		if(tmp >= 25 && tmp < 50)GPSData.setEW(GPSDataEnumHolder.CardinalDirections.EAST);
		if(tmp >= 50 && tmp < 75)GPSData.setNS(GPSDataEnumHolder.CardinalDirections.SOUTH);
	    if(tmp >= 75 && tmp < 100)GPSData.setEW(GPSDataEnumHolder.CardinalDirections.WEST);
	}
	
	/**
	 * Generates a random Course change" of the UAV
	 * @param angleUnit The Angle units can be changed to radial or gon
	 */
	private void generateRandomCourse(AngleUnits angleUnit)
	{
		if(angleUnit == AngleUnits.RADIAL)
		{
			if(GPSData.getNS() == GPSDataEnumHolder.CardinalDirections.NORTH  && GPSData.getEW() == GPSDataEnumHolder.CardinalDirections.EAST)
				GPSData.setCourse(rnd.nextInt(90));
			if(GPSData.getNS() == GPSDataEnumHolder.CardinalDirections.SOUTH  && GPSData.getEW() == GPSDataEnumHolder.CardinalDirections.EAST)
				GPSData.setCourse(rnd.nextInt(90) + 90);
			if(GPSData.getNS() == GPSDataEnumHolder.CardinalDirections.SOUTH  && GPSData.getEW() == GPSDataEnumHolder.CardinalDirections.WEST)
				GPSData.setCourse(rnd.nextInt(90) + 180);
			if(GPSData.getNS() == GPSDataEnumHolder.CardinalDirections.NORTH  && GPSData.getEW() == GPSDataEnumHolder.CardinalDirections.WEST)
				GPSData.setCourse(rnd.nextInt(90) + 270 );
		}else
		{
			if(GPSData.getNS() == GPSDataEnumHolder.CardinalDirections.NORTH  && GPSData.getEW() == GPSDataEnumHolder.CardinalDirections.EAST)
				GPSData.setCourse(rnd.nextInt(100));
			if(GPSData.getNS() == GPSDataEnumHolder.CardinalDirections.SOUTH  && GPSData.getEW() == GPSDataEnumHolder.CardinalDirections.EAST)
				GPSData.setCourse(rnd.nextInt(100) + 100);
			if(GPSData.getNS() == GPSDataEnumHolder.CardinalDirections.SOUTH  && GPSData.getEW() == GPSDataEnumHolder.CardinalDirections.WEST)
				GPSData.setCourse(rnd.nextInt(100) + 200);
			if(GPSData.getNS() == GPSDataEnumHolder.CardinalDirections.NORTH  && GPSData.getEW() == GPSDataEnumHolder.CardinalDirections.WEST)
				GPSData.setCourse(rnd.nextInt(100) + 300 );
		}
	}
	
	/**
	 * Generates a new random double digit depending on a passed String. The mode can be used to generate different types of
	 * Random Digits. The pattern is used to set the format pattern for specific data types
	 * @param digit Old value that is used. The random digit will be combined with this value
	 * @param mode the mode specifies the way how the random digit will be generated
	 * @param pattern needed to format the return value for secific data types 
	 * @return a random generated double digit based on a passed digit and mode
	 */
	private String randomDoubleDigit(String digit, Modes mode, Patterns pattern)
	{
		double var = Double.parseDouble(digit);
		double tmp = -1.0;
		
		if(pattern == Patterns.LATITUDE)
		{
			tmp = (rnd.nextInt(25) * 0.0001);
			format.applyPattern("0000.0000");
		}
		if(pattern == Patterns.LONGITUDE)
		{
			tmp = (rnd.nextInt(25) * 0.0001);
			format.applyPattern("00000.0000");
		}
		
		if(pattern == Patterns.VELOCITY)
		{
			tmp = (rnd.nextInt(10) * 0.01);
			format.applyPattern("000.0");
		}
		
		if(pattern == Patterns.ALTITUDE)
		{
			tmp = rnd.nextInt(25) * 0.01;
			format.applyPattern("0000.0");
		}
		
		if(pattern == Patterns.ALTITUDE)
		{
			tmp = rnd.nextInt(25) * 0.01;
			format.applyPattern("###0.0");
		}
		
		if(pattern == Patterns.DOP)
		{
			tmp = rnd.nextInt(15) * 0.01;
			format.applyPattern("0.0");
		}
		
		switch(mode)
		{
			case ASCENDING: var += tmp;
							break;
			
			case DESCENDING: var -= tmp;
							break;
			
			case RANDOM: 	if(((int)(Math.random() * 10)) % 2 == 0)
								var += tmp; 
							else
								var -= tmp;
			                break;
									
			default:		System.err.println("INVALID MODE");	
							break;
		}
		
		//Avoid negative numbers
		if(var <= 0.5 && !isInvalid)
			var  = 0.5 + (rnd.nextInt(9) * 0.1);
			
		return format.format(var).replace(",", ".");
	}
}
