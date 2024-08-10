package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;


public class AssignmentApplication {
    public static void main(String[] args) throws InterruptedException {
        Assignment8 assignmentApp = new Assignment8();

        List<Integer> dataList = new ArrayList<>();
        dataList = Collections.synchronizedList(dataList);
        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        ExecutorService pool = Executors.newCachedThreadPool();

            for (int i = 0; i < 1000; i++) {
                CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> assignmentApp.getNumbers(), pool)
                        .thenAcceptAsync(dataList::addAll, pool);
                tasks.add(task);
        }

        while (tasks.stream().filter(CompletableFuture::isDone).count() < 1000) {
            System.out.println("Number of completed threads: "
                    + tasks.stream().filter(CompletableFuture::isDone).count());
            Thread.sleep(500);
        }

        Map<Integer, Integer> newDataList = dataList.stream()
                .collect(Collectors.toMap(Function.identity(), duplicateValue -> 1, Integer::sum));

        System.out.println(newDataList);

    }
}
