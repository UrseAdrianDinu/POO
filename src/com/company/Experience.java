package com.company;

import java.time.LocalDate;

class Experience implements Comparable {
    LocalDate datainceput;
    LocalDate datasfarsit;
    String pozitie;
    String companie;

    public Experience(LocalDate datainceput, LocalDate datasfarsit, String pozitie, String companie) throws InvalidDatesException {
        this.pozitie = pozitie;
        this.companie = companie;
        if (datasfarsit != null) {
            if (datasfarsit.compareTo(datainceput) > 0) {
                this.datainceput = datainceput;
                this.datasfarsit = datasfarsit;
            } else {
                //Daca data de inceput este dupa data de sfarsit, se arunca exceptia InvalidDatesException
                throw new InvalidDatesException("Date invalid");
            }
        } else {
            this.datainceput = datainceput;
            this.datasfarsit = datasfarsit;
        }
    }

    public int compareTo(Object o) {
        Experience e = (Experience) o;
        if (e.datasfarsit != null && datasfarsit != null) {
            //Comparare descrescatoare dupa data de sfarsit
            if (e.datasfarsit.compareTo(datasfarsit) > 0) {
                return 1;
            } else {
                if (e.datasfarsit.compareTo(datasfarsit) < 0)
                    return -1;
            }
        } else {
            //Dace nu se pot compara dupa data de sfarsit
            //se compara crescator dupa numele companiei
            if (companie.compareTo(e.companie) > 0) {
                return 1;
            } else {
                return -1;
            }
        }
        return -1;
    }

    public String toString() {
        return companie + " " + pozitie + " " + datainceput + " " + datasfarsit;
    }
}
