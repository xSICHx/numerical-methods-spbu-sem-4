package Task3_1ReverseInterpolation.Methods;

import Task1ApproximateRoots.FindApproximateRoot;
import Task2Interpolation.Methods.Newton;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Math.abs;

public class DirectInterpolation extends MethodReverseInterpolation{
    private double epsilon;
//    private LagrangeInterpolation lagrangeInterpolation;
    private Newton newton;
    private double leftBound;
    private double rightBound;
    private List<Double> doubleRoots;

    public DirectInterpolation(List<List<Double>> fTable, double F, int n,
                               double leftBound, double rightBound, double epsilon) {
        super(fTable, F, n);

        this.leftBound = leftBound;
        this.rightBound = rightBound;
        if (!checkSegment())
            return;

        this.epsilon = epsilon;
//50 2 4 -2,2671579550552994 25
        sortTable();
//        lagrangeInterpolation = new LagrangeInterpolation(fTable, (leftBound+rightBound)/2, n);
        newton = new Newton(fTable, (leftBound+rightBound)/2, n);

        String F_string = F >= 0 ? "-" + BigDecimal.valueOf(abs(F)).toPlainString() : "+" + BigDecimal.valueOf(abs(F)).toPlainString();

        String function = "f(x) = " + newton + F_string;
        System.out.println(function);
        FindApproximateRoot roots = new FindApproximateRoot(function, leftBound, rightBound,
                200, epsilon, "bisections");
        doubleRoots = roots.getArrayOfRoots();
    }
//51 2 4
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

    public List<Double> getDoubleRoots() {
        return doubleRoots;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private void sortTable(){
        fTable.sort((o1, o2) -> {
            double d1 = abs(o1.get(1) - F);
            double d2 = abs(o2.get(1) - F);
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
}
