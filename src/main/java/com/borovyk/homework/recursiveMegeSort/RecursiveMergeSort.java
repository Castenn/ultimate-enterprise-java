package com.borovyk.homework.recursiveMegeSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RecursiveMergeSort {

    public static void main(String[] args) {
        var list = IntStream.of(1, 5, -1, 2, 0, 6, 4).boxed().collect(Collectors.toList());
        System.out.println(list.stream().map(Object::toString).collect(Collectors.joining(", ")));
        mergeSort(list);
        System.out.println(list.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    private static <T extends Comparable<T>> void mergeSort(List<T> list) {
        Objects.requireNonNull(list);
        if (list.size() < 2) {
            return;
        }

        var leftList = new ArrayList<>(list.subList(0, list.size() / 2));
        var rightList = new ArrayList<>(list.subList(list.size() / 2, list.size()));

        mergeSort(leftList);
        mergeSort(rightList);
        merge(list, leftList, rightList);
    }

    private static <T extends Comparable<T>> void merge(List<T> list, List<T> leftList, List<T> rightList) {
        var index = 0;
        var leftIndex = 0;
        var rightIndex = 0;

        while (leftIndex < leftList.size() && rightIndex < rightList.size()) {
            if (leftList.get(leftIndex).compareTo(rightList.get(rightIndex)) < 0) {
                list.set(index, leftList.get(leftIndex++));
            } else {
                list.set(index, rightList.get(rightIndex++));
            }
            index++;
        }

        while (leftIndex < leftList.size()) {
            list.set(index++, leftList.get(leftIndex++));
        }

        while (rightIndex < rightList.size()) {
            list.set(index++, rightList.get(rightIndex++));
        }
    }

}
