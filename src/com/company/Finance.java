package com.company;

import java.time.LocalDate;
import java.time.Period;

class Finance extends Department {
    @Override
    public double getTotalSalaryBudget() {
        double suma = 0;
        for (Employee e : super.angajati) {
            LocalDate date = LocalDate.now();
            Period period = Period.between(e.cv.experienta.first().datainceput, date);
            if (period.getYears() < 1) {
                suma += e.salariu + 0.1 * e.salariu;
            } else
                suma += e.salariu + 0.16 * e.salariu;
        }
        return suma;
    }

    @Override
    public String getType() {
        return "Finance";
    }
}
