package 第二季.sort.countsort;

import 第二季.sort.cmpSort.Sort;

public class RadixSort extends Sort<Integer> {
    @Override
    protected void sort() {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        for (int devider = 1; devider <= max; devider *= 10) {
            countingSort(devider);
        }

    }

    protected void countingSort(int divider) {
        //统计出元素对应索引时并且统计出元素的次数
        int[] counts = new int[10];
        for (int i = 0; i < array.length; i++) {
            counts[array[i] / divider % 10]++;
        }
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }
        //用于存放排序好的数据(从右往左保持稳定性)
        int[] output = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            output[--counts[array[i] / divider] % 10] = array[i];
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = output[i];
        }
    }



}
