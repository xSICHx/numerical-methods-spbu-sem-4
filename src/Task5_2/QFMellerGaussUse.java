package Task5_2;

import Task5_2.QF_Methods.Gauss;
import Task5_2.QF_Methods.Meller;
import dnl.utils.text.table.TextTable;

import java.util.List;
import java.util.Scanner;

import static java.lang.Math.abs;

public class QFMellerGaussUse {
    int N1;
    int N2;
    int N3;
    double epsilon;
    public QFMellerGaussUse(int N1, int N2, int N3, double epsilon){
        this.N1 = N1;
        this.N2 = N2;
        this.N3 = N3;
        this.epsilon = epsilon;


        Gauss gauss;
        System.out.println("Узлы и коэффициенты КФ Гаусса при N = 1, 2, 3, 4, 5, 6, 7, 8");
        for (int N = 1; N <= 8; N++) {
            System.out.println("N = " + N);
            String[][] data = new String[N][2];
            gauss = new Gauss(-1, 1, N, epsilon, "f(x) = cos(x^2)");
            List<Double> coeffs = gauss.getCoefs();
            List<Double> nodes = gauss.getNodes();
            for (int i = 0; i < gauss.getCoefs().size(); i++) {
                data[i][0] = String.format("%.10f", nodes.get(i));
                data[i][1] = String.format("%.10f", coeffs.get(i));
            }
            String[] columns = new String[]{"Узел", "Коэффициент"};
            TextTable tt = new TextTable(columns, data);
            tt.printTable();
        }



        System.out.println();
        System.out.println("Проверим точность на многочлене наивысшей степени для N = 8:");
        gauss = new Gauss(-0.5, 1, 8, 1e-12, "f(x) = x", "f(x) = x^2/2");
        System.out.println("a = -0.5, b = 1, N = 8, epsilon = 1e-12");
        System.out.println("f(x) = x\nАбсолютная фактическая погрешность: " + gauss.getAbsError());
        gauss = new Gauss(-0.5, 1, 8, 1e-12, "f(x) = x^15", "f(x) = x^16/16");
        System.out.println("f(x) = x^15\nАбсолютная фактическая погрешность: " + gauss.getAbsError());
        gauss = new Gauss(-0.5, 1, 8, 1e-12, "f(x) = x^16", "f(x) = x^17/17");
        System.out.println("f(x) = x^16\nАбсолютная фактическая погрешность: " + gauss.getAbsError());


        System.out.println();
        System.out.println();
        System.out.println("КФ Гаусса при a = 0, b = pi/4, rho(x) = 1, f(x) = cos(x^2), N = 3, 6, 7, 8.");
        System.out.println("Значение данного интеграла, посчитанное с помощью WolframAlpha = 0.75603527746291752180012503952467628");
        double temp = 0.75603527746291752180012503952467628;
        gauss = new Gauss(0, Math.PI/4, 3, 1e-15, "f(x) = cos(x^2)");
        System.out.println("N = 3\nЗначение интеграла: " + gauss.getRes());
        System.out.println("Абсолютная фактическая погрешность: " + abs(gauss.getRes()-temp));
        printGaussTable(gauss);

        gauss = new Gauss(0, Math.PI/4, 6, 1e-15, "f(x) = cos(x^2)");
        System.out.println("N = 6\nЗначение интеграла: " + gauss.getRes());
        System.out.println("Абсолютная фактическая погрешность: " + abs(gauss.getRes()-temp));
        printGaussTable(gauss);

        gauss = new Gauss(0, Math.PI/4, 7, 1e-15, "f(x) = cos(x^2)");
        System.out.println("N = 7\nЗначение интеграла: " + gauss.getRes());
        System.out.println("Абсолютная фактическая погрешность: " + abs(gauss.getRes()-temp));
        printGaussTable(gauss);

        gauss = new Gauss(0, Math.PI/4, 8, 1e-15, "f(x) = cos(x^2)");
        System.out.println("N = 8\nЗначение интеграла: " + gauss.getRes());
        System.out.println("Абсолютная фактическая погрешность: " + abs(gauss.getRes()-temp));
        printGaussTable(gauss);


        System.out.println();
        System.out.println("Желаете ввести новые параметры a, b, N? (1/0)");
        Scanner in = new Scanner(System.in);
        int answer = in.nextInt();
        while (answer == 1){
            System.out.println("Введите a:");
            double a = in.nextDouble();
            System.out.println("Введите b:");
            double b = in.nextDouble();
            System.out.println("Введите N:");
            int N = in.nextInt();
            gauss = new Gauss(a, b, N, epsilon, "f(x) = cos(x^2)");
            System.out.println("Значение интеграла: " + gauss.getRes());
            printGaussTable(gauss);
            System.out.println("Желаете ввести новые параметры a, b? (1/0)");
            answer = in.nextInt();
        }


        System.out.println();
        System.out.println("КФ Меллера, f(x) = exp(x) * sin(x^2):");
        Meller meller;
        System.out.println("N1 = "+ N1+":");
        meller = new Meller(N1,"f(x) = exp(x) * sin(x^2)");
        System.out.println("Значение интеграла: " + String.format("%.12f", meller.getRes()));
        printMellerTable(meller);

        System.out.println("N2 = "+ N2+":");
        meller = new Meller(N2,"f(x) = exp(x) * sin(x^2)");
        System.out.println("Значение интеграла: " + String.format("%.12f", meller.getRes()));
        printMellerTable(meller);

        System.out.println("N3 = "+ N3+":");
        meller = new Meller(N3,"f(x) = exp(x) * sin(x^2)");
        System.out.println("Значение интеграла: " + String.format("%.12f", meller.getRes()));
        printMellerTable(meller);


        System.out.println();
        System.out.println("Желаете ввести новые параметры N1, N2, N3? (1/0)");
        answer = in.nextInt();
        while (answer == 1){
            System.out.println("Введите N1:");
            N1 = in.nextInt();
            System.out.println("Введите N2:");
            N2 = in.nextInt();
            System.out.println("Введите N3:");
            N3 = in.nextInt();

            meller = new Meller(N1,"f(x) = exp(x) * sin(x^2)");
            System.out.println("N1 = "+ N1+":");
            System.out.println("Значение интеграла: " + String.format("%.12f", meller.getRes()));
            printMellerTable(meller);

            meller = new Meller(N2,"f(x) = exp(x) * sin(x^2)");
            System.out.println("N2 = "+ N2+":");
            System.out.println("Значение интеграла: " + String.format("%.12f", meller.getRes()));
            printMellerTable(meller);

            meller = new Meller(N3,"f(x) = exp(x) * sin(x^2)");
            System.out.println("N3 = "+ N3+":");
            System.out.println("Значение интеграла: " + String.format("%.12f", meller.getRes()));
            printMellerTable(meller);

            System.out.println("Желаете ввести новые параметры N1, N2, N3? (1/0)");
            answer = in.nextInt();
        }
    }

    private void printMellerTable(Meller meller){
        int N = meller.getCoefs().size();
        String[][] data = new String[N][2];
        List<Double> coeffs = meller.getCoefs();
        List<Double> nodes = meller.getNodes();
        for (int i = 0; i < meller.getCoefs().size(); i++) {
            data[i][0] = String.format("%.10f", nodes.get(i));
            data[i][1] = String.format("%.10f", coeffs.get(i));
        }
        String[] columns = new String[]{"Узел", "Коэффициент"};
        TextTable tt = new TextTable(columns, data);
        tt.printTable();
    }
    private void printGaussTable(Gauss gauss){
            int N = gauss.getCoefs().size();
            String[][] data = new String[N][2];
            List<Double> coeffs = gauss.getCoefs();
            List<Double> nodes = gauss.getNodes();
            for (int i = 0; i < gauss.getCoefs().size(); i++) {
                data[i][0] = String.format("%.10f", nodes.get(i));
                data[i][1] = String.format("%.10f", coeffs.get(i));
            }
            String[] columns = new String[]{"Узел", "Коэффициент"};
            TextTable tt = new TextTable(columns, data);
            tt.printTable();
    }
}
