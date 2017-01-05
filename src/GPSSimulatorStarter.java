import faultInjection.pertubation.perturbation_functions.PerturbationBuilder;
import faultInjection.pertubation.perturbation_functions.perturbation_strategies.RandomASCIIStrategy;
import faultInjection.pertubation.perturbation_functions.perturbation_strategies.StuckErrorStrategy;
import gps.GPSGeneratorFactory;
import gps.GPSParserFactory;


public class GPSSimulatorStarter {
    private static final int SPEND_TIME_WITHOUT_FAULTS = 3000;//20000;
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
        StuckErrorStrategy spoofedPositionStrategy = new StuckErrorStrategy();
        spoofedPositionStrategy.setStuckTime(10000);

        do {
            new PerturbationBuilder()//.addStrategy(new StuckErrorStrategy())
                                     //.addStrategy(spoofedPositionStrategy)
                                     .addStrategy(new RandomASCIIStrategy())
                                     .useRandomnessForConfiguration()
                                     .build();
            try {Thread.sleep(SPEND_TIME_WITHOUT_FAULTS);} catch (InterruptedException ignored) {}
        } while (true);
    }

    public static void main(String[] args) {
        attachShutdownHook();

        startGPSParser();
        startGPSGenerator();

        spreadTheChaos();
    }
}
