package main;

import java.util.Iterator;
import java.util.Map;

public class Util {

    public static void printMatrixWithHeader(double[][] matrix, String header){
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
            System.out.format("%.2f ", d);
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

    public static void printTree(Iterator iterator){
        System.out.println();
        System.out.println("Alternatives sorted by function of utility");
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.format("utility: %.2f| " , mentry.getKey());
            System.out.print("");
            System.out.println(mentry.getValue());
        }
    }
}
