package Task4_2CompoundQuadratureFormulas;

import dnl.utils.text.table.TextTable;
import org.mariuszgromada.math.mxparser.Function;

import java.util.List;

public class CQFUse {
    private final double a;
    private final double b;
    private final double m;
    private final Function func;
    private final Function trueIntegral;
    private final TextTable resultsTable;

    private class ParallelCalc extends Thread{
        private CompoundQuadratureFormula qf;
        private final String method;
        public ParallelCalc(String method){
            this.method = method;
        }
        @Override
        public void run() {
            qf = new CompoundQuadratureFormula(method, a, b, m, func.cloneForThreadSafe(), trueIntegral.cloneForThreadSafe());
        }
        public CompoundQuadratureFormula getQf() {
            return qf;
        }
    }

    public CQFUse(double a, double b, double m, String func, String trueIntegral) {
        this.a = a;
        this.b = b;
        this.m = m;
        this.func = new Function(func);
        this.trueIntegral = new Function(trueIntegral);

        String[] tableColumns = {"Название метода", "J(h)", "|J(h) - I|"};
        String[][] resTable = new String[5][3];
        List<ParallelCalc> results = List.of(
                new ParallelCalc("LR"),
                new ParallelCalc("RR"),
                new ParallelCalc("MR"),
                new ParallelCalc("TR"),
                new ParallelCalc("S")
        );

        for (ParallelCalc pc: results) {
            pc.start();
        }
        for (ParallelCalc pc: results) {
            try {
                pc.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int i = 0;
//        for (CompoundQuadratureFormula cqf: results) {
        for (ParallelCalc pc: results) {
            CompoundQuadratureFormula cqf = pc.getQf();
            resTable[i][0] = cqf.getName();
            resTable[i][1] = String.format("%.4e", cqf.getResult());
            resTable[i][2] = String.format("%.4e", cqf.getAbsActualErr());
            i++;
        }
        resultsTable = new TextTable(tableColumns, resTable);
    }

    public void printTable(){

        System.out.println("Результаты при m = " + m + ", a = " + a + ", b = " + b +
                ", f(x) = " + func.getFunctionExpressionString() +
                ", I(x) = " + trueIntegral.getFunctionExpressionString() + ":");
        resultsTable.printTable();
        System.out.println();
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getM() {
        return m;
    }

    public Function getFunc() {
        return func;
    }

    public Function getTrueIntegral() {
        return trueIntegral;
    }

    public TextTable getResultsTable() {
        return resultsTable;
    }
}
