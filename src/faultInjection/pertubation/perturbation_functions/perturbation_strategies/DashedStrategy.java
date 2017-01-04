package faultInjection.pertubation.perturbation_functions.perturbation_strategies;

import faultInjection.pertubation.perturbation_functions.modes.PerturbationModes;
import gps.data.GPSData;
import gps.data.GPSDataEnumHolder;

public class DashedStrategy extends AbstractPerturbationStrategy {

    private static final double DASH_COORDINATES_FACTOR = 42.0;

    public DashedStrategy() {super(PerturbationModes.DASH);}

    @Override
    public synchronized void perturb(String line2perturb) {
            System.err.println("Start dashing the GPS Position");
            double lat = Double.parseDouble(GPSData.getLatitude()) + DASH_COORDINATES_FACTOR;
            double lng = Double.parseDouble(GPSData.getLongitude()) + DASH_COORDINATES_FACTOR;
            double alt = Double.parseDouble(GPSData.getAltitude()) + DASH_COORDINATES_FACTOR;

            GPSData.setLongitude(Double.toString(lng));
            GPSData.setLatitude(Double.toString(lat));
            GPSData.setAltitude(Double.toString(alt));
    }
}
