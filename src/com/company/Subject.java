package com.company;

public interface Subject {
    void addObserver(User user);

    void remove(User c);

    void notifyAllObservers();
}
