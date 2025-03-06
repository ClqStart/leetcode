package LeetCode.列表;


import LeetCode.列表.ListNode;

//https://leetcode-cn.com/problems/middle-of-the-linked-list/


public class _876_链表的中间结点{
    class Solution {
        public ListNode middleNode(ListNode head) {
                if(head == null || head.next ==null){
                    return  head;
                }
                ListNode slow=head;
                ListNode fast = head;
                while (fast != null&&fast.next !=null){
                    slow=slow.next;
                    fast=fast.next.next;
                }
                return slow;
        }
    }
}
