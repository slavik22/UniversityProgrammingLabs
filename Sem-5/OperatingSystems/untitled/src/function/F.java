package function;

import java.io.IOException;
import java.io.PipedInputStream;

public class F extends Func {

    public F() {
        super("f");
    }

    @Override
    public Integer call() throws IOException, InterruptedException {
        byte[] buffer = new byte[4];
        inputStream.read(buffer);
        x = byteArrayToInt(buffer);

        Thread.sleep(5000);

        return 2 * x;
    }
}