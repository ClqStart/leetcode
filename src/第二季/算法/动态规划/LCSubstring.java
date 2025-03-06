package 第二季.算法.动态规划;

public class LCSubstring {
    public static void main(String[] args) {
        System.out.println(lcs2("ABDCBA", "ABDCBA"));
    }

    /**
     * 求str[i] 和 str[j] 公共字串
     * 1. str[i-1]==str[j-1]  dp[i,j] = dp[i-1,j-1]+1
     * 2. str[i-1] !=str[j-1]  dp[i,j] = 0;  以i和j结尾的公共字串为0
     * @param str1
     * @param str2
     * @return
     */

    static int lcs2(String str1, String str2) {
        if (str1 == null || str2 == null) return 0;
        char[] chars1 = str1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = str2.toCharArray();
        if (chars2.length == 0) return 0;
        char[] rowChars = chars1, colschars = chars2;
        if (chars2.length > chars1.length) {
            rowChars = chars2;
            colschars = chars1;
        }
        int max = 0;
        int[] dp = new int[colschars.length + 1];
        for (int i = 1; i <= rowChars.length; i++) {
            for (int j = colschars.length; j >=1; j--) {
                int leftTop = dp[j-1];
                if (rowChars[i - 1] != colschars[j - 1]) {
                    dp[j] = 0;
                } else {
                    dp[j] = leftTop + 1;
                    max = Math.max(dp[j], max);
                }
            }
        }
        return max;


    }

    static int lcs1(String str1, String str2) {
        if (str1 == null || str2 == null) return 0;
        char[] chars1 = str1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = str2.toCharArray();
        if (chars2.length == 0) return 0;
        char[] rowChars = chars1, colschars = chars2;
        if (chars2.length > chars1.length) {
            rowChars = chars2;
            colschars = chars1;
        }
        int max = 0;
        int[] dp = new int[colschars.length + 1];
        for (int i = 1; i <= rowChars.length; i++) {
            int cur = 0;
            for (int j = 1; j <= colschars.length; j++) {
                int leftTop = cur;
                cur = dp[j];
                if (rowChars[i - 1] != colschars[j - 1]) {
                    dp[j] = 0;
                } else {
                    dp[j] = leftTop + 1;
                    max = Math.max(dp[j], max);
                }
            }
        }
        return max;
    }

    static int lcs(String str1, String str2) {
        if (str1 == null || str2 == null) return 0;
        char[] chars1 = str1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = str2.toCharArray();
        if (chars2.length == 0) return 0;
        int max = 0;
        int[][] dp = new int[chars1.length + 1][chars2.length + 1];
        for (int row = 1; row <= chars1.length; row++) {
            for (int col = 1; col <= chars2.length; col++) {
                if (chars1[row - 1] != chars2[col - 1]) continue;
                dp[row][col] = dp[row - 1][col - 1] + 1;
                max = Math.max(dp[row][col], max);
            }
        }
        return max;
    }

}
