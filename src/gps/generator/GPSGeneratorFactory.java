package gps.generator;

import java.net.Socket;

import gps.generator.datagen_tasks.DataGenTask;
import gps.generator.datagen_tasks.DataGenTaskObjectHolderBuilder;
import gps.generator.GPSGenerator;

import static gps.generator.GPSGenEnumHolder.*;
import static gps.generator.GPSGenEnumHolder.Modes.*;
import static gps.generator.GPSGenEnumHolder.Patterns.*;

/**
 * This Class is used to simplify the construction of a gps.data.GPSGenerator and
 * all its single classes that are depending on it
 * @author Benjamin Trapp
 *
 */
public class GPSGeneratorFactory
{
	private DataGenTask dataTask = null ;
	private static GPSGenerator gen = null;
	private static final int PERIOD_OF_DATA_GENERATION = 500;
	
	/**
	 * Builds a gps.data.GPSGenerator with default values
	 * (Localhost, Port 4711) and two TimerTasks 
	 * to generate proper RMC and GGA Sentences
	 * @return returns true on success
	 */
	public boolean build()
	{
		createDataGenTask();
		createGPSGenerator();
		
		gen.generateRMCData();
		gen.generateGGAData();
        //gen.generateGSAData();
		
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
	public boolean build(DataGenTask dataTask, int period)
	{
		if(dataTask == null)
			throw new NullPointerException("Passed argument was null");
		createGPSGenerator(dataTask,period);
		
		gen.generateRMCData();
		gen.generateGGAData();
		//gen.generateGSAData();
		
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
	 */
	public void build(DataGenTask dataTask, int period, Socket socket)
	{
		if(dataTask == null || socket == null)
			throw new NullPointerException("Passed argument was null");

		new Thread(() -> {
            createGPSGenerator(dataTask,period, socket);
            gen.generateRMCData();
            gen.generateGGAData();
			//gen.generateGSAData();
        }).start();
	}

	/**
	 * Creates a basic DataGenTask/gets its instance if one
	 * is already existing
	 * @return Instance of the DataGenTask
	 */
	private DataGenTask createDataGenTask()
	{
		if(dataTask == null) {
            dataTask = DataGenTask.getInstance(new DataGenTaskObjectHolderBuilder().addMode(LATITUDE, ASCENDING)
                                                                                   .addMode(LONGITUDE, ASCENDING)
                                                                                   .addMode(ALTITUDE, RANDOM)
                                                                                   .addMode(VELOCITY, RANDOM)
                                                                                   .addMode(DOP, RANDOM)
                                                                                   .addAngleUnit(AngleUnits.GON)
                                                                                   .build());
        }

		return dataTask;
	}

	/**
	 * Creates a gps.data.GPSGenerator with default components
	 * @return instance of the gps.data.GPSGenerator
	 */
	private GPSGenerator createGPSGenerator()
	{
		if(gen == null)
			gen = new GPSGenerator(dataTask, PERIOD_OF_DATA_GENERATION);

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
	private GPSGenerator createGPSGenerator(DataGenTask dataTask, int period)
	{
		if(gen == null)
			gen = new GPSGenerator(dataTask, period);

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
	private GPSGenerator createGPSGenerator(DataGenTask dataTask, int period, Socket socket)
	{
		if(gen == null)
			gen = new GPSGenerator(dataTask, period, socket);

		return gen;
	}
}