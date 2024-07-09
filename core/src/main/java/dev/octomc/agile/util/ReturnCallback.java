package dev.octomc.agile.util;

public interface ReturnCallback<R, A, B> {
    R call(A a, B b);
}
