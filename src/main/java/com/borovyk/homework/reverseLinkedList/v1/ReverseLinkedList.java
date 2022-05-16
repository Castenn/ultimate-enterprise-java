package com.borovyk.homework.reverseLinkedList.v1;

import java.util.Objects;

public class ReverseLinkedList {

    public static void main(String[] args) {
       var linkedList = createLinkedList(5, 7, 1, 4);
       printLinkedList(linkedList);
       var reversedLinkedList = reverseLinkedList(linkedList);
       printLinkedList(reversedLinkedList);
    }

    /**
     * Creates a linked list based on the input array and returns a head
     */
    @SafeVarargs
    public static <T> Node<T> createLinkedList(T... element) {
        var head = new Node<>(element[0]);
        var currentNode = head;
        for (int i = 1; i < element.length; i++) {
            currentNode.next = new Node<>(element[i]);
            currentNode = currentNode.next;
        }
        return head;
    }

    /**
     * Prints a linked list with arrows like this
     * head:5 -> 7 -> 1 -> 4
     *
     * @param head the first element of the list
     */
    public static void printLinkedList(Node<?> head) {
        System.out.print("head:");
        while (head.next != null) {
            System.out.print(head.element + " -> ");
            head = head.next;
        }
        System.out.println(head.element);
    }

    /**
     * Accepts a linked list head, reverses all elements and returns a new head (the last element).
     * PLEASE NOTE that it should not create new nodes, only change the next references of the existing ones.
     * E.g. you have a like "head:5 -> 7 -> 1 -> 4" should this method will return "head:4 -> 1 -> 7 -> 5"
     *
     * @param head the first element of the list
     * @param <T>  element type
     * @return new head
     */
    public static <T> Node<T> reverseLinkedList(Node<T> head) {
        Objects.requireNonNull(head);

        Node<T> previousNode = null;
        var currentNode = head;
        while (currentNode.next != null) {
            var nextNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = nextNode;
        }
        currentNode.next = previousNode;
        return currentNode;
    }

}
