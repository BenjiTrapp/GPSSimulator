package communication.serial_com_module;

import java.io.IOException;
import java.io.OutputStream;

public class SerialWriter implements Runnable {
    private OutputStream out;

    SerialWriter(OutputStream out) {this.out = out;}

    public void run() {
        try {
            int c;
            while ((c = System.in.read()) > -1) {
                this.out.write(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
