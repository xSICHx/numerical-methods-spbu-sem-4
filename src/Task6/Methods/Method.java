package Task6.Methods;

import org.mariuszgromada.math.mxparser.Function;

public abstract class Method {
    protected int N;
    protected double h;
    protected double y_0;
    protected double x_0;
    protected Function dy;
    public Method(int N, double h, double y_0, double x_0, String dy) {
        this.N = N;
        this.h = h;
        this.y_0 = y_0;
        this.x_0 = x_0;
        this.dy = new Function(dy);
    }
}
