package LeetCode.递归;

//https://leetcode-cn.com/problems/climbing-stairs/
public class _70_爬楼梯 {
    //超时
    class Solution1 {
        public int climbStairs(int n) {
            if (n == 1 || n == 2) return n;
            return climbStairs(n - 2) + climbStairs(n - 1);
        }
    }

    class Solution2 {
        public int climbStairs(int n) {
            int n1 = 1;
            int n2 = 2;
            for (int i = 2; i < n; i++) {
                int tmp = n2;
                n2 = n1 + n2;
                n1 = tmp;
            }
            return n <= 2 ? n : n2;
        }
    }

    /**
     * 尾递归
     */

    class Solution3 {
        public int climbStairs(int n) {
            return resolution(n, 1, 2);
        }

        public int resolution(int n, int n1, int n2) {
            if (n == 1) return n1;
            if (n == 2) return n2;
            return resolution(--n, n2, n1 + n2);
        }
    }
}
