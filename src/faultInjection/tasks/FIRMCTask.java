package faultInjection.tasks;

import java.util.TimerTask;

import faultInjection.communication_jammer.ComJammer;
import gps.NMEA.sentences.RMCSentence;

/**
 * This Class is determined for the use in Combination with a Timer and contains a
 * build-in perturbation function. This class generates a GPRMC Sentence based on 
 * the gps.data.GPSData that are manipulated by the DataGenTask
 * 
 * This Class is redefined and assumed
 * to separate the functionality from the virtual prototype
 * 
 * @author Benjamin Trapp
 */
public class FIRMCTask extends TimerTask
{
	private RMCSentence rmc = new RMCSentence();
	private ComJammer fiWriter;

	/**
	 * CAUTION: Don't use this Constructor! This Class needs an instance of a
	 * StringWriter to obtain its full functionality
	 */
	public FIRMCTask()
	{
		throw new UnsupportedOperationException("This class needs an instance of a StringWriter");
	}

	/**
	 * Generates a new TimerTask that sends the generated GPGGA Sentence to the
	 * passed StringWriter Instance
	 * 
	 * @param fiWriter
	 *            Instance of the StringWriter that shall be used
	 */
	public FIRMCTask(ComJammer fiWriter)
	{
		assert fiWriter != null;
		this.fiWriter = fiWriter;
	}

	@Override
	public void run()
	{
		fiWriter.send(rmc.getSentence());
	}
}
