package clusterization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ������ on 09.06.2016.
 */
public class Clusterization {

    /*public static double findFullAlternativeDistance(double[] first, double[] second){
        double distance = 0;

        for(int i = 0; i<first.length; i++){
            distance += Math.abs(first[i]) - Math.abs(second[i]);
        }
        return distance;
    }*/

    public static double findEuclideanDistance(double[] first, double[] second){
        double squaredDistance = 0;

        for(int i = 0; i<first.length; i++){
            squaredDistance += ((first[i] - second[i])*(first[i] - second[i]));
        }
        return Math.sqrt(squaredDistance);
    }


    public static void findDistance(double[][] distances, double[][]sourceMatrix, double[][]centroids, int CLUSTER_NUM) {
        for(int i = 0; i < sourceMatrix.length; i++)
            for(int j = 0; j < distances[0].length; j++)
                distances[i][j]= findEuclideanDistance(sourceMatrix[i], centroids[j]);
    }

    public static int findMin(double[] vector){
        int index = 0;
        double min = vector[0];
        for(int i = 0; i<vector.length; i++){
            if(min > vector[i]){
                min = vector[i];
                index = i;
            }
        }

        return index;
    }


    public static void findClusters(int[] clusters, double[][] distances){
        for(int i = 0; i < distances.length; i++){
            clusters[i] = findMin(distances[i]);
        }
    }

    public static void generateStartIndexes(int[] indexes){
        for(int i = 0; i < indexes.length; i++) {
            int candidate;
            do{
                candidate = generateRandomInt(indexes.length);
            } while (!checkCandidate(candidate, i, indexes));

            indexes[i] = candidate;

            System.out.println("random = " + indexes[i]);
        }
    }

    public static boolean checkCandidate(int candidate, int index, int[] indexes){
        boolean passed = true;
        for(int i = 0; i < index; i++){
            if(indexes[i] == candidate) {
                passed = false;
            };
        }
        return passed;
    }

    public static int generateRandomInt(int array_size){
        int maxArrayIndex = array_size - 1;
        int minArrayIndex = 0;

        int integer;
        Random random = new Random();

        return integer = random.nextInt((maxArrayIndex - minArrayIndex + 1) + minArrayIndex);
    }

    public static void getCentroids(int[] startIndexes, double[][] centroids, double[][] sourceMatrix){
        for(int i = 0; i < centroids.length; i++)
            for(int j = 0; j < sourceMatrix[0].length; j++)
                centroids[i][j] = sourceMatrix[startIndexes[i]][j];
    }

    public static void fillAltCluster(double[][] altCluster, double[][] normailzedMatrix, int[] clusters){
        for(int i = 0; i < normailzedMatrix.length; i++) {
            for (int j = 0; j < normailzedMatrix[i].length; j++) {
                altCluster[i][j] = normailzedMatrix[i][j];
            }
        }

        for(int i = 0; i < normailzedMatrix.length; i++)
            altCluster[i][normailzedMatrix[0].length] = clusters[i];
    }

    public static void cleanMatrix(double[][] centroids){
        for(int i = 0; i < centroids.length; i++)
            for (int j = 0; j < centroids[0].length; j++)
                centroids[i][j] = 0;
    }

    /*public static double findAverageByCritria(List<Double> criteriaMeanings){
        Double temp = 0.0;

        for(int i = 0; i < criteriaMeanings.size(); i++){
            temp += criteriaMeanings.get(i);
        }

        return temp/criteriaMeanings.size();
    }*/

    public static void findNewCentroids(double[][] newCentroids, double[][] source, ArrayList<ArrayList<Integer>> indexes,  int CLUSTER_NUM, int CRIT_NUM){
        // int div = 0;
        double[][] sum = new double[CLUSTER_NUM][CRIT_NUM];

        for(int i = 0; i < indexes.size(); i++)
            for(int ind : indexes.get(i))
                for(int c = 0; c < sum[i].length; c++)
                    sum[i][c] += source[ind][c];

        for(int i = 0; i <  sum.length; i++)
            for(int j = 0; j < sum[0].length; j++)
                sum[i][j] /= indexes.get(i).size();

        for(int i = 0; i <  sum.length; i++)
            for(int j = 0; j < sum[0].length; j++)
                newCentroids[i][j] = sum[i][j];

        //PrintUtil.printMatrix3f(sum, "sum experimentel");
    }

    public static void formIndexes(ArrayList<ArrayList<Integer>> indexes, int[] clusters, int CLUSTER_NUM){

        for(int c = 0; c < CLUSTER_NUM; c++) {
            ArrayList<Integer> inner = new ArrayList();
            for (int i = 0; i < clusters.length; i++)
                if(clusters[i] == c){
                    inner.add(i);
                }
            indexes.add(inner);
        }
    }

    public static boolean hasCentroidsChanged(double[][] oldCentroids, double[][] newCentroids){
        boolean changed = false;

        for(int i = 0; i < oldCentroids.length; i++) {
            for (int j = 0; j < oldCentroids[0].length; j++) {
                if (oldCentroids[i][j] != newCentroids[i][j]) {
                    changed = true;
                }
            }
        }
        return changed;
    }

    public static void saveCentroidValue(double[][] oldCentroids, double[][] newCentroids){
        cleanMatrix(oldCentroids);
        for(int i = 0; i < oldCentroids.length; i++){
            for(int j = 0; j < oldCentroids[0].length; j++){
                oldCentroids[i][j] = newCentroids[i][j];
            }
        }
    }

    public static int findBestCluster(double[] utilityMeanings){
        double temp = 0;
        int index = 0;
        for(int i = 0; i< utilityMeanings.length; i++)
            if(utilityMeanings[i] > temp){
                temp = utilityMeanings[i];
                index = i;
            }
        System.out.println("index = " + index);
        return index;
    }

    public static void fillBestClusterMatrix(double[][] toArray, double[][] sourceArray, ArrayList<ArrayList<Integer>> indexes, int clusterIndex){
        int iterator = 0;
        for(Integer ig : indexes.get(clusterIndex)) {
            for (int j = 0; j < toArray[0].length; j++) {
                toArray[iterator][j] = sourceArray[ig][j];
            }
            iterator++;
        }
    }

        /*public static void makeIteration(double[][] distances, double[][] normalizedMatrix, double[][] centroids, int CLUSTERS_NUM, int[] clusters,
                                     double[][] altCluster, double[][] newCentroids, int CRIT_NUM){

            cleanMatrix(distances);
            findDistance(distances, normalizedMatrix, centroids, CRIT_NUM);
            PrintUtil.printMatrixWithHeader(distances, "distances");

            findClusters(clusters, distances);
            PrintUtil.printArrayWithHeader(clusters, "clusters");

            fillAltCluster(altCluster, normalizedMatrix, clusters, CRIT_NUM);
            PrintUtil.printMatrixWithHeader(altCluster, "Alt/Cluster");

            //cleanCentroids(centroids);
            //PrintUtil.printMatrixWithHeader(centroids, "cleaned centroids");

            findNewCentroids(newCentroids, altCluster, CLUSTERS_NUM, CRIT_NUM); //???
            PrintUtil.printMatrix3f(newCentroids, "new centroids");

    }*/

}
