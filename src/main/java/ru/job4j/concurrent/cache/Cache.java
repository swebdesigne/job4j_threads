package ru.job4j.concurrent.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public Base getMemoryById(int id) {
        return memory.get(id);
    }

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(),
                (id, base) -> {
                    if (base.getVersion() != model.getVersion()) {
                        throw new OptimisticException("Versions are not equal");
                    }
                    base = new Base(id, base.getVersion() + 1);
                    base.setName(model.getName());
                    return base;
                }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}
