package Task3_2FindTheDerivative;

import org.mariuszgromada.math.mxparser.License;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        License.iConfirmNonCommercialUse("xSICHx");
        System.out.println("Задача 3.2 по вычислительному практикуму. Нахождение производных таблично-заданной функции\n" +
                "по формулам численного дифференцирования");
        System.out.println("Вариант 8");
        System.out.println("f(x) = e^(6x)");
        System.out.println("Будет построена таблица из m+1 значений производных таблично-заданной функции в\n" +
                "равноотстоящих точках с шагом h, x_i=a+i*h:");
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите следующие значения с клавиатуры:");
        System.out.println("1) m:");
        int m = scan.nextInt();
//        int m = 5;
        System.out.println("2) a - наименьшая точка отрезка, в которой ищем производную:");
        double a = scan.nextDouble();
        System.out.println("3) h - шаг, с которым будут выбираться x_i:");
        double h = scan.nextDouble();
//        double h = 0.0001;
        DerivativesOfTableDefinedFunction derivativesOfTableDefinedFunction =
                new DerivativesOfTableDefinedFunction("e^(6x)", m, a, h);
        //suggest to enter new values
        System.out.println("Построить таблицу для других значений? (y/n)");
        String answer = scan.next();

        while (answer.equals("y")) {
            System.out.println("1) m:");
            m = scan.nextInt();
            System.out.println("2) a - наименьшая точка отрезка, в которой ищем производную:");
            a = scan.nextDouble();
            System.out.println("3) h - шаг, с которым будут выбираться x_i:");
            h = scan.nextDouble();
            derivativesOfTableDefinedFunction = new DerivativesOfTableDefinedFunction("e^(6x)", m, a, h);
            System.out.println("Построить таблицу для других значений? (y/n)");
            answer = scan.next();
        }
//
        test();
    }

    public static void test() {
        DerivativesOfTableDefinedFunction derivativesOfTableDefinedFunction =
                new DerivativesOfTableDefinedFunction("e^(6x)", 200, -2, 0.01);
    }

}
