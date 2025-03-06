package 第二季.sort.cmpSort;


import java.util.ArrayList;
import java.util.List;

public class ShellSort<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        List<Integer> stepSquence = sedgewickStepSequence();
        System.out.println(stepSquence);
        for (Integer step : stepSquence) {
            sort(step);
        }

    }

    private void sort(int step) {
        for (int col = 0; col < step; col++) {
            for (int begin = col + step; begin < array.length; begin += step) {
                int cur = begin;
                while (cur > col && cmp(cur, cur - step) < 0) {
                    swap(cur, cur - step);
                    cur -= step;
                }
            }
        }

    }

    private List<Integer> shellStepSquence() {
        List<Integer> stepSquence = new ArrayList<>();
        int step = array.length;
        while ((step >>= 1) > 0) {
            stepSquence.add(step);
        }
        return stepSquence;
    }

    private List<Integer> sedgewickStepSequence() {
        List<Integer> stepSquence = new ArrayList<>();
        int k = 0, step = 0;
        while (true) {
            if (k % 2 == 0) {
                int pow = (int) Math.pow(2, k >> 1);
                step = 1 + 9 * (pow * pow - pow);
            } else {
                int pow1 = (int) Math.pow(2, (k - 1) >> 1);
                int pow2 = (int) Math.pow(2, (k + 1) >> 1);
                step = 1 + 8 * pow1 * pow2 - 6 * pow2;
            }
            if(step>=array.length)break;
            stepSquence.add(0,step);
            k++;
        }
        return stepSquence;
    }
}
