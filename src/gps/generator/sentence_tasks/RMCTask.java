package gps.generator.sentence_tasks;

import communication.StringWriter;
import gps.NMEA.sentences.RMCSentence;

import java.util.TimerTask;

/**
 * This Class is determined for the use in Combination with a Timer. This class
 * generates a GPRMC Sentence based on the gps.data.GPSData that are manipulated by the
 * DataGenTask
 * 
 * @author Benjamin Trapp
 */
public class RMCTask extends TimerTask
{
	private RMCSentence rmc = new RMCSentence();
	private StringWriter strWriter;

	/**
	 * CAUTION: Don't use this Constructor! This Class needs an instance of a
	 * StringWriter to obtain its full functionality
	 */
	public RMCTask() { throw new UnsupportedOperationException("This class needs an instance of a StringWriter"); }

	/**
	 * Generates a new TimerTask that sends the generated GPGGA Sentence to the
	 * passed StringWriter Instance
	 * 
	 * @param strWriter
	 *            Instance of the StringWriter that shall be used
	 */
	public RMCTask(StringWriter strWriter)
	{
		this.strWriter = strWriter;
	}

	@Override
	public void run() {strWriter.send(rmc.getSentence());}
}
