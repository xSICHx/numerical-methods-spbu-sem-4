package Task4_1ApproximateIntegral;

import Task4_1ApproximateIntegral.QuadratureFormulas.*;
import dnl.utils.text.table.TextTable;
import org.mariuszgromada.math.mxparser.Function;

import java.util.List;

public class QuadratureFormulaUse {
    private final double a;
    private final double b;
    private final Function func;
    private final Function trueIntegral;
    private final List<QuadratureFormula> results;
    public QuadratureFormulaUse(double a, double b, String func, String trueIntegral) {
        this.a = a;
        this.b = b;
        this.func = new Function(func);
        this.trueIntegral = new Function(trueIntegral);
        this.results = List.of(
                new QFLeftRectangle(a, b, this.func, this.trueIntegral),
                new QFRightRectangle(a, b, this.func, this.trueIntegral),
                new QFMiddleRectangle(a, b, this.func, this.trueIntegral),
                new QFTrapezoid(a, b, this.func, this.trueIntegral),
                new QFSimpson(a, b, this.func, this.trueIntegral),
                new QFThreeEights(a, b, this.func, this.trueIntegral)
        );
    }

    public void printResults(){
        String[] tableColumns = {"Название метода", "J", "|J - I|"};
        String[][] resultsTable = new String[6][3];
        int i = 0;
        for (QuadratureFormula qf: results) {
            resultsTable[i][0] = qf.getName();
            resultsTable[i][1] = String.format("%.4e", qf.getResult());
            resultsTable[i][2] = String.format("%.4e", qf.getAbsActualErr());
            i++;
        }
        TextTable tt = new TextTable(tableColumns, resultsTable);
        tt.printTable();
    }

    public Function getTrueIntegral() {
        return trueIntegral;
    }

    public Function getFunc() {
        return func;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public List<QuadratureFormula> getResults() {
        return results;
    }
}
