package Task4_2CompoundQuadratureFormulas;

import org.mariuszgromada.math.mxparser.License;

import java.util.Scanner;

import static java.lang.Math.abs;

public class Main {

    public static void myCQF(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите с клавиатуры пределы интегрирования a, b, m:");
        double a = scan.nextDouble();
        double b = scan.nextDouble();
        double m = abs(scan.nextDouble());
        CQFUse cqf = new CQFUse(a, b, m,
                "f(x) = sin(x/2)+2x", "f(x) = x^2 - 2*cos(x/2)");
        cqf.printTable();
    }
    public static void tests(){


        CQFUse cqf = new CQFUse(0, 100, 100,
                "f(x) = sin(x/2)+2x", "f(x) = x^2 - 2*cos(x/2)");
        cqf.printTable();


        System.out.println("""
                Теоретическая погрешность:
                Для левого и правого прямоугольников: АСТ = 0, 1/2 * 1 * (100 - 0) * (100/1000)^1 = 5
                Для трапеции: АСТ = 1, 1/12 * 1 * (100 - 0) * (100/1000)^2 = 0.08333
                Для среднего прямоугольника: АСТ = 1, 1/24 * 1 * (100 - 0) * (100/1000)^2 = 0.0417
                Для Симпсона: АСТ = 3, 1/2880 * 1 * (100 - 0) * (100/1000)^4 = 3.4722e-6
                """);
        cqf = new CQFUse(0, 100, 1000,
                "f(x) = sin(x)", "f(x) = -cos(x)");
        cqf.printTable();

        cqf = new CQFUse(0, 100, 10000,
                "f(x) = sin(x/2)+2x", "f(x) = x^2 - 2*cos(x/2)");
        cqf.printTable();

        cqf = new CQFUse(0, 100, 100000,
                "f(x) = sin(x/2)+2x", "f(x) = x^2 - 2*cos(x/2)");
        cqf.printTable();

        cqf = new CQFUse(0, 100, 1000000,
                "f(x) = sin(x/2)+2x", "f(x) = x^2 - 2*cos(x/2)");
        cqf.printTable();

        cqf = new CQFUse(0, 100, 100,
                "f(x) = 1", "f(x) = x");
        cqf.printTable();

        cqf = new CQFUse(0, 100, 100,
                "f(x) = x", "f(x) = x^2/2");
        cqf.printTable();

        cqf = new CQFUse(0, 100, 100,
                "f(x) = x^2", "f(x) = x^3/3");
        cqf.printTable();

        cqf = new CQFUse(0, 100, 100,
                "f(x) = x^3", "f(x) = x^4/4");
        cqf.printTable();

        cqf = new CQFUse(0, 100, 100,
                "f(x) = x^4", "f(x) = x^5/5");
        cqf.printTable();

        cqf = new CQFUse(-8, 8, 128,
                "f(x) = 6*x^5+2*x", "f(x) = x^6+x^2");
        cqf.printTable();

        cqf = new CQFUse(-10, 10, 100,
                "f(x) = 6*x^5+2*x", "f(x) = x^6+x^2");
        cqf.printTable();
    }

    public static void main(String[] args){
        License.iConfirmNonCommercialUse("xSICHx");
        System.out.println("Задача 4.2 по вычислительному практикуму." +
                "\nПриближённое вычисление интеграла по составным квадратурным формулам");
        System.out.println("""
                Параметры задачи: пределы интегрирования a, b, весовая функция ρ(x) и
                функция f(x), m – число промежутков деления [a, b].
                Считаем вес p(x) = 1.
                Вычислить приближённо и вывести на печать значение интеграла от ρ(x)∙f(x)
                по [a, b] при помощи СКФ
                • Левых прямоугольников;
                • Правых прямоугольников;
                • средних прямоугольников;
                • трапеций;
                • Симпсона
                с параметром m. Обозначим эти значения J(h), здесь h = (b‒a)/m).
                """);
        System.out.println("""
                
                Вопросы:
                1) Сколько (в терминах m) значений функции f(x) участвует (в теории, а не при
                Вашей реализации программы) в вычислении интеграла по каждой СКФ?
                2) Почему, несмотря на то, что АСТ КФ средних прямоугольников равна 1, а АСТ
                Симпсона равна 3, они обе точны для f(x)= 1,27∙x^5+2,04∙x при интегрировании по
                [a, b]= [-5, 5] и для [a, b]= [-90, 90]?
                3) C чем может быть связана потеря точности «у Симпсона»?
                Ответы:
                1) Левых прямоугольников: m
                   Правых прямоугольников: m
                   Средних прямоугольников: m
                   Трапеций: m+1
                   Симпсона: 2m+1
                2) Потому что эта функция нечётная.
                3) Думаю, из-за большего разбиения и из-за деления на 6. Слишком много ошибок накапливается.
                """);
        tests();
    }
}
