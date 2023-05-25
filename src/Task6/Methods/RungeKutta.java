package Task6.Methods;

//некоторый аналог квадратурной формулы Симпсона
//Метод Рунге-Кутта 4-го порядка на одном шаге имеет погрешность порядка O(h^5)
public class RungeKutta extends Method{
    public RungeKutta(int N, double h, double y_0, double x_0, String dy) {
        super(N, h, y_0, x_0, dy);
        methodTable = new double[N+1][2];
        methodTable[0][0] = x_0;
        methodTable[0][1] = y_0;
        for (int i = 1; i <= N; i++) {
            setX_i(i);
            setY_i(i);
        }
    }
    private void setY_i(int index){
        double k1 = h*dy.calculate(getX_i(index-1), getY_i(index-1));
        double k2 = h*dy.calculate(getX_i(index-1)+h/2.0, getY_i(index-1)+k1/2.0);
        double k3 = h*dy.calculate(getX_i(index-1)+h/2.0, getY_i(index-1)+k2/2.0);
        double k4 = h*dy.calculate(getX_i(index-1)+h, getY_i(index-1)+k3);
        methodTable[index][1] = getY_i(index-1) + (k1 + 2*k2 + 2*k3 + k4)/6.0;
    }
    private void setX_i(int index){
        methodTable[index][0] = x_0 + h*index;
    }

}
