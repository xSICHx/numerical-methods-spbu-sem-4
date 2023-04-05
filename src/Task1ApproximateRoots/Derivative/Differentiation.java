package Task1ApproximateRoots.Derivative;

import org.mariuszgromada.math.mxparser.Function;

/**
 * A static library for derivative calculations of functions.
 *
 * @author Brian Norman
 * @version 0.1 beta
 */
public final class Differentiation {

    /**
     * Don't let anyone instantiate this class.
     */
    private Differentiation() {
    }

    /**
     * Returns a small value for h that is used for computing the derivative.
     *
     * @param x the point at which the h value will be used.
     * @return a small value for h.
     */
    private static double hValue(double x) {
        return Math.max(Math.abs(x / 1000.0), 0.0001);
    }

    /**
     * Returns the derivative of the specified function at the specified point. Taken from Numerical Mathematics and
     * Computing (6th Edition) by Ward Cheney and David Kincaid, pages 172-173.
     *
     * @param f the function to derive.
     * @param x the point of derivation.
     * @return the derivative of the function.
     */
    public static double derivative(Function f, double x) {
        double h = hValue(x);
        double xph = f.calculate(x + h);
        double xmh = f.calculate(x - h);
        return 1.0 / (2.0 * h) * (xph - xmh) -
                1.0 / (12.0 * h) * (f.calculate(x + 2.0 * h) - 2.0 * xph + 2.0 * xmh - f.calculate(x - 2.0 * h));
    }

    /**
     * Returns the second derivative of the specified function at the specified point. Taken from Numerical Mathematics
     * and Computing (6th Edition) by Ward Cheney and David Kincaid, page 173.
     *
     * @param f the function to derive.
     * @param x the point of derivation.
     * @return the second derivative of the function.
     */
    public static double secondDerivative(Function f, double x) {
        double h = hValue(x);
        return 1.0 / (h * h) * (f.calculate(x + h) - 2.0 * f.calculate(x) + f.calculate(x - h));
    }

}