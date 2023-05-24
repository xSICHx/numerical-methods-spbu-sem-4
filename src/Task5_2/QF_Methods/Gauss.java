package Task5_2.QF_Methods;

import Task1ApproximateRoots.FindApproximateRoot;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

public class Gauss {
    private double a;
    private double b;
    private int N;
    private Function f;
    private double epsilon;
    private List<String> poly;
    private List<Double> nodes;
    private List<Double> coefs;
    public Gauss(double a, double b, int N, double epsilon, String f){
        this.a = a;
        this.b = b;
        this.epsilon = epsilon;
        this.N = N;
        this.f = new Function(f);
        this.poly = new ArrayList<>();
        fillPoly();
        coefs = new ArrayList<>();
        fillNodes();
    }

    private void fillPoly(){
        poly.add("1");
        poly.add("x");

        for (int i = 2; i <= N; i++){

            String p_i_str =
                    "("+(2.0*i-1.0)/i+"*x*("+poly.get(i-1)+")"+
                    "-("+ (i-1.0)/i + "*("+poly.get(i-2)+"))"+
                    ")";
            poly.add(p_i_str);
        }
    }

    private void fillNodes(){
        FindApproximateRoot poly_roots = new FindApproximateRoot("f(x)="+poly.get(N), -1, 1, 100, epsilon, "secant");
        int partition = 1000;
        while (poly_roots.getArrayOfRoots().size() < N){
            partition *= 2;
            poly_roots = new FindApproximateRoot("f(x)="+poly.get(N), -1, 1, partition, epsilon, "secant");
        }
        this.nodes = poly_roots.getArrayOfRoots();
        sort(this.nodes);
        Function p_n_minus_1 = new Function("f(x)="+poly.get(N-1));
        for (Double t : nodes) {
            coefs.add(2*(1-t*t)/(N*N * p_n_minus_1.calculate(t)*p_n_minus_1.calculate(t)));
        }

        double sm = 0;
        for(int i = 0; i < N; i++){
            nodes.set(i, (b-a)/2*nodes.get(i) + (b+a)/2);
            coefs.set(i, (b-a)/2*coefs.get(i));
            sm += coefs.get(i)*this.f.calculate(nodes.get(i));
        }

        System.out.println(sm);
    }
}
