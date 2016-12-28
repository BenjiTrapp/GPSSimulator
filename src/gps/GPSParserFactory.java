package gps;

import java.net.ServerSocket;

import javax.activity.InvalidActivityException;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import communication.StringReader;
import gps.NMEA.parser.NMEAParser;
import gps.NMEA.TelemetrieDummy.TelemetrieDummy;

/**
 * This Class is used to simplify the construction of a parser and 
 * all its single classes that are depending on it
 * @author Benjamin Trapp
 *
 */
public class GPSParserFactory
{
	private static final String LOG4J_PROPERTIES = "log4j.properties";
	private final static Logger logger = LoggerFactory.getLogger(GPSParserFactory.class);
	private NMEAParser nmeaParser;
	private StringReader comm;
	private ParserThread parserThread = null;
	private TelemetrieDummy teleDummy;
	private Thread runningInstance;
	
	/**
	 * Default Constructor that creates a new TelemetrieDummy 
	 * instance and configures log4j 
	 */
	public GPSParserFactory()
	{
		this.teleDummy = new TelemetrieDummy();
		PropertyConfigurator.configure(LOG4J_PROPERTIES);
        logger.info("Parser is up and running");
	}
	
	/**
	 * Constructor to use an external TelemetrieDummy class
	 * in this parser factory and configures log4j 
	 * @param teleDummy Instance of the TelemetrieDummy that 
	 * shall be used in this factory
	 */
	public GPSParserFactory(TelemetrieDummy teleDummy)
	{
		assert teleDummy != null;
		this.teleDummy = teleDummy;
		PropertyConfigurator.configure(LOG4J_PROPERTIES);
	}
	
	/**
	 * Builds a parser based on the default values
	 * (Localhost, Port 4711) 
	 */
	public void build()
	{
        new Thread(() -> {
            createCommunication();
            createParser();
            createParserThread().run();
        }).start();
	}
	
	/**
	 * Destroys the Parser and closes all Connections
	 */
	public void destroy()
	{
		this.parserThread.stop();
		comm.closeAllCom();
		logger.info("Parser successfully destroyed");
	}
	
	/**
	 * Creates a parser 
	 * @return Instance of the created parser
	 */
	private NMEAParser createParser()
	{
		if (nmeaParser == null)
			nmeaParser = new NMEAParser();

		return nmeaParser;
	}
	
	/**
	 * Creates a Parser Thread
	 * @return Instance of the created ParserThread
	 */
	public ParserThread createParserThread()
	{
		if(parserThread == null)
			parserThread = new ParserThread();
		
		return parserThread;
	}
	
	/**
	 * Creates the Communication for the Parserfactory based
	 * on default values (Localhost and Port 4711). 
	 * 
	 * Attention: This thread must be started manually, by 
	 * calling the run method! 
	 * @return Instance of the StringReader
	 */
	private StringReader createCommunication()
	{
		 comm = StringReader.getInstance();
		 return comm;
	}
	
	/**
	 * Creates a StringReader based on a given ServerSocket
	 * @param socket Socket that the StringReader shall use for communication
	 * @return instance of the created StringReader on success
	 * @throws InvalidActivityException If the StringReader couldn't be initialized this 
	 * 		   exception will be thrown 
	 */
	public StringReader createCommunication(ServerSocket socket) throws InvalidActivityException 
	{
         if(StringReader.initInstance(socket))
         {
        	 comm = StringReader.getInstance();
        	 return comm;
         }
         
         throw new InvalidActivityException();
	}
	
	/**
	 * This Worker-Thread handles the concrete parsing of 
	 * the received Strings and is implemented as inner class
	 * to avoid misuse 
	 *  
	 * @author Benjamin Trapp
	 *
	 */
	private class ParserThread implements Runnable
	{
		private boolean isRunning = true;
		
		/**
		 * Stops the parser Thread in a more gracefully way than interrupting it
		 */
		void stop()
		{
			this.isRunning = false;
		}
		
		@Override
		public void run()
		{
			while(this.isRunning) {
                try {
                    String str = comm.receive();
                    //Fixes the miskate when two sentences are printed in the same line
                    if (str.contains("$GPGGA") && str.contains("$GPRMC")) {
                        int index = str.indexOf("*") + 2;
                        String tmp1 = str.substring(0, index);
                        String tmp2 = str.substring(index, str.length());
                        System.err.println(tmp1 + " ### " + tmp2);
                        teleDummy.write2File(tmp1);
						teleDummy.write2File(tmp2);
						logger.info(tmp1);
						logger.info(tmp2);
                    } else if (str.contains("$GPGGA") || str.contains("GPRMC")) {
                        teleDummy.write2File(str);
                        logger.info(str);
                    } else {
                        throw new RuntimeException("NMEA Sentence couldn't be recognized");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Something went wrong, catched Exception cause: "
                            + e.getCause());
                    this.isRunning = false;
                    comm.closeAllCom();
                }
            }
		}
	}
}
