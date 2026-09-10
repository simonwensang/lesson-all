package com.simon.algorithm.linkedlist;

public class LinkTest {

    public static class LinkNode{
        private int value;
        private LinkNode next;
        public LinkNode(int value){
            this.value=value;
        }
        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public LinkNode getNext() {
            return next;
        }

        public void setNext(LinkNode next) {
            this.next = next;
        }
    }


    public  static  LinkNode createList(int[] arr){
        if(arr == null || arr.length == 0){
            return null;
        }
        LinkNode head = new LinkNode(arr[0]);
        LinkNode current = head;
        for(int i=1;i< arr.length ; i++){
            current.next = new LinkNode(arr[i]);
            current = current.next;
        }
        return head;
    }

    /**
     * 删除链表倒数第 N 个节点
     *
     * 题目：给定一个链表 1->2->3->4->5->null，删除倒数第 n 个节点
     * 例如 n=2，删除节点 4，结果应该是 1->2->3->5->null
     */
    public static LinkNode removeLastIndex(LinkNode head ,int target){
        LinkNode dummy = new LinkNode(0);
        dummy.next= head;
        LinkNode fast = dummy;
        for(int i = 0 ;i <target ;i++){
            fast = fast.next;
        }
        LinkNode slow = dummy;
        while (fast.next!=null){
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }
    /**
     * 链表右移 k 个节点到表头
     *
     * 题目：给定一个链表 1->2->3->4->5->null，右移 k 个节点
     * 例如 k=2，结果应该是 4->5->1->2->3->null
     */
    public static LinkNode removeNodeToHead(LinkNode head,int target){
        LinkNode tail = head;
        for(int i = 0 ;i <target-1 ;i++){
            tail = tail.next;
        }
        LinkNode end = tail;
        LinkNode newHead = tail.next ;
        while (end.next != null){
            end = end.next;
        }
        tail.next = null;
        end.next = head;
        return newHead;
    }



    public static String printLinkList(LinkNode head){
        StringBuffer sb = new StringBuffer();
        while(head!=null){
            sb.append(head.getValue());
            if(head.next!=null){
                sb.append("->");
            }
            head = head.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LinkNode link = createList(new int[]{1,2,3,4,5});
        System.out.println(printLinkList(link));
        //System.out.println(printLinkList(removeLastIndex(link,1)));
        System.out.println(printLinkList(removeNodeToHead(link,3)));
    }
}
