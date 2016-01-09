package main;

import java.util.*;

/**
 * Created by Кирилл on 04.01.2016.
 */
public class Main {
    public static void main(String[] args) {

        Util.printArrayWithHeader(Math.wayOfOptimization, "wayOfOptimization");

        Math.checkSourceMatrixForZeros(Math.sourceMatrix);

        Util.printMatrixWithHeader(Math.sourceMatrix, "Loads");

    //create and initialise array of temporary arrays

        double[][] tempArrArr = new double[Math.ARR_SIZE][];

        for(int i = 0; i < Math.ARR_SIZE; i++){
            tempArrArr[i] = new double[Math.sourceMatrix.length];
        }

    //normalize matrix using temp array of arrays

        Math.normalizeFull(tempArrArr);

        for(int n = 0; n < Math.ARR_SIZE; n++)
            for(int i = 0; i < Math.sourceMatrix.length; i++)
                Math.normalizedMatrix[i][n] = tempArrArr[n][i];

        Util.printMatrixWithHeader(Math.normalizedMatrix,"Loads after normalization");

        Util.printArrayWithHeader(Math.criterialWeights, "Weights of criteria");

        //function of utility

        double[] utilMeanings = new double[Math.sourceMatrix.length];

        for(int i = 0; i < Math.normalizedMatrix.length; i++)
            for(int j = 0; j < Math.ARR_SIZE; j++)
                utilMeanings[i] += Math.normalizedMatrix[i][j] * Math.criterialWeights[j];

        Util.printArrayWithHeader(utilMeanings, "Function of utility meanings");

        //sorting
        TreeMap<Double, String> tmap = new TreeMap(Collections.reverseOrder());

        String[] sourceStrings = new String[Math.sourceMatrix.length];

        for(int i=0; i<sourceStrings.length; i++)
            sourceStrings[i] = "";

        for(int i = 0; i < Math.sourceMatrix.length; i++){
            for(int j = 0; j < Math.ARR_SIZE; j++)
            sourceStrings[i] += Math.sourceMatrix[i][j] + " ";
        }

        for(int i=0; i<sourceStrings.length; i++)
            tmap.put(utilMeanings[i], sourceStrings[i]);

        Set set = tmap.entrySet();
        Iterator iterator = set.iterator();

        Util.printTree(iterator);
    }
}
