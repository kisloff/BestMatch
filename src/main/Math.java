package main;

/**
 * Created by Кирилл on 09.01.2016.
 */
public class Math {

    public static final int ARR_SIZE = 5;

    public static double[][] sourceMatrix =
                    {{1.3, 99, 3, 28, 63},
                    {1.4, 92, 8, 33, 34},
                    {1.8, 81, 10, 112, 21}};

    public static double[][] normalizedMatrix = new double[sourceMatrix.length][ARR_SIZE];

    public static int[] wayOfOptimization = new int[]{1, 1, 0, 0, 1};
    // public static int[] wayOfOptimization = new int[]{0, 0, 0, 1, 1}; //order changes

    //привязать размер к размеру исходной матрицы. То же для предыдущего
    public static final double[] criterialWeights = new double[]{0.40, 0.15, 0.15, 0.15, 0.15};

    public static void checkSourceMatrixForZeros(double[][] sourceMatrix){
        for(int i = 0; i < sourceMatrix.length; i++)
            for(int j =0; j < ARR_SIZE; j++)
                if(sourceMatrix[i][j] == 0)
                    sourceMatrix[i][j] += 1;
    }

    public static double[] getColumn(double[] source, int n){
        for(int i = 0; i < sourceMatrix.length; i++)
            source[i] = sourceMatrix[i][n];
        return source;
    }

    public static void normalizeFull(double tempArrArr[][]){
        for(int i = 0; i < wayOfOptimization.length; i++)
            switch (wayOfOptimization[i]) {
                case 1:
                    normalizeMax(getColumn(new double[sourceMatrix.length], i), tempArrArr[i]);
                    break;
                case 0:
                    normalizeMin(getColumn(new double[sourceMatrix.length], i), tempArrArr[i]);
                    break;
            }
    }

    public static void normalizeMax(double[] source, double[] goal){
        double sum = findSum(source);
        for(int i = 0; i < source.length; i++){
            goal[i] = source[i] / sum;
        }
    }

    public static void normalizeMin(double[] source, double[] goal){
        double[] source_copy = new double[source.length];
        for(int i = 0; i < source.length; i++){
            source_copy[i] = 1 / source[i];
        }
        double sum = findSum(source_copy);
        for(int i = 0; i < source.length; i++){
            goal[i] = source_copy[i] / sum;
        }
    }

    public static double findSum(double[] arr){
        double sum = 0;
        for(double d: arr)
            sum += d;
        return sum;
    }
}
