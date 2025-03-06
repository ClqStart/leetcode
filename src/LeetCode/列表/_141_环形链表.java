package LeetCode.列表;

//https://leetcode-cn.com/problems/linked-list-cycle/
public class _141_环形链表 {

    public class Solution {
        public boolean hasCycle(ListNode head) {
            if(head==null || head.next ==null) return false;
            ListNode lastHead = head;
            ListNode fastHead = head.next;
            while (fastHead != null && fastHead.next != null) {
                if (fastHead == lastHead) return true;
                lastHead = lastHead.next;
                fastHead = fastHead.next.next;
            }
            return false;
        }
    }
}



