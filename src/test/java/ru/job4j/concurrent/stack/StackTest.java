package ru.job4j.concurrent.stack;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class StackTest {
    @Test
    public void when3PushThen3Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertThat(stack.pool()).isEqualTo(3);
        assertThat(stack.pool()).isEqualTo(2);
        assertThat(stack.pool()).isEqualTo(1);
    }

    @Test
    public void when1PushThen1Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        assertThat(stack.pool()).isEqualTo(1);
    }

    @Test
    public void when2PushThen2Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        assertThat(stack.pool()).isEqualTo(2);
        assertThat(stack.pool()).isEqualTo(1);
    }

}