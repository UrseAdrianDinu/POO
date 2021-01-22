package com.company;

class Management extends Department {

    public double getTotalSalaryBudget() {
        double suma = 0;
        for (Employee e : angajati) {
            suma += e.salariu;
        }
        //Impozitul angajatilor din departamentul Management este 16%
        return suma + 0.16 * suma;
    }

    //Metoda care intoarce tipul departamentului
    public String getType() {
        return "Management";
    }
}
