package gps.NMEA.exceptions;

/**
 *	This Exception occurs only in case of an passed invalid checksum
 * @author Benjamin Trapp
 */
public class InvalidChecksumException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -353170596722186054L;

	/**
	 * Throws an Exception if the Checksum is invalid
	 */
	public InvalidChecksumException()
	{
		super("The passed Checksum is invalid");
	}
	
	/**
	 *  Throws an Exception if the Checksum is invalid and 
	 *  prints the message passed by the user to gain more
	 *  info about the error that occurred 
	 * @param s Message with more details about the error that
	 * occurred 
	 */
	public InvalidChecksumException(String s)
	{
		super(s);
	}
}
