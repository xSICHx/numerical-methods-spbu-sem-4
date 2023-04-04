package Task1ApproximateRoots.Methods;

import org.mariuszgromada.math.mxparser.Function;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class MethodFindRoot extends Thread {
    String name;
    Function func;
    double inaccuracy;
    List<double []> solutionSegments;
    Set<Root> solutions;

    public class Root {
        private double value;
        private double discrepancy;
        private int numberOfSteps;
        private String additionalInfo;

        protected Root(double value, double discrepancy, int numberOfSteps) {
            this.value = value;
            this.discrepancy = discrepancy;
            this.numberOfSteps = numberOfSteps;
        }
        protected Root(double value, double discrepancy, int numberOfSteps, String additionalInfo){
            this(value, discrepancy, numberOfSteps);
            this.additionalInfo = additionalInfo;
        }
        private void printRoot(){

            System.out.println("x = " + value + "; discrepancy = " + discrepancy +
                    "; number of steps = " + numberOfSteps + "; " + additionalInfo);
        }
        public double getValue(){
            return value;
        }
    }
    public MethodFindRoot(Function func, double inaccuracy, List<double[]> solutionSegments) {
        this.func = func.cloneForThreadSafe();
        this.inaccuracy = inaccuracy;
        this.solutionSegments = solutionSegments;
        this.solutions = new HashSet<>();
    }

    @Override
    public void run() {
        for (double[] segment : solutionSegments) {
            Root root = getRoot(segment[0], segment[1]);
            if (root != null)
                solutions.add(root);
        }
    }

    public void printRoots(){
        System.out.println(name + " roots:");
        for (Root root : solutions) {
            System.out.print("  ");
            root.printRoot();
        }
    }
    public String getMethodName(){
        return name;
    }
    public Set<Root> getSolutions() {
        return solutions;
    }
    protected abstract Root getRoot(double begin, double end);
}
