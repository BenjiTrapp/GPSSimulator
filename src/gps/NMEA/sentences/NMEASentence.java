package gps.NMEA.sentences;

import java.text.SimpleDateFormat;

import gps.data.GPSData;

/**
 * This interface contains all necessary methods to
 * create a gps.NMEA Sentence of a specific type
 *
 * @author Benjamin Trapp
 */
@FunctionalInterface
public interface NMEASentence {

    String TIME_PATTERN = "HHmmss";
    String DATE_PATTERN = "ddMMYY";
    int SIXTY_SECONDS = 60;

    /**
     * Gets the gps.NMEA Sentence of it`s specific back
     *
     * @return gps.NMEA Sentence of a specific type as String
     */
    String getSentence();

    /**
     * Get's a time stamp back in the format HHmmss
     *
     * @return time stamp as String
     */
    default String getTimestamp() {
        return  new SimpleDateFormat(TIME_PATTERN).format(new java.sql.Timestamp(System.currentTimeMillis()));
    }

    /**
     * Get's a date stamp back in the format ddMMYY
     *
     * @return time stamp as String
     */
    default String getDatetime() {
        return  new SimpleDateFormat(DATE_PATTERN).format(new java.sql.Timestamp(System.currentTimeMillis()));
    }

    /**
     * Gets the gps.NMEA Latitude back, the data is already  transformed
     * into Degree" Minutes' and Seconds''
     *
     * @return A transfered/recalculated version of the Latitude
     */
    default double getNMEALatitude() {
        double degree = (int) Double.parseDouble(GPSData.getLatitude());
        double minute = (int) (Double.parseDouble(GPSData.getLatitude()) * SIXTY_SECONDS - degree * SIXTY_SECONDS);
        double second = (Double.parseDouble(GPSData.getLatitude())) * SIXTY_SECONDS
                         - (int) (Double.parseDouble((GPSData.getLatitude())) * SIXTY_SECONDS);

        return (double) Math.round((degree * 100 + minute + second) * 100) / 100;
    }

    /**
     * Gets the gps.NMEA Longitude back, the data is already  transformed
     * into Degree" Minutes' and Seconds''
     *
     * @return A transfered/recalculated version of the Longitude
     */
    default double getNMEALongitude() {
        double degree = (int) Double.parseDouble(GPSData.getLongitude());
        double minute = (int) (Double.parseDouble(GPSData.getLongitude()) * SIXTY_SECONDS - degree * SIXTY_SECONDS);
        double second = Double.parseDouble(GPSData.getLongitude()) * SIXTY_SECONDS
                        - (int) (Double.parseDouble(GPSData.getLongitude()) * SIXTY_SECONDS);

        return (double) Math.round((degree * 100 + minute + second) * 100) / 100;
    }
}
