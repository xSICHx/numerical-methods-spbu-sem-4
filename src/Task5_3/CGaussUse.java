package Task5_3;

import dnl.utils.text.table.TextTable;
import org.mariuszgromada.math.mxparser.Function;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.abs;

public class CGaussUse {
    private double a;
    private double b;
    private int N;
    private int m;
    private Function f;
    private Function trueIntegral;
    private double trueIntVal;
    private double epsilon;

    public CGaussUse(double a, double b, int n, int m, double epsilon, String f, String trueIntegral, int flagOfRequestNewParameters) {
        this.a = a;
        this.b = b;
        N = n;
        this.m = m;
        this.f = new Function(f);
        this.epsilon = epsilon;
        this.trueIntegral = new Function(trueIntegral);

        System.out.println("СКФ Гаусса");
        System.out.println("a = " + a + ", b = " + b + ", n = " + n + ", m = " + m + ", epsilon = " + epsilon + ", " + f + ", trueIntegral = " + trueIntegral);
        CGauss cGauss = new CGauss(a, b, n, m, epsilon, f, trueIntegral);
        List<Double> nodes = cGauss.getNodes();
        List<Double> coeffs = cGauss.getCoefs();
        String[] columns = new String[]{"i", "Узел", "Коэффициент"};
        String[][] data = new String[nodes.size()][3];
        for (int i = 0; i < nodes.size(); i++) {
            data[i][0] = String.valueOf(i);
            data[i][1] = String.format("%.5e", nodes.get(i));
            data[i][2] = String.format("%.5e", coeffs.get(i));
        }
        TextTable tt = new TextTable(columns, data);
        tt.printTable();
        trueIntVal = this.trueIntegral.calculate(b) - this.trueIntegral.calculate(a);
        System.out.println("Результат: " + cGauss.getRes());
        System.out.println("Абсолютная погрешность: " + cGauss.getAbsError());

        if (flagOfRequestNewParameters != 0){
            System.out.println("Желаете ввести новые N, m, epsilon? (1/0)");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            while (choice == 1){
                System.out.println("Введите N, m, epsilon");
                N = scanner.nextInt();
                m = scanner.nextInt();
                epsilon = scanner.nextDouble();
                System.out.println("a = " + a + ", b = " + b + ", n = " + N + ", m = " + m + ", epsilon = " + epsilon + ", " + f + ", trueIntegral = " + trueIntegral);
                cGauss = new CGauss(a, b, N, m, epsilon, f, trueIntegral);
                nodes = cGauss.getNodes();
                coeffs = cGauss.getCoefs();
                columns = new String[]{"i", "Узел", "Коэффициент"};
                data = new String[nodes.size()][3];
                for (int i = 0; i < nodes.size(); i++) {
                    data[i][0] = String.valueOf(i);
                    data[i][1] = String.format("%.5e", nodes.get(i));
                    data[i][2] = String.format("%.5e", coeffs.get(i));
                }
                tt = new TextTable(columns, data);
                tt.printTable();
                System.out.println("Результат: " + cGauss.getRes());
                System.out.println("Абсолютная погрешность: " + cGauss.getAbsError());
                System.out.println("Желаете ввести новые N, m, epsilon? (1/0)");
                choice = scanner.nextInt();
            }
        }
    }

    public void getBestParams(){
        System.out.println("Поиск наилучших параметров: ");
        System.out.println("N от 1 до 10 с шагом 1, m от 1 до 100 с шагом 10, epsilon = 1e-13");
        double bestAbsError = Double.MAX_VALUE;
        HashMap<String, Double> bestParams = new HashMap<>();
        for (int n = 1; n < 10; n++) {
            for (int m = 1; m < 110; m+=10) {
                double currEps = 1e-12;
                for (int i = 0; i < 1; i++) {
                    currEps *= 0.1;
                    CGauss cGauss = new CGauss(a, b, n, m, currEps, "f(x) = "+f.getFunctionExpressionString(), "f(x) = "+trueIntegral);
                    double currAbsErr = abs(cGauss.getRes()-trueIntVal);

                    if (  0 < currAbsErr && currAbsErr < bestAbsError){
                        bestAbsError = currAbsErr;
                        bestParams.put("n", (double) n);
                        bestParams.put("m", (double) m);
                        bestParams.put("epsilon", currEps);
                    }
                }
            }
        }
        System.out.println("Лучшие параметры: ");
        System.out.println("n = " + bestParams.get("n") + ", m = " + bestParams.get("m") + ", epsilon = " + bestParams.get("epsilon")+
                ", абсолютная погрешность = " + bestAbsError);
    }

    private double calcAbsError(double res){
        return abs(res - trueIntegral.calculate());
    }
}
