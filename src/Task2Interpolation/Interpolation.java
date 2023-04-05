package Task2Interpolation;

import Task2Interpolation.Methods.*;
import org.mariuszgromada.math.mxparser.Function;

import java.util.*;

import static java.lang.Math.abs;

public class Interpolation {
    /** 1) m+1 - число значений в таблице */
    private int m;
    /** 2) концы отрезка [a, b], из которого выбираются узлы интерполяции */
    private double leftBound;
    private double rightBound;
    /** 3) x ‒ точка интерполирования, значение в которой хотим найти */
    private double x;
    /** 4) n ‒ степень интерполяционного многочлена, который будет построен */
    private int n;
    private Function f = new Function("f(x) = 2*sin(x) - x/2");
    private List<List<Double>> fTable;

    public Interpolation(){
        System.out.println("Задача 2 по вычислительному практикуму. Задача алгебраического интерполирования");
        System.out.println("Вариант 8");
        System.out.println("f(x) = 2*sin(x) - x/2");
        System.out.println("Введите следующие значения с клавиатуры:");
        Scanner scan = new Scanner(System.in);
        System.out.println("1) m+1 - число значений в таблице:");
        int m = scan.nextInt();
        System.out.println("2) концы отрезка [a, b], из которого выбираются узлы интерполяции");
        double a = scan.nextDouble();
        double b = scan.nextDouble();
        this.m = m;
        if(!checkM())
            return;
        this.leftBound = a;
        this.rightBound = b;
        if (!checkSegment())
            return;
        fTable = new ArrayList<>();
        fillTable();
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Исходная таблица значений функции:");
        printTable();
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("3) x ‒ точка интерполирования, значение в которой хотим найти");
        double x = scan.nextDouble();
        System.out.println("4) n ‒ степень интерполяционного многочлена, который будет построен\n" +
                "для того, чтобы найти значение в точке x. Учтите, что n должно быть меньшим либо равным "+m);
        int n = scan.nextInt();
        this.x = x;
        this.n = n;
        if(!checkN())
            return;
        formalities1();

        while (suggestAgain()){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите новое значение x:");
            this.x = scanner.nextDouble();
            System.out.println("Введите новое значение n:");
            this.n = scanner.nextInt();
            if(!checkN())
                return;
            formalities1();
        }

    }
    public Interpolation(int m, double leftBound, double rightBound, double x, int n, Function f){
        this(m, leftBound, rightBound, x, n);
        this.f = f.cloneForThreadSafe();
    }
    public Interpolation(int m, double leftBound, double rightBound, double x, int n) {
        this.m = m;
        if(!checkM())
            return;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        if (!checkSegment())
            return;
        this.x = x;
        this.n = n;
        if(!checkN())
            return;
        fTable = new ArrayList<>();
        fillTable();

        formalities();

        while (suggestAgain()){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите новое значение x:");
            this.x = scanner.nextDouble();
            System.out.println("Введите новое значение n:");
            this.n = scanner.nextInt();
            if(!checkN())
                return;
            formalities();
        }

    }

