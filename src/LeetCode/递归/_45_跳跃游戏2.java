package LeetCode.递归;

//https://leetcode-cn.com/problems/jump-game-ii/
public class _45_跳跃游戏2 {
    class Solution {
        public int jump(int[] nums) {
            int MaxReach = 0;
            int step = 0;
            int end = 0;
            for (int i = 0; i < nums.length - 1; i++) {
                MaxReach = Math.max(nums[i] + i, MaxReach);
                if (i == end) {
                    step++;
                    end = MaxReach;
                }
            }
            return step;
        }
    }

    public int jump1(int[] nums) {
        int position = nums.length - 1;
        int step = 0;
        while (position != 0) {
            for (int i = 0; i < position; i++) {
                if (nums[i] >= position - i) {
                    position = i;
                    step++;
                    break;
                }
            }
        }
        return step;
    }
}
