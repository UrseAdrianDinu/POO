package com.company;

class IT extends Department {

    public double getTotalSalaryBudget() {
        double suma = 0;
        for (Employee e : super.angajati) {
            suma += e.salariu;
        }
        return suma;
    }

    @Override
    public String getType() {
        return "IT";
    }
}
