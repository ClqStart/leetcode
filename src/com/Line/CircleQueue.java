package com.Line;

@SuppressWarnings("unchecked")
public class CircleQueue<E> {
    private  int front;
    private  int size;
    private  E[]   elements;
    private static final int DEFAULT_CAPACITY = 10;

    CircleQueue(){
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size(){
        return  size;
    }
    public  void clear(){

        for (int i = 0; i <size ; i++) {
            elements[index(i)] =null;
        }
        front=0;
        size=0;
    }

    public boolean isEmpty(){
        return  size==0;
    }

    void enQueue(E element){
        enSureCapaCity(size+1);
        elements[index(size)] = element;
        size++;
    }

    E deQueue(){
        E frontElement = elements[front];
        elements[front] = null;
        size--;
        front =index(1);
        return  frontElement;
    }

    public  E  front(){
        return  elements[front];
    }

    private  void enSureCapaCity(int capacity){
        int oldCapacity = elements.length;
        if (oldCapacity>=capacity) return;

        int NewCapacity = oldCapacity+(oldCapacity>>1);
        E[] newElement = (E[]) new Object[NewCapacity];

        for (int i = 0; i < size; i++) {
            newElement[i] = elements[index(i)];
        }
        elements = newElement;
        front=0;
    }

    private  int index(int i){
        return (i+front) % elements.length;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("capacity=").append(elements.length).append(" size=").append(size).append("  front=").append(front).append(", [");
        for (int i = 0; i < elements.length; i++) {
            if(i!=0){
                stringBuilder.append(",");
            }
            stringBuilder.append(elements[i]);
        }
        stringBuilder.append("]");
        return  stringBuilder.toString();
    }
}
