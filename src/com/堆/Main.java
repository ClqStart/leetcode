package com.堆;


import com.堆.printer.BinaryTrees;
import com.集合.Tree.BinaryTree;
import 第二季.Graph.MinHeap;

import java.util.Comparator;

public class Main {
    static  void test01(){

        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(68);
        heap.add(72);
        heap.add(43);
        heap.add(50);
        heap.add(38);
        heap.add(10);
        heap.add(90);
        heap.add(65);
        BinaryTrees.println(heap);
//        heap.remove();
//        BinaryTrees.println(heap);

        System.out.println(heap.replace(70));
        BinaryTrees.println(heap);
    }

    static void test02(){
        Integer[] data = {88,44,53,41,16,6,70,18,85,98,81,23,36,43,37};
        BinaryHeap<Integer> heap = new BinaryHeap<>(data);
        BinaryTrees.println(heap);
        data[0]=10;
        data[1]=20;
        BinaryTrees.println(heap);
    }

    static void test03(){
        Integer[] data = {88,44,53,41,16,6,70,18,85,98,81,23,36,43,37};
        MinHeap<Integer> heap = new MinHeap<>(data, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });
        heap.remove();
        System.out.println(heap.get());
    }


    public static void main(String[] args) {

        test03();

    }
}
