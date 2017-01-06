package gps;

import java.net.ServerSocket;

import javax.activity.InvalidActivityException;

import gps.NMEA.sentences.NMEASentenceTypes;
import gps.NMEA.telemetry.TelemetryDummy;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import communication.StringReader;
import gps.NMEA.parser.NMEAParser;

/**
 * This Class is used to simplify the construction of a parser and
 * all its single classes that are depending on it
 *
 * @author Benjamin Trapp
 */
public class GPSParserFactory {
    private static final String LOG4J_PROPERTIES = "log4j.properties";
    private final static Logger logger = LoggerFactory.getLogger(GPSParserFactory.class);
    private NMEAParser nmeaParser;
    private StringReader comm;
    private ParserThread parserThread = null;
    private TelemetryDummy teleDummy;

    /**
     * Default Constructor that creates a new TelemetryDummy
     * instance and configures log4j
     */
    public GPSParserFactory() {
        this.teleDummy = new TelemetryDummy();
        PropertyConfigurator.configure(LOG4J_PROPERTIES);
        logger.info("############ Parser is up and running ############");
    }

    /**
     * Constructor to use an external TelemetryDummy class
     * in this parser factory and configures log4j
     * @param teleDummy Instance of the TelemetryDummy that
     * shall be used in this factory
     */
    public GPSParserFactory(TelemetryDummy teleDummy) {
        assert teleDummy != null;
        this.teleDummy = teleDummy;
        PropertyConfigurator.configure(LOG4J_PROPERTIES);
    }

    /**
     * Builds a parser based on the default values
     * (Localhost, Port 4711)
     */
    public void build() {
        new Thread(() -> {
            createCommunication();
            createParser();
            createParserThread().run();
        }).start();
    }

    /**
     * Destroys the Parser and closes all Connections
     */
    public void destroy() {
        this.parserThread.stop();
        comm.closeAllCom();
        logger.info("Parser successfully destroyed");
    }

    /**
     * Creates a parser
     * @return Instance of the created parser
     */
    private NMEAParser createParser() {
        if (nmeaParser == null)
            nmeaParser = new NMEAParser();

        return nmeaParser;
    }

    /**
     * Creates a Parser Thread
     * @return Instance of the created ParserThread
     */
    public ParserThread createParserThread() {
        if (parserThread == null)
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
	private StringReader createCommunication() {
		 comm = StringReader.getInstance();
		 return comm;
	}

    /**
	 * This Worker-Thread handles the concrete parsing of 
	 * the received Strings and is implemented as inner class
	 * to avoid misuse 
	 *  
	 * @author Benjamin Trapp
	 *
	 */
	private class ParserThread implements Runnable {
		private boolean isRunning = true;
		
		/**
		 * Stops the parser Thread in a more gracefully way than interrupting it
         */
        void stop() {
            this.isRunning = false;
        }

        @Override
        public void run() {
            while (this.isRunning) {
                try {
                    String str = comm.receive();
                    if (NMEASentenceTypes.containsValidType(str)) {
                        teleDummy.write2File(str);
                        logger.info(str);
                    } else {
                        //TODO: To fix the malformed sentence ==> Add a strategy here
                        System.err.println("NMEA Sentence (" + str + ") malformed! " +
                                "Implement a strategy to fix or harden this weakness.");
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
