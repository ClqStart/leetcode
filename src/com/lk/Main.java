package com.lk;


public class Main {

    static  void josephus(){
        DoubleCircleLinkedList_question<Integer> list= new DoubleCircleLinkedList_question<>();
        for (int i = 1; i <= 8; i++) {
            list.add(i);
        }
        list.reset();
        while (!list.isEmpty()){
            list.next();
            list.next();
            System.out.println(list.remove());
        }
    }
    public static void main(String[] args) {
//        josephus();
        List<Integer> list1= new LinkedList<>();
        list1.add(5);
        list1.add(6);
        list1.add(1,5);
        list1.add(1,7);
        list1.add(0,8);
        System.out.println(list1);
        list1.remove(2);
        System.out.println(list1);
        System.out.println(list1.getSize());
        System.out.println(list1.indexOf(6));



    }
}
