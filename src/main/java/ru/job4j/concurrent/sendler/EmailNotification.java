package ru.job4j.concurrent.sendler;

import com.sun.istack.NotNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    private String subject(@NotNull final User user) {
        return "Notification " + user.userName() + " to email " + user.email();
    }

    private String body(@NotNull final User user) {
        return "Add a new event to " + user.email();
    }

    public void emailTo(User user) {
        pool.submit(() -> send(subject(user), body(user), user.email()));
    }

    public void send(String subject, String body, String email) {
        System.out.printf("subject = %s,\nbody = %s,\nemail = %s", subject, body, email);
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        List<User> users = List.of(
                new User("email_1@gmial.com", "User1"),
                new User("email_2@gmial.com", "User2"),
                new User("email_3@gmial.com", "User3")
        );
        EmailNotification emailNotification = new EmailNotification();
        users.forEach(emailNotification::emailTo);
        emailNotification.close();
    }
}
