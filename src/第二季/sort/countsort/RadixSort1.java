package 第二季.sort.countsort;

import 第二季.sort.cmpSort.Sort;

public class RadixSort1 extends Sort<Integer> {
    @Override
    protected void sort() {
        int[][] buckets = new int[10][array.length];
        int[]  bucketSize = new int[buckets.length]; //统计每个桶的个数
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        for (int divider = 1; divider <= max; divider *= 10) {
            for (int i = 0; i < array.length; i++) {
                int nu = array[i] / divider % 10;
                buckets[nu][bucketSize[nu]++] = array[i];
            }
            int index=0;
            for (int i = 0; i < buckets.length; i++) {
                for (int j = 0; j < bucketSize[i]; j++) {
                    array[index++] = buckets[i][j];
                }
                bucketSize[i] =0;
            }
        }
    }
}
