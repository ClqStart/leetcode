package com.Line;

@SuppressWarnings("unchecked")
public class CircleDeque<E> {

    private  int  front;
    private  int size;
    private  E[]  elements;
    private static final int DEFAULT_CAPACITY = 10;
    public  CircleDeque(){
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size() {
        return  size;
    }

    public boolean isEmpty() {
        return  size==0;
    }
    public  void clear(){

        for (int i = 0; i <size; i++) {
            elements[index(i)] =null;
        }
        front=0;
        size=0;
    }



    public void enQueueRear(E element) {
        enSureCapaCity(size+1);
        elements[index(size)]=element;
        size++;

    }

    public E deQueueFront() {
        E frontElement =elements[front];
        elements[front]=null;
        front=index(1);
        return  frontElement;
    }

    public void enQueueFront(E element) {
        enSureCapaCity(size+1);
        front = index(-1);
        elements[front]=element;
        size++;
    }

    public E deQueueRear() {
       int rearIndex= index(size-1);
       E rear = elements[rearIndex];
       elements[rearIndex]=null;
       size--;
       return  rear;
    }

    public E front() {
        return elements[front];
    }

    public E rear() {
        return  elements[index(size-1)];
    }

    private  int  index(int index) {
        index+=front;
        if(index>0){
            return  index % elements.length;  //ȡģת��  index - (index >= elements.length ? elements.length : 0 )
        }else {
            return  index + elements.length;
        }
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
