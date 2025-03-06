package LeetCode.列表;


//https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/

public class _83_删除排序链表中的重复元素 {
    class Solution {
        public ListNode deleteDuplicates(ListNode head) {
            if(head==null ||head.next==null){
                return head;
            }
            ListNode tmp = head;
            while (tmp.next !=null){
                if(tmp.val == tmp.next.val){
                    tmp.next=tmp.next.next;
                    continue;
                }
                tmp=tmp.next;
            }
            return head;
        }
    }
}
