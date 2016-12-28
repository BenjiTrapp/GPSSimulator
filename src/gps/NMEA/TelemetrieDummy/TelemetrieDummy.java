/**
 * 
 */
package gps.NMEA.TelemetrieDummy;

import gps.NMEA.parser.InvalidChecksumException;
import gps.NMEA.parser.NMEAParser;

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
        }catch (IOException ignored)
		{
		}finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
