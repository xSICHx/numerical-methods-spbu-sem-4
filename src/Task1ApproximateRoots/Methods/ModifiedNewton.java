package Task1ApproximateRoots.Methods;

import org.mariuszgromada.math.mxparser.Function;

import java.util.List;

import static java.lang.Math.abs;

/**
 * Modified Newton method. Releases the tangent from the function once to find the root.
 * */

public class ModifiedNewton extends Newton{

    public ModifiedNewton(Function func, double inaccuracy, List<double[]> solutionSegments) {
        super(func, inaccuracy, solutionSegments);
        name = "Modified Newton";
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
            System.out.println("Modified Newton method can not find all roots. Try to choose a smaller partition");
            return null;
        }

        final double constDer = getFirstDer(x);
        int numberOfSteps = 0;
        while (abs(func.calculate(x)) > inaccuracy){
            x = x - (func.calculate(x)/constDer);
            if (!checkConvergence(x, signFirstDer, signSecondDer)){
                System.out.println("Modified Newton method can not find all roots. Try to choose a bigger partition");
                return null;
            }
            numberOfSteps++;
        }
        return new Root(x, abs(func.calculate(x)), numberOfSteps,
                "initial approximation to the root = " + x0);
    }
}
