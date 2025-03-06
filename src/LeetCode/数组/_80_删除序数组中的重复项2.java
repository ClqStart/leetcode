package LeetCode.数组;

//https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii/
public class _80_删除序数组中的重复项2 {
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) {
            return nums.length;
        }
        int q = 1;
        int p = 1;
        int count = 1;

        while (p < nums.length) {
            if (nums[p] == nums[p - 1]) {
                count++;
            } else {
                count = 1;
            }
            if (count < 3) {
                nums[q] = nums[p];
                q++;
            }
            p++;
        }
        return q;
    }

    public int removeDuplicates1(int[] nums) {
        int len = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i < 2 || nums[i] != nums[len - 2]) {
                if (len != i) nums[len] = nums[i];
                len++;
            }

        }
        return len;
    }
}
