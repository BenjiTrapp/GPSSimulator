import faultInjection.fault_library.PerturbationBuilder;
import faultInjection.fault_library.perturbation_strategies.RandomASCIIStrategy;
import faultInjection.fault_library.perturbation_strategies.SpoofedPositionStrategy;
import faultInjection.fault_library.perturbation_strategies.StuckAtErrorStrategy;
import gps.NMEA.parser.hardening_functions.StuckAtErrorDetectionStrategy;
import gps.NMEA.parser.hardening_functions.HardeningStrategy;
import gps.NMEA.parser.hardening_functions.SpoofingDetectionStrategy;
import gps.generator.GPSGeneratorFactory;
import gps.NMEA.parser.NMEAParserFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;


public class GPSSimulatorStarter {
    private static final int SPEND_TIME_WITHOUT_FAULTS = 5000;
    private static final int FIFTEEN_SECONDS = 15000;
    private static final int RETRY_COUNT = 10;
    private static NMEAParserFactory NMEAParserFactory;
    private static AtomicBoolean isRunning = new AtomicBoolean(true);

    private static void startGPSParserWithHardeningStrategies(){
        Set<HardeningStrategy> hardeningStrategies = new HashSet<>();
        hardeningStrategies.add(new StuckAtErrorDetectionStrategy());
        hardeningStrategies.add(new SpoofingDetectionStrategy());

        NMEAParserFactory = new NMEAParserFactory().build(hardeningStrategies);
    }

    private static void startGPSParserWithoutHardeningStrategies(){
        NMEAParserFactory = new NMEAParserFactory().build();
    }

    private static void startGPSGenerator(){new GPSGeneratorFactory().build();}

    private static void attachShutdownHook(){Runtime.getRuntime()
                                                    .addShutdownHook(new Thread(() -> NMEAParserFactory.destroy()));
                                                     isRunning.set(false);}

    private static void spreadTheChaos(){
        SpoofedPositionStrategy spoofedPositionStrategy = new SpoofedPositionStrategy();
        spoofedPositionStrategy.setRetryCount(RETRY_COUNT);

        StuckAtErrorStrategy stuckAtErrorStrategy = new StuckAtErrorStrategy();
        stuckAtErrorStrategy.setStuckTime(FIFTEEN_SECONDS);

        do {
            new PerturbationBuilder().useRandomnessForConfiguration()
//                                     .addStrategy(spoofedPositionStrategy)
//                                     .addStrategy(stuckAtErrorStrategy)
                                     .addStrategy(new RandomASCIIStrategy())
                                     .build();
            try {Thread.sleep(SPEND_TIME_WITHOUT_FAULTS);} catch (InterruptedException ignored) {}
        } while (isRunning.get());
    }

    public static void main(String[] args) {
        attachShutdownHook();

        startGPSParserWithHardeningStrategies();
//        startGPSParserWithoutHardeningStrategies();
        startGPSGenerator();

        spreadTheChaos();
    }
}
