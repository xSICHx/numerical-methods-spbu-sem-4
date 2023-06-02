package Task5_2;

import org.mariuszgromada.math.mxparser.License;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        License.iConfirmNonCommercialUse("xSICHx");
        formalities();
        QFMellerGaussUse qfMellerGaussUse = new QFMellerGaussUse(3, 6, 7,  1e-12);
    }

    public static void myParameters(){
        System.out.println("Введите N1, N2, N3, epsilon");
        Scanner scanner = new Scanner(System.in);
        int N1 = scanner.nextInt();
        int N2 = scanner.nextInt();
        int N3 = scanner.nextInt();
        double epsilon = scanner.nextDouble();
        QFMellerGaussUse qfMellerGaussUse = new QFMellerGaussUse(N1, N2, N3, epsilon);
    }



    public static void formalities(){
        System.out.println("""
                Задание №5.2 по вычислительному практикуму
                    КФ Гаусса, ее узлы и коэффициенты. Вычисление интегралов при помощи КФ Гаусса
                    КФ Мелера, ее узлы и коэффициент. Вычисление интегралов при помощи КФ Мелера
                Вариант 8
                    Найти КФ Гаусса при a = 0, b = pi/4, rho(x) = 1, f(x) = cos(x^2), N = 3, 6, 7, 8.
                    Для КФ Мелера f(x) = exp(x) * sin(x^2)
                    
                """);
    }
}
