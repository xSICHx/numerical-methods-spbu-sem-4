package Task3_1ReverseInterpolation.Methods;

import java.util.List;

import static java.lang.Math.abs;

public abstract class MethodReverseInterpolation {
    List<List<Double>> fTable;
    double F;
    int n;

    public MethodReverseInterpolation(List<List<Double>> fTable, double F, int n) {
        this.fTable = fTable;
        this.F = F;
        this.n = n;
    }
    void sortFTable(){
        fTable.sort((o1, o2) -> {
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

    void defaultSortByX(){
        fTable.sort((o1, o2) -> {
            double d1 = abs(o1.get(1));
            double d2 = abs(o2.get(1));
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
