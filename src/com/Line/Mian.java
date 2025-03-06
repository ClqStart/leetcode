package com.Line;



public class Mian {
    static  void  test01(){
        Queue<Integer> queue = new Queue<>();

        queue.enQueue(11);
        queue.enQueue(12);
        queue.enQueue(13);
        queue.enQueue(14);
        queue.enQueue(15);
        System.out.println(queue.size());
        System.out.println(queue.isEmpty());

        while (!queue.isEmpty()){
            System.out.print(queue.deQueue());
        }
    }
    static  void test02(){
        CircleQueue<Integer> queue = new CircleQueue<>();
        for (int i = 0; i <10 ; i++) {
            queue.enQueue(i);
        }

        for (int i = 0; i <5 ; i++) {
            queue.deQueue();
        }
        for (int i = 15; i < 25; i++) {
            queue.enQueue(i);
        }
        System.out.println(queue);
        while (! queue.isEmpty()){
            System.out.println(queue.deQueue());
        }
    }

    static  void  test03(){
        CircleDeque<Integer> queue = new CircleDeque<>();
        for (int i = 0; i <10 ; i++) {
            queue.enQueueFront(i+1);
            queue.enQueueRear(i+100);

        }
        System.out.println(queue);
    }
public static void main(String[] args) {
       test03();

    }
}
