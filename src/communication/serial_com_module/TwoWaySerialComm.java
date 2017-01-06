package communication.serial_com_module;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.PortUnreachableException;

public class TwoWaySerialComm {
    private void connect(String portName) throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned())
            throw new PortInUseException();

        CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

        if (commPort instanceof SerialPort) {
            SerialPort serialPort = (SerialPort) commPort;
            serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            InputStream in = serialPort.getInputStream();
            OutputStream out = serialPort.getOutputStream();

            (new Thread(new SerialReader(in))).start();
            (new Thread(new SerialWriter(out))).start();

        } else {
            throw new PortUnreachableException("ERROR - Only serial ports are handled by this class");
        }

    }

    /**
     * Class only used for testing purposes ...
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            (new TwoWaySerialComm()).connect("COM1");
        } catch (Exception ignored) {}
    }
}