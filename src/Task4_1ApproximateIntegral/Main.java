package Task4_1ApproximateIntegral;

import org.mariuszgromada.math.mxparser.License;

import java.util.Scanner;

public class Main {

    public static void myQF(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите с клавиатуры пределы интегрирования a, b:");
        double a = scan.nextDouble();
        double b = scan.nextDouble();
        QuadratureFormulaUse qf = new QuadratureFormulaUse(a, b, "f(x) = sin(x/2)+2x", "f(x) = x^2 - 2*cos(x/2)");
        qf.printResults();
    }

    public static void tests(){
        System.out.println("Тесты: ");
        System.out.println();

        System.out.println("При a = 0, b = 1, f(x) = sin(x/2)+2x, I = x^2 - 2*cos(x/2):");
        QuadratureFormulaUse qf1 = new QuadratureFormulaUse(0, 1, "f(x) = sin(x/2)+2x", "f(x) = x^2 - 2*cos(x/2)");
        qf1.printResults();
        System.out.println();

        System.out.println("При a = 0, b = 100, f(x) = sin(x/2)+2x, I = x^2 - 2*cos(x/2):");
        QuadratureFormulaUse qf2 = new QuadratureFormulaUse(0, 100, "f(x) = sin(x/2)+2x", "f(x) = x^2 - 2*cos(x/2)");
        qf2.printResults();
        System.out.println();

        System.out.println("При a = 0, b = 100, f(x) = 1, I = x:");
        QuadratureFormulaUse qf3 = new QuadratureFormulaUse(0, 100, "f(x) = 1", "f(x) = x");
        qf3.printResults();
        System.out.println();

        System.out.println("При a = 0, b = 100, f(x) = x, I = x^2/2:");
        QuadratureFormulaUse qf4 = new QuadratureFormulaUse(0, 100, "f(x) = x", "f(x) = x^2/2");
        qf4.printResults();
        System.out.println();

        System.out.println("При a = 0, b = 100, f(x) = x^2, I = x^3/3:");
        QuadratureFormulaUse qf5 = new QuadratureFormulaUse(0, 100, "f(x) = x^2", "f(x) = x^3/3");
        qf5.printResults();
        System.out.println();

        System.out.println("При a = 0, b = 100, f(x) = x^3, I = x^4/4:");
        QuadratureFormulaUse qf6 = new QuadratureFormulaUse(0, 100, "f(x) = x^3", "f(x) = x^4/4");
        qf6.printResults();
        System.out.println();

        System.out.println("При a = 0, b = 100, f(x) = x^4, I = x^5/5:");
        QuadratureFormulaUse qf7 = new QuadratureFormulaUse(0, 100, "f(x) = x^4", "f(x) = x^5/5");
        qf7.printResults();
        System.out.println();
    }

    public static void main(String[] args){
        License.iConfirmNonCommercialUse("xSICHx");
        System.out.println("Задача 4.1 по вычислительному практикуму." +
                "\nПриближённое вычисление интеграла по квадратурным формулам");
        System.out.println("""
                Считаем вес p(x) = 1.
                Вычислим приближённо значение интеграла от f(x) по [a, b] при помощи:
                • КФ левого прямоугольника;
                • КФ правого прямоугольника;
                • КФ среднего прямоугольника;
                • КФ трапеции;
                • КФ Симпсона (или парабол);
                • КФ 3/8.
                """);
        tests();
    }
}
