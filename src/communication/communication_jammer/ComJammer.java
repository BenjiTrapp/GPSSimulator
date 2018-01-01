package communication.communication_jammer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * This Class is redefined and assumed
 * to separate the functionality from the virtual prototype.
 * <p>
 * The ComJammer is used to inject the fault into
 * the SUT (gps.NMEA.NMEAParser)
 *
 * @author Benjamin Trapp
 */
public class  ComJammer {
    private static ComJammer instance = null;
    private OutputStream outStream = null;
    private static Socket socket = null;

    private ComJammer(Socket socket) {
        try {
            outStream = socket.getOutputStream();
        } catch (IOException e) {System.err.println("ERROR @ in- /output Stream, IOException");}
    }

    /**
     * Gets the Instance of the StringReader back. If the instance
     * was initialized properly the passed socket will be used otherwise
     * a default instance is created with the ServerSocket 4711.
     *
     * Note: After the instance is set up once the instance can't
     * be changed or modified! So if you want to use a specific ServerSocket
     * you have to initialize the instance first via initInstance(ServerSocket)
     *
     * @return The one and only Instance of the StringReader
     */
    public static ComJammer getInstance() {
        if (ComJammer.socket == null && instance == null){
            System.err.println("Using default Socket (locallhost, 6771). If you want to use a specific one, use " +
                               "the initComJammerMethod to configure a Socket");
            ComJammer.initComJammerWithDefaultSocket();
        }

        return instance == null ? new ComJammer(ComJammer.socket) : instance;
    }

    public static void initComJammer(Socket socket){
        ComJammer.socket = socket;
    }

    private static void initComJammerWithDefaultSocket(){
        try {
            ComJammer.socket = new Socket("localhost", 6771);
        } catch (IOException e) {
            System.err.println("Error during creation of the FI-Default-Socket");
        }
    }

    public synchronized void send(String originalMsg) {
        assert originalMsg != null;

        try {
            outStream.write(originalMsg.getBytes());
            outStream.flush();
        } catch (IOException e) {
            System.err.println("I/O Exception @ Outstream of ComJammer");
            e.printStackTrace();
        }finally { try {outStream.close();} catch (IOException ignored) {} }

    }
}