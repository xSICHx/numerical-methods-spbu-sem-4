package Task5_2.QF_Methods;

import org.mariuszgromada.math.mxparser.Function;

public class Meller {
    private Function f;
    private int N;
    public Meller(int N, String f){
        this.f = new Function(f);
        this.N = N;
        double sm = 0;
        Function cosine = new Function("f(x)= cos(x)");
        for (int i = 1; i <= N ; i++) {
            double x_i = (2*i-1)*Math.PI/(2*N);
            sm += this.f.calculate(cosine.calculate(x_i));
        }
        sm *= Math.PI/N;
        System.out.println(sm);
    }

}
