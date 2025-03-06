package 第二季.算法.动态规划;

public class knapsack {
    public static void main(String[] args) {
        int[] value = {6, 3, 5, 4, 6};
        int[] weight = {2, 2, 6, 5, 4};
        int capacity = 20;
        System.out.println(RightFull2(value, weight, capacity));
    }
    private static int RightFull2(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) return 0;
        if (values.length != weights.length || capacity <= 0) return 0;

        int[] dp = new int[capacity + 1];
        for (int i = 1; i <= capacity; i++) {
            dp[i] = Integer.MIN_VALUE;
        }
        for (int i = 1; i <= values.length; i++) {
            for (int j = capacity; j >=1; j--) {
                if (j < weights[i-1]) continue;
                    dp[j] = Math.max(dp[j - weights[i-1]] + values[i-1],dp[j]);
                }
            }
        return dp[capacity] < 0 ? -1 : dp[capacity];
    }

    private static int RightFull1(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) return 0;
        if (values.length != weights.length || capacity <= 0) return 0;

        int[][] dp = new int[values.length + 1][capacity + 1];
        for (int i = 1; i <= capacity; i++) {
            dp[0][i] = Integer.MIN_VALUE;
        }
        for (int i = 1; i <= values.length; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (j < weights[i-1]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j - weights[i-1]] + values[i-1], dp[i - 1][j]);
                }
            }
        }
        return dp[values.length][capacity] < 0 ? -1 : dp[values.length][capacity];
    }

    private static int RightFull(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) return 0;
        if (values.length != weights.length || capacity <= 0) return 0;
        int[] dp = new int[capacity + 1];
        for (int j = 1; j <= capacity; j++) {
            dp[j] = Integer.MIN_VALUE;
        }
        for (int i = 1; i <= values.length; i++) {
            for (int j = capacity; j >= weights[i - 1]; j--) {
                dp[j] = Math.max(dp[j], values[i - 1] + dp[j - weights[i - 1]]);
            }
        }
        return dp[capacity] < 0 ? -1 : dp[capacity];

    }

    /**
     * 最大总价值
     *
     * @param value
     * @param weight
     * @param capacity
     * @return
     */
    private static int maxValue3(int[] value, int[] weight, int capacity) {
        if (value == null || weight == null) return 0;
        if (value.length == 0 || weight.length == 0) return 0;
        if (value.length != weight.length || capacity <= 0) return 0;
        int[] dp = new int[capacity + 1];
        for (int i = 1; i <= value.length; i++) {   //i表示几件物品
            for (int j = capacity; j >= weight[i - 1]; j--) {   //j表示容量
                dp[j] = Math.max(dp[j - weight[i - 1]] + value[i - 1], dp[j]);
            }
        }
        return dp[capacity];
    }

    private static int maxValue2(int[] value, int[] weight, int capacity) {
        if (value == null || weight == null) return 0;
        if (value.length == 0 || weight.length == 0) return 0;
        if (value.length != weight.length || capacity <= 0) return 0;
        int[] dp = new int[capacity + 1];
        for (int i = 1; i <= value.length; i++) {   //i表示几件物品
            for (int j = capacity; j >= 1; j--) {   //j表示容量
                if (j < weight[i - 1]) continue;
                dp[j] = Math.max(dp[j - weight[i - 1]] + value[i - 1], dp[j]);
            }
        }
        return dp[capacity];
    }

    private static int maxValue1(int[] value, int[] weight, int capacity) {
        if (value == null || weight == null) return 0;
        if (value.length == 0 || weight.length == 0) return 0;
        if (value.length != weight.length || capacity <= 0) return 0;
        int[][] dp = new int[2][capacity + 1];
        for (int i = 1; i <= value.length; i++) {   //i表示几件物品
            for (int j = 1; j <= capacity; j++) {   //j表示容量
                if (j < weight[i - 1]) {
                    dp[i & 1][j] = dp[(i - 1) & 1][j];
                } else {
                    dp[i & 1][j] = Math.max(dp[(i - 1) & 1][j - weight[i - 1]] + value[i - 1], dp[(i - 1) & 1][j]);
                }
            }
        }
        return dp[value.length & 1][capacity];
    }

    /**
     * 1.当最后一件物品不选 d[i][j] = d[i-1][j]    i 表示物品j表示重量   容量不够
     * 2.i件物品选  dp[i][j] = value[i-1] + dp[i-1,j-weight[i-1]] 和  d[i-1][j] 最大值  ;
     *
     * @param value
     * @param weight
     * @param capacity
     * @return
     */

    private static int maxValue(int[] value, int[] weight, int capacity) {
        if (value == null || weight == null) return 0;
        if (value.length == 0 || weight.length == 0) return 0;
        if (value.length != weight.length || capacity <= 0) return 0;
        int[][] dp = new int[value.length + 1][capacity + 1];
        for (int i = 1; i <= value.length; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (j < weight[i - 1]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j - weight[i - 1]] + value[i - 1], dp[i - 1][j]);
                }
            }
        }
        return dp[value.length][capacity];
    }
}
