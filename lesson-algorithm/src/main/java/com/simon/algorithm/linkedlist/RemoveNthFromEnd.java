package com.simon.algorithm.linkedlist;

/**
 * 删除链表倒数第 N 个节点
 * 
 * 题目：给定一个链表 1->2->3->4->5->null，删除倒数第 n 个节点
 * 例如 n=2，删除节点 4，结果应该是 1->2->3->5->null
 * 
 * Created by sang on 2019/6/19.
 */
public class RemoveNthFromEnd {

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
     * 使用快慢指针，快指针先走 n 步，然后快慢指针同时走。
     * 当快指针到达末尾时，慢指针指向要删除节点的前一个节点。
     * 
     * 举例：1->2->3->4->5->null，n=2（删除倒数第2个节点，即节点4）
     * 
     * 步骤演示：
     * 1. 创建虚拟头节点 dummy，指向原头节点
     *    dummy->1->2->3->4->5->null
     * 2. 快指针先走 n=2 步：fast 指向 3
     * 3. 快慢指针同时走：
     *    slow=dummy, fast=3
     *    slow=1, fast=4
     *    slow=2, fast=5
     * 4. 此时 fast 到达末尾，slow 指向 2（要删除节点4的前驱）
     * 5. 执行删除：slow.next = slow.next.next
     *    即 2->next = 5
     * 结果：1->2->3->5->null
     * 
     * 时间复杂度：O(n) - 只遍历一次链表
     * 空间复杂度：O(1)
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 创建虚拟头节点，简化删除头节点的情况
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // 快慢指针都从虚拟头节点开始
        ListNode fast = dummy;
        ListNode slow = dummy;

        // 快指针先走 n 步
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        // 快慢指针同时走，直到快指针到达末尾
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }

        // 此时 slow 指向要删除节点的前一个节点
        // 执行删除操作
        slow.next = slow.next.next;

        return dummy.next;
    }

    /**
     * 方法2：两次遍历法
     * 
     * 算法思想：
     * 1. 第一次遍历计算链表长度 L
     * 2. 倒数第 n 个节点 = 正数第 (L - n + 1) 个节点
     * 3. 第二次遍历找到要删除节点的前一个节点，执行删除
     * 
     * 举例：1->2->3->4->5->null，n=2
     * 
     * 步骤演示：
     * 1. 第一次遍历：长度 L = 5
     * 2. 要删除的是正数第 (5 - 2 + 1) = 4 个节点，即节点 4
     * 3. 第二次遍历：找到第 3 个节点（节点 3）
     * 4. 执行删除：3->next = 5
     * 结果：1->2->3->5->null
     * 
     * 时间复杂度：O(n) - 遍历两次链表
     * 空间复杂度：O(1)
     */
    public ListNode removeNthFromEndTwoPass(ListNode head, int n) {
        // 1. 计算链表长度
        int length = getLength(head);

        // 2. 计算要删除的是正数第几个节点
        int targetIndex = length - n + 1;

        // 3. 如果要删除的是头节点
        if (targetIndex == 1) {
            return head.next;
        }

        // 4. 找到要删除节点的前一个节点
        ListNode current = head;
        for (int i = 1; i < targetIndex - 1; i++) {
            current = current.next;
        }

        // 5. 执行删除
        current.next = current.next.next;

        return head;
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
        RemoveNthFromEnd solution = new RemoveNthFromEnd();

        // 测试用例1：1->2->3->4->5->null，删除倒数第2个节点
        ListNode head1 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.println("原链表: " + printList(head1));
        ListNode result1 = solution.removeNthFromEnd(head1, 2);
        System.out.println("删除倒数第2个节点: " + printList(result1));
        System.out.println("期望结果: 1->2->3->5");
        System.out.println();

        // 测试用例2：1->2->3->4->5->null，删除倒数第1个节点（尾节点）
        ListNode head2 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.println("原链表: " + printList(head2));
        ListNode result2 = solution.removeNthFromEnd(head2, 1);
        System.out.println("删除倒数第1个节点: " + printList(result2));
        System.out.println("期望结果: 1->2->3->4");
        System.out.println();

        // 测试用例3：1->2->3->4->5->null，删除倒数第5个节点（头节点）
        ListNode head3 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.println("原链表: " + printList(head3));
        ListNode result3 = solution.removeNthFromEnd(head3, 5);
        System.out.println("删除倒数第5个节点: " + printList(result3));
        System.out.println("期望结果: 2->3->4->5");
        System.out.println();

        // 测试用例4：只有一个节点，删除它
        ListNode head4 = createList(new int[]{1});
        System.out.println("原链表: " + printList(head4));
        ListNode result4 = solution.removeNthFromEnd(head4, 1);
        System.out.println("删除倒数第1个节点: " + printList(result4));
        System.out.println("期望结果: null");
        System.out.println();

        // 测试用例5：两次遍历法验证
        ListNode head5 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.println("原链表: " + printList(head5));
        ListNode result5 = solution.removeNthFromEndTwoPass(head5, 2);
        System.out.println("两次遍历法删除倒数第2个节点: " + printList(result5));
    }

}
