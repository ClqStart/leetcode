package com.AVL树;


import com.AVL树.Tree.AVLTree;
import com.AVL树.Tree.BST;
import com.AVL树.printer.BinaryTrees;

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


    public static void main(String[] args) {
            test01();
    }
}
