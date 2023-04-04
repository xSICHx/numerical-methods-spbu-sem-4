package Task3_1ReverseInterpolation;

import Task3_1ReverseInterpolation.Methods.DirectInterpolation;
import Task3_1ReverseInterpolation.Methods.ReversedTableInterpolation;
import org.mariuszgromada.math.mxparser.Function;

import java.util.*;

import static java.lang.Math.abs;
import static java.util.Collections.sort;

public class ReverseInterpolation {
    /** 1) m+1 - число значений в таблице */
    private int m;

    /** 2) концы отрезка [a, b], из которого выбираются узлы интерполяции */
    private double leftBound;
    private double rightBound;

    /** 3) x ‒ точка интерполирования, значение в которой хотим найти */
    private double F;

    /** 4) n ‒ степень интерполяционного многочлена, который будет построен */
    private int n;
    private Function f = new Function("f(x) = 2*sin(x) - x/2");
    private List<List<Double>> fXTable;
    private List<List<Double>> xFTable;
    private double epsilon;

    public ReverseInterpolation(){
        System.out.println("Задача 3.1 по вычислительному практикуму. Задача обратного интерполирования");
        System.out.println("Вариант 8");
        System.out.println("f(x) = 2*sin(x) - x/2");
        System.out.println("Введите следующие значения с клавиатуры:");
        Scanner scan = new Scanner(System.in);
        System.out.println("1) m+1 - число значений в таблице:");
        int m = scan.nextInt();
        System.out.println("2) концы отрезка [a, b], из которого выбираются узлы");
        double a = scan.nextDouble();
        double b = scan.nextDouble();
        this.m = m;
        if(!checkM())
            return;
        this.leftBound = a;
        this.rightBound = b;
        if (!checkSegment())
            return;

        fillXFTable();
        printTable();
//        System.out.println(xFTable);
        System.out.println("3) F ‒ значение функции f(x), для которого ищем подходящие значения аргумента");
        double x = scan.nextDouble();
        System.out.println("4) n ‒ степень интерполяционного многочлена, который будет построен\n" +
                "в ходе расчётов. Учтите, что n должно быть меньшим либо равным " + m);
        int n = scan.nextInt();
        this.epsilon = 0.00000001;


        this.F = x;
        this.n = n;
        if(!checkN())
            return;

        fillFXTable();


        formalities();


        while (suggestAgain()){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите новое значение F:");
            this.F = scanner.nextDouble();
            System.out.println("Введите новое значение n:");
            this.n = scanner.nextInt();
            if(!checkN())
                return;
            System.out.println("Введите новое значение epsilon:");
            this.epsilon = scanner.nextDouble();
            formalities();
        }

    }

    //TODO переписать под Ньютона, так как он работает лучше из-за меньших вычислений
    public ReverseInterpolation(int m, double leftBound, double rightBound, double F, int n, double epsilon){
        this.epsilon = epsilon;
        this.m = m;
        if(!checkM())
            return;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        if (!checkSegment())
            return;
        this.F = F;
        this.n = n;
        if(!checkN())
            return;

        fillFXTable();
        fillXFTable();

        formalities();


        while (suggestAgain()){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите новое значение F:");
            this.F = scanner.nextDouble();
            System.out.println("Введите новое значение n:");
            this.n = scanner.nextInt();
            if(!checkN())
                return;
            System.out.println("Введите новое значение epsilon:");
            this.epsilon = scanner.nextDouble();
            formalities();
        }
    }


    private void printTable(){
        System.out.println("i, x, f(x)");
        int i = 0;
        for (List<Double> x: xFTable) {
            System.out.println(i + " " + x.get(0) + " " + x.get(1));
            ++i;

        }
    }
    private void formalities(){
        ReversedTableInterpolation reversedTable = new ReversedTableInterpolation(fXTable, F, n);
        printReversedTableInterpolationResult(reversedTable);

        System.out.println("-----------------------------------------------------\n" +
                "Результат, посчитанный вторым способом");
        DirectInterpolation directInterpolation = new DirectInterpolation(xFTable, F, n, leftBound, rightBound, epsilon);
        List<Double> res = directInterpolation.getDoubleRoots();
        sort(res);
        for (double x: res) {
            System.out.println("    P_n("+x+") ≈ "+F);
            String str = String.format("%.3e", abs(f.calculate(x)-F));
            System.out.println("      Модуль невязки r_n("+x+") = "+str);
        }

    }
// 20 2 4 -3 10
    private boolean suggestAgain(){
        System.out.println("Вы можете попробовать ввести другие параметры задачи, введите Y," +
                " если желаете продолжить и N в противном случае");
        Scanner scanner = new Scanner(System.in);
        return Objects.equals(scanner.next(), "Y");
    }

    private void printReversedTableInterpolationResult(ReversedTableInterpolation o){
        if (o.exist()){
            double X = o.eval(F);
            System.out.println("Результат, посчитанный первым способом:");
            System.out.println("    f^(-1) (F) = " + X);
            String str = String.format("%.3e", abs(f.calculate(X)-F));
            System.out.println("    Модуль невязки r_n(" + X + ") = " + str);
            return;
        }
        System.out.println("Функция не монотонна, первым способом считать нельзя");
    }



    private void fillFXTable(){
        fXTable = new ArrayList<>();
        double step = (rightBound - leftBound)/m;
        for (int k = 0; k <= m; k++) {
            double x_k = leftBound + step*k;
            fXTable.add(Arrays.asList(f.calculate(x_k), x_k));
        }
    }


    private void fillXFTable(){
        xFTable = new ArrayList<>();
        double step = (rightBound - leftBound)/m;
        for (int k = 0; k <= m; k++) {
            double x_k = leftBound + step*k;
            xFTable.add(Arrays.asList(x_k, f.calculate(x_k)));
        }
    }

    private void sortFXTable(){
        fXTable.sort((o1, o2) -> {
            double d1 = abs(o1.get(0) - F);
            double d2 = abs(o2.get(0) - F);
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

}
