package Task4_3;

import Task4_2CompoundQuadratureFormulas.CompoundQuadratureFormula;
import dnl.utils.text.table.TextTable;
import org.mariuszgromada.math.mxparser.Function;

import java.util.HashMap;
import java.util.List;

import static java.lang.Math.abs;

public class CQFExpandUse{
    private final double a;
    private final double b;
    private final double m;
    private final double l;
    private final Function func;
    private final Function trueIntegral;
    private final TextTable resultsTable;

    private class ParallelCalc extends Thread{
        private static final HashMap<String, Double> rungeConstants = new HashMap<>(){{
           put("LR", 1.0);
           put("RR", 1.0);
           put("MR", 1.0/3.0);
           put("TR", 1.0/3.0);
           put("S", 1.0/15.0);
        }};
        private CompoundQuadratureFormula qf_m;
        private CompoundQuadratureFormula qf_ml;
        private double errM;
        private double errML;
        private final String method;
        public ParallelCalc(String method){
            this.method = method;
        }
        @Override
        public void run() {
            qf_m = new CompoundQuadratureFormula(method, a, b, m, func.cloneForThreadSafe(), trueIntegral.cloneForThreadSafe());
            qf_ml = new CompoundQuadratureFormula(method, a, b, m*l, func.cloneForThreadSafe(), trueIntegral.cloneForThreadSafe());
            CompoundQuadratureFormula qf_m_2 = new CompoundQuadratureFormula(method, a, b, m/2, func.cloneForThreadSafe(), trueIntegral.cloneForThreadSafe());
            CompoundQuadratureFormula qf_ml_2 = new CompoundQuadratureFormula(method, a, b, m*l/2, func.cloneForThreadSafe(), trueIntegral.cloneForThreadSafe());
            errML = (qf_ml.getResult() - qf_ml_2.getResult())*getRungeConstant(method);
            errM = (qf_m.getResult() - qf_m_2.getResult())*getRungeConstant(method);
        }

        private double getRungeConstant(String method){
            if (rungeConstants.containsKey(method))
                return rungeConstants.get(method);
            return rungeConstants.get("TR");
        }

        public CompoundQuadratureFormula getQf_m() {
            return qf_m;
        }

        public CompoundQuadratureFormula getQf_ml() {
            return qf_ml;
        }

        public double getErrM() {
            return errM;
        }

        public double getErrML() {
            return errML;
        }
    }

    public CQFExpandUse(double a, double b, double m, double l, String func, String trueIntegral) {
        this.a = a;
        this.b = b;
        this.m = m;
        this.l = l;
        this.func = new Function(func);
        this.trueIntegral = new Function(trueIntegral);

        String[] tableColumns = {"Название метода", "J(h)", "|J(h) - I|", "Уточнённое J(h)", "|Уточнённое J(h) - I|", "||||", "J(h/l)", "|J(h/l) - I|", "Уточнённое J(h/l)", "|Уточнённое J(h/l) - I|"};
        String[][] resTable = new String[5][10];
        List<CQFExpandUse.ParallelCalc> results = List.of(
                new CQFExpandUse.ParallelCalc("LR"),
                new CQFExpandUse.ParallelCalc("RR"),
                new CQFExpandUse.ParallelCalc("MR"),
                new CQFExpandUse.ParallelCalc("TR"),
                new CQFExpandUse.ParallelCalc("S")
        );

        for (CQFExpandUse.ParallelCalc pc: results) {
            pc.start();
        }
        for (CQFExpandUse.ParallelCalc pc: results) {
            try {
                pc.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int i = 0;
        for (CQFExpandUse.ParallelCalc pc: results) {
            CompoundQuadratureFormula qf_m = pc.getQf_m();
            CompoundQuadratureFormula qf_ml = pc.getQf_ml();
            double trueIntegralRes = this.trueIntegral.calculate(b) - this.trueIntegral.calculate(a);
            double errM = pc.getErrM();
            double errML = pc.getErrML();
            System.out.println(qf_ml.getName() + " " + errM);
            resTable[i][0] = qf_m.getName();
            resTable[i][1] = String.format("%.4e", qf_m.getResult());
            resTable[i][2] = String.format("%.4e", qf_m.getAbsActualErr());
            resTable[i][3] = String.format("%.4e", qf_m.getResult()+errM);
            resTable[i][4] = String.format("%.4e", abs(qf_m.getResult()+errM - trueIntegralRes));
            resTable[i][5] = "||||";
            resTable[i][6] = String.format("%.4e", qf_ml.getResult());
            resTable[i][7] = String.format("%.4e", qf_ml.getAbsActualErr());
            resTable[i][8] = String.format("%.4e", qf_ml.getResult()+errML);
            resTable[i][9] = String.format("%.4e", abs(qf_ml.getResult()+errML - trueIntegralRes));
            i++;
        }
        resultsTable = new TextTable(tableColumns, resTable);
    }

    public void printTable(){

        System.out.println("Результаты при m = " + m + ", l = " + l + ", a = " + a + ", b = " + b +
                ", f(x) = " + func.getFunctionExpressionString() +
                ", I(x) = " + trueIntegral.getFunctionExpressionString() + ", I(x)|a,b = " + (trueIntegral.calculate(b)-trueIntegral.calculate(a))+ ":");

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
