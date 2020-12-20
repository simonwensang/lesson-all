package  collection;

/**
 * Created by sang on 2018/4/8.
 */
public interface ILink<E> {

    Boolean add(E e);

    Boolean add(int index,E e);

    Boolean remove(int i);

    Object get(int i);

    Boolean set(int index,E e);

}
