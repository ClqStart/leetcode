package com.哈希表;

import com.mj.person;

public class Person {
    private  int age;
    private  float  height;
    private  String  name;

    public Person(int age, float height, String name) {
        this.age = age;
        this.height = height;
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hashcode = Integer.hashCode(age);
        hashcode = hashcode*31 + Float.hashCode(height);
        hashcode = hashcode*31 + (name != null ? name.hashCode():0);

        return hashcode;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj )return true;
        if(obj ==null || obj.getClass() != getClass()) return false;   // obj instanceof  Person
        Person person = (Person) obj;
        return person.age == age
                && person.height == height
                && person.name == null ? name ==null :person.name.equals(name);
    }
}
