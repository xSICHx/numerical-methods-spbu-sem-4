package Task5_2;

import Task5_2.QF_Methods.Gauss;
import Task5_2.QF_Methods.Meller;
import org.mariuszgromada.math.mxparser.License;

public class Main {

    public static void main(String[] args){
        License.iConfirmNonCommercialUse("xSICHx");
        formalities();
        Gauss gauss = new Gauss(0, Math.PI/4, 11, 1e-12, "f(x) = cos(x^2)");
        Meller meller = new Meller(10, "f(x) = exp(x)* sin(x^2)");

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
