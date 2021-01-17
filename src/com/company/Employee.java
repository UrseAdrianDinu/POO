package com.company;

import java.util.ArrayList;
import java.util.List;

class Employee extends Consumer {
    String numecompanie;
    long salariu;
    Long id;

    public Employee(Resume cv, String numecompanie, long salariu, Long id) {
        super(cv);
        this.numecompanie = numecompanie;
        this.salariu = salariu;
        this.id = id;
    }

    @Override
    public String toString() {
        return cv + "\n" + numecompanie + " " + salariu + "ID = " + id;
    }
}