    private boolean checkM(){
        Scanner scan = new Scanner(System.in);
        String str;
        while (m < 1){
            System.out.println("Semantic error: m < 1. Enter another n or write EXIT");

            str = scan.nextLine();
            if (Objects.equals(str, "EXIT"))
                return false;
            if (isNumeric(str))
                this.m = Integer.parseInt(str);
        }
        return true;
    }
    private boolean checkN(){
        Scanner scan = new Scanner(System.in);
        String str;
        while (n > m){
            System.out.println("Semantic error: n > m. Enter another n or write EXIT");

            str = scan.nextLine();
            if (Objects.equals(str, "EXIT"))
                return false;
            if (isNumeric(str))
                this.n = Integer.parseInt(str);
        }
        return true;
    }
    private boolean checkSegment(){
        Scanner scan = new Scanner(System.in);
        String str;
        while (this.leftBound > this.rightBound){
            System.out.println("Semantic error: a > b. Enter another a on the next line and than b or write EXIT ");

            str = scan.nextLine();
            if (Objects.equals(str, "EXIT"))
                return false;
            if (isNumeric(str))
                this.leftBound = Double.parseDouble(str);

            str = scan.nextLine();
            if (Objects.equals(str, "EXIT"))
                return false;
            if (isNumeric(str))
                this.rightBound = Double.parseDouble(str);
        }
        return true;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void print(){
        System.out.println(m);
        System.out.println(leftBound);
        System.out.println(rightBound);
        System.out.println(x);
        System.out.println(n);
    }

    private void fillTable(){
        double step = (rightBound - leftBound)/m;
        for (int k = 0; k <= m; k++) {
            double x_k = leftBound + step*k;
            fTable.add(Arrays.asList(x_k, f.calculate(x_k)));
        }
    }
    private void sortTable(){
        fTable.sort((o1, o2) -> {
            double d1 = abs(o1.get(0) - x);
            double d2 = abs(o2.get(0) - x);
            if (d1 < d2)
                return -1;           // Neither val is NaN, thisVal is smaller
            if (d1 > d2)
                return 1;            // Neither val is NaN, thisVal is larger

            // Cannot use doubleToRawLongBits because of possibility of NaNs.
            long thisBits = Double.doubleToLongBits(d1);
            long anotherBits = Double.doubleToLongBits(d2);

            // Values are equal
            // (-0.0, 0.0) or (!NaN, NaN)
            return (Long.compare(thisBits, anotherBits));
        });
    }
    private void printTable(){
        System.out.println("i, x, f(x)");
        int i = 0;
        for (List<Double> x: fTable) {
            System.out.println(i + " " + x.get(0) + " " + x.get(1));
            ++i;
        }
    }


    private boolean suggestAgain(){
        System.out.println("Вы можете попробовать ввести другие параметры задачи, введите Y," +
                " если желаете продолжить и N в противном случае");
        Scanner scanner = new Scanner(System.in);
        return Objects.equals(scanner.next(), "Y");
    }

    private void formalities1(){
        sortTable();
        System.out.println();
        System.out.println("Отсортированная по удалённости от точки x таблица значений функции:");
        printTable();
        System.out.println("------------------------------------------------------------------------------------");
        LagrangeInterpolation lagrange = new LagrangeInterpolation(fTable, x, n);
        Newton newton = new Newton(fTable, x, n);
        System.out.println("Метод Лагранжа:");
        System.out.println("    Интерполяционный многочлен, полученный в ходе вычислений:");
        System.out.println("    " + lagrange);
        System.out.println("    Значение интерполяционного многочлена Лагранжа в точке x: " + lagrange.getResultValue());

        String str = String.format("%.3e", abs(f.calculate(x) - lagrange.getResultValue()));
        System.out.println("    Значение абсолютной фактической погрешности: " + str);

        System.out.println("Метод Ньютона:");
        System.out.println("    Интерполяционный многочлен, полученный в ходе вычислений:");
        System.out.println("    " + newton);
        System.out.println("    Значение интерполяционного многочлена Ньютона в точке x: " + newton.eval(x));
        str = String.format("%.3e", abs(f.calculate(x) - newton.eval(x)));
        System.out.println("    Значение абсолютной фактической погрешности: " + str);
        System.out.println("------------------------------------------------------------------------------------");

    }
    private void formalities(){
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Исходная таблица значений функции:");
        printTable();
        sortTable();
        System.out.println();
        System.out.println("Отсортированная по удалённости от точки x таблица значений функции:");
        printTable();
        System.out.println("------------------------------------------------------------------------------------");
        LagrangeInterpolation lagrange = new LagrangeInterpolation(fTable, x, n);
        Newton newton = new Newton(fTable, x, n);
        System.out.println("Метод Лагранжа:");
        System.out.println("    Интерполяционный многочлен, полученный в ходе вычислений:");
        System.out.println("    " + lagrange);
        System.out.println("    Значение интерполяционного многочлена Лагранжа в точке x: " + lagrange.eval(x));

        String str = String.format("%.3e", abs(f.calculate(x) - lagrange.eval(x)));
        System.out.println("    Значение абсолютной фактической погрешности: " + str);

        System.out.println("Метод Ньютона:");
        System.out.println("    Интерполяционный многочлен, полученный в ходе вычислений:");
        System.out.println("    " + newton);
        System.out.println("    Значение интерполяционного многочлена Ньютона в точке x: " + newton.eval(x));
        str = String.format("%.3e", abs(f.calculate(x) - newton.eval(x)));
        System.out.println("    Значение абсолютной фактической погрешности: " + str);
        System.out.println("------------------------------------------------------------------------------------");

    }
}
