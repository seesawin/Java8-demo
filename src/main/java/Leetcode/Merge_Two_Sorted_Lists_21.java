package Leetcode;

import dataStructure.ListNode;

public class Merge_Two_Sorted_Lists_21 {
    /**
     * Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.
     * <p>
     * Example:
     * <p>
     * Input: 1->2->4, 1->3->4
     * Output: 1->1->2->3->4->4
     */
    public static void main(String[] args) {
        Merge_Two_Sorted_Lists_21 object = new Merge_Two_Sorted_Lists_21();
        ListNode a1 = new ListNode(1);
        ListNode a2 = new ListNode(2);
        ListNode a4 = new ListNode(4);
        a1.next = a2;
        a2.next = a4;

        ListNode b1 = new ListNode(1);
        ListNode b3 = new ListNode(3);
        ListNode b4 = new ListNode(4);
        ListNode b5 = new ListNode(5);
        b1.next = b3;
        b3.next = b4;
        b4.next = b5;

        ListNode newNode = object.mergeTwoLists(a1, b1);
        System.out.println(newNode);
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode masterNode = l1;
        ListNode slaveNode = l2;
        if (l2.val > l1.val) {
            masterNode = l2;
            slaveNode = l1;
        }

        ListNode nowNode = masterNode;
        ListNode nextNode = null;
        ListNode oriMasterNextNode = null;
        ListNode oriSlaveNextNode = null;
        ListNode nextSlaveNode = null;

        ListNode popSlaveNode = null;

        int masterNodeCount = countListNodes(masterNode, 1);
        for (int i = 0; i < masterNodeCount; i++) {
            if (i == 0) {
                int v1 = nowNode.val;
                int v2 = slaveNode.val;

                if (v2 > v1 || v2 == v1) {
                    oriMasterNextNode = nowNode.next;
                    popSlaveNode = slaveNode;
                    slaveNode = slaveNode.next;
                    nowNode.next = popSlaveNode;
                    popSlaveNode.next = oriMasterNextNode;
                    nextNode = oriMasterNextNode;
                } else {
                    nextNode = nowNode.next;
                }
            } else {
                nowNode = nextNode;

                int v1 = nowNode.val;
                int v2 = slaveNode.val;

                if (v2 > v1 || v2 == v1) {
                    oriMasterNextNode = nowNode.next;
                    popSlaveNode = slaveNode;
                    slaveNode = slaveNode.next;
                    nowNode.next = popSlaveNode;
                    popSlaveNode.next = oriMasterNextNode;
                    nextNode = oriMasterNextNode;
                } else {
                    nextNode = nowNode.next;
                }

                if(nextNode.next == null || slaveNode.next == null) {
                    return nowNode;
                }
            }
        }
        return nowNode;
    }

    public int countListNodes(ListNode listNode, int count) {
        if (listNode.next == null) {
            return count;
        } else {
            count++;
            return countListNodes(listNode.next, count);
        }
    }
}
