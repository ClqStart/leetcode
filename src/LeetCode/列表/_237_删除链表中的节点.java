package LeetCode.列表;

import LeetCode.列表.ListNode;

//https://leetcode-cn.com/problems/delete-node-in-a-linked-list/

class __237_删除链表中的节点 {
    class Solution {
        public void deleteNode(ListNode node) {
            node.val=node.next.val;
            node.next=node.next.next;

        }
    }
}
