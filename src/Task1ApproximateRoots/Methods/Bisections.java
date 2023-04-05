package Task1ApproximateRoots.Methods;

import org.mariuszgromada.math.mxparser.Function;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

/** Bisections method. It is divided by two
 *  until it approaches the root with the specified accuracy
 * */
public class Bisections extends MethodFindRoot {
    public Bisections(Function func, double inaccuracy, List<double[]> solutionSegments) {
        super(func, inaccuracy, solutionSegments);
        name = "Bisections";
    }

    @Override
    protected Root getRoot(double leftBound, double rightBound){
        double begin = leftBound;
        double end = rightBound;
        double middle = (begin + end) / 2.0;
        int numberOfSteps = 0;
        while (abs(func.calculate(middle)) > inaccuracy){
            if (signum(func.calculate(begin)) * signum(func.calculate(middle)) < 0)
                end = middle;
            if (signum(func.calculate(end)) * signum(func.calculate(middle)) < 0)
                begin = middle;
            middle = (begin + end) / 2.0;
            numberOfSteps++;
        }
        return new Root(middle, abs(func.calculate(middle)), numberOfSteps,
                "last segment length = " + abs(begin-end));
    }
}
