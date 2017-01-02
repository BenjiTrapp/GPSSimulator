package gps.generator.sentence_tasks;

import java.util.TimerTask;

import communication.StringWriter;
import gps.NMEA.sentences.GGASentence;

/**
 * This Class is determined for the use in Combination with 
 * a Timer. This class generates a GPGGA Sentence based
 * on the gps.data.GPSData that are manipulated by the DataGenTask
 * @author Benjamin Trapp
 */
public class GGATask extends TimerTask
{
	private GGASentence gga = new GGASentence();
	private StringWriter strWriter;
	
	/**
	 * CAUTION: Don't use this Constructor!
	 * This Class needs an instance of
	 * a StringWriter to obtain its full
	 * functionality  
	 */
	public GGATask()
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Generates a new TimerTask that sends 
	 * the generated GPGGA Sentence to the
	 * passed StringWriter Instance
	 * @param strWriter Instance of the
	 *  StringWriter that shall be used
	 * 
	 */
	public GGATask(StringWriter strWriter)
	{
		assert strWriter != null;
		this.strWriter = strWriter;
	}

	@Override
	public void run() {strWriter.send(gga.getSentence());}
}
