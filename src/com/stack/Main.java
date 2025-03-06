package com.stack;

public class Main {

    public static void main(String[] args) {
        Stack<Integer> stack =new Stack<>();
        stack.push(11);
        stack.push(12);
        stack.push(13);
        stack.push(14);
        System.out.println(stack.top());
        System.out.println(stack.size());

        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
        stack.clear();
        System.out.println(stack.isEmpty());
    }
}
