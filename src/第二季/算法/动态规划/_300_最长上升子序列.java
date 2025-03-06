package 第二季.算法.动态规划;
//https://leetcode-cn.com/problems/longest-increasing-subsequence/


/**
 *  1.状态定义：1.j<i, nums[j]<num[i] max(dp[j]+1)
 *             2.nums[j] >=nums[i] contiune;
 *  2.初始化 dp[0] =1,dp[i]=1;
 *
 *
 */


public class _300_最长上升子序列 {
    public static void main(String[] args) {
        System.out.println(lengthOfLIS(new int[]{10, 2, 2, 5, 1, 7, 101, 18}));
    }

    static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        int max = dp[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] <= nums[j]) continue;
                dp[i] = Math.max(dp[i],dp[j]+1);
            }
            max = Math.max(dp[i],max);
        }
        return max;
    }

}
