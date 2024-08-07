package dev.octomc.agile.util;

public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}
