package ru.job4j.concurrent.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Async {
    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("You: A`m working");
            TimeUnit.MILLISECONDS.sleep(1);
            count++;
        }
    }

    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Son: dad/mom, i went to throw the wastes");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Son: dad/mom, i`m came back");
                }
        );
    }

    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        iWork();
    }

    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Son: dad/mom, i went to the store");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Son: dad/mom, i bought the " + product);
                    return product;
                }
        );
    }

    public static void supplyAsyncExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Milk");
        iWork();
        System.out.println("Was bought: " + bm.get());
    }

    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(() -> {
                    int count = 0;
                    while (count < 3) {
                        System.out.println("Son: i`m washing the hands");
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        count++;
                    }
                    System.out.println("Son: i washed the hands");
                }
        );
        iWork();
    }

    public static void thenAcceptExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Milk");
        bm.thenAccept((product) -> System.out.println("Son: i removed the " + product + "in the refrigerator"));
        iWork();
        System.out.println("Was bought: " + bm.get());
    }

    public static void thenApplyExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Milk")
                .thenApply((product) -> "Son: i poured into your a cup " + product + ". Take it.");
        iWork();
        System.out.println(bm.get());
    }

    public static void thenComposeExample()  throws Exception {
        CompletableFuture<String> result = goToTrash().thenCompose(a -> buyProduct("Milk"));
        result.get();
    }

    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = buyProduct("Milk")
                .thenCombine(buyProduct("Bread"), (r1, r2) -> "Bought: " + r1 + " and " + r2);
        iWork();
        System.out.println(result.get());
    }

    public static CompletableFuture<Void> washHands(String name) {
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ", washes his hands");
        });
    }

    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Dad"), washHands("mom"),
                washHands("Ivan"), washHands("Alina")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name + ", washes his hands";
        });
    }

    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Dad"), whoWashHands("Mom"),
                whoWashHands("Ivan"), whoWashHands("Alina")
        );
        System.out.println("Who washes the hands now?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }
}
