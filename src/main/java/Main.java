import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import static java.lang.Math.pow;

public class Main {
    private static int n = 10000;
    private static double epsilon;
    private static Vector x0;
    private static final int MAX_NUMBER_OF_ITERATIONS = 40;

    public static double minimize(UnaryOperator<Double> function, double begin, double end) {
        double step = (end - begin) / n;

        List<Double> functionValues = IntStream
                .range(0, n)
                .mapToObj(i -> function.apply(begin + i * step))
                .toList();

        int indexOfMinimum = functionValues.indexOf(Collections.min(functionValues));
        return begin + indexOfMinimum * step;
    }

    public static void input() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите начальную точку: ");
        x0 = new Vector(scanner.nextDouble(), scanner.nextDouble());
        System.out.print("Введите эпсилон: ");
        epsilon = scanner.nextDouble();
    }

    public static void main(String[] args) {
        input();
        MultidimensionalFunction<Double> function = x -> pow(x[0] - 2, 3) + pow(x[1] + 1, 3);
        Vector xResult = null;
        Vector xK = x0, xKPrevious;
        int k = 0;
        Vector[] y = new Vector[4];
        Vector[] directions = new Vector[]{Vector.getOrtX2(), Vector.getOrtX1(), Vector.getOrtX2()};
        y[0] = x0;
        do {
            k++;
            System.out.println("k = " + k);
            xKPrevious = xK.copy();
            for (int i = 0; i < 3; i++) {
                int finalI = i;
                Function<Double, Vector> nextYFinder = a -> Vector.sum(y[finalI], Vector.mul(a, directions[finalI]));
                System.out.print("      y_" + i + ": " + y[finalI] + " direction: " + directions[finalI]);
                double minimumA = minimize(a -> function.apply(nextYFinder.apply(a).getAsArray()), -10, 10);
                System.out.println("    min:" + minimumA);
                y[finalI + 1] = nextYFinder.apply(minimumA);
            }
            System.out.println("      y_3: " + y[3]);
            if (y[3].equals(y[1])) {
                xResult = y[3];
                System.out.println("\ny_3 = y_1");
                break;
            }
            xK = y[3];
            directions[1] = directions[2];
            directions[0] = directions[2] = Vector.sub(y[3], y[1]);
            y[0] = xK;
        } while (Math.abs(function.apply(xK.getAsArray()) - function.apply(xKPrevious.getAsArray())) >= epsilon && k < MAX_NUMBER_OF_ITERATIONS);
        if (k == MAX_NUMBER_OF_ITERATIONS) {
            System.out.println("Достигнуто максимальное количество итераций");
            return;
        }
        if (xResult == null) {
            xResult = xK;
        }
        System.out.println("\nРешение: " + xResult);
    }
}
