package main;

import ahp.AHPM;
import utils.PrintUtil;
import clusterization.Clusterization;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        final double[][] sourceMatrix =
                {       {39990, 1.7, 2, 6144, 2048},
                        {37990, 1.7, 2, 6144, 2048},
                        {22990, 1.35, 2, 4096, 2048},
                        {55850, 2.5, 4, 8192, 2048},
                        {9990, 1.33, 4, 2048, 2048},
                        {10990, 1.33, 4, 2048, 2048},
                        {11990, 1.33, 4, 2048, 2048},
                        {299750, 2.7, 4, 32768, 8192},
                        {399750, 2.7, 4, 32768, 8192},
                        {199750, 2.7, 4, 32768, 8192}
                };

        final int CRIT_NUM = sourceMatrix[0].length;

        int[] wayOfOptimization = new int[]{0, 1, 1, 1, 1};

        double[][] normalizedMatrix = new double[sourceMatrix.length][CRIT_NUM];

        final double[] criterialWeights = new double[]{0.5, 0.25, 0.1, 0.2, 0.15};

        System.out.println(new Main().getClass().getSimpleName().toString());

        PrintUtil.printArrayWithHeader(wayOfOptimization, PrintUtil.WAY_OF_OPT);

        AHPM.checkSourceMatrixForZeros(sourceMatrix, CRIT_NUM);

        PrintUtil.printMatrixWithHeader(sourceMatrix, PrintUtil.ALTERNATIVES);

        //create and initialise array of temporary arrays
        double[][] tempArrArr = new double[CRIT_NUM][];

        for(int i = 0; i < CRIT_NUM; i++){
            tempArrArr[i] = new double[sourceMatrix.length];
        }

        //normalize matrix using temp array of arrays
        AHPM.normalizeFull(tempArrArr, sourceMatrix, wayOfOptimization);

        for(int n = 0; n < CRIT_NUM; n++)
            for(int i = 0; i < sourceMatrix.length; i++)
                normalizedMatrix[i][n] = tempArrArr[n][i];

        PrintUtil.printMatrix3f(normalizedMatrix, PrintUtil.NORMALIZED);

        PrintUtil.printArrayWithHeader(criterialWeights, PrintUtil.CRITERIAL_WEIGHTS);


        //CLUSTERISATION START

        //initial period
        final int CLUSTERS_NUM = 3;

        if(sourceMatrix.length > 9){
            int[] startIndexes = new int[CLUSTERS_NUM];
            double[][] oldCentroids = new double[CLUSTERS_NUM][CRIT_NUM];
            double[][] distances = new double[sourceMatrix.length][CLUSTERS_NUM];

            Clusterization.generateStartIndexes(startIndexes);
            Clusterization.getCentroids(startIndexes, oldCentroids, normalizedMatrix);
            PrintUtil.printMatrix3f(oldCentroids, "centroids");


            Clusterization.findDistance(distances, normalizedMatrix, oldCentroids, CLUSTERS_NUM);
            PrintUtil.printMatrixWithHeader(distances, "distances");

            int[] clusters = new int[sourceMatrix.length];
            Clusterization.findClusters(clusters, distances);
            PrintUtil.printArrayWithHeader(clusters, "clusters");


            double[][] altCluster = new double[sourceMatrix.length][CRIT_NUM + 1];
            Clusterization.fillAltCluster(altCluster, normalizedMatrix, clusters);
            PrintUtil.printMatrix3f(altCluster, "Alt/Cluster");

            double[][] newCentroids = new double [CLUSTERS_NUM][CRIT_NUM];

            ArrayList<ArrayList<Integer>> indexes = new ArrayList(CLUSTERS_NUM);

            Clusterization.formIndexes(indexes, clusters, CLUSTERS_NUM);


            Clusterization.findNewCentroids(newCentroids, normalizedMatrix, indexes, CLUSTERS_NUM, CRIT_NUM); //???
            PrintUtil.printMatrix3f(newCentroids, "new centroids");
            //initial period end

            System.out.println(Clusterization.hasCentroidsChanged(oldCentroids, newCentroids));

            boolean isCentroidChanged = Clusterization.hasCentroidsChanged(oldCentroids, newCentroids);


            while (isCentroidChanged) {

                Clusterization.saveCentroidValue(oldCentroids, newCentroids);

                Clusterization.cleanMatrix(distances);
                Clusterization.findDistance(distances, normalizedMatrix, newCentroids, CLUSTERS_NUM);
                PrintUtil.printMatrixWithHeader(distances, "distances");

                Clusterization.findClusters(clusters, distances);
                PrintUtil.printArrayWithHeader(clusters, "clusters");

                Clusterization.fillAltCluster(altCluster, normalizedMatrix, clusters);
                PrintUtil.printMatrix3f(altCluster, "Alt/Cluster");

                Clusterization.findNewCentroids(newCentroids, altCluster, indexes, CLUSTERS_NUM, CRIT_NUM); //???
                PrintUtil.printMatrix3f(newCentroids, "new centroids");

                isCentroidChanged = Clusterization.hasCentroidsChanged(oldCentroids, newCentroids);
                Clusterization.cleanMatrix(newCentroids);

            }

            ArrayList<ArrayList<Integer>> indexes2 = new ArrayList(CLUSTERS_NUM);
            Clusterization.formIndexes(indexes2, clusters, CLUSTERS_NUM);

            int clusterIndex = AHPM.executeAHPForCentroids(oldCentroids, criterialWeights); //change for cluster centroids

            PrintUtil.printArrayOfArrays(indexes2, clusterIndex, "indexes");

            double[][] BestClusterMatrix = new double[indexes2.get(clusterIndex).size()][CRIT_NUM];
            double[][] BestClusterNormalizedMatrix = new double[indexes2.get(clusterIndex).size()][CRIT_NUM];

            Clusterization.fillBestClusterMatrix(BestClusterMatrix, sourceMatrix, indexes2, clusterIndex);
            Clusterization.fillBestClusterMatrix(BestClusterNormalizedMatrix, normalizedMatrix, indexes2, clusterIndex);


            AHPM.executeAHPFull(BestClusterMatrix, BestClusterNormalizedMatrix, criterialWeights); //goal
           // AHPM.executeAHPFull(sourceMatrix, normalizedMatrix, criterialWeights); //initial - for all alternatives}
        }

        else {
            AHPM.executeAHPFull(sourceMatrix, normalizedMatrix, criterialWeights); //initial - for all alternatives}
        }
    }
}
