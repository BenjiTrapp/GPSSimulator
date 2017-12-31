package gps.generator.datagen_tasks;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.TimerTask;

import gps.data.GPSData;
import gps.generator.GPSGenEnumHolder.AngleUnits;
import gps.generator.GPSGenEnumHolder.Modes;
import gps.generator.GPSGenEnumHolder.Patterns;

import static gps.data.GPSDataEnumHolder.*;
import static gps.data.GPSDataEnumHolder.GPSFixTypes.*;
import static gps.generator.GPSGenEnumHolder.Patterns.*;

/**
 * This Class is used for the GPS-Data Generation and is implemented as
 * Singleton to avoid multiple manipulation of the GPS-Data. Use this
 * Class in combination with a Timer. Also there are plenty getter and
 * setter for all important things like changing the generation mode of
 * the data or angle unit
 *
 * @author Benjamin Trapp
 */
public class DataGenTask extends TimerTask {
    private static final String LATITUDE_LONGITUDE_PATTERN = "0000.0000";
    private static final String VELOCITY_PATTERN = "000.0";
    private static final String ALTITUDE_PATTERN = "###0.0";
    private static final int BOUND_25 = 25;
    private static final int BOUND_10 = 10;
    private static final int BOUND_90 = 90;
    private static final int BOUND_100 = 100;
    private Random rnd = new Random();
    private DecimalFormat format = new DecimalFormat();
    private DataGenTaskObjectHolder dataGenTaskObjectHolder;
    private static DataGenTask instance = null;

    /**
     * This Constructor is private, coming due through the
     * Singleton-Pattern
     */
    private DataGenTask(DataGenTaskObjectHolder dataGenTaskObjectHolder) {
        this.dataGenTaskObjectHolder = dataGenTaskObjectHolder;
    }

    public static DataGenTask getInstance(DataGenTaskObjectHolder dataGenTaskObjectHolder) {
        return instance == null ? instance = new DataGenTask(dataGenTaskObjectHolder) : instance;
    }

    @Override
    public void run() {
        generateRandomData();
    }

    /**
     * Generates random GPS Data for more details take a look at
     * http://www.gpsinformation.org/dale/nmea.htm
     */
    private void generateRandomData() {
        generateRandomLatitude(dataGenTaskObjectHolder.getMode(LATITUDE));
        generateRandomLongitude(dataGenTaskObjectHolder.getMode(LONGITUDE));
        generateRandomAltitude(dataGenTaskObjectHolder.getMode(ALTITUDE));
        generateRandomVelocity(dataGenTaskObjectHolder.getMode(VELOCITY));
        generateRandomDOPs(dataGenTaskObjectHolder.getMode(DOP));
        generateRandomCourse(dataGenTaskObjectHolder.getAngleUnit());
        generateRandomSatellites();
        generateRandomCardinalDirections();
    }

    /**
     * Generates random values for the latitude depending on the passed mode
     *
     * @param mode Mode for the generation of the gps.data.GPSData (ASCNECING,DESCENDING,RANDOM)
     */
    private void generateRandomLatitude(Modes mode) {
        GPSData.setLatitude(randomDoubleDigit(GPSData.getLatitude(), mode, LATITUDE));
    }

    /**
     * Generates random values for the longitude depending on the passed mode
     *
     * @param mode Mode for the generation of the gps.data.GPSData (ASCNECING,DESCENDING,RANDOM)
     */
    private void generateRandomLongitude(Modes mode) {
        GPSData.setLongitude(randomDoubleDigit(GPSData.getLongitude(), mode, LONGITUDE));
    }

    /**
     * Generates random values for the velocity depending on the passed mode
     *
     * @param mode Mode for the generation of the gps.data.GPSData (ASCNECING,DESCENDING,RANDOM)
     */
    private void generateRandomVelocity(Modes mode) {
        GPSData.setVelocity(randomDoubleDigit(GPSData.getLongitude(), mode, VELOCITY));
    }

    /**
     * Generates random values for the altitude depending on the passed mode
     *
     * @param mode Mode for the generation of the gps.data.GPSData (ASCNECING,DESCENDING,RANDOM)
     */
    private void generateRandomAltitude(Modes mode) {
        GPSData.setAltitude(randomDoubleDigit(GPSData.getAltitude(), mode, ALTITUDE));
    }

    /**
     * Generates random values for the three Dilutions of Point depending on the passed mode
     * for more details: http://de.wikipedia.org/wiki/Dilution_of_Precision
     *
     * @param mode Mode for the generation of the gps.data.GPSData (ASCNECING,DESCENDING,RANDOM)
     */
    private void generateRandomDOPs(Modes mode) {
        GPSData.setVDOP(randomDoubleDigit(GPSData.getVDOP(), mode, DOP));
        GPSData.setHDOP(randomDoubleDigit(GPSData.getHDOP(), mode, DOP));
        GPSData.setPDOP(randomDoubleDigit(GPSData.getPDOP(), mode, DOP));
    }

