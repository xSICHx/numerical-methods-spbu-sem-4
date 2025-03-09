
# Вычислительный практикум на Java

Этот репозиторий содержит решения задач по вычислительному практикуму, реализованные на Java. Каждая задача решает определённую вычислительную проблему, такую как нахождение корней уравнений, интерполяция, численное дифференцирование и интегрирование, а также решение дифференциальных уравнений.

## Содержание

1. **Нахождение корня одномерного уравнения**
   - Класс: `FindApproximateRoot`
   - Пример использования:
     ```java
     public static void main (String[] args) {
         License.iConfirmNonCommercialUse("xSICHx");
         FindApproximateRoot answer = new FindApproximateRoot("f(x) = 4*cos(x)+0.3*x", -15, 5, 50, 0.00001);
         answer.printRoots();
     }
     ```

2. **Алгебраическое интерполирование**
   - Класс: `Interpolation`
   - Пример использования:
     ```java
     Interpolation interpolation = new Interpolation(10, 0.2, 0.7, 0.5, 8);
     ```

3. **Обратное интерполирование**
   - Класс: `ReverseInterpolation`
   - Пример использования:
     ```java
     ReverseInterpolation reverseInterpolation = new ReverseInterpolation(m, a, b, x, n, 0.00000001);
     ```

4. **Нахождение производных таблично-заданной функции**
   - Класс: `DerivativesOfTableDefinedFunction`
   - Пример использования:
     ```java
     DerivativesOfTableDefinedFunction derivativesOfTableDefinedFunction = new DerivativesOfTableDefinedFunction("e^(6x)", m, a, h);
     ```

5. **Приближённое вычисление интеграла по квадратурным формулам**
   - Класс: `QuadratureFormulaUse`
   - Пример использования:
     ```java
     QuadratureFormulaUse qf1 = new QuadratureFormulaUse(0, 1, "f(x) = sin(x/2)+2x", "f(x) = x^2 - 2*cos(x/2)");
     qf1.printResults();
     ```

6. **Приближённое вычисление интеграла по составным квадратурным формулам**
   - Класс: `CQFUse`
   - Пример использования:
     ```java
     CQFUse cqf = new CQFUse(0, 100, 100, "f(x) = sin(x/2)+2x", "f(x) = x^2 - 2*cos(x/2)");
     cqf.printTable();
     ```

7. **Приближённое вычисление интеграла по составным квадратурным формулам с расширением**
   - Класс: `CQFExpandUse`
   - Пример использования:
     ```java
     CQFExpandUse cqf = new CQFExpandUse(-3, 50, 100, 5, "f(x) = sin(x/2)+2x", "f(x) = x^2 - 2*cos(x/2)");
     cqf.printTable();
     ```

8. **Приближённое вычисление интеграла при помощи квадратурных формул Наивысшей Алгебраической Степени Точности (КФ НАСТ)**
   - Класс: `QFha`
   - Пример использования:
     ```java
     QFha qfha = new QFha(0, 1, 2, "f(x) = exp(x)", "f(x) = sin(x)", "f(x) = 1/2 * exp(x) * (sin(x) - cos(x))");
     qfha.printInfo();
     ```

9. **Приближённое вычисление интеграла при помощи КФ Гаусса и КФ Мелера**
   - Класс: `QFMellerGaussUse`
   - Пример использования:
     ```java
     QFMellerGaussUse qfMellerGaussUse = new QFMellerGaussUse(3, 6, 7, 1e-12);
     ```

10. **Численное решение Задачи Коши для обыкновенного дифференциального уравнения первого порядка**
    - Класс: `SolveODE`
    - Пример использования:
      ```java
      SolveODE hde = new SolveODE(10, 0.1, 1, 0, "f(x, y) = -y*(1+x)", "f(x) = exp(-1/2 * x * (x+2))");
      ```
