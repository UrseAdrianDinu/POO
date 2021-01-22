package com.company;

class IT extends Department {

    public double getTotalSalaryBudget() {
        double suma = 0;
        //Angajatii din departamentul IT au impozitul 0
        for (Employee e : super.angajati) {
            suma += e.salariu;
        }
        return suma;
    }

    //Metoda care intoarce tipul departamentului
    public String getType() {
        return "IT";
    }
}
