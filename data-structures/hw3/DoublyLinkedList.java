
package hw3;


import java.util.AbstractList;

public class DoublyLinkedList<E> extends AbstractList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoublyLinkedList() {
        head = new Node<E>(null, null, null);
        tail = new Node<E>(null, head, head);
        head.setNext(tail);
        head.setPrev(tail);
        // tail.setNext(head);
        // tail.setPrev(head);
        size = 0;
    }

    public boolean isEmpty() {
        return (head.getNext() == tail);
    }

    @Override
    public int size() {
        if (isEmpty())
            throw new RuntimeException("The list is empty");
        else if (size == 0) {
            Node<E> helpPtr = head;
            int size = 1;
            while (!helpPtr.equals(tail)) {
                size++;
                helpPtr = helpPtr.getNext();
            }
            this.size = size;
        }
        return this.size;
    }

    @Override
    public boolean add(E element) {
        this.addBetween(element, tail.getPrev(), tail);
        return true;
    }

    @Override
    public void add(int index, E element) {
        Node<E> oldNode = getNode(index);
        this.addBetween(element, oldNode.getPrev(), oldNode);
    }

   
    public void addFirst(E element) {
        this.addBetween(element, head, head.getNext());
    }

    private void addBetween(E element, Node<E> prev, Node<E> next) {
        if (prev == next)
            throw new IndexOutOfBoundsException("Previous and next elements are the same");
        Node<E> newNode = new Node<E>(element, prev, next);
        prev.setNext(newNode);
        next.setPrev(newNode);
        this.size++;
    }

    

    @Override
    public E set(int index, E element) {
        Node<E> node = getNode(index);
        E oldElement = node.getElement();
        node.setElement(element);
        return oldElement;
    }

    @Override
    public E remove(int index) {
        // Remove node from circular linked list
        Node<E> nodeToRemove = getNode(index);
        Node<E> prevNode = nodeToRemove.getPrev();
        Node<E> nextNode = nodeToRemove.getNext();
        prevNode.setNext(nextNode);
        nextNode.setPrev(prevNode);
        size--;
        return nodeToRemove.getElement();
    }

    
    public E first() {
        return firstNode().getElement();
    }

    
    public E last() {
        return lastNode().getElement();
    }

    
    private Node<E> firstNode() {
        if (isEmpty())
            throw new RuntimeException("The list is empty");
        return head.getNext();
    }

   
    private Node<E> lastNode() {
        if (isEmpty())
            throw new RuntimeException("The list is empty");
        return tail.getPrev();
    }

    
    public void rotate() {
        if (isEmpty())
            throw new RuntimeException("The list is empty");
        head.getNext().setPrev(tail.getPrev());
        tail.getPrev().setNext(head.getNext());
        tail.setPrev(head.getNext());
        head.setNext(head.getNext().getNext());
        tail.getPrev().setNext(tail);
    }

    @Override
    public E get(int index) {
        return this.getNode(index).getElement();
    }

    public int get(E value) {
        for (int i = 0; i < size(); i++)
            if (get(i).equals(value))
                return i;
        return -1;
    }

    
    private Node<E> getNode(int index) {
        if (index < 0)
            throw new IllegalArgumentException("Index cannot be negative");
        if (isEmpty())
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0");
        else if (index > (size() - 1))
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, this.size()));

        Node<E> helpPtr = this.head.getNext();
        for (int i = 0; i < index; i++)
            helpPtr = helpPtr.getNext();
        return helpPtr;
    }

    
    public void clone(DoublyLinkedList<E> list) {
        if (list.isEmpty())
            throw new RuntimeException("This list is empty");
        for (int i = 0; i < list.size(); i++)
            this.add((E) list.get(i));
    }

    
}
