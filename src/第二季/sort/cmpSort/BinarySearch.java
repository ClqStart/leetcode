package 第二季.sort.cmpSort;

public class BinarySearch {
    public static int indexOf(int[] array, int v) {
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length;
        while (begin < end) {
            int mid = array.length >> 1;
            if (v < array[mid]) {
                end = mid;
            } else if (v > array[mid]) {
                begin = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static int search(int[] array, int v) {
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length;
        while (begin < end) {
            int mid = array.length >> 1;
            if (v < array[mid]){
                end = mid;
            }else {
                begin = mid+1;
            }
        }
        return begin;
    }

}
