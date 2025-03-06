package 第二季.算法.递归;

import 第二季.sort.cmpSort.Times;

public class TailCall {

    public static void main(String[] args) {
        TailCall tc = new TailCall();
        int n =10;
        Times.test("factorial", () -> {
            System.out.println(tc.factorial(n));
        });
        Times.test("factorial1", () -> {
            System.out.println(tc.factorial1(n));
        });

    }

    public int factorial(int n) {
        if (n <= 1) return n;
        return n * factorial(n - 1);
    }

    public int factorial1(int n) {
        return factorial1(n, 1);
    }

    private int factorial1(int n, int result) {
        if (n <= 1) return result;
        return factorial1(n - 1, result * n);
    }
}
