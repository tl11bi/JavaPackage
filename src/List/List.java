package List;

import java.util.Comparator;

public interface List<E> extends Collection<E>{

    /*this method will return item at index*/
    public boolean get(int index);
    /*This method is used to add an
        element at a specific index in the List.*/
    public boolean add(int index, E e);
    /*Inserts all of the elements in the specified collection into this list at the specified position (optional operation).*/
    public boolean addAll(int index, Collection<? extends E> c);

    /*Since a List is indexed, this method takes an
   integer value which simply removes the element present
    at that specific index in the List. After removing
     the element, all the elements are moved to the left
      to fill the space and the indices of the objects
      are updated.*/
    public boolean remove(int index);

    /* Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element.
     */
    public int indexOf(E o);

    /*Returns true if this list contains no elements.
     */
    boolean	isEmpty();

    /*Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain the element.*/
    int	lastIndexOf(E o);


    /*    Sorts this list according to the order induced by the specified Comparator.
     */
    public void	sort(Comparator<? super E> c);

    /*Returns a view of the portion of this list between the specified fromIndex, inclusive, and toIndex, exclusive.*/
    List<E>	subList(int fromIndex, int toIndex);
}
