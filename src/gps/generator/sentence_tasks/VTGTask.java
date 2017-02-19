package gps.generator.sentence_tasks;

import communication.StringWriter;
import gps.NMEA.sentences.GSASentence;
import gps.NMEA.sentences.NMEASentence;
import gps.NMEA.sentences.VTGSentence;
import org.jetbrains.annotations.NotNull;

import java.util.TimerTask;

/**
 * This Class is determined for the use in Combination with 
 * a Timer. This class generates a GPVTG Sentence based
 * on the gps.data.GPSData that are manipulated by the DataGenTask
 * @author Benjamin Trapp
 */
public class VTGTask extends TimerTask
{
	private NMEASentence vtg = new VTGSentence();
	private StringWriter strWriter;

	/**
	 * CAUTION: Don't use this Constructor!
	 * This Class needs an instance of
	 * a StringWriter to obtain its full
	 * functionality
	 */
	public VTGTask()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Generates a new TimerTask that sends
	 * the generated GPVTG Sentence to the
	 * passed StringWriter Instance
	 * @param strWriter Instance of the
	 *  StringWriter that shall be used
	 *
	 */
	public VTGTask(@NotNull StringWriter strWriter)
	{
		this.strWriter = strWriter;
	}

	@Override
	public void run() {strWriter.send(vtg.getSentence());}
}
