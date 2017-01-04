import faultInjection.pertubation.perturbation_functions.PerturbationBuilder;
import faultInjection.pertubation.perturbation_functions.modes.PerturbationModes;
import faultInjection.pertubation.perturbation_functions.perturbation_strategies.DashedStrategy;
import faultInjection.pertubation.perturbation_functions.perturbation_strategies.StuckAtStrategy;

import java.io.IOException;
import java.net.Socket;

/**
 * This class is used to create and run a PerturbedGPSGenerator that
 * creates RMC and GGA Sentences Based on gps.NMEA 0183. Also this class
 * is used in combination with the perturbation factory to start a 
 * fault-injection experiment
 * 
 * Attention: Make sure that the Parser is running otherwise
 * this class will end in an Exception because the ServerSocket
 * is missing! 
 */
public class FaultInjectionEnvironmentStarter
{
	/**
	 * The Main to run a Fault-Injection experiment, specify
	 * the Test-Scenario by change the perturbation modes 
	 * @param args not used
	 */
	public static void main(String[] args)
	{
	    StuckAtStrategy stuckAtStrategy = new StuckAtStrategy();
	    stuckAtStrategy.setStuckedTime(1500);
	    /*
	        Ready to spread the chaos ?
	     */
		new PerturbationBuilder().addStrategy(new DashedStrategy())
                                 .addStrategy(stuckAtStrategy)
                                 .useRandomnessForConfiguration()
                                 .build();
	}
}
