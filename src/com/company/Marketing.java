package com.company;

class Marketing extends Department {

    public double getTotalSalaryBudget() {

        double suma = 0;
        for (Employee e : super.angajati) {
            if (e.salariu > 5000) {
                suma += e.salariu + 0.1 * e.salariu;
            } else {
                if (e.salariu < 3000) {
                    suma += e.salariu;
                } else {
                    suma += e.salariu + 0.16 * e.salariu;
                }
            }
        }
        return suma;
    }

    @Override
    public String getType() {
        return "Marketing";
    }

}
