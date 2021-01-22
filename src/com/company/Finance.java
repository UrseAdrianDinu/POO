package com.company;

import java.time.LocalDate;
import java.time.Period;

class Finance extends Department {
    @Override
    public double getTotalSalaryBudget() {
        double suma = 0;
        for (Employee e : super.angajati) {
            //Am calculat vechimea angajatului
            LocalDate date = LocalDate.now();
            Period period = Period.between(e.cv.experienta.first().datainceput, date);
            //Daca vechimea este mai mica de 1 an, impozitul este 10%
            if (period.getYears() < 1) {
                suma += e.salariu + 0.1 * e.salariu;
            } else
                suma += e.salariu + 0.16 * e.salariu;
        }
        return suma;
    }

    //Metoda care intoarce tipul departamentului
    public String getType() {
        return "Finance";
    }
}
