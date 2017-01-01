package gps.NMEA.utils;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class ChecksumUtilities {
    private static final int ASTERISK_POSITION_FROM_CHECKSUM = 3;
    private static final Logger LOG = Logger.getLogger("ChecksumLogger");
    private static final String LOGGING_PATH = "log/Checksum.log";

    static {
        try {
            FileHandler fh = new FileHandler(LOGGING_PATH);
            LOG.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
            LOG.setUseParentHandlers(false);
            LOG.setLevel(Level.FINE);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
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
        boolean isValid = true;
        int msglen = nmeaSentence.length();
        if (nmeaSentence.isEmpty())
            throw new InvalidParameterException();

        if (!nmeaSentence.startsWith("$")) {
            appendError("NMEA Sentence - Missing leading '$' (" + nmeaSentence + ") is INVALID");
            isValid = false;
        }

        if (msglen > 4 && isValid) {
            if (nmeaSentence.charAt(msglen - ASTERISK_POSITION_FROM_CHECKSUM) == '*') {
                String chk_s = getCRC(nmeaSentence.substring(0, msglen - 3));

                isValid = nmeaSentence.substring(msglen - 2, msglen).equals(chk_s);
                LOG.info("Sentence: " + nmeaSentence + " is VALID");
                return (isValid);
            } else {
                appendError("Sentence has no valid checksum: Leading asterisk is missing  (" + nmeaSentence + " ) + is INVALID");
                return false;
            }
        }

        if (isValid)
            appendError("message length > 4 characters for Sentence (" + nmeaSentence + ") is INVALID");

        return false;
    }

    /**
     * Creates the checksum from the passed gps.NMEA Sentence
     *
     * @param nmeaSentence String containing the full gps.NMEA message without checksum
     * @return String String that contains the calculated checksum
     */
    public static synchronized String getCRC(String nmeaSentence) {
        assert nmeaSentence != null;
        assert !nmeaSentence.isEmpty();
        assert nmeaSentence.startsWith("$");
        assert !nmeaSentence.contains("*");

        int checksum = 0;
        String checksumResult;

        for (int i = 1; i < nmeaSentence.length(); i++)
            checksum ^= nmeaSentence.charAt(i);

        checksumResult = Integer.toHexString(checksum).toUpperCase();

        if (checksumResult.length() < 2)
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
