package Task3_1ReverseInterpolation.Methods;

import Task2Interpolation.Methods.LagrangeInterpolation;
import Task2Interpolation.Methods.Newton;

import java.util.List;

/** Метод, вычисляет f^-1 при условии, что функция монотонна*/
public class ReversedTableInterpolation extends MethodReverseInterpolation {
//    private LagrangeInterpolation lagrangeInterpolation;
    private Newton newton;
    public ReversedTableInterpolation(List<List<Double>> fXTable, double F, int n) {
        super(fXTable, F, n);

        if (!checkMonotony()){
            return;
        }
        sortFTable();

        newton = new Newton(fXTable, F, n);
//        lagrangeInterpolation = new LagrangeInterpolation(fXTable, F, n);
//        defaultSortByX();
    }
    private boolean checkMonotony(){

        if (fTable.size() <= 1)
            return true;

        boolean flag1 = true;
        double temp = fTable.get(0).get(0);
        for (int i = 1; i < fTable.size(); i++) {
            List<Double> doubles = fTable.get(i);
            if (temp <= doubles.get(0)) {
                flag1 = false;
                break;
            }
            temp = doubles.get(0);
        }

        boolean flag2 = true;
        temp = fTable.get(0).get(0);
        for (int i = 1; i < fTable.size(); i++) {
            List<Double> doubles = fTable.get(i);
            if (temp >= doubles.get(0)) {
                flag2 = false;
                break;
            }
            temp = doubles.get(0);
        }
        return flag2 || flag1;
    }

    public double eval(double f) {
//        return lagrangeInterpolation.eval(f);
        return newton.eval(f);
    }

    public boolean exist(){

//        return lagrangeInterpolation != null;
        return newton != null;
    }
}
