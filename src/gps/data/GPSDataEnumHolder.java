package gps.data;

/**
 * This class is used to keep ,all enumerations that are
 * needed for the work with the gps.data.GPSData Class, at one spot
 * @author Benjamin Trapp
 */
public class GPSDataEnumHolder
{
	/**
	 * The "typical" Cardinal Directions of a compass
	 * @author Benjamin Trapp
	 */
	public enum CardinalDirections
	{
		NORTH, EAST, SOUTH, WEST;

		@Override
		public String toString()
		{
			return this.name().substring(0, 1)
					+ this.name().substring(1).toLowerCase();
		}
	}
	
	/**
	 * GPS FixTypes
	 * 
	 * for more Info take a look at:
	 * http://msdn.microsoft.com/en-us/library/bb202070.aspx
	 * @author Benjamin Trapp
	 */
	public enum GPSFixTypes
	{
		GPS_FIX_UNKNOWN, GPS_FIX_2D, GPS_FIX_3D
	}
	
	/**
	 * Modes despite of the mode "FAULT_INJECTION" all modes
	 * are based on the gps.NMEA Protocol
	 * @author Benjamin Trapp
	 *
	 */
	public enum Modes
	{
		AUTONOMOUS, DIFFERENTIAL, ESTIMATED, NOT_VALID, SIMULATION, FAULT_INJECTION
	}

	/**
	 * Status A = Acvive
	 * Status V = Void
	 * @author Benjamin Trapp
	 *
	 */
	public enum Status
	{
			A, V
	}

	public enum Track {
		T, //track made good is relative to true north
		M, // track made good, relative to magnetic north
		N, //speed measured in knots
		K  // speed over ground measured in km/h
	}

	public enum TrackModes{
		A, // autonomous
		D, // differential
		E  // estimated
	}

	public enum SatelliteMode{
	    A, // autonomous
        M  // manual
    }
}
