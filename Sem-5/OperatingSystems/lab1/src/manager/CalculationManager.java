package manager;

import function.F;
import function.Func;
import function.G;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.*;

import static function.GCD.gcd;

public class CalculationManager {
    private final ExecutorService executorService;

    public CalculationManager() {
        executorService = Executors.newFixedThreadPool(2);
    }

    private int func(int x, Func function, long timeout) {
        PipedInputStream inputStream = new PipedInputStream();

        try {
            PipedOutputStream outputStream= new PipedOutputStream(inputStream);
            function.setInputStream(inputStream);
            outputStream.write(intToByteArray(x));

        }catch (IOException e){
            System.err.println(function.name + " pipe exception");
            throw new RuntimeException(e);
        }

        Future<Integer> future = executorService.submit(function);

        int y;

        try {
            y = future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e ) {
            System.err.println(function.name + " : function is interrupted");
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            System.err.println(function.name + " : time out");
            throw new RuntimeException(e);
        }

        return y;
    }

    public int calculate(int f_x, int g_x, long timeout) {
        int f_y = func(f_x, new F(), timeout);
        int g_y = func(g_x, new G(), timeout);

        return gcd(f_y, g_y);
    }
    public void shutdown() {
        executorService.shutdown();
    }

    private static byte[] intToByteArray(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (value >> 24);
        bytes[1] = (byte) (value >> 16);
        bytes[2] = (byte) (value >> 8);
        bytes[3] = (byte) value;
        return bytes;
    }
}
