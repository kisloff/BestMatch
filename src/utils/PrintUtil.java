package utils;

import java.util.*;

public class PrintUtil {

    public static String WAY_OF_OPT = "Way of optimization";
    public static String ALTERNATIVES = "Alternatives";
    public static String NORMALIZED = "After normalization";
    public static String CRITERIAL_WEIGHTS = "Weights of criteria";
    public static String UTILITY_FUNCTION = "Function of utility meanings";

    public static void printMatrixWithHeader(double[][] matrix, String header){
        System.out.print(header);
        for(double[] d : matrix) {
            System.out.println();
            for (double n : d) {
                System.out.format("%.1f ", n);
            }
        }
        System.out.println();
        System.out.println();
    }

    public static void printMatrix3f(double[][] matrix, String header){
        System.out.print(header);
        for(double[] d : matrix) {
            System.out.println();
            for (double n : d) {
                System.out.format("%.3f ", n);
            }
        }
        System.out.println();
        System.out.println();
    }

    public static void printArrayWithHeader(double[] array, String header){
        System.out.println();
        System.out.println(header);
        for (double d : array)
            System.out.format("%.4f ", d);
        System.out.println();
    }

    public static void printArrayWithHeader(int[] array, String header){
        System.out.println();
        System.out.println(header);
        for (int i : array)
            System.out.print(" " + i);
        System.out.println();
        System.out.println();
    }

    public static void printArrayOfArrays(ArrayList<ArrayList<Integer>> arrayLists, int clusterIndex, String header){
        System.out.println(header);

        //for(ArrayList<Integer> a: arrayLists)
            for(Integer i : arrayLists.get(clusterIndex))
                System.out.println("i = " + i);
    }

    public static void printTree(TreeMap tmap){
        Set set = tmap.entrySet();
        Iterator iterator = set.iterator();

        System.out.println();
        System.out.println("Alternatives sorted by function of utility");
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.format("utility: %.4f| " , mentry.getKey());
            System.out.print("");
            System.out.println(mentry.getValue());
        }
    }
}
