package Task3_2FindTheDerivative;

import dnl.utils.text.table.TextTable;
import org.mariuszgromada.math.mxparser.Function;

import javax.swing.table.TableModel;

import static java.lang.Math.abs;

public class DerivativesOfTableDefinedFunction {
    private int m;
    private double a;
    private double h;
    private Double[][] derivativeTable;
    private Function f;
    public DerivativesOfTableDefinedFunction(String func, int m, double a, double h){
        this.m = m;
        this.a = a;
        this.h = h;
        this.f = new Function("f(x) = " + func);
        derivativeTable = new Double[m+1][6];
        for (int i = 0; i < m+1; i++) {
            derivativeTable[i][0] = a + i*h;
            derivativeTable[i][1] = f.calculate(derivativeTable[i][0]);
        }
        for (int i = 0; i < m+1; i++) {
            derivativeTable[i][2] = getFirstDerivative(i);
            derivativeTable[i][3] = abs(6*derivativeTable[i][1] - derivativeTable[i][2]);
        }
        for (int i = 0; i < m+1; i++) {
            derivativeTable[i][4] = getSecondDerivative(i);
            if (derivativeTable[i][4] != null)
                derivativeTable[i][5] = abs(36*derivativeTable[i][1] - derivativeTable[i][4]);
            else derivativeTable[i][5] = null;
        }
        String[] columnNames = {"x_i", "f(x_i)", "f'(x_i)", "|f'_чд(x_i) - f'_t(x_i)|", "f''(x_i)", "|f''_чд(x_i) - f''_t(x_i)|"};
        String[][] table = new String[m+1][6];
        for (int i = 0; i < m+1; i++) {
            // table[i][j] format in exponential notation
            for (int j = 0; j < 6; j++) {
                if (derivativeTable[i][j] != null){
                    if (j == 3 || j == 5)
                        table[i][j] = String.format("%.4e", derivativeTable[i][j]);
                    else
                        table[i][j] = String.valueOf(derivativeTable[i][j]);
                }
                else
                    table[i][j] = "null";
            }
        }
        TextTable tt = new TextTable(columnNames, table);
        tt.printTable();

    }

    private double getFirstDerivative(int i){
        if (m == 1){
            if (i == 0)
                return (derivativeTable[i+1][1] - derivativeTable[i][1])/h;
            else
                return (derivativeTable[i][1] - derivativeTable[i-1][1])/h;
        }

        if (i == 0)
            return (-3*derivativeTable[i][1] + 4*derivativeTable[i+1][1] - derivativeTable[i+2][1])/(2*h);
        else if (i == m)
            return (3*derivativeTable[i][1] - 4*derivativeTable[i-1][1] + derivativeTable[i-2][1])/(2*h);
        else
            return (derivativeTable[i+1][1] - derivativeTable[i-1][1])/(2*h);
    }

    private Double getSecondDerivative(int i){
        if (m == 1){
            return null;
        }
        if (i == 0){
            if (m >= 2)
                return (-2*derivativeTable[i][2] + 5*derivativeTable[i+1][2] - 4*derivativeTable[i+2][2] + derivativeTable[i+3][2])/(h*h);
            else return null;
        } else if (i == m) {
            if (m >= 2)
                return (2*derivativeTable[i][2] - 5*derivativeTable[i-1][2] + 4*derivativeTable[i-2][2] - derivativeTable[i-3][2])/(h*h);
            else return null;
        } else
            return (derivativeTable[i+1][2] - 2*derivativeTable[i][2] + derivativeTable[i-1][2])/(h*h);
    }


}
