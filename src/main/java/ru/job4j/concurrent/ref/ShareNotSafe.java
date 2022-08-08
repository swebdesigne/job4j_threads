package ru.job4j.concurrent.ref;

public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        User user2 = User.of("name_2");
        cache.add(user);
        cache.add(user2);
        Thread first = new Thread(
                () -> user.setName("rename")
        );
        first.start();
        first.join();
        System.out.println(cache.findById(1).getName());
        System.out.println(cache.findAll());
    }
}
