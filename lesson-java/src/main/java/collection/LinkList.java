package  collection;

/**
 * Created by sang on 2018/4/8.
 */
public class LinkList<E> implements ILink<E> {

    private LinkNode<E> first;

    private LinkNode<E> last;

    private int size ;


    private void addFirst(E e){
        LinkNode<E> f  = first;
        LinkNode<E> node = new LinkNode(null,null,e);
        first = node;
        if (f == null)
            last = node;
        else
            f.pre = node;

        size++;
    }

    private void addLast(E e){

        LinkNode<E> l = last;
        LinkNode<E> node = new LinkNode<E>(l,null,e);
        last = node;
        if(l == null){
            first = l;
        }else{
            l.next =node;
        }

        size++;
    }

    private LinkNode<E>  node(int index){

        if( index < (size >> 1)){
            LinkNode<E> node = first;
            for(  int i = 0 ; i<index ; i++  ){
                node = node.next;
            }
            return node;

        }else{

            LinkNode<E> node = last;
            for(  int i = size -1 ; i > index ; i--  ){
                node = node.pre;
            }
            return node;

        }

    }

    @Override
    public Boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public Boolean add(int index, E e) {
        return null;
    }

    @Override
    public Boolean remove(int i) {
        return null;
    }

    @Override
    public E get(int i) {
        return null;
    }

    @Override
    public Boolean set(int index,  E e) {
        return null;
    }

    public LinkNode<E> getFirst() {
        return first;
    }

    public LinkNode<E> getLast() {
        return last;
    }

    public int getSize() {
        return size;
    }

    private void setFirst(LinkNode<E> first) {
        this.first = first;
    }

    private void setLast(LinkNode<E> last) {
        this.last = last;
    }

    public static class LinkNode<E> {

        private LinkNode<E> pre;

        private LinkNode<E> next;

        private E item;

        public LinkNode(LinkNode<E> pre, LinkNode<E> next, E item) {
            this.pre = pre;
            this.next = next;
            this.item = item;
        }

    }

}
