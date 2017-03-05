package communication.serial_com_module;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.PortUnreachableException;

public class TwoWaySerialComm {

    private static final int COMPORT_PORT = 2000;
    private static final int BAUD_RATE = 57600;

    private void connect(String portName) throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned())
            throw new PortInUseException();

        CommPort commPort = portIdentifier.open(this.getClass().getName(), COMPORT_PORT);

        if (commPort instanceof SerialPort) {
            SerialPort serialPort = (SerialPort) commPort;
            serialPort.setSerialPortParams(BAUD_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            InputStream in = serialPort.getInputStream();
            OutputStream out = serialPort.getOutputStream();

            (new Thread(new SerialReader(in))).start();
            (new Thread(new SerialWriter(out))).start();

        } else {
            throw new PortUnreachableException("ERROR - Only serial ports are handled by this class");
        }

    }

    /*
     * Class only used for testing purposes ...
     */
    public static void main(String[] args) {
        try {
            new TwoWaySerialComm().connect("COM1");
        } catch (Exception ignored) {}
    }
}