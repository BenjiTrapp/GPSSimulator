package faultInjection.pertubation.perturbation_functions.perturbation_strategies;

import faultInjection.pertubation.perturbation_functions.modes.PerturbationModes;
import gps.data.GPSData;

public class DashedStrategy extends AbstractPerturbationStrategy {

    private static final double DASH_COORDINATES_FACTOR = 4711.0;

    public DashedStrategy() {super(PerturbationModes.DASH);}

    @Override
    public synchronized void perturb(String line2perturb) {
            System.err.print("Start dashing the GPS Position ... ");
            double lat = Double.parseDouble(GPSData.getLatitude()) + DASH_COORDINATES_FACTOR;
            double lng = Double.parseDouble(GPSData.getLongitude()) + DASH_COORDINATES_FACTOR;
            double alt = Double.parseDouble(GPSData.getAltitude()) + DASH_COORDINATES_FACTOR;

            GPSData.setLongitude(Double.toString(lng));
            GPSData.setLatitude(Double.toString(lat));
            GPSData.setAltitude(Double.toString(alt));

            System.err.println("Finished dashing Coordinates");
    }
}
