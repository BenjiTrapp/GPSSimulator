package communication.serial_com_module;

import java.io.IOException;
import java.io.InputStream;

public class SerialReader implements Runnable {
    private InputStream in;

    SerialReader(InputStream in) {
        this.in = in;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = this.in.read(buffer)) > -1) {
                System.out.print(new String(buffer, 0, len));
            }
        } catch (IOException ignored) {}
    }
}