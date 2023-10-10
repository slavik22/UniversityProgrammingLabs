import org.junit.Test;
import tridiagonal.TridiagonalSolver;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class TridiagonalSolverTest {

    @Test
    public void testSolve() {
        double[] A = {1.0, 1.0, 1.0, 0};
        double[] B = {2.0, 2.0, 2.0, 2.0};
        double[] C = {1.0, 1.0, 1.0, 0};
        double[] D = {1.0, 2.0, 3.0, 4.0};

        double[] expectedX = {0,1,0,2};
        double[] actualX = TridiagonalSolver.solve(A, B, C, D);

        System.out.println(Arrays.toString(actualX));

        assertArrayEquals(expectedX, actualX, 1e-6);
    }

    @Test
    public void testParallelSolve() {
        double[] A = {1.0, 1.0, 1.0, 0};
        double[] B = {2.0, 2.0, 2.0, 2.0};
        double[] C = {1.0, 1.0, 1.0, 0};
        double[] D = {1.0, 2.0, 3.0, 4.0};

        double[] expectedX = {0,1,0,2};
        double[] actualX = TridiagonalSolver.parallelSolve(A, B, C, D, 2);

        assertArrayEquals(expectedX, actualX, 1e-6);
    }
}

