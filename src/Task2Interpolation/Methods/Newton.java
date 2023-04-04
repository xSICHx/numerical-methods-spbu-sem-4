package Task2Interpolation.Methods;


import Task2Interpolation.Polynomial.Polynomial;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Newton extends MethodInterpolation {
    int pointsConstructed;
    List<List<Polynomial>> polyElems;
    public Newton(List<List<Double>> fTable, double x, int n) {
        super(fTable, x, n);
        name = "Newton";
        pointsConstructed = 0;
        polyElems = new ArrayList<>();
        calculatePolynomial();
        System.out.println(polyElems);
    }
    private List<Polynomial> calcElementInSum(int index){
        if (index == 0)
            return new ArrayList<>(Collections.singleton(new Polynomial(new double[]{fTable.get(index).get(1)})));
        List<Polynomial> result = new ArrayList<>(Collections.singleton(new Polynomial(new double[]{calcDividedDifference(index)})));
        for (int i = 0; i < index; i++) {
            Polynomial temp = new Polynomial(new double[]{-fTable.get(i).get(0), 1});
            result.add(temp);
        }
        return result;
    }
    void calculatePolynomial(){
        for (int i = pointsConstructed; i < n; ++i) {
            polyElems.add(calcElementInSum(i));
        }
    }


    @Override
    public double eval(double x) {
        double res = 0;
        for (List<Polynomial> listPoly: polyElems) {
            double temp = 1;
            for (Polynomial poly: listPoly) {
                temp *= poly.evaluate(x);
            }
            res += temp;
        }
        return res;}


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

    @Override
    public String toString() {
        String str = "";
        boolean flagSum = false;
        for (List<Polynomial> listPoly: polyElems) {
            flagSum = true;
            String temp = "";
            boolean flagMult = false;
            for (Polynomial poly: listPoly) {
                flagMult = true;
                temp += "("+poly.toString()+")*";
            }
            if (flagMult)
                temp += "1";
            str += temp + "+";
        }
        if (flagSum)
            str += "0";
        return str;
    }
}
