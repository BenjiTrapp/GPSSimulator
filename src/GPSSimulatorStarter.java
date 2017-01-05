import faultInjection.pertubation.perturbation_functions.PerturbationBuilder;
import faultInjection.pertubation.perturbation_functions.perturbation_strategies.DashedStrategy;
import faultInjection.pertubation.perturbation_functions.perturbation_strategies.RandomASCIIStrategy;
import faultInjection.pertubation.perturbation_functions.perturbation_strategies.StuckAtStrategy;
import gps.GPSGeneratorFactory;
import gps.GPSParserFactory;
import gps.data.GPSData;


public class GPSSimulatorStarter {
    private static GPSParserFactory gpsParserFactory;

    private static void startGPSParser(){
        gpsParserFactory = new GPSParserFactory();
        gpsParserFactory.build();
    }

    private static void startGPSGenerator(){
        new GPSGeneratorFactory().build();
    }

    private static void attachShutdownHook(){Runtime.getRuntime().addShutdownHook(new Thread(() -> gpsParserFactory.destroy()));}

    private static void spreadTheChaos(){
        StuckAtStrategy stuckAtStrategy = new StuckAtStrategy();
        stuckAtStrategy.setStuckedTime(1500);

        do {
            new PerturbationBuilder()//.addStrategy(new DashedStrategy())
                                     .addStrategy(stuckAtStrategy)
                                     .useRandomnessForConfiguration()
                                     .build();
            try {Thread.sleep(20000);} catch (InterruptedException ignored) {}
        } while (true);
    }

    public static void main(String[] args) {
        attachShutdownHook();

        startGPSParser();
        startGPSGenerator();

        spreadTheChaos();
    }
}
