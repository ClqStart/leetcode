package 第二季.sort.cmpSort;

@SuppressWarnings("unchecked")
public class MergeSort<E extends Comparable<E>> extends Sort<E> {
    private E[] leftArray;

    @Override
    protected void sort() {
        leftArray = (E[]) new Comparable[array.length >> 1];
        sort(0, array.length);

    }

    private void sort(int begin, int end) {
        if (end - begin < 2) return;
        int mid = (begin + end) >> 1;
        sort(begin, mid);
        sort(mid, end);
        merge(begin, mid, end);
    }

    private void merge(int begin, int mid, int end) {
        int li = 0, le = mid - begin;
        int ri = mid, re = end;
        int ai = begin;
        for (int i = li; i < le; i++) {
            leftArray[i] = array[begin + i];
        }
        //如果左边没有结束，如果左边结束，右边相当自动填入
        while (li < le) {
            if (ri < re && cmp(array[ri], leftArray[li]) < 0) {
                array[ai++] = array[ri++];
            } else {
                array[ai++] = leftArray[li++];
            }
        }
    }
    private static class SortThread extends Thread {
    private  int value;

    public  SortThread(int value){
        this.value=value;
    }

        @Override
        public void run() {
            try {
                Thread.sleep(value);
                System.out.println(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       public  void test(){
        int[]  array = {10,100,50,30,60};
           for (int i = 0; i < array.length; i++) {
               new SortThread(array[i]).start();
           }
       }
    }
}