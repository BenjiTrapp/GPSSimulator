/**
 * 
 */
package gps.NMEA.parser;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This Class is used to simulate the telemetrie-module. This class uses 
 * a FileWriter to log the parsed Info
 * 
 * @author Benjamin Trapp
 *
 */
public class TelemetrieDummy
{

	private static final String TELEMETRIE_FILE_NAME = "log/Telemetrie.log";
	private FileWriter fw;
	private NMEAParser nmeaParser;
	
	/**
	 * Default Constructor of the Telemetrie Dummy
	 */
	public TelemetrieDummy()
	{
		try
		{
			this.fw = new FileWriter(TELEMETRIE_FILE_NAME);
			this.nmeaParser = new NMEAParser();
		} catch (IOException e)
		{
			System.err.println("IOException @ creating FileWriter");
		}
	}
	
	/**
	 * Use this Constructor to use this class with an own file writer
	 * @param fw file writer that shall be used
	 */
	public TelemetrieDummy(FileWriter fw)
	{
		this.fw = fw;
		this.nmeaParser = new NMEAParser();
	}
	
	/**
	 * Function to write a string to the log file
	 * @param str String that shall be logged
	 */
	public void write2File(String str)
	{
		try
		{
			fw.write(nmeaParser.parse(str).toString() + "\n");
			fw.flush();
		} catch (IOException | InvalidChecksumException e)
		{
			System.err.println(e.getMessage() + " @ [" +str+ "] to the TelemetrieDummy logfile");
		}
	}
	
	/**
	 * Closes the FileWriter
	 */
	public void close()
	{
		try
		{
			fw.close();
		} catch (IOException e)
		{
			System.err.println(e.getCause() + "@ closing the writer and logfile");
		}
	}
}
