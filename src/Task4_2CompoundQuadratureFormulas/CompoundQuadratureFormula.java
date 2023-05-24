package Task4_2CompoundQuadratureFormulas;

import Task4_1ApproximateIntegral.QuadratureFormulas.*;
import org.mariuszgromada.math.mxparser.Function;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import static java.lang.Math.abs;

public class CompoundQuadratureFormula extends QuadratureFormula {
    private static final HashMap<String, String> ruMethodNames = new HashMap<>(){{
        put("LR", "Левый прямоугольник");
        put("RR", "Правый прямоугольник");
        put("MR", "Средний прямоугольник");
        put("TR", "Трапеция");
        put("S", "Симпсон");
    }};
    private String name;
    private static final HashMap<String, Class<? extends QuadratureFormula>> methodsClassMap = new HashMap<>(){{
        put("LR", QFLeftRectangle.class);
        put("RR", QFRightRectangle.class);
        put("MR", QFMiddleRectangle.class);
        put("TR", QFTrapezoid.class);
        put("S", QFSimpson.class);
    }};
    private final double m;
    private final double h;
    private final Class<? extends QuadratureFormula> method;

    //Если не выбран никакой метод и нет информации о настоящем интеграле
    public CompoundQuadratureFormula(double a, double b, double m, Function func) {
        this.a = a;
        this.b = b;
        this.func = func;
        this.trueIntegral = null;
        this.absActualErr = null;
        this.h = (b-a)/m;
        this.m = m;
        this.method = methodsClassMap.get("TR");
        this.name = ruMethodNames.get("TR");
        this.result = calculate();
    }
    //Если выбран метод и нет информации о настоящем интеграле
    public CompoundQuadratureFormula(String method, double a, double b, double m, Function func){
        this.a = a;
        this.b = b;
        this.func = func;
        this.trueIntegral = null;
        this.absActualErr = null;
        this.h = (b-a)/m;
        this.m = m;
        this.method = getMethod(method);
        this.name = getMethodName(method);
        this.result = calculate();
    }
    //Нет информации о методе, но есть об интеграле
    public CompoundQuadratureFormula(double a, double b, double m, Function func, Function trueIntegral) {
        this.a = a;
        this.b = b;
        this.func = func;
        this.trueIntegral = trueIntegral;
        this.h = (b-a)/m;
        this.m = m;
        this.method = getMethod("TR");
        this.name = getMethodName("TR");
        this.result = calculate();
        this.absActualErr = abs(this.trueIntegral.calculate(b) - this.trueIntegral.calculate(a) - this.result);
    }
    //Есть информация о методе и интеграле
    public CompoundQuadratureFormula(String method, double a, double b, double m, Function func, Function trueIntegral) {
        this.a = a;
        this.b = b;
        this.func = func;
        this.trueIntegral = trueIntegral;
        this.h = (b-a)/m;
        this.m = m;
        this.method = getMethod(method);
        this.name = getMethodName(method);
        this.result = calculate();
        this.absActualErr = abs(this.trueIntegral.calculate(b) - this.trueIntegral.calculate(a) - this.result);
    }
    private Class<? extends QuadratureFormula> getMethod(String method){
        if (methodsClassMap.containsKey(method))
            return methodsClassMap.get(method);
        return methodsClassMap.get("TR");
    }
    private String getMethodName(String str){
        if (ruMethodNames.containsKey(str))
            return ruMethodNames.get(str);
        return ruMethodNames.get("TR");
    }

    @Override
    protected double calculate() {
        double res = 0;
        double currA = a;
        double currB = currA + h;
        try {
            Constructor<? extends QuadratureFormula> constructor = method.getConstructor(double.class, double.class, Function.class);
            QuadratureFormula instance = constructor.newInstance(currA, currB, func);
            res += instance.getResult();
            for (int i = 1; i < m; i++) {
//                res += constructor.newInstance(currA, currB, func).getResult();
                currA = currB;
                currB += h;
                res += instance.recalculate(currA, currB);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public String getName() {
        return name;
    }
}
