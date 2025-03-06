package 第二季.算法.贪心;
//最大子序和

public class _53_最大子序和 {
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubarray(nums,0,nums.length));

    }
    static  int maxSubarray(int[] nums){
        if(nums==null ||nums.length==0) return 0;
        return maxSubarray(nums,0,nums.length);
    }

    /**
     * 分治算法   nlogn时间复杂性度
     * 分两部分，分别进行求最大，再中间进行求解
     * @param nums
     * @param begin
     * @param end
     * @return
     */
    static int maxSubarray(int[] nums, int begin, int end) {
        if (end - begin < 2) return nums[begin];
        int mid = (begin + end) >> 1;
        int leftMax = Integer.MIN_VALUE;
        int leftSumm=0;
        for (int i = mid-1; i >= begin ; i--) {
            leftSumm +=nums[i];
            leftMax = Math.max(leftSumm,leftMax);
        }
        int rightMax = Integer.MIN_VALUE;
        int rightSum = 0;
        for (int i = mid; i <end ; i++) {
            rightSum +=nums[i];
            rightMax = Math.max(rightMax,rightSum);
        }
        return Math.max(leftMax + rightMax,
                Math.max(maxSubarray(nums, begin, mid),
                        maxSubarray(nums, mid, end)));
    }


    /**
     * 贪心
     *
     * @param nums
     * @return
     */
    static int maxSubarray2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            int sum = 0;
            for (int end = begin; end < nums.length; end++) {
                sum += nums[end];
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    static int maxSubarray1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            for (int end = begin; end < nums.length; end++) {
                int sum = 0;
                for (int i = begin; i <= end; i++) {
                    sum += nums[i];
                }
                max = Math.max(max, sum);
            }
        }
        return max;
    }
}
