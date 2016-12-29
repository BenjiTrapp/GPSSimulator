import gps.GPSGeneratorFactory;
import gps.GPSParserFactory;


public class GPSSimulatorStarter {
    private static GPSParserFactory gpsParserFactory;

    private static void startGPSParser(){
        gpsParserFactory = new GPSParserFactory();
        gpsParserFactory.build();
    }

    private static void startGPSGenerator(){
        new GPSGeneratorFactory().build();
    }

    private static void attachShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> gpsParserFactory.destroy()));
    }

    public static void main(String[] args) {
        attachShutdownHook();

        startGPSParser();
        startGPSGenerator();
    }
}
