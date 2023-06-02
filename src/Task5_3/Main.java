package Task5_3;

import Task5_2.QF_Methods.Gauss;
import org.mariuszgromada.math.mxparser.License;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        License.iConfirmNonCommercialUse("xSICHx");
        formalities();
        tests();

    }


    public static void tests(){
        CGaussUse cGauss = new CGaussUse(0, 1, 2, 100, 1e-12, "f(x) = exp(x)*sin(x)", "f(x) = 1/2 * exp(x) * (sin(x) - cos(x))", 0);
        Gauss gauss = new Gauss(0, 1, 2, 1e-12, "f(x) = exp(x)*sin(x)", "f(x) = 1/2 * exp(x) * (sin(x) - cos(x))");
        System.out.println("Ошибка при обычной КФ Гаусса: " + gauss.getAbsError());
        System.out.println();

        cGauss = new CGaussUse(0, 1, 2, 3, 1e-12, "f(x) = exp(x)*sin(x)", "f(x) = 1/2 * exp(x) * (sin(x) - cos(x))", 0);
        System.out.println("Ошибка при обычной КФ Гаусса: " + gauss.getAbsError());
        System.out.println();

        cGauss = new CGaussUse(0, 1, 5, 3, 1e-12, "f(x) = exp(x)*sin(x)", "f(x) = 1/2 * exp(x) * (sin(x) - cos(x))", 0);
        gauss = new Gauss(0, 1, 5, 1e-12, "f(x) = exp(x)*sin(x)", "f(x) = 1/2 * exp(x) * (sin(x) - cos(x))");
        System.out.println("Ошибка при обычной КФ Гаусса: " + gauss.getAbsError());
        System.out.println();

        cGauss = new CGaussUse(0, 1, 5, 1, 1e-12, "f(x) = exp(x)*sin(x)", "f(x) = 1/2 * exp(x) * (sin(x) - cos(x))", 0);
        System.out.println("Ошибка при обычной КФ Гаусса: " + gauss.getAbsError());
        System.out.println();

        cGauss.getBestParams();

    }


    public static void formalities(){
        System.out.println("""
                Задание 5.3 по вычислительному практикуму.
                Приближённое вычисление интеграла при помощи составной КФ Гаусса.
                Вариант 8
                [𝑎, 𝑏] = [0, 1], 𝑓(𝑥) = sin(𝑥), 𝜌(𝑥) = 𝑒^x
                """);
    }

    public static void myParameters(){
        System.out.println("Введите a, b, N, m");
        Scanner scanner = new Scanner(System.in);
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        int N = scanner.nextInt();
        int m = scanner.nextInt();
        CGaussUse cGauss = new CGaussUse(a, b, N, m, 1e-12, "f(x) = exp(x)*sin(x)", "f(x) = 1/2 * exp(x) * (sin(x) - cos(x))", 1);
    }
}
