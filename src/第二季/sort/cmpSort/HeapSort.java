package 第二季.sort.cmpSort;

public class HeapSort<E extends Comparable<E>> extends Sort<E> {
    private int headSize;

    @Override
    protected void sort() {
        headSize = array.length;
        //原地建堆
        for (int i = (headSize >> 1); i >= 0; i--) {
            siftDown(i);
        }
        while (headSize > 1) {
            swap(0, --headSize);
            //回复堆
            siftDown(0);
        }
    }

    private void siftDown(int index) {
        E element = array[index];
        int half = headSize >> 1;
        while (index < half) {
            int ChildIndex = (index << 1) + 1;
            E child = array[ChildIndex];
            int rightChild = ChildIndex + 1;
            if (rightChild < headSize && cmp(array[rightChild], child) > 0) {
                ChildIndex = rightChild;
                child = array[rightChild];
            }
            if (cmp(element, child) >= 0) break;
            array[index] = child;
            index = ChildIndex;
        }
        array[index] = element;
    }

}
