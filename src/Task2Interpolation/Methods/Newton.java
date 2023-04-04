package Task2Interpolation.Methods;


import Task2Interpolation.Polynomial.Polynomial;

import java.util.List;

public class Newton extends MethodInterpolation {
    int pointsConstructed;
    public Newton(List<List<Double>> fTable, double x, int n) {
        super(fTable, x, n);
        name = "Newton";
        pointsConstructed = 0;
        calculatePolynomial();
    }
    void calculatePolynomial(){
        for (int i = pointsConstructed; i < n; ++i) {
            result = result.plus(calcElementInSum(i));
            ++pointsConstructed;
        }
    }

    private Polynomial calcElementInSum(int index){
        if (index == 0)
            return new Polynomial(new double[]{fTable.get(index).get(1)});
        Polynomial result = new Polynomial(new double[]{calcDividedDifference(index)});
        for (int i = 0; i < index; i++) {
            Polynomial temp = new Polynomial(new double[]{-fTable.get(i).get(0), 1});
            result = result.multiply(temp);
        }

        return result;
    }

    private double calcDividedDifference(int index){
        double result = 0;
        for(int i = 0; i <= index; i++){
            double product = 1;
            for(int j =0; j <= index;j++){
                if(i == j)
                    continue;
                product *= (fTable.get(i).get(0) - fTable.get(j).get(0));

            }
            result += fTable.get(i).get(1)/product;
        }
        return result;
    }
}
