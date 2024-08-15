package org.example;

public interface Observer<M> {
    void notifyChange(M message);
}
