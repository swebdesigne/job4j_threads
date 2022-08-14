package ru.job4j.concurrent.atomicity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CountTest {

    @Test
    void whenExecute2ThreadThen2() throws InterruptedException {
        var count = new Count();
        var first = new Thread(count::increment);
        var second = new Thread(count::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.getValue()).isEqualTo(2);
    }

}