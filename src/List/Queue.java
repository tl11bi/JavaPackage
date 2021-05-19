package List;

public interface Queue<E> extends Iterable, Collection<E> {


    /*    Retrieves, but does not remove, the head of this queue.
     */
    public E element();

    /*    Inserts the specified element into this queue if it is possible to do so immediately without violating capacity restrictions.
     */
    public boolean offer(E e);

    /*    Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     */
    public E peek();

    /*    Retrieves and removes the head of this queue, or returns null if this queue is empty.
     */
    public E poll();

    /*    Retrieves and removes the head of this queue.
     */
    public E remove();

}
