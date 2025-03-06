package 第二季.算法.动态规划;
//https://leetcode-cn.com/problems/longest-common-subsequence/

/**
 * 1.定义初始状态    dp[0][j] = dp[i][0]=0
 * 2.定义状态转移  dp[i][j]  1. dp[i-1][j-1] +1   nums1[i-1]==nums2[j-1]
 * 2. max(dp[i-1][j],dp[i][j-1])                   nums1[i-1] !=nums2[j-1]
 */
public class _1143_最长公共子序列 {
    public static void main(String[] args) {
        int len = lcs3(new int[]{1, 3, 5, 10}, new int[]{1, 3, 10});
        System.out.println(len);

    }

    public int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null) return 0;
        char[] chars1 = text1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = text2.toCharArray();
        if (chars2.length == 0) return 0;
        char[] newrow = chars2, newcol = chars1;
        if (chars1.length > chars2.length) {
            newcol = chars2;
            newrow = chars1;
        }
        int[] dp = new int[newcol.length + 1];
        for (int i = 1; i <= newrow.length; i++) {
            int cur = 0;
            for (int j = 1; j <= newcol.length; j++) {
                int pre = cur;
                cur = dp[j];
                if (newrow[i - 1] == newcol[j - 1]) {
                    dp[j] = pre + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j-1]);
                }
            }
        }
        return dp[newcol.length];
    }

    static int lcs4(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) return 0;
        int[] newrow = nums2, newcol = nums1;
        if (nums1.length > nums2.length) {
            newcol = nums2;
            newrow = nums1;
        }
        int[] dp = new int[newcol.length + 1];
        for (int i = 1; i <= newrow.length; i++) {
            for (int j = newcol.length; j >= 1; j--) {
                int leftTop = dp[j - 1];
                if (newrow[i - 1] == newcol[j - 1]) {
                    dp[j] = leftTop + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j-1]);
                }
            }
        }
        return dp[newcol.length];
    }

    static int lcs3(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) return 0;
        int[] newrow = nums2, newcol = nums1;
        if (nums1.length > nums2.length) {
            newcol = nums2;
            newrow = nums1;
        }
        int[] dp = new int[newcol.length + 1];
        for (int i = 1; i <= newrow.length; i++) {
            int cur = 0;
            for (int j = 1; j <= newcol.length; j++) {
                int pre = cur;
                cur = dp[j];
                if (newrow[i - 1] == newcol[j - 1]) {
                    dp[j] = pre + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j-1]);
                }
            }
        }
        return dp[newcol.length];
    }

    static int lcs2(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) return 0;
        int[][] dp = new int[2][nums2.length + 1];
        for (int i = 1; i <= nums1.length; i++) {
            int row = i & 1;
            int prerow = (i - 1) & 1;
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[row][j] = dp[prerow][j - 1] + 1;
                } else {
                    dp[row][j] = Math.max(dp[prerow][j], dp[row][j - 1]);
                }
            }
        }
        return dp[nums1.length & 1][nums2.length];
    }

    /**
     * 求nums1和nums2前
     *
     * @param nums1
     * @param nums2
     * @return
     */
    static int lcs1(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) return 0;
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[nums1.length][nums2.length];
    }

    static int lcs(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;
        return lcs(nums1, nums1.length, nums2, nums2.length);
    }

    private static int lcs(int[] nums1, int i, int[] nums2, int j) {
        if (i == 0 || j == 0) return 0;
        if (nums1[i - 1] == nums2[j - 1]) {
            return lcs(nums1, i - 1, nums2, j - 1) + 1;
        }
        return Math.max(lcs(nums1, i - 1, nums2, j), lcs(nums1, i, nums2, j - 1));
    }

}
