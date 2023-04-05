package Task1ApproximateRoots.Methods;


import Task1ApproximateRoots.Derivative.Differentiation;
import org.mariuszgromada.math.mxparser.Function;

import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

/**
 *  Newton method. Releases the tangent from the function each iteration to find the root.
 * */

public class Newton extends MethodFindRoot {
    public Newton(Function func, double inaccuracy, List<double[]> solutionSegments) {
        super(func, inaccuracy, solutionSegments);
        name = "Newton";
    }

    @Override
    protected Root getRoot(double leftBound, double rightBound) {
        //selecting x so that condition 3 is met
        double x = getRandomDoubleInSegment(leftBound, rightBound);
        while (getFuncSign(x)*getSecondDerSign(x) <= 0){
            x = getRandomDoubleInSegment(leftBound, rightBound);
        }
        double x0 = x;
        int signFirstDer = getFirstDerSign(leftBound);
        int signSecondDer = getSecondDerSign(leftBound);
        if (!checkConvergence(x, signFirstDer, signSecondDer)){
            System.out.println("Newton method can not find all roots. Try to choose a smaller partition");
            return null;
        }

        int numberOfSteps = 0;
        while (abs(func.calculate(x)) > inaccuracy){
            x = x - (func.calculate(x)/getFirstDer(x));
//            if (!checkConvergence(x, signFirstDer, signSecondDer)){
//                System.out.println("Newton method can not find all roots. Try to choose a bigger partition");
//                return null;
//            }
            numberOfSteps++;
        }
        return new Root(x, abs(func.calculate(x)), numberOfSteps,
                "initial approximation to the root = " + x0);
    }

    /**
     *      1) f(a) * f(b) < 0
     *      2) f'(x), f''(x) - saves sign in [a,b]
     *      3) f(x) * f''(x) > 0 - x in [a,b]
     * */
    boolean checkConvergence(double x, int signFirstDer, int signSecondDer){
        boolean a = getFirstDerSign(x) == signFirstDer && getSecondDerSign(x) == signSecondDer;
        boolean b = getFuncSign(x) * getSecondDerSign(x) > 0;
        return a && b;
    }

    double getRandomDoubleInSegment(double a, double b){
        Random rnd = new Random();
        return rnd.nextDouble(a, b);
    }
    int getFuncSign(double x){
        return (int) signum(func.calculate(x));
    }
    int getFirstDerSign(double x){
        return (int) signum(Differentiation.derivative(func, x));
    }
    int getSecondDerSign(double x){
        return (int) signum(Differentiation.secondDerivative(func, x));
    }
    double getFirstDer(double x){
        return  Differentiation.derivative(func, x);
    }
}
