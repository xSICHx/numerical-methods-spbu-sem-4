package Task5_3;

import Task5_2.QF_Methods.Gauss;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;
import java.util.List;

public class CGauss {
    private double a;
    private double b;
    private int N;
    private Function f;
    private double epsilon;
    private List<String> poly;
    private List<Double> nodes;
    private List<Double> coefs;
    private Function trueIntegral;
    private double absError;
    private int m;
    private double res;


    public CGauss(double a, double b, int N, int m, double epsilon, String f, String trueIntegral){
        this.a = a;
        this.b = b;
        this.epsilon = epsilon;
        this.N = N;
        this.f = new Function(f);
        this.poly = new ArrayList<>();
        this.coefs = new ArrayList<>();
        this.trueIntegral = new Function(trueIntegral);
        Gauss gauss = new Gauss(-1, 1, N, epsilon, f, trueIntegral);
        this.m = m;
        this.coefs = gauss.getCoefs();
        this.nodes = gauss.getNodes();
        double h = (b-a)/m;
        res = 0;
        for (int j = 0; j < m; j++) {
            double z_j = a+j*h;
            double z_j_next = z_j+h;
            double temp_sm = 0;
            for (int k = 0; k < N; k++) {
                double x_k_j = h/2.0 * nodes.get(k) + (z_j+z_j_next)/2;
                temp_sm += coefs.get(k)*this.f.calculate(x_k_j);
            }
            res += temp_sm;
        }
        res *= h/2;
        this.absError = Math.abs(this.trueIntegral.calculate(b) - this.trueIntegral.calculate(a) - res);


        this.absError = Math.abs(this.trueIntegral.calculate(b) - this.trueIntegral.calculate(a) - res);
    }


    public List<Double> getNodes() {
        return nodes;
    }

    public List<Double> getCoefs() {
        return coefs;
    }

    public double getRes() {
        return res;
    }

    public double getAbsError() {
        return absError;
    }
}
