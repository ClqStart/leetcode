package 第二季.sort.cmpSort;


import java.util.LinkedList;
import java.util.List;

public class bucketSort {

    private static double[] array = {0.34, 0.47, 0.29, 0.84, 0.45, 0.38, 0.35, 0.74};
    public static void sort() {
        List<Double>[] buckets = new List[array.length];
        for (int i = 0; i < array.length; i++) {
            int bucketIndex = (int) (array[i] * array.length);
            List<Double> bucket = buckets[bucketIndex];
            if (bucket == null) {
                bucket = new LinkedList<>();
                buckets[bucketIndex] = bucket;
            }
            bucket.add(array[i]);
        }
        int index = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] == null) continue;
            buckets[i].sort(null);
            for (Double d : buckets[i]) {
                array[index++] = d;
            }
        }
        for (int i = 0; i <array.length ; i++) {
            System.out.print(array[i]+"_");
        }
    }

    public static void main(String[] args) {
        sort();
    }

}
