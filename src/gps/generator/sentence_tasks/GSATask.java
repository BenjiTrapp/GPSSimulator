package gps.generator.sentence_tasks;

import communication.StringWriter;
import gps.NMEA.sentences.GSASentence;
import org.jetbrains.annotations.NotNull;

import java.util.TimerTask;

/**
 * This Class is determined for the use in Combination with 
 * a Timer. This class generates a GPGSA Sentence based
 * on the gps.data.GPSData that are manipulated by the DataGenTask
 * @author Benjamin Trapp
 */
public class GSATask extends TimerTask
{
	private GSASentence gsa = new GSASentence();
	private StringWriter strWriter;

	/**
	 * CAUTION: Don't use this Constructor!
	 * This Class needs an instance of
	 * a StringWriter to obtain its full
	 * functionality
	 */
	public GSATask()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Generates a new TimerTask that sends
	 * the generated GPGSA Sentence to the
	 * passed StringWriter Instance
	 * @param strWriter Instance of the
	 *  StringWriter that shall be used
	 *
	 */
	public GSATask(@NotNull StringWriter strWriter)
	{
		this.strWriter = strWriter;
	}

	@Override
	public void run() {strWriter.send(gsa.getSentence());}
}
