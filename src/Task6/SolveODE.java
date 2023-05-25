package Task6;

import Task6.Methods.AdamsODE;
import Task6.Methods.Euler;
import Task6.Methods.RungeKutta;
import dnl.utils.text.table.TextTable;
import org.mariuszgromada.math.mxparser.Function;

import java.util.Objects;

import static java.lang.Math.abs;

public class SolveODE {
    private final int N;
    private final double h;
    private final double y_0;
    private final double x_0;
    private final Function dy;
    private final Function trueY;
    private double[][] TaylorTable;
    private double[][] AdamsTable;
    private double[][] RungeKuttaTable;
    private double[][] EulerTable1;
    private double[][] EulerTable2;
    private double[][] EulerTable3;
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
        printMethodTable("Значения решения, полученные методом Адамса:",
                AdamsTable,
                "Погрешности Адамса (на промежутке O(h^(k+1)), на шаге O(h^(k+2)), в данном случае k=4):"
        );

        solveRungeKutta();
        printMethodTable("Значения решения, полученные методом Рунге-Кутта:",
                RungeKuttaTable,
                "Погрешности Рунге-Кутта (на одном шаге имеет погрешность порядка O(h^5)):"
        );

        solveEuler("1");
        solveEuler("2");
        solveEuler("3");
        printMethodTable("Значения решения, полученные методом Эйлера 1:",
                EulerTable1,
                "Погрешности Эйлера 1 (на одном шаге имеет погрешность порядка O(h^2)):"
        );
        printMethodTable("Значения решения, полученные методом Эйлера 2(средний прямоугольник):",
                EulerTable2,
                "Погрешности Эйлера 2 (на одном шаге имеет погрешность порядка O(h^3)):"
        );
        printMethodTable("Значения решения, полученные методом Эйлера 3(трапеция):",
                EulerTable3,
                "Погрешности Эйлера 3 (на одном шаге имеет погрешность порядка O(h^3)):"
        );

        printLastXForeach();
    }




    private void printLastXForeach(){
        System.out.println("Погрешность последнего значения(N) для каждого из методов:");
        String[] columns = new String[]{"x_i", "Тейлор", "Адамс", "Рунге-Кутта", "Эйлер 1", "Эйлер 2", "Эйлер 3"};
        String[][] data = new String[1][7];

        data[0][0] = String.format("%.2f", TaylorTable[TaylorTable.length-1][0]);
        data[0][1] = String.format("%.4e", abs(trueTable[trueTable.length-1][1] - TaylorTable[TaylorTable.length-1][1]));
        data[0][2] = String.format("%.4e", abs(trueTable[trueTable.length-1][1] - AdamsTable[AdamsTable.length-1][1]));
        data[0][3] = String.format("%.4e", abs(trueTable[trueTable.length-1][1] - RungeKuttaTable[RungeKuttaTable.length-1][1]));
        data[0][4] = String.format("%.4e", abs(trueTable[trueTable.length-1][1] - EulerTable1[EulerTable1.length-1][1]));
        data[0][5] = String.format("%.4e", abs(trueTable[trueTable.length-1][1] - EulerTable2[EulerTable2.length-1][1]));
        data[0][6] = String.format("%.4e", abs(trueTable[trueTable.length-1][1] - EulerTable3[EulerTable3.length-1][1]));
        TextTable tt = new TextTable(columns, data);
        tt.printTable();
    }


    private void solveEuler(String type){
        double[][] table;
        Euler euler = new Euler(N, h, y_0, x_0, "f(x,y)="+dy.getFunctionExpressionString(), type);
        table = euler.getMethodTable();
        if (Objects.equals(type, "1"))
            EulerTable1 = table;
        else if (Objects.equals(type, "2"))
            EulerTable2 = table;
        else
            EulerTable3 = table;
    }



    private void solveRungeKutta(){
        RungeKuttaTable = new double[N+1][2];
        RungeKutta rk = new RungeKutta(N, h, y_0, x_0, "f(x,y)="+dy.getFunctionExpressionString());
        RungeKuttaTable = rk.getMethodTable();
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
        AdamsTable = test.getMethodTable();
    }

    private void printMethodTable(String intro, double[][] table, String errorsString){
        System.out.println(intro);
        String[] columns = new String[]{"x_k", "y_k"};
        String[][] tempTable = new String[table.length][2];

        for (int i = 0; i < table.length; i++) {
            tempTable[i][0] = String.format("%.2f", table[i][0]);
            tempTable[i][1] = String.format("%.4e", table[i][1]);
        }

        TextTable tt = new TextTable(columns, tempTable);
        tt.printTable();

        double[] errors = new double[table.length];
        for (int i = 0; i < table.length; i++) {
            errors[i] = abs(trueTable[i+(N+2+1-table.length)][1] - table[i][1]);
        }
        for (int i = 0; i < table.length; i++) {
            tempTable[i][0] = String.format("%.2f", table[i][0]);
            tempTable[i][1] = String.format("%.4e", errors[i]);
        }
        System.out.println(errorsString);
        tt = new TextTable(columns, tempTable);
        tt.printTable();
        System.out.println();
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
        System.out.println();

        for (int i = 0; i < N+2+1; i++) {
            tempTable[i][0] = String.format("%.2f", TaylorTable[i][0]);
            tempTable[i][1] = String.format("%.4e", TaylorTable[i][1]);
        }
        System.out.println("Значения решения, полученные методом Тейлора(количество слагаемых = "+TaylorOrder+"):");
        tt = new TextTable(columns, tempTable);
        tt.printTable();

        double[] errors = new double[N+2+1];
        for (int i = 0; i < N+2+1; i++) {
            errors[i] = abs(trueTable[i][1] - TaylorTable[i][1]);
        }
        for (int i = 0; i < N+2+1; i++) {
            tempTable[i][0] = String.format("%.2f", TaylorTable[i][0]);
            tempTable[i][1] = String.format("%.4e", errors[i]);
        }
        System.out.println("Абсолютная фактическая погрешность при методе Тейлора:");
        tt = new TextTable(columns, tempTable);
        tt.printTable();
        System.out.println();
    }
}
