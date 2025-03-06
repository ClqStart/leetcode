package com.集合;

import com.集合.Set.Set;
import com.集合.Set.TreeSet;

public class Main {

    static void test() {
//        com.映射.Set<Integer> listSet = new ListSet<>();
//        listSet.add(10);
//        listSet.add(11);
//        listSet.add(11);
//        listSet.add(12);
//        listSet.add(12);
//        listSet.add(13);

        Set<Integer> treeSet = new TreeSet<>();
        treeSet.add(10);
        treeSet.add(11);
        treeSet.add(11);
        treeSet.add(16);
        treeSet.add(12);
        treeSet.add(13);
        treeSet.add(7);


        treeSet.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });

    }

    public static void main(String[] args) {
        test();
    }
}
