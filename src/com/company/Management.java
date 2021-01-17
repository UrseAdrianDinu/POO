package com.company;

class Management extends Department {

    public double getTotalSalaryBudget() {
        double suma = 0;
        for (Employee e : super.angajati) {
            suma += e.salariu;
        }
        return suma + 0.16 * suma;
    }

    @Override
    public String getType() {
        return "Management";
    }
}
