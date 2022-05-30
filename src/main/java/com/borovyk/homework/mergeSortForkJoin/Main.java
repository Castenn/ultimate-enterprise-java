package com.borovyk.homework.mergeSortForkJoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        var list = Arrays.asList(3, 2, 6, -1, 211, 53, -22);
        ForkJoinPool.commonPool().invoke(new MergeSortTask<>(list)).forEach(System.out::println);
    }

}
