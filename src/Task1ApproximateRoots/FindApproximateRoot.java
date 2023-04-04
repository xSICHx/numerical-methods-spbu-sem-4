package Task1ApproximateRoots;

import Task1ApproximateRoots.Methods.*;
import Task1ApproximateRoots.Methods.MethodFindRoot;
import Task1ApproximateRoots.Methods.Newton;
import org.mariuszgromada.math.mxparser.Function;

import java.util.*;
import java.util.function.BiFunction;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

public class FindApproximateRoot {
    private Function func;
    private double a;
    private double b;
    private int partition;
    private double inaccuracy;
    private List<double []> solutionSegments;
    private List<MethodFindRoot> solutions;
    private HashSet<Double> rootsWhileCalculatingSegments;

    public FindApproximateRoot(){
        func = new Function("0");
        a = 0;
        b = 0;
        partition = 0;
        solutionSegments = new ArrayList<>();
        solutions = new ArrayList<>();
        rootsWhileCalculatingSegments = new HashSet<>();
    }
    public FindApproximateRoot(String function, double a, double b, int partition, double inaccuracy) {
        this();
        Scanner scan = new Scanner(System.in);
        String str;

        //checking correctness
        this.func = new Function(function);
        if(!checkFunction())
            return;

        this.a = a;
        this.b = b;
        if(!checkSegment())
            return;

        this.partition = partition;
        if (!checkPartition())
            return;

        this.inaccuracy = abs(inaccuracy);

        //calculate the number of roots
        calculateNumberOfRoots();
        str = "NO";
        while (!Objects.equals(str, "YES")){
            System.out.println("Do you agree with these roots? Enter YES/NO or write EXIT");
            str = scan.nextLine();
            if (Objects.equals(str, "EXIT"))
                return;
            if (Objects.equals(str, "NO")){
                System.out.println("Enter another partition");
                str = scan.nextLine();
                if (isNumeric(str))
                    this.partition = Integer.parseInt(str);
                if (!checkPartition())
                    return;
                calculateNumberOfRoots();
            }
        }

        //finding roots with different methods
        MethodFindRoot bisections = new Bisections(func, inaccuracy, solutionSegments);
        MethodFindRoot newton = new Newton(func, inaccuracy, solutionSegments);
        MethodFindRoot modifiedNewton = new ModifiedNewton(func, inaccuracy, solutionSegments);
        MethodFindRoot secant = new Secant(func, inaccuracy, solutionSegments);

        secant.start();
        modifiedNewton.start();
        newton.start();
        bisections.start();
        try{
            bisections.join();
            newton.join();
            modifiedNewton.join();
            secant.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        solutions.addAll(Arrays.asList(bisections, newton, modifiedNewton, secant));
    }

    //Выполняет свою работу одним выбранным методом без вывода в консоль лишних слов
    public FindApproximateRoot(String function, double a, double b, int partition, double inaccuracy, String method) {
        this();

        //checking correctness
        this.func = new Function(function);
        if(!checkFunction())
            return;

        this.a = a;
        this.b = b;
        if(!checkSegment())
            return;

        this.partition = partition;
        if (!checkPartition())
            return;

        this.inaccuracy = abs(inaccuracy);

        //calculate the number of roots
        calculateNumberOfRootsNoPrints();

        //finding roots with different methods
        MethodFindRoot solvingMethod = switch (method) {
            case ("newton") -> new Newton(func, inaccuracy, solutionSegments);
            case ("modNewton") -> new ModifiedNewton(func, inaccuracy, solutionSegments);
            case ("secant") -> new Secant(func, inaccuracy, solutionSegments);
            default -> new Bisections(func, inaccuracy, solutionSegments);
        };
        solvingMethod.start();
        try{
            solvingMethod.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        solutions.add(solvingMethod);
    }

    public void printRoots(){
        System.out.println("Roots appeared while calculating segments:");
        for (double x: rootsWhileCalculatingSegments) {
            System.out.println("    x = " + x);
        }
        for (MethodFindRoot method: solutions) {
            method.printRoots();
        }
    }
    public HashSet<Double> getRootsWhileCalculatingSegments() {
        return rootsWhileCalculatingSegments;
    }

    private void calculateNumberOfRoots(){
        double h = (b-a)/partition;
        this.solutionSegments = new ArrayList<>();
        double x1, x2;
        System.out.println("This program can find roots on these segments");
        for (double i = a; i < b; i += h) {
            x1 = i;
            x2 = Math.min(i + h, b);
            if (signum(func.calculate(x1)) * signum(func.calculate(x2)) < 0){
                double[] segment = new double[] {x1, x2};
                this.solutionSegments.add(segment);
                System.out.print(Arrays.toString(segment) + " ");
            } else if (signum(func.calculate(x1)) * signum(func.calculate(x2)) == 0) {
                double root = func.calculate(x1) == 0 ? func.calculate(x1) : func.calculate(x2);
//                solutions.get("Roots appeared while calculating segments").add(root);
                rootsWhileCalculatingSegments.add(root);
            }
        }
        System.out.println("Total number of roots = "
                + (solutionSegments.size() + rootsWhileCalculatingSegments.size()));
    }
    private void calculateNumberOfRootsNoPrints(){
        double h = (b-a)/partition;
        this.solutionSegments = new ArrayList<>();
        double x1, x2;
        for (double i = a; i < b; i += h) {
            x1 = i;
            x2 = Math.min(i + h, b);
            if (signum(func.calculate(x1)) * signum(func.calculate(x2)) < 0){

                double[] segment = new double[] {x1, x2};
                this.solutionSegments.add(segment);
            } else if (signum(func.calculate(x1)) * signum(func.calculate(x2)) == 0) {
                double root = func.calculate(x1) == 0 ? func.calculate(x1) : func.calculate(x2);
                rootsWhileCalculatingSegments.add(root);
            }
        }
    }


    //getters
    public List<MethodFindRoot> getSolutions() {
        return solutions;
    }
    public Function getFunc() {
        return func;
    }
    public String getMainFuncString(){
        return func.getFunctionExpressionString();
    }


    private boolean checkPartition(){
        Scanner scan = new Scanner(System.in);
        String str;
        while (this.partition <= 0) {
            System.out.println("Syntax error: try to enter another partition or write EXIT");
            str = scan.nextLine();
            if (Objects.equals(str, "EXIT"))
                return false;
            if (isNumeric(str))
                this.partition = Integer.parseInt(str);
        }
        return true;
    }
    private boolean checkSegment(){
        Scanner scan = new Scanner(System.in);
        String str;
        while (this.a > this.b){
            System.out.println("Semantic error: a > b. Enter another a on the next line and than b or write EXIT ");

            str = scan.nextLine();
            if (Objects.equals(str, "EXIT"))
                return false;
            if (isNumeric(str))
                this.a = Double.parseDouble(str);

            str = scan.nextLine();
            if (Objects.equals(str, "EXIT"))
                return false;
            if (isNumeric(str))
                this.b = Double.parseDouble(str);

        }
        return true;
    }
    private boolean checkFunction(){
        Scanner scan = new Scanner(System.in);
        String str;
        while (!func.checkSyntax()){
            System.out.println("Syntax error: try to enter another function or write EXIT");
            str = scan.nextLine();
            if (Objects.equals(str, "EXIT"))
                return false;
            func = new Function(str);
        }
        return true;
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public List<Double> getArrayOfRoots(){
        List<Double> res = new ArrayList<>(rootsWhileCalculatingSegments);
        for (MethodFindRoot method: solutions) {
            for (MethodFindRoot.Root root: method.getSolutions()) {
                res.add(root.getValue());
            }
            break;
        }
        return res;
    }


    /* будем считать производную по процентному соотношению разностей:
     если процент значения очередного шага от предыдущего меньше 1 - останавливаемся*/
    public static double calculateDerivative(Function f, double x, int derivativeDegree){
        // формула первой производной
        BiFunction<Double, Double, Double> firstDer =
                (y, delta) -> (f.calculate(y+delta) - f.calculate(y))/delta;
        //http://www.machinelearning.ru/wiki/index.php?title=Вычисление_второй_производной_по_одной_переменной
        // приближённая формула второй производной
        BiFunction <Double, Double, Double> secondDer =
                (y, delta) -> (-f.calculate(y+2*delta) + 16*f.calculate(y+delta)
                        - 30*f.calculate(y) + 16*f.calculate(y-delta)
                        -f.calculate(y-2*delta))/(12*delta*delta);
        BiFunction<Double, Double, Double> der = derivativeDegree <= 1 ? firstDer : secondDer;

        final int percentEpsilon = 1;
        double currPercent = 100;
        double h = 0.01;
//        double prevValue = (f.calculate(x+h)-f.calculate(x))/h;
        double prevValue = der.apply(x, h);
        h /= 2;
//        double currValue = (f.calculate(x+h)-f.calculate(x))/h;
        double currValue = der.apply(x, h);
        double prevDifference = abs(currValue - prevValue);
        h /= 2;
        prevValue = currValue;
//        currValue = (f.calculate(x+h)-f.calculate(x))/h;
        currValue = der.apply(x, h);
        double currDifference = abs(currValue - prevValue);
        currPercent = abs(100.0 / (prevDifference / currDifference));
        while (currPercent > percentEpsilon){
            h /= 2;
            prevValue = currValue;
//            currValue = (f.calculate(x+h)-f.calculate(x))/h;
            currValue = der.apply(x, h);
            prevDifference = currDifference;
            currDifference = abs(currValue - prevValue);
            currPercent = abs(100.0 / (prevDifference / currDifference));
//            System.out.println(Double.toString(currValue) + " " + Double.toString(h));
        }
//        return (f.calculate(x+h)-f.calculate(x))/(h);
        return der.apply(x,h);
    }
}
