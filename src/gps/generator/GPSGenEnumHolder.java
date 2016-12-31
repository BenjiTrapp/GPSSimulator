package gps.generator;

/**
 * This Class is used to keep all the different Enumerations
 * at one place
 *
 * @author Benjamin Trapp
 */
class GPSGenEnumHolder {
    /**
     * Enumeration for the possible angle units
     *
     * @author Benjamin Trapp
     */
    public enum AngleUnits {
        RADIAL, GON
    }

    /**
     * Enumeration for the GPS-Genration Modes
     *
     * @author Benjamin Trapp
     */
    public enum Modes {
        RANDOM, ASCENDING, DESCENDING
    }

    /**
     * Enumeration for the possible formating patterns
     * for a correct presentation of the data
     *
     * @author Benjamin Trapp
     */
    public enum Patterns {
        LATITUDE, LONGITUDE, ALTITUDE, VELOCITY, DOP
    }
}
