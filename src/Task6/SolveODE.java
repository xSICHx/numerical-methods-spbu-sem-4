package Task6;

import Task6.Methods.AdamsODE;
import dnl.utils.text.table.TextTable;
import org.mariuszgromada.math.mxparser.Function;

public class SolveODE {
    private final int N;
    private final double h;
    private final double y_0;
    private final double x_0;
    private final Function dy;
    private Function trueY;
    private double[][] TaylorTable;
    private double[][] AdamsTable;
    private final double[][] trueTable;
    private final int TaylorOrder = 19;


    public SolveODE(int N, double h, double y_0, double x_0, String dy, String trueY) {
        this.N = N;
        this.h = h;
        this.y_0 = y_0;
        this.x_0 = x_0;
        this.dy = new Function(dy);
        this.trueY = new Function(trueY);
        trueTable = new double[N + 2 + 1][2];
        System.out.println("N = " + N + ", h = " + h + ", y_0 = " + y_0 + ", x_0 = " + x_0 + ", dy = " + this.dy.getFunctionExpressionString() + ", trueY = " + this.trueY.getFunctionExpressionString());

        for (int i = -2; i <= N; i++) {
            int k = i+2;
            trueTable[k][0] = x_0 + i * h;
            trueTable[k][1] = this.trueY.calculate(trueTable[k][0]);
        }
        solveTaylor();
        printTasks_1_2_3();

        solveAdam();
        printAdam();
    }


    private void solveTaylor(){
        TaylorTable = new double[N+2+1][2];
        double[] TaylorCoeffs = new double[TaylorOrder];
        TaylorCoeffs[0] = 1;
        TaylorCoeffs[1] = -1;
        for (int i = 2; i < TaylorOrder; i++) {
            TaylorCoeffs[i] = -1*(TaylorCoeffs[i-1]+(i-1)*TaylorCoeffs[i-2]);
        }
        for (int i = -2; i <= N; i++) {
            int k = i+2;
            TaylorTable[k][0] = x_0 + i * h;
            TaylorTable[k][1] = calcTaylor(TaylorTable[k][0], TaylorCoeffs);
        }
    }

    private void solveAdam(){
        double[][] AdamsInitTable = new double[5][2];
        for (int i = 0; i < 5; i++) {
            AdamsInitTable[i][0] = TaylorTable[i][0];
            AdamsInitTable[i][1] = TaylorTable[i][1];
        }
        AdamsODE test = new AdamsODE(N, h, y_0, x_0, "f(x, y)="+dy.getFunctionExpressionString(), AdamsInitTable, 5);
        AdamsTable = test.getAdamsTable();
    }

    private void printAdam(){
        System.out.println("Значения решения, полученные методом Адамса:");
        String[] columns = new String[]{"x_k", "y_k"};
        String[][] tempTable = new String[N+2+1][2];

        for (int i = 0; i < N+2+1; i++) {
            tempTable[i][0] = String.format("%.2f", AdamsTable[i][0]);
            tempTable[i][1] = String.format("%.4e", AdamsTable[i][1]);
        }

        TextTable tt = new TextTable(columns, tempTable);
        tt.printTable();

        double[] errors = new double[N+2+1];
        for (int i = 0; i < N+2+1; i++) {
            errors[i] = Math.abs(trueTable[i][1] - AdamsTable[i][1]);
        }
        for (int i = 0; i < N+2+1; i++) {
            tempTable[i][0] = String.format("%.2f", AdamsTable[i][0]);
            tempTable[i][1] = String.format("%.4e", errors[i]);
        }
        System.out.println("Погрешности Адамса:");
        tt = new TextTable(columns, tempTable);
        tt.printTable();
    }

    private double calcTaylor(double x, double[] coeffs){
        double res = 0;
        long factorial = 1;
        res += coeffs[0];
        for (int i = 1; i < coeffs.length; i++) {
            factorial *= i;
            res += coeffs[i]*Math.pow(x, i) / factorial;
        }
        return res;
    }

    private void printTasks_1_2_3(){
        String[] columns = new String[]{"x_k", "y_k"};
        String[][] tempTable = new String[N+2+1][2];

        for (int i = 0; i < N+2+1; i++) {
            tempTable[i][0] = String.format("%.2f", trueTable[i][0]);
            tempTable[i][1] = String.format("%.4e", trueTable[i][1]);
        }
        System.out.println("Значения настоящего решения:");
        TextTable tt = new TextTable(columns, tempTable);
        tt.printTable();

        for (int i = 0; i < N+2+1; i++) {
            tempTable[i][0] = String.format("%.2f", TaylorTable[i][0]);
            tempTable[i][1] = String.format("%.4e", TaylorTable[i][1]);
        }
        System.out.println("Значения решения, полученные методом Тейлора(количество слагаемых = "+TaylorOrder+"):");
        tt = new TextTable(columns, tempTable);
        tt.printTable();

        double[] errors = new double[N+2+1];
        for (int i = 0; i < N+2+1; i++) {
            errors[i] = Math.abs(trueTable[i][1] - TaylorTable[i][1]);
        }
        for (int i = 0; i < N+2+1; i++) {
            tempTable[i][0] = String.format("%.2f", TaylorTable[i][0]);
            tempTable[i][1] = String.format("%.4e", errors[i]);
        }
        System.out.println("Абсолютная фактическая погрешность при методе Тейлора:");
        tt = new TextTable(columns, tempTable);
        tt.printTable();
    }
}