    /**
     * Generates a random amount of "currently" available satellites (0 to 12)
     */
    private void generateRandomSatellites() {
        int tmp = rnd.nextInt(12);
        StringBuilder s = new StringBuilder();

        if (tmp < 10)
            s.append("0");

        s.append(tmp);

        if (tmp < 3)
            GPSData.setFixType(GPS_FIX_UNKNOWN);

        if (tmp == 3)
            GPSData.setFixType(GPS_FIX_2D);

        if (tmp >= 4)
            GPSData.setFixType(GPS_FIX_3D);

        GPSData.setSatellites(s.toString());
    }

    /**
     * Generates based on a randomly calculated digit a random
     * cardinal direction
     */
    private void generateRandomCardinalDirections() {
        int tmp = rnd.nextInt(BOUND_100);

        if (tmp >= 0 && tmp < 25) GPSData.setNS(CardinalDirections.NORTH);
        if (tmp >= 25 && tmp < 50) GPSData.setEW(CardinalDirections.EAST);
        if (tmp >= 50 && tmp < 75) GPSData.setNS(CardinalDirections.SOUTH);
        if (tmp >= 75 && tmp < 100) GPSData.setEW(CardinalDirections.WEST);
    }

    /**
     * Generates a random Course change" of the UAV
     *
     * @param angleUnit The Angle units can be changed to radial or gon
     */
    private void generateRandomCourse(AngleUnits angleUnit) {
        if (angleUnit == AngleUnits.RADIAL) {
            if (GPSData.getNS() == CardinalDirections.NORTH && GPSData.getEW() == CardinalDirections.EAST)
                GPSData.setCourse(rnd.nextInt(BOUND_90));
            if (GPSData.getNS() == CardinalDirections.SOUTH && GPSData.getEW() == CardinalDirections.EAST)
                GPSData.setCourse(rnd.nextInt(BOUND_90) + 90);
            if (GPSData.getNS() == CardinalDirections.SOUTH && GPSData.getEW() == CardinalDirections.WEST)
                GPSData.setCourse(rnd.nextInt(BOUND_90) + 180);
            if (GPSData.getNS() == CardinalDirections.NORTH && GPSData.getEW() == CardinalDirections.WEST)
                GPSData.setCourse(rnd.nextInt(BOUND_90) + 270);
        } else {
            if (GPSData.getNS() == CardinalDirections.NORTH && GPSData.getEW() == CardinalDirections.EAST)
                GPSData.setCourse(rnd.nextInt(BOUND_100));
            if (GPSData.getNS() == CardinalDirections.SOUTH && GPSData.getEW() == CardinalDirections.EAST)
                GPSData.setCourse(rnd.nextInt(BOUND_100) + 100);
            if (GPSData.getNS() == CardinalDirections.SOUTH && GPSData.getEW() == CardinalDirections.WEST)
                GPSData.setCourse(rnd.nextInt(BOUND_100) + 200);
            if (GPSData.getNS() == CardinalDirections.NORTH && GPSData.getEW() == CardinalDirections.WEST)
                GPSData.setCourse(rnd.nextInt(BOUND_100) + 300);
        }
    }

    /**
     * Generates a new random double digit depending on a passed String. The mode can be used to generate different types of
     * Random Digits. The pattern is used to set the format pattern for specific data types
     *
     * @param digit   Old value that is used. The random digit will be combined with this value
     * @param mode    the mode specifies the way how the random digit will be generated
     * @param pattern needed to format the return value for secific data types
     * @return a random generated double digit based on a passed digit and mode
     */
    private String randomDoubleDigit(String digit, Modes mode, Patterns pattern) {
        double var = Double.parseDouble(digit);
        double tmp = -1.0;

        if (pattern == LATITUDE) {
            tmp = (rnd.nextInt(BOUND_25) * 0.0001);
            format.applyPattern(LATITUDE_LONGITUDE_PATTERN);
        }
        if (pattern == LONGITUDE) {
            tmp = (rnd.nextInt(BOUND_25) * 0.0001);
            format.applyPattern(LATITUDE_LONGITUDE_PATTERN);
        }

        if (pattern == VELOCITY) {
            tmp = (rnd.nextInt(BOUND_10) * 0.01);
            format.applyPattern(VELOCITY_PATTERN);
        }

        if (pattern == ALTITUDE) {
            tmp = rnd.nextInt(BOUND_25) * 0.01;
            format.applyPattern(ALTITUDE_PATTERN);
        }

        if (pattern == DOP) {
            tmp = rnd.nextInt(15) * 0.01;
            format.applyPattern("0.0");
        }

        switch (mode) {
            case ASCENDING:     var += tmp;
                                break;

            case DESCENDING:    var -= tmp;
                                break;

            case RANDOM:        if (((int) (Math.random() * 10)) % 2 == 0)
                                    var += tmp;
                                else
                                    var -= tmp;
                                break;

            default:            System.err.println("INVALID MODE");
                                break;
        }

        //Avoid negative numbers
        if (var <= 0.5)
            var = 0.5 + (rnd.nextInt(9) * 0.1);

        return format.format(var).replace(",", ".");
    }
}
