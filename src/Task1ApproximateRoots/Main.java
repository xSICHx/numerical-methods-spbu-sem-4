package Task1ApproximateRoots;

import org.mariuszgromada.math.mxparser.License;

public class Main {
    public static void main (String[] args) {
        License.iConfirmNonCommercialUse("xSICHx");
        FindApproximateRoot answer = new FindApproximateRoot("f(x) = 4*cos(x)+0.3*x", -15, 5, 50, 0.00001);
        answer.printRoots();

    }
}
