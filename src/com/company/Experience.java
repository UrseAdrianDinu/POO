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
            if (e.datasfarsit.compareTo(datasfarsit) > 0) {
                return 1;
            } else {
                if (e.datasfarsit.compareTo(datasfarsit) < 0)
                    return -1;
            }
        } else {
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
