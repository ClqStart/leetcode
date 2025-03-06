package 第二季.sort.cmpSort;


import com.mj.Asserts;
import 第二季.sort.Integers;
import 第二季.sort.countsort.CountingSort;
import 第二季.sort.countsort.RadixSort1;

import java.util.Arrays;

public class Main {
    static void testSorts(Integer[] array, Sort... sorts) {
        for (Sort sort : sorts) {
            Integer[] newArray = Integers.copy(array);
            sort.sort(newArray);
            Asserts.test(Integers.isAscOrder(newArray));
            Integers.println(newArray);
        }
        Arrays.sort(sorts);
        for (Sort sort : sorts) {
            System.out.println(sort);
        }

    }

    public static void main(String[] args) {
        Integer[] array = Integers.random(10000, 1, 20000);
        testSorts(array, new CountingSort(),
                new RadixSort1()
//                new SelectionSort(),
//                new BubbleSort3(),
//                new BubbleSort1(),
//                new BubbleSort2(),
//                new InsertionSort(),
//                new InsertionSort1(),
//                new InsertionSort2(),
//                new MergeSort(),
//                new QuickSort(),
//                new ShellSort()
        );
    }

}
