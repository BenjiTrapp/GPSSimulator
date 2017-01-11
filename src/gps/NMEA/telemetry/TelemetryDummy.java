/**
 * 
 */
package gps.NMEA.telemetry;

import gps.NMEA.utils.InvalidChecksumException;
import gps.NMEA.parser.NMEAParser;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This Class is used to simulate the telemetry-module. This class uses
 * a FileWriter to log the parsed Info
 * 
 * @author Benjamin Trapp
 *
 */
public class TelemetryDummy
{

	private static final String TELEMETRY_FILE_NAME = "log/Telemetry.log";
	private FileWriter fw;
	private NMEAParser nmeaParser;

	public TelemetryDummy(FileWriter fileWriter, NMEAParser parser)
	{
		this.fw = fileWriter;
		this.nmeaParser = parser;
	}


	/**
	 * Default Constructor of the Telemetry Dummy
	 */
	public TelemetryDummy()
	{
		try
		{
			this.fw = new FileWriter(TELEMETRY_FILE_NAME);
			this.nmeaParser = new NMEAParser();
		} catch (IOException e)
		{
			System.err.println("IOException @ creating FileWriter");
		}
	}
	
	/**
	 * Function to write a string to the log file
	 * @param str String that shall be logged
	 */
	public synchronized void write2File(String str)
	{
		try
		{
            fw.write(nmeaParser.parse(str).toString() + "\n");
            fw.flush();
		} catch (InvalidChecksumException e) {
            System.err.println("InvalidChecksumException!");
        }catch (IOException ignored) {
		}finally {
			try {
				fw.close();
			} catch (IOException e) {
				System.err.println("IOException @ closing FileWriter");
			}
		}
	}
}
