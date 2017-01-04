package faultInjection.pertubation.generator.tasks;

import java.util.TimerTask;

import faultInjection.communication_jammer.ComJammer;
import faultInjection.pertubation.perturbation_functions.perturbation_strategies.PerturbationStrategy;
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
	private ComJammer comJammer;
	private PerturbationStrategy strategy;

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
	 * @param comJammer
	 *            Instance of the StringWriter that shall be used
	 */
	public FIRMCTask(ComJammer comJammer) {
		assert comJammer != null;
		assert strategy != null;

		this.comJammer = comJammer;
	}

	@Override
	public void run() {comJammer.send(rmc.getSentence());}
}
