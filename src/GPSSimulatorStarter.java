import faultInjection.pertubation.PerturbationBuilder;
import faultInjection.pertubation.perturbation_functions.perturbation_strategies.RandomASCIIStrategy;
import faultInjection.pertubation.perturbation_functions.perturbation_strategies.SpoofedPositionStrategy;
import faultInjection.pertubation.perturbation_functions.perturbation_strategies.StuckErrorStrategy;
import gps.generator.GPSGeneratorFactory;
import gps.NMEA.parser.NMEAParserFactory;


public class GPSSimulatorStarter {
    private static final int SPEND_TIME_WITHOUT_FAULTS = 5000;
    private static final int FIFTEEN_SECONDS = 15000;
    private static final int RETRY_COUNT = 10;
    private static NMEAParserFactory NMEAParserFactory;

    private static void startGPSParser(){
        NMEAParserFactory = new NMEAParserFactory().build();}

    private static void startGPSGenerator(){new GPSGeneratorFactory().build();}

    private static void attachShutdownHook(){Runtime.getRuntime().addShutdownHook(new Thread(() -> NMEAParserFactory.destroy()));}

    private static void spreadTheChaos(){
        SpoofedPositionStrategy spoofedPositionStrategy = new SpoofedPositionStrategy();
        spoofedPositionStrategy.setRetryCount(RETRY_COUNT);

        StuckErrorStrategy stuckErrorStrategy = new StuckErrorStrategy();
        stuckErrorStrategy.setStuckTime(FIFTEEN_SECONDS);

        do {
            new PerturbationBuilder().useRandomnessForConfiguration()
                                     .addStrategy(spoofedPositionStrategy)
                                   //  .addStrategy(stuckErrorStrategy)
                                     .addStrategy(new RandomASCIIStrategy())
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
