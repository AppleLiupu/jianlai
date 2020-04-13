package com.lp.jianlai.leetcode;

/**
 * @Author: liupu
 * @Description:两数相加
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 * @Date: 2020/3/17
 */
public class Number2Code {

    public ListNode add(ListNode node, int num) {
        ListNode node1 = new ListNode(num);
        node.next = node1;
        return node1;
    }

    public void printNode(ListNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node.val);
        printNode(node.next);
    }

    /**
     * main
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        ListNode temp = root;
        boolean flag = false;
        while(l1 != null || l2 != null || flag){
            int val1 = l1 != null ? l1.val : 0;
            int val2 = l2 != null ? l2.val : 0;
            int sum = 0;
            if(flag){
                sum = val1 + val2 + 1;
            }else{
                sum = val1 + val2;
            }
            //是否进位
            flag = sum / 10 > 0 ? true : false;
            temp.next = new ListNode(sum % 10);
            temp = temp.next;

            if(l1!=null)l1 = l1.next;
            if(l2!=null)l2 = l2.next;
        }
        return root.next;
    }


    public static void main(String[] args) {
        Number2Code test = new Number2Code();

        ListNode n1 = new ListNode(2);
        test.add(test.add(n1, 4), 3);

        ListNode n2 = new ListNode(5);
        test.add(test.add(n2, 6), 4);

        test.printNode(test.addTwoNumbers(n1, n2));
    }
}

class ListNode {
    int val;

    ListNode next;

    ListNode(int x) {
        val = x;
    }
}