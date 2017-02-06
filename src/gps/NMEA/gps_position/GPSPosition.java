package gps.NMEA.gps_position;

import java.util.Objects;

/**
 * This Class is used to store the parsed
 * information
 * 
 * @author Benjamin Trapp
 */
public class GPSPosition
{
	private boolean fixed;
	private Double time;
	private Double latitude;
	private Double longitude;
	private Double quality;
	private Double direction;
	private Double altitude;
	private Double velocity;

	/**
	 * @return the time
	 */
	public Double getTime()
	{
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Double time)
	{
		assert time != null;
	    this.time = time;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude()
	{
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude)
	{
		assert latitude != null;
	    this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude()
	{
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude)
	{
		assert longitude != null;
	    this.longitude = longitude;
	}

	/**
	 * @return the fixed
	 */
	public boolean isFixed()
	{
		return fixed;
	}

	/**
	 * @param fixed the fixed to set
	 */
	public void setFixed(boolean fixed)
	{
		this.fixed = fixed;
	}

	/**
	 * @return the quality
	 */
	public Double getQuality()
	{
		return quality;
	}

	/**
	 * @param quality the quality to set
	 */
	public void setQuality(Double quality)
	{
		assert  quality != null;
	    this.quality = quality;
	}

	/**
	 * @return the direction
	 */
	public Double getDirection()
	{
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Double direction)
	{
		assert direction != null;
	    this.direction = direction;
	}

	/**
	 * @return the altitude
	 */
	public Double getAltitude()
	{
		return altitude;
	}

	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(Double altitude)
	{
		assert altitude != null;
	    this.altitude = altitude;
	}

	/**
	 * @return the velocity
	 */
	public Double getVelocity()
	{
		return velocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Double velocity)
	{
		this.velocity = velocity;
	}

	/**
	 * Function to print all the collected data of this class
	 */
	public String toString()
	{
		return String.format("Latitude: %.2f | Longitude: %.2f | Time: %.0f | Quality: %.4f | Direction: %.4f | Altitude: %.4f | velocity: %.4f",
						latitude, longitude, time, quality, direction, altitude, velocity);
	}
	
	/**
	 * Checks if a GPSPosition is equal to an other GPSPosition-Instance
	 * @param that object that shall be used for checking equality to this object
	 * @return true if the objects are equal else false
	 */
	boolean isEqual(GPSPosition that)
	{
		if(this.equals(that))
		{
			if(Objects.equals(this.altitude, that.altitude)
					&& Objects.equals(this.latitude, that.latitude)
					&& Objects.equals(this.longitude, that.longitude)
					&& Objects.equals(this.direction, that.direction)
					&& Objects.equals(this.quality, that.quality)
					&& Objects.equals(this.time, that.time)
					&& Objects.equals(this.velocity, that.velocity)
					&& this.fixed == that.fixed)
				return true;
		}
		return false;		
	}

	public boolean isBasicPositionEqual(GPSPosition that)
	{
		if(this.equals(that))
		{
			if(Objects.equals(this.altitude, that.altitude)
					&& Objects.equals(this.latitude, that.latitude)
					&& Objects.equals(this.longitude, that.longitude))
				return true;
		}
		return false;
	}
}
