package Task4_1ApproximateIntegral.QuadratureFormulas;

import org.mariuszgromada.math.mxparser.Function;

/*АСТ = 0*/
public class QFLeftRectangle extends QuadratureFormula {
    private static final String name = "Левый прямоугольник";
    public QFLeftRectangle(double a, double b, Function func) {
        super(a, b, func);
    }
    public QFLeftRectangle(double a, double b, Function func, Function trueIntegral) {
        super(a, b, func, trueIntegral);
    }

    @Override
    protected double calculate() {
        return (this.b - this.a) * this.func.calculate(this.a);
    }

    @Override
    public String getName() {
        return name;
    }

}
