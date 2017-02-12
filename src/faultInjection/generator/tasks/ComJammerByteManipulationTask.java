package faultInjection.generator.tasks;

import java.util.TimerTask;

import communication.communication_jammer.ComJammer;
import faultInjection.ByteManipulationCascade;
import gps.NMEA.sentences.GGASentence;

/**
 * This Class is determined for the use in Combination with 
 * a Timer  and contains a build-in perturbation function. This class generates 
 * a NMEA Sentence based on the gps.data.GPSData that are manipulated by the DataGenTask
 * 
 * This Class is redefined and assumed
 * to separate the functionality from the virtual prototype
 * 
 * @author Benjamin Trapp
 */
public class ComJammerByteManipulationTask extends TimerTask
{
	private ComJammer comJammerInstance;
	private ByteManipulationCascade byteManipulationCascade;

	/**
	 * CAUTION: Don't use this Constructor!
	 * This Class needs an instance of
	 * a StringWriter to obtain its full
	 * functionality  
	 */
	public ComJammerByteManipulationTask()
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Generates a new TimerTask that sends 
	 * the perturbed Sentence to the
	 * passed StringWriter Instance
	 * @param comJammerInstance Instance of the
	 *  StringWriter that shall be used
	 * 
	 */
	public ComJammerByteManipulationTask(ByteManipulationCascade byteManipulationCascade, ComJammer comJammerInstance) {
		assert comJammerInstance != null;
		assert byteManipulationCascade != null;

		this.byteManipulationCascade = byteManipulationCascade;
		this.comJammerInstance = comJammerInstance;
	}

	@Override
	public void run() {
		this.byteManipulationCascade.sendByteManipulations(this.comJammerInstance);
		this.byteManipulationCascade.sendByteManipulations(this.comJammerInstance);
	}
}
