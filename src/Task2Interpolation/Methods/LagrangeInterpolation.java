package Task2Interpolation.Methods;


import Task2Interpolation.Polynomial.Polynomial;

import java.util.ArrayList;
import java.util.List;

public class LagrangeInterpolation extends MethodInterpolation {
    double resultValue = 0;
    public LagrangeInterpolation(List<List<Double>> fTable, double x, int n) {
        super(fTable, x, n);
        name = "Lagrange";
        calculatePolynomial();
        calculateResultValue();
    }


    void calculateResultValue(){
        for (int i = 0; i < n; i++) {
            //f_i
            double l_i = fTable.get(i).get(1);
            double x_i = fTable.get(i).get(0);
            double divide = 1;
            //calculate l_i
            for (int j = 0; j < n; j++) {
                if (j == i)
                    continue;
                double x_j = fTable.get(j).get(0);
                divide = divide * (x_i-x_j);
                double temp = x-x_j;
                l_i = l_i * temp;
            }
            l_i = l_i /divide;
            resultValue = resultValue + l_i;
        }
    }

    public double getResultValue() {
        return resultValue;
    }

    void calculatePolynomial(){
        for (int i = 0; i < n; i++) {
            //f_i
            Polynomial l_i = new Polynomial(new double[]{fTable.get(i).get(1)});

            double x_i = fTable.get(i).get(0);
            double divide = 1;
            //calculate l_i
            for (int j = 0; j < n; j++) {
                if (j == i)
                    continue;
                double x_j = fTable.get(j).get(0);
                divide = divide * (x_i-x_j);
                Polynomial temp = new Polynomial(new double[]{-x_j, 1});
                l_i = l_i.multiply(temp);
            }
            l_i = l_i.multiply(new Polynomial( new double[]{1/divide}));
            result = result.plus(l_i);
        }
    }

    public double eval(double f) {
        return super.eval(f);
    }
}
