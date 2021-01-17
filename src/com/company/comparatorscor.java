package com.company;

import java.util.Comparator;

class comparatorscor implements Comparator<Request> {
    public int compare(Request o1, Request o2) {
        return (int) (o2.getScore() - o1.getScore());
    }
}
