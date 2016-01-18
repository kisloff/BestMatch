package main;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class LaptopMain {
    public static void main(String[] args) {

        final int ARR_SIZE = 5;

        int[] wayOfOptimization = new int[]{0, 1, 1, 1, 1};

        double[][] sourceMatrix =
                {       {37990, 1.7, 2, 6144, 2048},
                        {22990, 1.35, 2, 4096, 2048},
                        {55850, 2.5, 4, 8192, 2048},
                        {9990, 1.33, 4, 2048, 2048},
                        {299750, 2.7, 4, 32768, 8192}
                };


        double[][] normalizedMatrix = new double[sourceMatrix.length][ARR_SIZE];

        //привязать размер к размеру исходной матрицы. То же для предыдущего
        final double[] criterialWeights = new double[]{0.5, 0.25, 0.1, 0.2, 0.15};

        System.out.println(new LaptopMain().getClass().getSimpleName().toString());

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
                sourceStrings[i] += sourceMatrix[i][j] + "\t";
        }

        for(int i=0; i<sourceStrings.length; i++)
            tmap.put(utilMeanings[i], sourceStrings[i]);

        Set set = tmap.entrySet();
        Iterator iterator = set.iterator();

        PrintUtil.printTree(iterator);
    }

}
