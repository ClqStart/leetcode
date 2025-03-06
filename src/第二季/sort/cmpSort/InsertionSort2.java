package 第二季.sort.cmpSort;

public class InsertionSort2<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            E v = array[begin];
            int insertIndex = search(begin);
            for (int i = begin; i > insertIndex; i--) {
                array[i] = array[i-1];
            }
            array[insertIndex]=v;
        }
    }

    private int search(int index) {
        int begin = 0;
        int end = index;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (cmp(array[index], array[mid]) < 0) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }
}
