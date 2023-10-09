import manager.CalculationManager;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;


public class Main {
    public static void main(String[] args) {
        CalculationManager manager = new CalculationManager();

        int result = manager.calculate(2, 2, 10000);
        System.out.println("Result: " + result);
        manager.shutdown();

    }
}
