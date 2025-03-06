package com.映射;

import com.映射.Set.Set;
import com.映射.Set.TreeSet;
import com.映射.map.Map;
import com.映射.map.TreeMap;

public class Main {
    static void test() {
     Map<String ,Integer> map = new TreeMap<>();

     map.put("class",2);
     map.put("public",5);
     map.put("text",6);
     map.put("public",8);
     map.put("sex",20);

     map.traversal(new Map.Visitor<String, Integer>() {
         @Override
         public boolean visit(String key, Integer value) {
             System.out.println(key + "_" + value);
             return false;
         }
     });
    }

    static  void test1() {
        Set<String> set = new TreeSet<>();
        set.add("a");
        set.add("c");
        set.add("d");
        set.add("b");
        set.add("c");
    set.traversal(new Set.Visitor<String>() {
        @Override
        public boolean visit(String element) {
            System.out.println(element);
            return false;
        }
    });
    }

    public static void main(String[] args) {
            test1();
    }
}
