package Task4_1ApproximateIntegral.QuadratureFormulas;

import org.mariuszgromada.math.mxparser.Function;

import static java.lang.Math.abs;

public abstract class QuadratureFormula {
    protected double a;
    protected double b;
    protected Function func;
    protected Function trueIntegral;
    protected double result;
    protected Double absActualErr;

    public QuadratureFormula(double a, double b, Function func) {
        this.a = a;
        this.b = b;
        this.func = func;
        this.trueIntegral = null;
        this.result = calculate();
        this.absActualErr = null;
    }

    public QuadratureFormula(double a, double b, Function func, Function trueIntegral){
        this.a = a;
        this.b = b;
        this.func = func;
        this.trueIntegral = trueIntegral;
        this.result = calculate();
        this.absActualErr = abs(trueIntegral.calculate(b) - trueIntegral.calculate(a) - this.result);
    }

    public QuadratureFormula() {}

    protected abstract double calculate();
    public double recalculate(double a, double b){
        this.a = a;
        this.b = b;
        this.result = calculate();
        return this.result;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public Function getFunc() {
        return func;
    }

    public Function getTrueIntegral() {
        return trueIntegral;
    }

    public abstract String getName();

    public double getResult() {
        return result;
    }

    public Double getAbsActualErr() {
        return absActualErr;
    }
}
