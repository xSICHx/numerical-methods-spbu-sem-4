package Task2Interpolation.Polynomial;

import java.math.BigDecimal;
import java.util.Arrays;

import static java.lang.Math.abs;

public class Polynomial {
    private double[] coeffs; // массив, хранит коэффициенты полинома

    // Конструктор, получает на вход 'coeffs' - массив коэффициентов полинома
    // coeff - локальное обозначение массива, используется только для работы конструктора
    public Polynomial(double[] coeffs) {
        double[] coeff = new double[getExp(coeffs) + 1];
        if (coeffs.length == 0) throw new IllegalArgumentException("Многочлен не существует");
        if (coeffs == null) throw new NullPointerException("Null");
        System.arraycopy(coeffs, 0, coeff, 0, getExp(coeffs) + 1);
        this.coeffs = coeff;
    }

    // Геттер, возвращает старшую степень полинома
    public int getExp(double[] array) {
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0)
                result = i;
        }
        return result;
    }

    // Сумма двух полиномов:
    public Polynomial plus(Polynomial other){
        int maxLength = Math.max(this.coeffs.length, other.coeffs.length);
        int minLength = Math.min(this.coeffs.length, other.coeffs.length);
        double[] max;
        double[] min;
        if (this.coeffs.length >= other.coeffs.length) {
            max = this.coeffs;
            min = other.coeffs;
        } else {
            max = other.coeffs;
            min = this.coeffs;
        }
        // В новом массиве, по размерам соответствующем большему полиному, хранятся коэффициенты меньшего полинома
        double[] sameSized = new double[maxLength];
        for (int i = 0; i < minLength; i++){
            sameSized[i] = min[i];
        }
        // Сложение коэффициентов массивов равных размеров
        for (int i = 0; i < maxLength; i++) {
            sameSized[i] += max[i];
        }
        return new Polynomial(sameSized);
    }

    //Разность полиномов:
    public Polynomial minus(Polynomial other){
        int maxLength = Math.max(this.coeffs.length, other.coeffs.length);
        int minLength = Math.min(this.coeffs.length, other.coeffs.length);
        double[] min;
        if (this.coeffs.length <= other.coeffs.length) {
            min = this.coeffs;
        } else {
            min = other.coeffs;
        }
        double[] maxSizedResult = new double[maxLength];
        double[] maxSizedMin = new double[maxLength];
        for (int i = 0; i < minLength; i++) {
            maxSizedMin[i] = min[i];
        }
        if (getExp(this.coeffs) < getExp(other.coeffs)) {
            for (int i = 0; i < maxLength; i++)
                maxSizedResult[i] = maxSizedMin[i] - other.coeffs[i];
        } else {
            for (int i = 0; i < maxLength; i++)
                maxSizedResult[i] = this.coeffs[i] - maxSizedMin[i];
        }
        Polynomial result = new Polynomial(maxSizedResult);
        return result;
    }
    // Произведение 1, на вход поступает другой полином
    public Polynomial multiply(Polynomial other) {
        double[] product = new double[getExp(this.coeffs) + getExp(other.coeffs) + 1]; //x^5 * x^7=/5+7=x^12, но еще +1 тк массив с 0.
        for (int i = 0; i < this.coeffs.length; i++) {
            for (int j = 0; j < other.coeffs.length; j++) {
                product[i + j] += this.coeffs[i] * other.coeffs[j];
            }
        }
        return new Polynomial(product);
    }
    //Произведение 2, вызывается методом div() для вычисления остатка от деления полиномов:
    private Polynomial multiply(double currentCoeff, int currentExp) {
        double[] product = new double[getExp(this.coeffs) + currentExp + 1];
        for (int i = 0; i < this.coeffs.length; i++)
            product[i + currentExp] = this.coeffs[i] * currentCoeff;
        return new Polynomial(product);
    }

    // Деление двух полиномов нацело:
    public Polynomial div(Polynomial other) {
        if (getExp(other.coeffs) == 0 && other.coeffs[0] == 0) // && проверит первый операнд false? => false
            throw new ArithmeticException("Деление на ноль");
        Polynomial temp;
        Polynomial a = this; // п1 ???
        double[] x = new double[this.coeffs.length];
        int dividentExp = getExp(this.coeffs);
        int divisorExp = getExp(other.coeffs);
        while (dividentExp >= divisorExp) {
            x[dividentExp - divisorExp] = this.coeffs[dividentExp] / other.coeffs[divisorExp];
            temp = other.multiply(x[dividentExp - divisorExp], dividentExp - divisorExp);
            a = a.minus(temp); // не укорачивается с п.1 ???
            dividentExp--;
        }
        return new Polynomial(x);
    }

    // Остаток от деления полинома на полином:
    public Polynomial mod(Polynomial other) {
        return this.minus((this.div(other)).multiply(other));
    }

    //Возвращает значение полинома при данном X:
    public double evaluate(double x) {
        double result = 0;
        double currentExp = 1;
        for (int i = 0; i < coeffs.length; i++) {
            result += coeffs[i] * currentExp;
            currentExp *= x;
        }
        return result;
    }

    // Переопределение стандартных функций класса Object:
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj instanceof Polynomial) {
            Polynomial other = (Polynomial) obj;
            if (getExp(((Polynomial) obj).coeffs) != getExp(other.coeffs)) return false;
            for (int i = 0; i <= getExp(other.coeffs); i++)
                if (((Polynomial) obj).coeffs[i] != other.coeffs[i]) return false;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coeffs);
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < coeffs.length; i++) {
            String s = coeffs[i] >= 0 ? "+" + BigDecimal.valueOf(abs(coeffs[i])).toPlainString() : "-" + BigDecimal.valueOf(abs(coeffs[i])).toPlainString();
            str = str + s + "x^(" + i + ")";
        }
        return str;
    }
}