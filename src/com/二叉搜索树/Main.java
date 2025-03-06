package com.二叉搜索树;


import com.二叉搜索树.file.Files;
import com.二叉搜索树.printer.BinaryTrees;


import java.util.Comparator;

public class Main {

    private  static  class PersonComparator implements Comparator<Person> {

        @Override
        public int compare(Person e1, Person e2) {
            return e1.getAge()-e2.getAge();
        }
    }
    private  static  class PersonComparator2 implements Comparator<Person> {

        @Override
        public int compare(Person e1,Person e2) {
            return e2.getAge()-e1.getAge();
        }
    }

    static  void test01(){
        Integer[] data = new Integer[]{
               7,4,9,2,1
        };

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i <data.length ; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst);
        System.out.println(bst.isComplete2());

    }

    static  void  test02(){
        Integer[] data = new Integer[]{
                7,4,9,2,5,8,11,3
        };

        BinarySearchTree<Person> bst = new BinarySearchTree<>(new PersonComparator());
        for (int i = 0; i <data.length ; i++) {
            bst.add(new Person(data[i]));
        }

        BinaryTrees.println(bst);

    }
    static  void test03(){
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i <30 ; i++) {
            bst.add((int)(Math.random()*100));
        }
        String str = BinaryTrees.printString(bst);
        Files.writeToFile("./1.txt",str);
    }
    static  void  test04(){
        BinarySearchTree<Person> bst = new BinarySearchTree<>();
        bst.add(new Person(12,"jim"));
        bst.add(new Person(10,"tom"));
        bst.add(new Person(6,"jack"));
        bst.add(new Person(6,"xixi"));
        BinaryTrees.println(bst);
    }

    static  void test06(){
        Integer[] data = new Integer[]{
                7,4,2,1,3,5,9,8,11,10,12
        };

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i <data.length ; i++) {
            bst.add(data[i]);
        }
        BinaryTrees.println(bst);
        bst.preorderTraversal();
        System.out.println("\n");
        bst.inorderTraversal();
        System.out.println("\n");
        bst.PostorderTraversal();
        System.out.println("\n");
        bst.levelOrderTranversal();
        System.out.println("\n");

        bst.levelOrder(new BinarySearchTree.Visitor<Integer>() {
            public boolean visit(Integer element) {
                System.out.print("_"+ element+"  ");
                return false;
            }
        });
    }
    static  void test07(){
        Integer[] data = new Integer[]{
                7,4,9,2,5,8,11,3
        };

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i <data.length ; i++) {
            bst.add(data[i]);
        }

        System.out.println(bst);
        System.out.println(bst.height());

    }
    static  void test08(){
        Integer[] data = new Integer[]{
                7,4,9,2,5,8,11,3,12,1
        };

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i <data.length ; i++) {
            bst.add(data[i]);
        }
        BinaryTrees.println(bst);
        bst.remove(7);
        BinaryTrees.println(bst);




    }


    public static void main(String[] args) {
            test08();



        BinarySearchTree<Person> bst2 = new BinarySearchTree<>();{
            bst2.add(new Person(11));
            bst2.add(new Person(12));
        }
        BinarySearchTree< Person> bst3 = new BinarySearchTree<>(new PersonComparator());{
            bst2.add(new Person(13));
            bst2.add(new Person(14));
        }
        BinarySearchTree<Person> bst4= new BinarySearchTree<>(new Comparator<Person>() {
            @Override
            public int compare(Person e1, Person e2) {
                return e2.getAge()-e1.getAge();
            }
        });{
            bst2.add(new Person(15));
            bst2.add(new Person(16));
        }



    }
}
