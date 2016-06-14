package ahp;

import clusterization.Clusterization;
import utils.PrintUtil;

import java.util.*;

public class AHPM {

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

    public static void findFunctionOFUtilityMeanings(double[] utilMeanings, double[][] normalizedMatrix, double[] criterialWeights){
        for(int i = 0; i < normalizedMatrix.length; i++)
            for(int j = 0; j < normalizedMatrix[0].length; j++)
                utilMeanings[i] += normalizedMatrix[i][j] * criterialWeights[j];
    }

    public static void findFunctionOFUtilityCentroids(double[] utilMeanings, double[][] oldCentroids, double[] criterialWeights, int CRIT_NUM) {
        for (int i = 0; i < oldCentroids.length; i++)
            for (int j = 0; j < CRIT_NUM; j++)
                utilMeanings[i] += oldCentroids[i][j] * criterialWeights[j];
    }

    public static void sortAlternativesByUtility(TreeMap tmap, double[] utilMeanings, double[][] sourceMatrix){
        String[] sourceStrings = new String[sourceMatrix.length];

        for(int i=0; i<sourceStrings.length; i++)
            sourceStrings[i] = "";

        for(int i = 0; i < sourceMatrix.length; i++){
            for(int j = 0; j < sourceMatrix[0].length; j++)
                sourceStrings[i] += sourceMatrix[i][j] + "\t";
        }

        for(int i=0; i<sourceStrings.length; i++)
            tmap.put(utilMeanings[i], sourceStrings[i]);
    }

    public static void executeAHPFull(double[][] sourceMatrix, double[][] normalizedMatrix, double[] criterialWeights){
        double[] utilMeanings = new double[sourceMatrix.length];

        //for pure AHP (all alternatives by normalized values)
        findFunctionOFUtilityMeanings(utilMeanings, normalizedMatrix, criterialWeights); //change normalized Matrix to matrix for clusters
        PrintUtil.printArrayWithHeader(utilMeanings, PrintUtil.UTILITY_FUNCTION);

        //sorting
        TreeMap<Double, String> tmap = new TreeMap(Collections.reverseOrder());

        sortAlternativesByUtility(tmap, utilMeanings, sourceMatrix);

        PrintUtil.printTree(tmap);
    }

    public static int executeAHPForCentroids(double[][] centroids, double[] criterialWeights){
        double[] utilMeanings = new double[centroids.length];

        //for pure AHP (all alternatives by normalized values)
        findFunctionOFUtilityMeanings(utilMeanings, centroids, criterialWeights); //change normalized Matrix to matrix for clusters
        PrintUtil.printArrayWithHeader(utilMeanings, PrintUtil.UTILITY_FUNCTION);

        int indexOfBestCluster;

        return indexOfBestCluster = Clusterization.findBestCluster(utilMeanings);

        //sorting
        //TreeMap<Double, String> tmap = new TreeMap(Collections.reverseOrder());

        //sortAlternativesByUtility(tmap, utilMeanings, centroids);

        //PrintUtil.printTree(tmap);
    }

    //public static
}
