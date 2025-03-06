package LeetCode.列表;

//https://leetcode-cn.com/problems/reverse-linked-list/
public class _206_反转链表 {
    class Solution {

        public ListNode reverseList(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode newlistnode = reverseList(head.next);
            head.next.next = head;
            head.next = null;

            return newlistnode;
        }
    }

    public ListNode reverseList1(ListNode head) {

        ListNode newHead = null;
        while (head != null) {
            ListNode tmp = head.next;
            head.next = newHead;
            newHead = head;
            head = tmp;
        }
        return newHead;

    }
}
