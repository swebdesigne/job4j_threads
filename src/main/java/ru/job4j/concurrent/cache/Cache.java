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
        Base stored = memory.get(model.getId());
        if (stored.getVersion() != model.getVersion()) {
            throw new OptimisticException("Versions is not equals");
        }
        return memory.computeIfPresent(model.getId(),
                (id, base) -> new Base(base.getId(), base.getVersion() + 1)) == null;
    }

    public void delete(Base model) {
        if (!memory.containsKey(model.getId())) {
            throw new OptimisticException("Object is not exists");
        }
        memory.entrySet().removeIf(e -> e.getValue().equals(model));
    }
}
