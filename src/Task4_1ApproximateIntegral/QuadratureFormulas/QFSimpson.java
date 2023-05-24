package Task4_1ApproximateIntegral.QuadratureFormulas;

import org.mariuszgromada.math.mxparser.Function;

/*АСТ = 3*/
public class QFSimpson extends QuadratureFormula {
    private static final String name = "Симпсон";
    public QFSimpson(double a, double b, Function func) {
        super(a, b, func);
    }

    public QFSimpson(double a, double b, Function func, Function trueIntegral) {
        super(a, b, func, trueIntegral);
    }

    @Override
    protected double calculate() {
        return (b - a)/6 * (func.calculate(a) + 4 * func.calculate((a+b)/2) + func.calculate(b));
    }

    @Override
    public String getName() {
        return name;
    }
}
