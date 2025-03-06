package 第二季.sort;


public class Integers {
    public static Integer[] random(int count, int min, int max) {
        if (count <= 0 || min > max) return null;
        Integer[] array = new Integer[count];
        int delta = max - min - 1;
        for (int i = 0; i < count; i++) {
            array[i] = min + (int) (Math.random() * delta);
        }
        return array;
    }

    public static Integer[] combine(Integer[] array1, Integer[] array2) {
        if (array1 == null || array2 == null) return null;
        Integer[] array = new Integer[array1.length + array2.length];
        for (int i = 0; i < array1.length; i++) {
            array[i] = array1[i];
        }
        for (int i = 0; i < array2.length; i++) {
            array[i] = array2[i];
        }
        return array;
    }

    public static Integer[] same(int count, int unsameCount) {
        if (count <= 0 || unsameCount > count) return null;
        Integer[] array = new Integer[count];
        for (int i = 0; i < unsameCount; i++) {
            array[i] = unsameCount - 1;
        }
        for (int i = unsameCount; i < count; i++) {
            array[i] = unsameCount - 1;
        }
        return array;
    }

    public static void println(Integer[] array) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                string.append("_");
            }
            string.append(array[i]);
        }
        System.out.println(string.toString());
    }

    public static boolean isAscOrder(Integer[] array) {
        if(array==null||array.length<2)return true;
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) return false;
        }
        return true;
    }
    public  static  Integer[] copy(Integer[] array){
        if(array==null) return null;
        Integer[] newArray = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

}


