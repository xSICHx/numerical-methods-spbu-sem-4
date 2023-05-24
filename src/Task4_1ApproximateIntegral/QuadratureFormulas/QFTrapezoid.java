package Task4_1ApproximateIntegral.QuadratureFormulas;

import org.mariuszgromada.math.mxparser.Function;

/*АСТ = 1*/
public class QFTrapezoid extends QuadratureFormula {
    private static final String name = "Трапеция";
    public QFTrapezoid(double a, double b, Function func) {
        super(a, b, func);
    }

    public QFTrapezoid(double a, double b, Function func, Function trueIntegral) {
        super(a, b, func, trueIntegral);
    }

    @Override
    protected double calculate() {
        return (b-a)/2 * (func.calculate(a) + func.calculate(b));
    }

    @Override
    public String getName() {
        return name;
    }
}
