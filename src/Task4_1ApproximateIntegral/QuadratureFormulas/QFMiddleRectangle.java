package Task4_1ApproximateIntegral.QuadratureFormulas;

import org.mariuszgromada.math.mxparser.Function;

/*АСТ = 1*/
public class QFMiddleRectangle extends QuadratureFormula {
    private static final String name = "Средний прямоугольник";
    public QFMiddleRectangle(double a, double b, Function func) {
        super(a, b, func);
    }

    public QFMiddleRectangle(double a, double b, Function func, Function trueIntegral) {
        super(a, b, func, trueIntegral);
    }

    @Override
    protected double calculate() {
        return (this.b - this.a) * this.func.calculate((this.a+this.b)/2);
    }

    @Override
    public String getName() {
        return name;
    }
}
