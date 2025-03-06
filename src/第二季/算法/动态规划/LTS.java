package 第二季.算法.动态规划;

public class LTS {
    public static void main(String[] args) {
        System.out.println(lengthOfLTS2(new int[]{10, 2, 2, 5, 1, 7, 101, 18}));
    }

    static int lengthOfLTS2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int len = 0;
        int[] top = new int[nums.length];
        for (int num : nums) {
            int begin = 0;
            int end = len;
            while (begin < end) {
                int mid = (begin + end) >> 1;
                if (num <= top[mid]) {
                    end = mid;
                } else {
                    begin = mid + 1;
                }
            }
            top[begin] = num;
            if (begin == len) len++;
        }
        return len;
    }

    /**
     * 牌顶
     *
     * @param nums
     * @return
     */
    static int lengthOfLTS1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int len = 0;
        int[] top = new int[nums.length];
        for (int num : nums) {
            int j = 0;
            while (j < len) {
                if (top[j] >= num) {
                    top[j] = num;
                    break;
                }
                j++;
            }
            if (j == len) {
                len++;
                top[j] = num;
            }
        }
        return len;
    }


    /**
     * 动态规划
     *
     * @param nums
     * @return
     */
    static int lengthOfLTS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        int max = dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] <= nums[j]) continue;
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }


}
