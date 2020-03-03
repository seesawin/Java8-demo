package dataStructure;

import java.util.*;
import java.util.stream.Collectors;

public class Leetcode {
    public static void main(String[] args) {
        /*
        Question:
        Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.
        Example:
        Input: 1->2->4, 1->3->4
        Output: 1->1->2->3->4->4
        * */
        List<Integer> integers = Arrays.asList(1, 2, 4);
        integers = new ArrayList<>(integers);
        List<Integer> integers1 = Arrays.asList(1, 3, 4);
        integers.addAll(integers1);
        integers.sort(Comparator.naturalOrder());
//        integers.forEach(System.out::println);

        System.out.println("------------------------------------------------");

        LinkedList<Integer> list1 = new LinkedList<>();
        list1.add(1);
        list1.add(2);
        list1.add(4);

        LinkedList<Integer> list2 = new LinkedList<>();
        list1.add(1);
        list1.add(3);
        list1.add(4);

        list1.addAll(list2);
        list1.sort(Comparator.naturalOrder());
//        list1.forEach(System.out::println);

        System.out.println("----------------------------------------");

        ListNode a1 = new ListNode(1);
        ListNode a2 = new ListNode(2);
        ListNode a4 = new ListNode(4);
        a1.next = a2;
        a2.next = a4;
//        a4.next = null;

        ListNode b1 = new ListNode(1);
        ListNode b3 = new ListNode(3);
        ListNode b4 = new ListNode(4);
        b1.next = b3;
        b3.next = b4;
//        b4.next = null;

        Leetcode leetcode = new Leetcode();
//        int allListNode1 = leetcode.getAllListNode(a1);
//        System.out.println(allListNode1);
//        int allListNode2 = leetcode.getAllListNode(b1);
//        System.out.println(allListNode2);

        ListNode listNode = leetcode.mergeTwoListNode(a1, b1);
        List<Map<Integer, ListNode>> allListNodeVal = leetcode.getAllListNodeVal(listNode, new ArrayList<>());
        List<Integer> collect = allListNodeVal.stream().map(map -> map.keySet().stream().findFirst().get()).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    public int getAllListNode(ListNode listNode) {
        if (listNode.next == null) {
            return listNode.val;
        } else {
            return getAllListNode(listNode.next);
        }
    }

    public List<Map<Integer, ListNode>> getAllListNodeVal(ListNode listNode, List<Map<Integer, ListNode>> valList) {
        if (listNode.next == null) {
            return valList;
        } else {
            Map map = new HashMap<>();
            map.put(listNode.val, listNode);
            valList.add(map);
            return getAllListNodeVal(listNode.next, valList);
        }
    }

    public ListNode mergeTwoListNode(ListNode masterListNode, ListNode node2) {
        List<Map<Integer, ListNode>> allListNodeValOfNode2 = getAllListNodeVal(node2, new ArrayList<>());

        allListNodeValOfNode2.stream().forEach(map -> {
            Integer key = map.keySet().stream().findFirst().get();
            ListNode listNode1 = map.get(key);
            Optional<ListNode> listNodeOp = getSameValListNode(masterListNode, key);
            if(listNodeOp.isPresent()) {
                ListNode listNode = listNodeOp.get();
                ListNode next = listNode.next;
                //加入節點
                listNode1.next = next;
                listNode.next = listNode1;
            } else {
                ListNode greatListNode = getGreatListNode(masterListNode, key);
                ListNode next = greatListNode.next;
                if(next == null) {
                    listNode1.next = greatListNode;
                } else {
                    ListNode oriNext = listNode1.next;
                    listNode1.next = greatListNode;
                    greatListNode.next = oriNext;
                }
            }
        });
        return masterListNode;
    }

    public Optional<ListNode> getSameValListNode(ListNode masterListNode, Integer i) {
        if(masterListNode.val == i) {
            return Optional.of(masterListNode);
        } else {
            if(masterListNode.next == null) {
                return Optional.empty();
            }
            return getSameValListNode(masterListNode.next, i);
        }

    }

    public ListNode getSameLastListNode(ListNode masterListNode) {
        if(masterListNode.next == null) {
            return masterListNode;
        } else {
            return getSameLastListNode(masterListNode.next);
        }
    }

    public ListNode getGreatListNode(ListNode masterListNode, int val) {
        if(masterListNode.val > val) {
            if(masterListNode.next == null) {
                return masterListNode;
            }
            //取得下一節點
            ListNode nextListNode = getGreatListNode(masterListNode.next, val);
            if(nextListNode == null) {
                return masterListNode;
            }

            int nextVal = nextListNode.val;
            if(nextVal > val) {
                return masterListNode;
            } else {

                return getGreatListNode(masterListNode.next, val);
            }
        } else {
            return getGreatListNode(masterListNode.next, val);
        }
    }

    public ListNode mergeTwoNodeSolution(ListNode node1, ListNode node2) {
        ListNode master;
        if(node1.val < node2.val) {
            master = node1;
        } else {
            master = node2;
        }

        ListNode masterNext = master.next;
        int masterNextVal = masterNext.val;
        return null;
    }

}
