package com.lp.jianlai.leetcode;

/**
 * @Author: liupu
 * @Description:两数相加
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 * @Date: 2020/3/17
 */
public class Number2Code {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        StringBuilder b1 = new StringBuilder();
        StringBuilder b2 = new StringBuilder();
        b1 = getNode(l1, b1);
        b2 = getNode(l2, b2);
        String s = doAdd(b1.reverse().toString(), b2.reverse().toString());
        return changeNum(s);
    }

    public ListNode changeNum(String s) {
        String[] str = s.split("");

        ListNode head = new ListNode(Integer.valueOf(str[str.length - 1]));
        ListNode node = head;
        for (int i = str.length - 1; i > 0; i--) {
            ListNode nextNode = new ListNode(Integer.valueOf(str[i - 1]));
            node.next = nextNode;
            node = node.next;
        }
        return head;
    }

    public StringBuilder getNode(ListNode node, StringBuilder sum) {
        if (node == null) {
            return sum;
        }
        sum.append(node.val);
        return getNode(node.next, sum);
    }

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

    String doAdd(String a, String b) {
        String str = "";
        int lenA = a.length();
        int lenB = b.length();
        int maxLen = (lenA > lenB) ? lenA : lenB;
        int minLen = (lenA < lenB) ? lenA : lenB;
        String strTmp = "";
        for (int i = maxLen - minLen; i > 0; i--) {
            strTmp += "0";
        }
        //把长度调整到相同
        if (maxLen == lenA) {
            b = strTmp + b;
        } else
            a = strTmp + a;
        int JW = 0;//进位
        for (int i = maxLen - 1; i >= 0; i--) {
            int tempA = Integer.parseInt(String.valueOf(a.charAt(i)));
            int tempB = Integer.parseInt(String.valueOf(b.charAt(i)));
            int temp;
            if (tempA + tempB + JW >= 10 && i != 0) {
                temp = tempA + tempB + JW - 10;
                JW = 1;
            } else {
                temp = tempA + tempB + JW;
                JW = 0;
            }
            str = String.valueOf(temp) + str;
        }
        return str;
    }


    /**
     * 高级方式
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        ListNode cursor = root;
        int carry = 0;
        while(l1 != null || l2 != null || carry != 0) {
            int l1Val = l1 != null ? l1.val : 0;
            int l2Val = l2 != null ? l2.val : 0;
            int sumVal = l1Val + l2Val + carry;
            carry = sumVal / 10;

            ListNode sumNode = new ListNode(sumVal % 10);
            cursor.next = sumNode;
            cursor = sumNode;

            if(l1 != null) l1 = l1.next;
            if(l2 != null) l2 = l2.next;
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