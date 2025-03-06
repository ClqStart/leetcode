package 第二季.算法.递归;

import 第二季.sort.cmpSort.Times;

public class Fib {
    public static void main(String[] args) {
        Fib fib = new Fib();
        int n = 45;
        Times.test("fib0", () -> {
            System.out.println(fib.fib0(n));
        });
        Times.test("fib1", () -> {
            System.out.println(fib.fib1(n));
        });
        Times.test("fib2", () -> {
            System.out.println(fib.fib2(n));
        });
        Times.test("fib3", () -> {
            System.out.println(fib.fib3(n));
        });
        Times.test("fib4", () -> {
            System.out.println(fib.fib4(n));
        });
        Times.test("fib5", () -> {
            System.out.println(fib.fib5(n));
        });
        Times.test("fib6", () -> {
            System.out.println(fib.fib6(n));
        });
        Times.test("fib7", () -> {
            System.out.println(fib.fib7(n));
        });


    }

    public int fib0(int n) {
        if (n <= 2) return n = 1;
        return fib0(n - 1) + fib0(n - 2);
    }

    public int fib1(int n) {
        if (n <= 2) return n = 1;
        int[] array = new int[n + 1];
        array[1] = array[2] = 1;
        return fib1Array(n, array);
    }

    private int fib1Array(int n, int[] array) {
        if (array[n] == 0) {
            array[n] = fib1Array(n - 1, array) + fib1Array(n - 2, array);
        }
        return array[n];
    }

    public int fib2(int n) {
        if (n <= 2) return n = 1;
        int[] array = new int[n + 1];
        array[1] = array[2] = 1;
        for (int i = 3; i <= n; i++) {
            array[i] = array[i - 1] + array[i - 2];
        }
        return array[n];
    }

    /**
     * 滚动数组进行优化
     *
     * @param n
     * @return
     */
    public int fib3(int n) {
        if (n <= 2) return n = 1;
        int[] array = new int[2];
        array[0] = array[1] = 1;
        for (int i = 3; i <= n; i++) {
            array[i % 2] = array[(i - 1) % 2] + array[(i - 2) % 2];
        }
        return array[n % 2];
    }

    /**
     * 滚动数组进行优化对模2进一步优化
     *
     * @param n
     * @return
     */
    public int fib4(int n) {
        if (n <= 2) return n = 1;
        int[] array = new int[2];
        array[0] = array[1] = 1;
        for (int i = 3; i <= n; i++) {
            array[i & 1] = array[(i - 1) & 1] + array[(i - 2) & 1];
        }
        return array[n & 1];
    }

    public int fib5(int n) {
        if (n <= 2) return n = 1;
        int second = 1;
        int fist = 1;
        for (int i = 3; i <= n; i++) {
            second = fist + second;
            fist = second - fist;
        }
        return second;
    }

    public int fib6(int n) {
        double c = Math.sqrt(5);
        return (int) ((Math.pow((1 + c) / 2, n) - Math.pow((1 - c) / 2, n)) / c);
    }

    /**
     * 尾递归重复利用占；
     * @param n
     * @return
     */
    public  int fib7(int n){
        return  fib7(n,1,1);
    }

    private int fib7(int n, int first, int second) {
        if(n<=1)return first;
        return fib7(--n,second,first+second);
    }

}
