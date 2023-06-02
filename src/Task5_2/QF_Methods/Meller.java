package Task5_2.QF_Methods;

import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;
import java.util.List;

public class Meller {
    private Function f;
    private int N;
    private List<Double> coefs;
    private List<Double> nodes;
    private double res;
    public Meller(int N, String f){
        this.f = new Function(f);
        this.N = N;
        nodes = new ArrayList<>();
        coefs = new ArrayList<>();
        double sm = 0;
        Function cosine = new Function("f(x)= cos(x)");
        for (int i = 1; i <= N ; i++) {
            double x_i = (2.0*i-1.0)*Math.PI/(2.0*N);
            sm += this.f.calculate(cosine.calculate(x_i));
            nodes.add(cosine.calculate(x_i));
            coefs.add(Math.PI/N);
        }
        sm *= Math.PI/N;
        res = sm;
    }

    public List<Double> getCoefs() {
        return coefs;
    }

    public List<Double> getNodes() {
        return nodes;
    }

    public double getRes() {
        return res;
    }
}
