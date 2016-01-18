package main;

public class Math {

    public static void checkSourceMatrixForZeros(double[][] sourceMatrix, int ARR_SIZE){
        for(int i = 0; i < sourceMatrix.length; i++)
            for(int j =0; j < ARR_SIZE; j++)
                if(sourceMatrix[i][j] == 0)
                    sourceMatrix[i][j] += 1;
    }

    public static double[] getColumn(double[] source, double[][] sourceMatrix, int n){
        for(int i = 0; i < sourceMatrix.length; i++)
            source[i] = sourceMatrix[i][n];
        return source;
    }

    public static void normalizeFull(double tempArrArr[][], double[][] sourceMatrix, int[] wayOfOptimization){
        for(int i = 0; i < wayOfOptimization.length; i++)
            switch (wayOfOptimization[i]) {
                case 1:
                    normalizeMax(getColumn(new double[sourceMatrix.length], sourceMatrix, i), tempArrArr[i]);
                    break;
                case 0:
                    normalizeMin(getColumn(new double[sourceMatrix.length],sourceMatrix,  i), tempArrArr[i]);
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
