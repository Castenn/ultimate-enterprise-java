package com.borovyk.homework.mergeSortForkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.RecursiveTask;

public class MergeSortTask<T extends Comparable<T>> extends RecursiveTask<List<T>> {

    private final List<T> list;

    public MergeSortTask(List<T> list) {
        this.list = list;
    }

    @Override
    protected List<T> compute() {
        Objects.requireNonNull(list);
        if (list.size() < 2) {
            return list;
        }

        var leftList = new ArrayList<>(list.subList(0, list.size() / 2));
        var rightList = new ArrayList<>(list.subList(list.size() / 2, list.size()));

        var leftTask = new MergeSortTask<>(leftList);
        var rightTask = new MergeSortTask<>(rightList);

        leftTask.fork();
        rightTask.compute();
        leftTask.join();

        merge(leftList, rightList);
        return list;
    }

    private void merge(List<T> leftList, List<T> rightList) {
        var index = 0;
        var leftIndex = 0;
        var rightIndex = 0;

        while (leftIndex < leftList.size() && rightIndex < rightList.size()) {
            var leftValue = leftList.get(leftIndex);
            var rightValue = rightList.get(rightIndex);

            if (leftValue.compareTo(rightValue) < 0) {
                list.set(index, leftValue);
                leftIndex++;
            } else {
                list.set(index, rightValue);
                rightIndex++;
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
