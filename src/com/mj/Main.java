package com.mj;

public class Main {
    public static void main(String[] args) {

//        ArrayList<person>list= new ArrayList<>();
//        list.add(new person(10,"Jack"));
//        list.add(new person(13,"Tom"));
//        list.add(new person(20,"Ll"));
//        list.add(new person(40,"kk"));
//        list.add(new person(20,"tt"));
//        System.out.println(list);

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        for (int i = 0; i < 50; i++) {
            list.remove(0);

        }
        System.out.println(list);

    }

}
