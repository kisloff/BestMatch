package main;

import java.util.*;

public class TruckerMain {

    public static void main(String[] args) {

        final int ARR_SIZE = 5;

        int[] wayOfOptimization = new int[]{1, 1, 0, 0, 1};
        // public static int[] wayOfOptimization = new int[]{0, 0, 0, 1, 1}; //order changes

        double[][] sourceMatrix =
                        {{1.3, 99, 3, 28, 63},
                        {1.4, 92, 8, 33, 34},
                        {1.8, 81, 10, 112, 21}};


        double[][] normalizedMatrix = new double[sourceMatrix.length][ARR_SIZE];

        //��������� ������ � ������� �������� �������. �� �� ��� �����������
        final double[] criterialWeights = new double[]{0.40, 0.15, 0.15, 0.15, 0.15};

        System.out.println(new TruckerMain().getClass().getSimpleName().toString());

        PrintUtil.printArrayWithHeader(wayOfOptimization, PrintUtil.WAY_OF_OPT);

        Math.checkSourceMatrixForZeros(sourceMatrix, ARR_SIZE);

        PrintUtil.printMatrixWithHeader(sourceMatrix, PrintUtil.LOADS);

        //create and initialise array of temporary arrays

        double[][] tempArrArr = new double[ARR_SIZE][];

        for(int i = 0; i < ARR_SIZE; i++){
            tempArrArr[i] = new double[sourceMatrix.length];
        }

        //normalize matrix using temp array of arrays

        Math.normalizeFull(tempArrArr, sourceMatrix, wayOfOptimization);

        for(int n = 0; n < ARR_SIZE; n++)
            for(int i = 0; i < sourceMatrix.length; i++)
                normalizedMatrix[i][n] = tempArrArr[n][i];

        PrintUtil.printMatrixWithHeader(normalizedMatrix, PrintUtil.LOADS_NORMALIZED);

        PrintUtil.printArrayWithHeader(criterialWeights, PrintUtil.CRITERIAL_WEIGHTS);

        //function of utility

        double[] utilMeanings = new double[sourceMatrix.length];

        for(int i = 0; i < normalizedMatrix.length; i++)
            for(int j = 0; j < ARR_SIZE; j++)
                utilMeanings[i] += normalizedMatrix[i][j] * criterialWeights[j];

        PrintUtil.printArrayWithHeader(utilMeanings, PrintUtil.UTILITY_FUNCTION);

        //sorting

        TreeMap<Double, String> tmap = new TreeMap(Collections.reverseOrder());

        String[] sourceStrings = new String[sourceMatrix.length];

        for(int i=0; i<sourceStrings.length; i++)
            sourceStrings[i] = "";

        for(int i = 0; i < sourceMatrix.length; i++){
            for(int j = 0; j < ARR_SIZE; j++)
            sourceStrings[i] += sourceMatrix[i][j] + " ";
        }

        for(int i=0; i<sourceStrings.length; i++)
            tmap.put(utilMeanings[i], sourceStrings[i]);

        Set set = tmap.entrySet();
        Iterator iterator = set.iterator();

        PrintUtil.printTree(iterator);
    }
}
