package function;

import java.io.PipedInputStream;
import java.util.concurrent.Callable;

public abstract class Func implements Callable<Integer> {
    public final String name;

    protected int x;

    protected PipedInputStream inputStream;

    public Func(String name) {
        this.name = name;
    }

    public void setInputStream(PipedInputStream inputStream) {
        this.inputStream = inputStream;
    }

    protected int byteArrayToInt(byte[] bytes) {
        return (bytes[0] << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }
}