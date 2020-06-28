package gps.NMEA.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.IntStream;

public final class ChecksumUtilities {
    private static final int ASTERISK_POSITION_FROM_CHECKSUM = 3;
    private static final Logger LOG = Logger.getLogger("ChecksumLogger");
    private static final String LOGGING_PATH = "log/Checksum.log";
    private static final int CHECKSUM_HEX_LENGTH = 2;
    private static final int IGNORE_FIRST_TOKEN = 1;
    private static final int CHECKSUM_COMPLETE_LENGTH = 3;
    private static final char CHECKSUM_ASTERISK_DELIMITER = '*';
    private static final String NMEA_SENTENCE_INITIALIZER = "$";

    static {
        try {
            FileHandler fh = new FileHandler(LOGGING_PATH);
            fh.setFormatter(new SimpleFormatter());
            LOG.addHandler(fh);
            LOG.setUseParentHandlers(false);
            LOG.setLevel(Level.FINE);
        } catch (SecurityException | IOException e) {
            System.err.println("Error during instantiation of the Checksum logger");
        }
    }

    /**
     * Calculates the checksum from a gps.NMEA Sentence and compares it
     * with the passed gps.NMEA Sentence.
     * <p>
     * Important: The passed gps.NMEA Sentence must contain a checksum
     * of the type *DD (D for a Hex digit). If the CRC has only one
     * Hex digit it must have a leading 0.
     *
     * @param nmeaSentence The gps.NMEA Sentence that shall be checked
     * @return true if the checksum is valid, otherwise false
     */
    public static synchronized boolean isChecksumValid(String nmeaSentence) {
        assert !nmeaSentence.isEmpty();
        assert nmeaSentence.contains(Character.toString(CHECKSUM_ASTERISK_DELIMITER));
        assert nmeaSentence.contains(NMEA_SENTENCE_INITIALIZER);

        int msglen = nmeaSentence.length();

        if (!nmeaSentence.startsWith(NMEA_SENTENCE_INITIALIZER)) {
            appendError("NMEA Sentence - Missing leading '$' (" + nmeaSentence + ") is INVALID");
            return false;
        }

        if (msglen != CHECKSUM_COMPLETE_LENGTH) {
            if (getChecksumDelimiterFromSentence(nmeaSentence) == CHECKSUM_ASTERISK_DELIMITER) {
                String chk_s;

                chk_s = getCRC(nmeaSentence.substring(0, msglen - CHECKSUM_COMPLETE_LENGTH));

                if(nmeaSentence.substring(msglen - CHECKSUM_HEX_LENGTH, msglen).equals(chk_s)){
                    LOG.info("Sentence: " + nmeaSentence + " is VALID");
                    return true;
                }else{
                    appendError("Validation failed -> the Checksum is INVALID");
                    return false;
                }
            } else {
                appendError("Sentence has no valid checksum: Leading asterisk is missing  (" + nmeaSentence + " ) + is INVALID");
                return false;
            }
        }else{
            appendError("Sentence has no valid checksum: Checksum length != 3  (" + nmeaSentence + " ) + is INVALID");
            return false;
        }
    }

    private static char getChecksumDelimiterFromSentence(String nmeaSentence) {
        return nmeaSentence.charAt(nmeaSentence.length() - ASTERISK_POSITION_FROM_CHECKSUM);
    }

    /**
     * Creates the checksum from the passed gps.NMEA Sentence
     *
     * @param nmeaSentence String containing the full gps.NMEA message without checksum
     * @return String String that contains the calculated checksum
     */
    public static synchronized String getCRC(String nmeaSentence) {
        assert !nmeaSentence.isEmpty();
        assert nmeaSentence.startsWith(NMEA_SENTENCE_INITIALIZER);
        assert !nmeaSentence.contains(Character.toString(CHECKSUM_ASTERISK_DELIMITER));

        int checksum;

        checksum = IntStream.range(IGNORE_FIRST_TOKEN, nmeaSentence.length())
                            .map(nmeaSentence::charAt)
                            .reduce(0, (a, b) -> a ^ b);

        String checksumResult = Integer.toHexString(checksum).toUpperCase();

        if (checksumResult.length() < CHECKSUM_HEX_LENGTH)
            checksumResult = "0" + checksumResult;

        LOG.info("Created CRC (" + checksumResult + ")  for Sentence: " + nmeaSentence);
        return checksumResult;
    }

    /**
     * Append an "ERROR" to the logger
     *
     * @param error String to describe the error
     */
    private static void appendError(String error) {
        LOG.log(Level.FINE, error);
    }
}
