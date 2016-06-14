package test;

import java.util.Random;


/**
 * Created by Кирилл on 23.03.2016.
 */
public class CaseGenerator {

    public static double[][] generateCase(int m, int n){
        int ARR_SIZE = m;
        int ARR_WIDTH = n;
        double[][] tempArrArr = new double[ARR_SIZE][ARR_WIDTH];

        Random random = new Random();

        for(int i = 0; i < ARR_SIZE; i++)
            tempArrArr[i][0] = (double)(random.nextInt(Integer.MAX_VALUE) + 1);

        for(int i = 0; i < ARR_SIZE; i++)
            tempArrArr[i][1] = (double)(random.nextInt(Integer.MAX_VALUE) + 1);

        for(int i = 0; i < ARR_SIZE; i++)
            tempArrArr[i][2] = (double)(random.nextInt(Integer.MAX_VALUE) + 1);

        for(int i = 0; i < ARR_SIZE; i++)
            tempArrArr[i][3] = (double)(random.nextInt(Integer.MAX_VALUE) + 1);

        for(int i = 0; i < ARR_SIZE; i++)
            tempArrArr[i][4] = (double)(random.nextInt(Integer.MAX_VALUE) + 1);


        //PrintUtil.printMatrixWithHeader(tempArrArr, "case");

        return tempArrArr;
    }

    public static void main(String[] args) {
        generateCase(100, 5);

        //Random random = new Random();
        //System.out.println("random.nextInt(Integer.MAX_VALUE) + 1 = " + random.nextInt() * 20000 + 10000);
        //System.out.println("random.nextInt= " + (random.nextInt(Integer.MAX_VALUE) + 1));
    }
}
