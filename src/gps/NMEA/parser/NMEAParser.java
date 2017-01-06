package gps.NMEA.parser;

import java.util.*;
import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.gps_position.GPSPositionHistory;
import gps.NMEA.parser.sentences.GPGGAParser;
import gps.NMEA.parser.sentences.GPRMCParser;
import gps.NMEA.parser.sentences.NMEASentenceParser;
import gps.NMEA.sentences.NMEASentenceTypes;
import gps.NMEA.utils.InvalidChecksumException;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gps.NMEA.utils.ChecksumUtilities;

/**
 * This Class is the main class of the NMEA Parser to combine the
 * sub-parsers. Also this class is used by the NMEAParserFactory
 * 
 * @author Benjamin Trapp
 */
public class NMEAParser
{
    private static final Map<NMEASentenceTypes, NMEASentenceParser> sentenceParsers = new HashMap<>();
	private static final String LOG4J_PROPERTIES = "log4j.properties";
    private static final String SPLIT_DELIMITER = ",";
    private final static Logger logger = LoggerFactory.getLogger(NMEAParser.class);
    private static final int AMOUNT_HISTORIC_POS = 3;
    private final List<GPSPosition> gpsPositions;
    private GPSPositionHistory historicPosition;

	public NMEAParser()
	{
		PropertyConfigurator.configure(LOG4J_PROPERTIES);
		gpsPositions = Collections.synchronizedList(new ArrayList<>());
		sentenceParsers.put(NMEASentenceTypes.GPGGA, GPGGAParser.getInstance());
		sentenceParsers.put(NMEASentenceTypes.GPRMC, GPRMCParser.getInstance());
    }

	/**
	 * Parses a passed NMEA-Sentence
	 * @param nmeaSentence NMEA-Sentence that shall be parsed
	 * @return GPSPosition that contains the parsed info
	 * @throws InvalidChecksumException in case of a malformed gps.NMEA-Sentence
	 */
	public GPSPosition parse(String nmeaSentence) throws InvalidChecksumException {
		GPSPosition currentPosition;
        NMEASentenceTypes type;
		String[]nmeaWords;

		// Make sure everything  was correctly transmitted by validating the Checksum
        validateChecksum(nmeaSentence);

        nmeaWords = splitSentenceIntoWords(nmeaSentence);
        type = retrieveSentenceType(nmeaWords);

        //TODO: Restliche relevante GPS Koordinaten aufnehmen in GPS Pos Objekt
        currentPosition = sentenceParsers.get(type).parse(nmeaWords);
        gpsPositions.add(currentPosition);

        if (gpsPositions.size() > AMOUNT_HISTORIC_POS) {historicPosition = createHistoricGPSPositionSnapshot(currentPosition, type);}

		return currentPosition;
	}

    private NMEASentenceTypes retrieveSentenceType(String[] nmeaWords) {
        return validateNMEASentenceType(NMEASentenceTypes.getType(nmeaWords[0]));
    }

    private String[] splitSentenceIntoWords(String nmeaSentence) {
        return nmeaSentence.substring(1).split(SPLIT_DELIMITER);
    }

    private NMEASentenceTypes validateNMEASentenceType(NMEASentenceTypes type) {
        //This is the "main" Protection against the implemented faults
        if (!NMEASentenceTypes.isValidType(type.toString())) {
            logger.error("Type of the NMEA-Sentence is unknown or malformed");
            throw new RuntimeException();
        }

        return type;
    }

    private void validateChecksum(String line) throws InvalidChecksumException {
        if (!ChecksumUtilities.isChecksumValid(line)) {
            logger.error("NMEA-Sentence malformed (" + line + ")");
            throw new InvalidChecksumException();
        }
    }

    private GPSPositionHistory createHistoricGPSPositionSnapshot(GPSPosition currentPosition, NMEASentenceTypes type) {
        int size = gpsPositions.size();

	    GPSPositionHistory tmp = new GPSPositionHistory(type);

        tmp.addPositions(currentPosition
                        ,gpsPositions.get(size - 1)
                        ,gpsPositions.get(size - 2)
                        ,gpsPositions.get(size - 3));
        return tmp;
    }
}
