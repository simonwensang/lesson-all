package com.simon.algorithm.linkedlist;

/**
 * Created by sang on 2020/12/12.
 */
public class SingleLinkReverse {

    public static class SNode{
        private SNode next;

        private int value ;

        public SNode(int value) {
            this.value = value;
        }
    }

    public static SNode createList(int[] arr){
        SNode head = new SNode(arr[0]);
        SNode current = head;
        for(int i =1;i<arr.length;i++){
            current.next = new SNode(arr[i]);
            current = current.next ;
        }
        return head;
    }

    public static String printf(SNode head){
        StringBuffer sb = new StringBuffer();
        SNode sNode = head;
        while (sNode != null){
            sb.append(sNode.value);
            if(sNode.next!=null){
                sb.append("->");
            }
            sNode = sNode.next;
        }
        return sb.toString();
    }

    public static SNode reverse(SNode end,SNode head){
        //终止条件
        if(end == head){
            return head;
        }
        //缓存指针得到end和head的下一个节点
        SNode headNext = head.next;
        SNode endNext = end.next;
        //end指向head
        end.next = head;
        //head指向end下一个节点
        head.next = endNext;
        //end继续指向head的下一个节点
        return  reverse(end,headNext);
    }

    /*
    * 1->2->3->null
    * 2->3->1->null
    * 3->2->1->null
    *
    * node 1 2 3         1 2 3       1 2 3
    * next 2 3 null   null 3 1    null 1 2
    */
    public static void main(String[] args) {
        SNode head = createList(new int[]{1,2,3});
        System.out.println(printf(head));
        SNode end = head;
        while(end.next!=null){
           end = end.next ;
        }
        SNode sNode = reverse(end,head);
        System.out.println(printf(sNode));
    }
}
