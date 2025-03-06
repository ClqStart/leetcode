package com.红黑树;


import com.红黑树.Tree.AVLTree;
import com.红黑树.Tree.BinaryTree;
import com.红黑树.Tree.RBTree;
import com.红黑树.printer.BinaryTrees;

public class Main {
    static  void test01(){
        Integer[] data = new Integer[]{
             85,19,69,3,7,99,95
        };

        AVLTree<Integer> avl = new AVLTree<>();
        for (int i = 0; i <data.length ; i++) {
            avl.add(data[i]);
            //System.out.println("【"+data[i]+"】")
            //System.out.println("----------------------------------");
        }
        BinaryTrees.println(avl);
        avl.remove(99);
        BinaryTrees.println(avl);
        avl.remove(85);
        BinaryTrees.println(avl);
        avl.remove(95);
        BinaryTrees.println(avl);
    }
    static  void test02(){
        Integer[] data = new Integer[]{
              55,87,56,74,96,22,62,20,70,68,90,50

        };

        RBTree<Integer> rb= new RBTree<>();
        for (int i = 0; i <data.length ; i++) {
            rb.add(data[i]);
            System.out.println("【"+data[i]+"】");
            BinaryTrees.println(rb);
            System.out.println("---------------------------------------------------");
        }
    }
    static  void test03(){
        Integer[] data = new Integer[]{
                55,87,56,74,96,22,62,20,70,68,90,50
        };

        RBTree<Integer> rb= new RBTree<>();
        for (int i = 0; i <data.length ; i++) {
            rb.add(data[i]);
        }
        BinaryTrees.println(rb);
        System.out.println("--------【删除】---------------");
        for (int i = 0; i < data.length; i++) {
            rb.remove(data[i]);
            System.out.println("【"+data[i]+"】");
            BinaryTrees.println(rb);
            System.out.println("---------------------------------------------------");
        }
    }




    public static void main(String[] args) {
            test03();
    }
}
