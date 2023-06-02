package Task6.Methods;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Замена на интерполяционный многочлен, экстраполяционный многочлен, должны быть равноостоящие ущлы
public class AdamsODE extends Method{

    HashMap<String, Double> divDiff;

    public AdamsODE(int N, double h, double y_0, double x_0, String dy, double[][] initTable, int m) {
        super(N, h, y_0, x_0, dy);
        divDiff = new HashMap<>();
        for (int i = 0; i < m; i++) {
            divDiff.put("x" + i, initTable[i][0]);
            divDiff.put("y" + i, initTable[i][1]);
        }
        methodTable = new double[N+2+1][2];
        for (int i = 0; i < N+2+1; i++) {
            methodTable[i][0] = getElemInMap("x", i);
            methodTable[i][1] = getElemInMap("y", i);
        }

    }


    //рекурсивная функция для нахождения
    private double getElemInMap(String type, int index){
        if (divDiff.containsKey(type+index))
            return divDiff.get(type+index);
        else if (type.equals("x")){
            divDiff.put("x"+index, getElemInMap("x", 0)+index*h);
        }
        else if (type.equals("y")){
            divDiff.put("y"+index,
                    getElemInMap("y", index-1)+
                    getElemInMap("0q", index-1)+
                    getElemInMap("1q", index-2)/2.0+
                    getElemInMap("2q", index-3)*5.0/12.0+
                    getElemInMap("3q", index-4)*3.0/8.0+
                    getElemInMap("4q", index-5)*251.0/720.0
                    );
        }
        else if (type.equals("0q")){
            divDiff.put(type+index, h*dy.calculate(getElemInMap("x", index), getElemInMap("y", index)));
        }
        else{
            //Паттерн для получения первого числа в строке
            Pattern pat=Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
            Matcher matcher=pat.matcher(type);
            int j = Integer.parseInt(matcher.find()?matcher.group():"0");
            divDiff.put(type+index,
                    getElemInMap((j-1)+"q", index+1)-
                    getElemInMap((j-1)+"q", index)
            );
        }
        return divDiff.get(type+index);
    }
}
