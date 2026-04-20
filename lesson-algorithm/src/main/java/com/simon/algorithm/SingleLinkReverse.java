package com.simon.algorithm;

/**
 * Created by sang on 2020/12/12.
 */
public class SingleLinkReverse {

    private SNode head ;

    private SNode end ;

    public SingleLinkReverse(SNode head) {
        this.head = head;
    }

    public static class SNode{
        private SNode next;

        private int value ;

        public SNode(int value) {
            this.value = value;
        }
    }

    public boolean add(SNode sNode){
        if(end == null){
            end = sNode;
            head.next = end ;
        }else {
            end.next = sNode;
            end = sNode ;
        }
        return true;
    }

    public SNode reverse(SNode pre,SNode next){
        if(pre == next){
            return next;
        }

        SNode nNext = next.next;

        SNode preNext = pre.next;
        pre.next = next;
        next.next = preNext;

        return  reverse(pre,nNext);
    }

    public void printf(){
        SNode sNode = this.head;
        while (sNode != null){
            System.out.print(sNode.value);
            sNode = sNode.next;
        }
        System.out.println("-");
        System.out.println("----end----");
    }

    public static void main(String[] args) {
        SingleLinkReverse initLink = new SingleLinkReverse(new SNode(1));
        for (int i =2 ;i<6 ; i++){
            initLink.add(new SNode(i));
        }
        initLink.printf();
        SNode sNode = initLink.reverse(initLink.end,initLink.head);

        while (sNode != null){
            System.out.print(sNode.value);
            sNode = sNode.next;
        }

    }
}
