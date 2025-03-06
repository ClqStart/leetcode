package 第二季.算法.递归;


import 第二季.sort.cmpSort.Times;

public class ClimbStairs {
    public static void main(String[] args) {
        ClimbStairs cli = new ClimbStairs();
        int n = 10;
        Times.test("climbStairs", () -> {
            System.out.println(cli.climbStairs(n));
        });
        Times.test("climbStairs1", () -> {
            System.out.println(cli.climbStairs1(n));
        });
        Times.test("climbStairs2", () -> {
            System.out.println(cli.climbStairs2(n));
        });
    }
    public int climbStairs(int n){
        if(n<=2) return n;
        return climbStairs(n-1)+climbStairs(n-2);
    }

    public  int climbStairs1(int n){
        if(n<=2) return n;
        int first = 1;
        int second =2;
        for (int i = 3; i <=n ; i++) {
            second = first+second;
            first = second-first;
        }
        return second;
    }

    /**
     * 尾递归
     * @param n
     * @return
     */
    public  int climbStairs2(int n){
        return Solution(n,1,2);
    }
    public int Solution(int n , int n1,int n2){
        if (n == 1) return n1;
        if (n == 2) return n2;
        return Solution(--n,n2,n1+n2);
    }
}
