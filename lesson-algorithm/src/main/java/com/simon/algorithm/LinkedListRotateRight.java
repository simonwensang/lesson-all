package com.simon.algorithm;

import com.alibaba.fastjson.JSON;

/**
 * 链表右移 k 个节点到表头
 * 
 * 题目：给定一个链表 1->2->3->4->5->null，右移 k 个节点
 * 例如 k=2，结果应该是 4->5->1->2->3->null
 * 
 * Created by sang on 2019/6/19.
 */
public class LinkedListRotateRight {

    // 链表节点定义
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 方法1：双指针法（推荐）
     * 
     * 算法思想：
     * 1. 先计算链表长度 n
     * 2. 将 k 对 n 取模，避免无效旋转（k >= n 时）
     * 3. 使用快慢指针：快指针先走 k 步
     * 4. 然后快慢指针同时走，当快指针到达末尾时，慢指针指向新尾节点
     * 5. 将原尾节点指向原头节点，形成环
     * 6. 断开环，得到新头节点
     * 
     * 举例：1->2->3->4->5->null，k=2
     * 
     * 步骤演示：
     * 1. 计算长度 n=5
     * 2. k = 2 % 5 = 2
     * 3. 快指针先走 2 步：fast 指向 3
     * 4. 快慢指针同时走：
     *    slow=1, fast=3
     *    slow=2, fast=4
     *    slow=3, fast=5
     * 5. 此时 slow=3，slow.next=4 是新头节点
     * 6. 将 5->next 指向 1，形成环：1->2->3->4->5->1...
     * 7. 断开 3->next，新头是 4
     * 结果：4->5->1->2->3->null
     * 
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public ListNode rotateRight(ListNode head, int k) {
        // 边界条件处理
        if (head == null || head.next == null || k == 0) {
            return head;
        }

        // 1. 计算链表长度，并找到尾节点
        int length = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        // 2. k 对长度取模，避免无效旋转
        k = k % length;
        if (k == 0) {
            return head;
        }

        // 3. 快慢指针：快指针先走 k 步
        ListNode fast = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }

        // 4. 快慢指针同时走，直到快指针到达尾节点
        ListNode slow = head;
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }

        // 5. 此时 slow 指向新尾节点，slow.next 是新头节点
        ListNode newHead = slow.next;

        // 6. 将原尾节点指向原头节点，形成环
        tail.next = head;

        // 7. 断开环
        slow.next = null;

        return newHead;
    }

    /**
     * 方法2：暴力法（每次右移 1 个节点，重复 k 次）
     * 
     * 算法思想：
     * 每次将尾节点移到表头，重复 k 次
     * 
     * 举例：1->2->3->4->5->null，k=2
     * 
     * 第1次右移：
     * 1. 找到尾节点 5 和前驱节点 4
     * 2. 将 5 移到表头：5->1->2->3->4->null
     * 
     * 第2次右移：
     * 1. 找到尾节点 4 和前驱节点 3
     * 2. 将 4 移到表头：4->5->1->2->3->null
     * 
     * 时间复杂度：O(n * k)
     * 空间复杂度：O(1)
     */
    public ListNode rotateRightBruteForce(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        }

        // k 对长度取模
        int length = getLength(head);
        k = k % length;

        // 重复 k 次右移操作
        for (int i = 0; i < k; i++) {
            head = rotateOne(head);
        }

        return head;
    }

    /**
     * 右移 1 个节点：将尾节点移到表头
     */
    private ListNode rotateOne(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 找到尾节点和前驱节点
        ListNode prev = head;
        ListNode tail = head;
        while (tail.next != null) {
            prev = tail;
            tail = tail.next;
        }

        // 将尾节点移到表头
        tail.next = head;
        prev.next = null;

        return tail;
    }

    /**
     * 计算链表长度
     */
    private int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }

    /**
     * 创建链表（从数组）
     */
    public static ListNode createList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        return head;
    }

    /**
     * 打印链表
     */
    public static String printList(ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) {
                sb.append("->");
            }
            head = head.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LinkedListRotateRight solution = new LinkedListRotateRight();

        // 测试用例1：1->2->3->4->5->null，k=2
        ListNode head1 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.println("原链表: " + printList(head1));
        ListNode result1 = solution.rotateRight(head1, 2);
        System.out.println("右移2个节点: " + printList(result1));
        System.out.println("期望结果: 4->5->1->2->3");
        System.out.println();

        // 测试用例2：1->2->3->4->5->null，k=3
        ListNode head2 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.println("原链表: " + printList(head2));
        ListNode result2 = solution.rotateRight(head2, 3);
        System.out.println("右移3个节点: " + printList(result2));
        System.out.println("期望结果: 3->4->5->1->2");
        System.out.println();

        // 测试用例3：1->2->3->4->5->null，k=7（k > 长度）
        ListNode head3 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.println("原链表: " + printList(head3));
        ListNode result3 = solution.rotateRight(head3, 7);
        System.out.println("右移7个节点: " + printList(result3));
        System.out.println("期望结果: 4->5->1->2->3（7 % 5 = 2）");
        System.out.println();

        // 测试用例4：暴力法验证
        ListNode head4 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.println("原链表: " + printList(head4));
        ListNode result4 = solution.rotateRightBruteForce(head4, 2);
        System.out.println("暴力法右移2个节点: " + printList(result4));
    }

}
