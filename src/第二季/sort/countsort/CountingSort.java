package 第二季.sort.countsort;

import 第二季.sort.cmpSort.Sort;

public class CountingSort extends Sort<Integer> {
    @Override
    protected void sort() {
        int max = array[0];
        int min = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        //统计出元素对应索引时并且统计出元素的次数
        int[] counts = new int[max - min + 1];
        for (int i = 0; i < array.length; i++) {
            counts[array[i] - min]++;
        }
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }
        //用于存放排序好的数据(从右往左保持稳定性)
        int[] output = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            output[--counts[array[i] - min]] = array[i];
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = output[i];
        }
    }

    private void sort0() {
        //获取最大值
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        //开辟最大之所对的容量
        int[] counts = new int[1 + max];

        //取出的值正好是新数据的索引
        for (int i = 0; i < array.length; i++) {
            counts[array[i]]++;
        }
        //取出整数的出现的次数，对整数进行排序
        int index = 0;
        for (int i = 0; i < counts.length; i++) {
            while ((counts[i]--) > 0) {
                array[index++] = i;
            }

        }
    }

    public static void main(String[] args) {
        Person[] persons = new Person[]{
                new Person(20, "A"),
                new Person(-13, "B"),
                new Person(17, "C"),
                new Person(12, "D"),
                new Person(-13, "E"),
                new Person(20, "F")
        };
        int max = persons[0].age;
        int min = persons[0].age;
        for (int i = 0; i < persons.length; i++) {
            if (persons[i].age > max) {
                max = persons[i].age;
            }
            if (persons[i].age < min) {
                min = persons[i].age;
            }
        }
        //统计出元素对应索引时并且统计出元素的次数
        int[] counts = new int[max - min + 1];
        for (Person value : persons) {
            counts[value.age - min]++;
        }
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }
        //用于存放排序好的数据(从右往左保持稳定性)
        Person[] output = new Person[persons.length];
        for (int i = persons.length - 1; i >= 0; i--) {
            output[--counts[persons[i].age - min]] = persons[i];
        }
        System.arraycopy(output, 0, persons, 0, persons.length);
        for (Person person : persons) {
            System.out.println(person);
        }
    }

    private static class Person {
        int age;
        String name;

        Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person  [age=" + age
                    + ", name=" + name + "]";
        }
    }

}
