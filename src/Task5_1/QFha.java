package Task5_1;

import Task1ApproximateRoots.FindApproximateRoot;
import Task4_2CompoundQuadratureFormulas.CompoundQuadratureFormula;
import dnl.utils.text.table.TextTable;
import org.apache.commons.math3.linear.RealMatrix;
import org.mariuszgromada.math.mxparser.Function;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.sort;
import static org.apache.commons.math3.linear.MatrixUtils.inverse;

public class QFha {
    private final double sm;
    private double a;
    private double b;
    private Function rho;
    private Function f;
    private int N;
    private Function trueIntegral;
    private double abs_err;
    private RealMatrix A;
    private RealMatrix nodes;
    private RealMatrix moments;
    private double simpleErr;
    private double simpleSm;

    public Function getTrueIntegral() {
        return trueIntegral;
    }

    public double getAbs_err() {
        return abs_err;
    }

    public double getSm() {
        return sm;
    }

    public QFha(double a, double b, int N, String rho, String f, String trueIntegral){
        this.trueIntegral = new Function(trueIntegral);
        this.a = a;
        this.b = b;
        this.rho = new Function(rho);
        this.f = new Function(f);
        this.N = N;

        double[] moments_arr = new double[2*N];
        for (int i = 0; i < 2*N; i++) {
            Function func = new Function(rho+"*x^("+i+")");
            CompoundQuadratureFormula cqf = new CompoundQuadratureFormula(a, b, 10000, func);
            moments_arr[i] = cqf.getResult();
        }

        // Получить моменты и коэфиценты полинома
        double[][] moments_data = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                moments_data[i][j] = moments_arr[i+j];
            }
        }

        double[][] moments_minus_vec = new double[N][1];
        for (int i = N; i < 2*N; i++) {
            moments_minus_vec[i-N][0] = -moments_arr[i];
        }

        RealMatrix moments_inverse = inverse(new org.apache.commons.math3.linear.Array2DRowRealMatrix(moments_data));
        RealMatrix moments_minus = new org.apache.commons.math3.linear.Array2DRowRealMatrix(moments_minus_vec);
        RealMatrix a_vec = moments_inverse.multiply(moments_minus);

        // Посчитать узлы
        String polyString = "f(x) = ";
        // записать полином как строку с коэфицентами из a_vec в polyString
        for (int i = 0; i < N; i++) {
            if (a_vec.getEntry(i, 0) >= 0)
                polyString += "+" + a_vec.getEntry(i, 0) + "*x^(" + i + ")";
            else
                polyString += a_vec.getEntry(i, 0) + "*x^(" + i + ")";
        }
        polyString += "+x^(" + N + ")";
        Function func = new Function(polyString);
        FindApproximateRoot poly_roots = new FindApproximateRoot(polyString, a, b, 100, 1e-10, "Bisection");
        int partition = 1000;
        while (poly_roots.getArrayOfRoots().size() < N){
            partition *= 2;
            poly_roots = new FindApproximateRoot(polyString, a, b, partition, 1e-10, "Bisection");
        }

        List<Double> nodes = poly_roots.getArrayOfRoots();
        sort(nodes);
        this.nodes = new org.apache.commons.math3.linear.Array2DRowRealMatrix(nodes.size(), 1);
        for (int i = 0; i < nodes.size(); i++) {
            this.nodes.setEntry(i, 0, nodes.get(i));
        }

        double[][] nodes_data = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                nodes_data[i][j] = Math.pow(nodes.get(j), i);
            }
        }
        RealMatrix nodes_inverse = inverse(new org.apache.commons.math3.linear.Array2DRowRealMatrix(nodes_data));
        double[][] moments_0_to_N_minus_1 = new double[N][1];
        for(int i = 0; i < N; i++){
            moments_0_to_N_minus_1[i][0] = moments_arr[i];
        }
        RealMatrix moments_0_to_N_minus_1_vec = new org.apache.commons.math3.linear.Array2DRowRealMatrix(moments_0_to_N_minus_1);
        moments = moments_0_to_N_minus_1_vec;

        RealMatrix A = nodes_inverse.multiply(moments_0_to_N_minus_1_vec);
        this.A = A;
        double sm = 0;
        for (int i = 0; i < N; i++) {
            sm += A.getEntry(i, 0) * this.f.calculate(nodes.get(i));
        }
        this.sm = sm;
        this.abs_err = Math.abs(this.trueIntegral.calculate(b) - this.trueIntegral.calculate(a) - sm);
    }

    private List<double[]> doSimpleQFha(){
        double[] simpleNodes = new double[N];
        double x_i = a;
        double h = (b - a)/(N-1);
        for (int i = 0; i < N; i++) {
            simpleNodes[i] = x_i;
            x_i += h;
        }

        double[] simpleMoments = new double[N];
        for (int i = 0; i < N; i++) {
            Function func = new Function("f(x) = " + this.rho.getFunctionExpressionString() + "*x^(" + i + ")");
            CompoundQuadratureFormula cqf = new CompoundQuadratureFormula(a, b, 10000, func);
            simpleMoments[i] = cqf.getResult();
        }

        double[][] simpleNodesData = new double[N][N];
        for (int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++){
                simpleNodesData[i][j] = Math.pow(simpleNodes[j], i);
            }
        }
        RealMatrix simpleNodesMatrix = new org.apache.commons.math3.linear.Array2DRowRealMatrix(simpleNodesData);

        double[][] simpleMomentsData = new double[N][1];
        for (int i = 0; i < N; i++) {
            simpleMomentsData[i][0] = simpleMoments[i];
        }
        RealMatrix simpleMomentsMatrix = new org.apache.commons.math3.linear.Array2DRowRealMatrix(simpleMomentsData);

        RealMatrix simpleA = inverse(simpleNodesMatrix).multiply(simpleMomentsMatrix);

        double[] simpleAData = new double[N];
        for (int i = 0; i < N; i++) {
            simpleAData[i] = simpleA.getEntry(i, 0);
        }

        double simpleSm = 0;
        for (int i = 0; i < N; i++) {
            simpleSm += simpleA.getEntry(i, 0) * this.f.calculate(simpleNodes[i]);
        }
        this.simpleSm = simpleSm;
        this.simpleErr = Math.abs(this.trueIntegral.calculate(b) - this.trueIntegral.calculate(a) - simpleSm);
        return Arrays.asList(simpleMoments, simpleNodes, simpleAData);
    }

    public void printInfo(){
        System.out.println("Отрезок интегрирования: [" + this.a + ", " + this.b + "]");
        System.out.println("Количство узлов: " + this.N);
        System.out.println("Весовая функция: " + this.rho.getFunctionExpressionString() + "\n"+
                "f(x) = " + this.f.getFunctionExpressionString());
        System.out.println("Точное значение интеграла: " + (this.trueIntegral.calculate(b)-this.trueIntegral.calculate(a)));
        String[] simpleTableColumns = new String[]{"i", "Моменты", "Узлы", "Коэффициенты"};
        List<double[]> simpleQFha = doSimpleQFha();
        double[] simpleMoments = simpleQFha.get(0);
        double[] simpleNodes = simpleQFha.get(1);
        double[] simpleA = simpleQFha.get(2);
        String[][] simpleTableData = new String[N][4];
        for (int i = 0; i < N; i++) {
            simpleTableData[i][0] = String.valueOf(i);
            // format double to 12 symbols after dot
            simpleTableData[i][1] = String.format("%.12f", simpleMoments[i]);
            simpleTableData[i][2] = String.format("%.12f", simpleNodes[i]);
            simpleTableData[i][3] = String.format("%.12f", simpleA[i]);
        }
        TextTable simpleTable = new TextTable(simpleTableColumns, simpleTableData);
        System.out.println("Моменты, узлы и коэффициенты для простой КФ: ");
        simpleTable.printTable();
        //format with exponential notation

        System.out.println("Абсолютная погрешность для простой КФ: " + String.format("%.12e",this.simpleErr));
        System.out.println("Значение интеграла для простой КФ: " + String.format("%.12f",this.simpleSm));

        String[] tableColumns = new String[]{"i", "Моменты", "Узлы", "Коэффициенты"};
        String[][] tableData = new String[N][4];
        for (int i = 0; i < N; i++) {
            tableData[i][0] = String.valueOf(i);
            // format double to 12 symbols after dot
            tableData[i][1] = String.format("%.12f", this.moments.getEntry(i, 0));
            tableData[i][2] = String.format("%.12f", this.nodes.getEntry(i, 0));
            tableData[i][3] = String.format("%.12f", this.A.getEntry(i, 0));
        }
        TextTable table = new TextTable(tableColumns, tableData);
        System.out.println("Моменты, узлы и коэффициенты для НАСТ КФ: ");
        table.printTable();
        System.out.println("Абсолютная погрешность для НАСТ КФ: " + String.format("%.12e",this.abs_err));
        System.out.println("Значение интеграла для НАСТ КФ: " + String.format("%.12f",this.sm));
    }




    public double getA() {
        return a;
    }

    public RealMatrix getNodes() {
        return nodes;
    }

    public RealMatrix getMoments() {
        return moments;
    }

    public double getB() {
        return b;
    }

    public Function getRho() {
        return rho;
    }

    public Function getF() {
        return f;
    }

    public int getN() {
        return N;
    }
}
