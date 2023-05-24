package Task4_1ApproximateIntegral.QuadratureFormulas;

import org.mariuszgromada.math.mxparser.Function;

/*АСТ = 3*/
public class QFThreeEights extends QuadratureFormula {
    private static final String name = "3/8";
    public QFThreeEights(double a, double b, Function func) {
        super(a, b, func);
    }

    public QFThreeEights(double a, double b, Function func, Function trueIntegral) {
        super(a, b, func, trueIntegral);
    }

    @Override
    protected double calculate() {
        double h = (b - a)/3;
        return (b - a) * (func.calculate(a)/8 + 3*func.calculate(a + h)/8 +
                3*func.calculate(a+2*h)/8 + func.calculate(b)/8);
    }

    @Override
    public String getName() {
        return name;
    }
}
