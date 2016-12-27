package faultInjection.pertubation;

import java.net.Socket;

import gps.generator.DataGenTask;
import faultInjection.FIGPSGenerator;

/**
 * This class is used to create a Fault-Injection experiment
 * by using a specific  perturbation mode
 * 
 * @author Benjamin Trapp
 */
public class PertubationFactory
{
	private DataGenTask dataTask = null ;
	private static FIGPSGenerator gen = null;
	private final static int PERIOD_OF_DATA_GENERATION = 500;
	
	/**
	 * Builds a gps.data.GPSGenerator with default values
	 * (Localhost, Port 4711) and two TimerTasks 
	 * to generate proper RMC and GGA Sentences
	 * @return returns true on success
	 */
	public boolean build(EPertubationModes mode)
	{
		if(mode == null)
			throw new NullPointerException("Passed argument was null");
		
		createDataGenTask();
		createFIGPSGenerator(mode);
		
		gen.generateFIRMCData();
		gen.generateFIGGAData();
		
		return true;
	}
	
	/**
	 * Builds a gps.data.GPSGenerator with a specific DataGenTask to
	 * generate other Data despite of the "basic" Task. The
	 * PERIOD_OF_DATA_GENERATION is used to specify the generation interval of
	 * the generated data. This generates uses Localhost and Port 4711
	 * for communication and also creates two TimerTasks to
	 *  generate proper RMC and GGA Sentences
	 * @param dataTask External task that generates the Basic gps.data.GPSData
	 * @param period interval in which the data shall be generated
	 * @return returns true on success
	 */
	public boolean build(DataGenTask dataTask, int period, EPertubationModes mode)
	{
		if(dataTask == null || mode == null)
			throw new NullPointerException("Passed argument was null");
		
		createFIGPSGenerator(dataTask,period, mode);
		
		gen.generateFIRMCData();
		gen.generateFIGGAData();
		
		return true;
	}

	/**
	 * Builds a gps.data.GPSGenerator with a specific DataGenTask to
	 * generate other Data despite of the "basic" Task. The
	 * PERIOD_OF_DATA_GENERATION is used to specify the generation interval of
	 * the generated data. This generates uses the IP and Port
	 * for communication that are passed through the Socket Instance, 
	 * and this class also creates two TimerTasks to generate proper RMC 
	 * and GGA Sentences
	 * @param dataTask External task that generates the Basic gps.data.GPSData
	 * @param period interval in which the data shall be generated
	 * @param socket The socket that shall be used for communication
	 * @return returns true on success
	 */
	public boolean build(DataGenTask dataTask, int period, Socket socket, EPertubationModes mode)
	{
		if(dataTask == null || mode == null || socket == null)
			throw new NullPointerException("Passed argument was null");
		
		createFIGPSGenerator(dataTask,period, socket, mode);
		
		gen.generateFIRMCData();
		gen.generateFIGGAData();
		
		return true;
	}
	
	/**
	 * Creates a basic DataGenTask/gets its instance if one
	 * is already existing 
	 * @return Instance of the DataGenTask
	 */
	private DataGenTask createDataGenTask()
	{
		if(dataTask == null)
			dataTask = DataGenTask.getInstance();
		
		return dataTask;
	}
	
	/**
	 * Creates a gps.data.GPSGenerator with default components
	 * @return instance of the gps.data.GPSGenerator
	 */
	private FIGPSGenerator createFIGPSGenerator(EPertubationModes mode)
	{
		if(gen == null)
			gen = new FIGPSGenerator(dataTask, PERIOD_OF_DATA_GENERATION, mode);

		return gen;
	}
	
	/**
	 * Creates a gps.data.GPSGenerator with the passed DataGenTask and PERIOD_OF_DATA_GENERATION in
	 * which the DataGenTask generates its output data. The created 
	 * gps.data.GPSGenerator will use the local host and Port 4711 for communication
	 * @param dataTask Instance of a DataGenTask to generate Data 
	 * @param period the PERIOD_OF_DATA_GENERATION in which the DataGenTask generates its Data
	 * @return instance of the created gps.data.GPSGenerator
	 */
	private FIGPSGenerator createFIGPSGenerator(DataGenTask dataTask, int period, EPertubationModes mode)
	{
		if(gen == null)
			gen = new FIGPSGenerator(dataTask, period, mode);
		
		return gen;
	}
	
	/**
	 * Creates a gps.data.GPSGenerator with the passed DataGenTask and PERIOD_OF_DATA_GENERATION in
	 * which the DataGenTask generates its output data. The created 
	 * gps.data.GPSGenerator will use the the passed socket (that contains IP and Port)
	 * for the communication
	 * @param dataTask Instance of a DataGenTask to generate Data 
	 * @param socket The Socket that shall be used for communication
	 * @param period the PERIOD_OF_DATA_GENERATION in which the DataGenTask generates its Data
	 * @return instance of the created gps.data.GPSGenerator
	 */
	private FIGPSGenerator createFIGPSGenerator(DataGenTask dataTask, int period, Socket socket, EPertubationModes mode)
	{
		if(gen == null)
			gen = new FIGPSGenerator(dataTask, period, socket, mode);
		
		return gen;
	}
}