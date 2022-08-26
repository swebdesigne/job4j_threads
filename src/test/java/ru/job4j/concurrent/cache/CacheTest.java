package ru.job4j.concurrent.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CacheTest {
    @Test
    public void whenAddToCache() {
        Base base = new Base(1, 0);
        Cache cache = new Cache();
        cache.add(base);
        assertThat(cache.getMemoryById(base.getId())).isEqualTo(base);
    }

    @Test
    public void whenAddToCacheAndChangeTheVersion() {
        Base base = new Base(1, 0);
        Cache cache = new Cache();
        cache.add(base);
        cache.update(base);
        assertThat(cache.getMemoryById(base.getId()).getVersion()).isEqualTo(base.getVersion() + 1);
    }

    @Test
    public void whenVersionsNotEquals() {
        Base base = new Base(1, 0);
        Cache cache = new Cache();
        cache.add(base);
        cache.update(base);
        assertThrows(OptimisticException.class, () -> cache.update(base));
    }

}