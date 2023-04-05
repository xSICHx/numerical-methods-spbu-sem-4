package Task1ApproximateRoots.Methods;

import org.mariuszgromada.math.mxparser.Function;

import java.util.List;

import static java.lang.Math.abs;

public class Secant extends Newton{
    public Secant(Function func, double inaccuracy, List<double[]> solutionSegments) {
        super(func, inaccuracy, solutionSegments);
        name = "Secant";
    }

    @Override
    protected Root getRoot(double leftBound, double rightBound) {
        //selecting x_k and x_k_next so that condition 3 is met
        double x_k_prev = getRandomDoubleInSegment(leftBound, rightBound);
        while (getFuncSign(x_k_prev)*getSecondDerSign(x_k_prev) <= 0){
            x_k_prev = getRandomDoubleInSegment(leftBound, rightBound);
        }
        double x0 = x_k_prev;
        double x_k = getRandomDoubleInSegment(leftBound, rightBound);
        while (getFuncSign(x_k)*getSecondDerSign(x_k) <= 0){
            x_k = getRandomDoubleInSegment(leftBound, rightBound);
        }

        int signFirstDer = getFirstDerSign(leftBound);
        int signSecondDer = getSecondDerSign(leftBound);
        if (!checkConvergence(x_k_prev, signFirstDer, signSecondDer) ||
                !checkConvergence(x_k, signFirstDer, signSecondDer)){
            System.out.println("Secant method can not find all roots. Try to choose a smaller partition");
            return null;
        }

        double f_k_prev = func.calculate(x_k_prev);
        double f_k = func.calculate(x_k);
        double x_k_next = x_k - f_k/(f_k - f_k_prev)*(x_k-x_k_prev);
        double f_k_next = func.calculate(x_k_next);
        int numberOfSteps = 0;
        while (abs(func.calculate(x_k_next)) > inaccuracy){
            x_k_prev = x_k;
            x_k = x_k_next;
            f_k_prev = f_k;
            f_k = f_k_next;

            x_k_next = x_k - f_k/(f_k - f_k_prev)*(x_k-x_k_prev);
            f_k_next = func.calculate(x_k_next);
            if (!checkConvergence(x_k_next, signFirstDer, signSecondDer)){
                System.out.println("Secant method can not find all roots. Try to choose a bigger partition");
                return null;
            }
            numberOfSteps++;
        }

        return new Root(x_k_next, abs(func.calculate(x_k_next)), numberOfSteps,
                "initial approximation to the root = " + x0);
    }

}
