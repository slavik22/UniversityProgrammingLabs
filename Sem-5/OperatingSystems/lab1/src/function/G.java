package function;


import java.io.IOException;
import java.io.PipedInputStream;

public class G extends Func {
    public G() {
        super("g");
    }

    @Override
    public Integer call() throws IOException, InterruptedException {
        byte[] buffer = new byte[4];
        inputStream.read(buffer);
        x = byteArrayToInt(buffer);

        Thread.sleep(5000);

        return 3 * x;
    }
}