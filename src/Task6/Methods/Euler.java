package Task6.Methods;

import java.util.Objects;

//Использует квадратурные формулы
// 1 - Тейлор с погрешностью O(h^2)
// 2 - средний прямоугольник O(h^3)
// 3 - трапеция O(h^3)
public class Euler extends Method{
    String type;
    public Euler(int N, double h, double y_0, double x_0, String dy, String type) {
        super(N, h, y_0, x_0, dy);
        this.type = type;
        methodTable = new double[N+1][2];
        methodTable[0][0] = x_0;
        methodTable[0][1] = y_0;
        for (int i = 1; i <= N; i++) {
            setX_i(i);
            setY_i(i);
        }
    }

    private void setY_i(int index){
        if (Objects.equals(type, "1")){
            methodTable[index][1] = getY_i(index-1) + h*dy.calculate(getX_i(index-1), getY_i(index-1));
        } else if (Objects.equals(type, "2")) {
            double y_i_half = getY_i(index-1) + h/2.0*dy.calculate(getX_i(index-1), getY_i(index-1));
            methodTable[index][1] = getY_i(index-1) +h*dy.calculate(getX_i(index-1)+h/2.0, y_i_half);
        }
        else{
            double Y_index = getY_i(index-1) +h*dy.calculate(getX_i(index-1), getY_i(index-1));
            methodTable[index][1] = getY_i(index-1) + h/2.0*(
                    dy.calculate(getX_i(index-1), getY_i(index-1))+
                    dy.calculate(getX_i(index), Y_index)
                    );
        }

    }
    private void setX_i(int index){
        methodTable[index][0] = x_0 + h*index;
    }
}
