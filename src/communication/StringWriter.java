/**
 *
 */
package communication;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;

/**
 * This class is used to send a String via ByteStream. To avoid complications
 * this class is implemented as Singleton. To use this class, simply get an
 * instance
 *
 * @author Benjamin Trapp
 */
public class StringWriter extends AComModule {
    private static final String NEW_LINE = "\n";
    private static final String LOCALHOST = "localhost";
    private static final int PORT = 6711;
    private static Socket socket;
    private static StringWriter instance;

    /**
     * Creates a new Communication Object
     *
     * @param socket Socket for the communication
     */
    private StringWriter(Socket socket) {
        super(socket);
    }

    /**
     * Gets the Instance of the StringReader back. If the instance
     * was initialized properly the passed socket will be used otherwise
     * a default instance is created with the ServerSocket 4711.
     * <p>
     * Note: After the StringReader was set up once the instance can't
     * be changed or modified! So if you want to use a specific ServerSocket
     * you have to initialize the instance first via initInstance(ServerSocket)
     *
     * @return The one and only Instance of the StringReader
     */
    public static StringWriter getInstance() {
        try {
            if (instance == null && socket == null)
                instance = new StringWriter(new Socket(LOCALHOST, PORT));

            if (instance == null)
                instance = new StringWriter(socket);

        } catch (IOException e) {
            instance.closeAllCom();
            e.printStackTrace();
        }

        return instance;
    }

    /**
     * Initializes the StringReader Instance if necessary
     * Caution: This can only be done once and before calling
     * the getInstance() Method!
     *
     * @param socket Socket that the StringReader shall use
     * @return true if the initialization was done else false
     */
    public static boolean initInstance(Socket socket) {
        if (StringWriter.socket != null) {
            StringWriter.socket = socket;
            return true;
        }

        return false;
    }

    @Override
    public synchronized void send(@NotNull String msg) {
        msg += NEW_LINE;

        try {
            super.outStream.write(msg.getBytes());
            super.outStream.flush();
        } catch (IOException e) {
            System.err.println("ERROR @ write output stream");
            instance.closeAllCom();
        }
    }

    @Override
    public String receive() {
        throw new UnsupportedOperationException();
    }
}