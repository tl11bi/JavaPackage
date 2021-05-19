package List;

public interface Collection<E> extends Iterable{
    /*This method is used to add an
  element at the end of the List.*/
    public boolean add(E e);


    /*Adds all of the elements in the specified collection to this collection (optional operation).*/
    public boolean addAll(Collection<? extends E> c);

    /*Returns true if this collection contains the specified element.*/
    public boolean contains(E o);

    /*Returns true if this collection contains all of the elements in the specified collection.
     */
    public boolean containsAll(Collection<?> c);





    /*Removes all of the elements from this collection (optional operation).*/
    public void clear();

    /*This method is used to simply remove
    an object from the List. If there are
    multiple such objects, then the first occurrence
     of the object is removed.*/
    public boolean remove(E Object);


    /*Returns an array containing all of the elements
in this collection.
 */
    public E[] toArray();

    /*returns the size of the list*/
    public int size();
}
