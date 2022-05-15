package com.borovyk.homework.hashtable;

import java.util.Objects;

/**
 * A simple implementation of the Hash Table that allows storing a generic key-value pair. The table itself is based
 * on the array of {@link Node} objects.
 * <p>
 * An initial array capacity is 16.
 * <p>
 * Every time a number of elements is equal to the array size that tables gets resized
 * (it gets replaced with a new array that it twice bigger than before). E.g. resize operation will replace array
 * of size 16 with a new array of size 32. PLEASE NOTE that all elements should be reinserted to the new table to make
 * sure that they are still accessible  from the outside by the same key.
 *
 * @param <K> key type parameter
 * @param <V> value type parameter
 */
public class HashTable<K, V> {

    private static final float DEFAULT_MULTIPLIER = 1.75F;

    private Node<K, V>[] container;
    private int size;

    @SuppressWarnings("unchecked")
    public HashTable() {
        container = new Node[16];
        size = 0;
    }

    /**
     * Puts a new element to the table by its key. If there is an existing element by such key then it gets replaced
     * with a new one, and the old value is returned from the method. If there is no such key then it gets added and
     * null value is returned.
     *
     * @param key   element key
     * @param value element value
     * @return old value or null
     */
    public V put(K key, V value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        if (size > 1.5 * container.length) {
            resize();
        }

        var putIndex = calculateIndex(value);
        if (container[putIndex] == null) {
            size++;
            container[putIndex] = new Node<>(key, value);
            return null;
        }

        var node = container[putIndex];
        while (node.next != null) {
            if (Objects.equals(key.hashCode(), node.key.hashCode()) && Objects.equals(key, node.key)) {
                var oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            node = node.next;
        }

        node.next = new Node<K, V>(key, value);
        size++;
        return null;
    }

    /**
     * Prints a content of the underlying table (array) according to the following format:
     * 0: key1:value1 -> key2:value2
     * 1:
     * 2: key3:value3
     * ...
     */
    public void printTable() {
        for (int i = 0; i < container.length; i++) {
            var node = container[i];
            System.out.printf("%d: %s%n", i, composeBucketToString(node));
        }
    }

    private static String composeBucketToString(Node<?, ?> node) {
        if (node == null) {
            return "";
        }
        var stringBuilder = new StringBuilder();
        while (node.next != null) {
            stringBuilder.append(node.key).append(":").append(node.value).append(" -> ");
            node = node.next;
        }
        stringBuilder.append(node.key).append(":").append(node.value);
        return stringBuilder.toString();
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        size = 0;
        var source = container;
        var newLength = (int) (container.length * DEFAULT_MULTIPLIER);
        container = new Node[newLength];

        for (var node : source) {
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
    }

    private int calculateIndex(V value) {
        return value.hashCode() % container.length;
    }

}
