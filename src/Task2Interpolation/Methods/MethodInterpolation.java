package Task2Interpolation.Methods;

import Task2Interpolation.Polynomial.Polynomial;

import java.util.List;

public abstract class MethodInterpolation {
    String name;
    List<List<Double>> fTable;
    double x;
    Polynomial result;
    int n;


    public MethodInterpolation(List<List<Double>> fTable, double x, int n) {
        this.fTable = fTable;
        this.x = x;
        this.n = n;
        this.result = new Polynomial(new double[]{0});
    }
    abstract void calculatePolynomial();
    public double eval(double x){
        return result.evaluate(x);
    }

    public void printPoly(){
        String str = result.toString();
        System.out.println(str);
    }
    @Override
    public String toString(){
        return result.toString();
    }
}
