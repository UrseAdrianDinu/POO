package com.company;

import java.time.LocalDate;

class Education implements Comparable {
    LocalDate datainceput;
    LocalDate datasfarsit;
    String numeleinstitutiei;
    String nivel;
    double mediafinalizare;

    public Education(LocalDate inceput, LocalDate sfarsit, String nume, String niv, double medie) throws InvalidDatesException {
        numeleinstitutiei = nume;
        nivel = niv;
        mediafinalizare = medie;
        if(sfarsit!=null) {
            if (sfarsit.compareTo(inceput) >= 0) {
                datainceput = inceput;
                datasfarsit = sfarsit;
            } else {
                throw new InvalidDatesException("Date invalide");
            }
        }
        else
        {
            datainceput=inceput;
            datasfarsit=sfarsit;
        }

    }

    public int compareTo(Object o) {
        Education e = (Education) o;
        if (e.datasfarsit != null && datasfarsit != null) {
            if (e.datasfarsit.compareTo(datasfarsit) > 0) {
                return 1;
            } else {
                if (e.datasfarsit.compareTo(datasfarsit) < 0)
                    return -1;
                else {
                    if (e.datasfarsit.compareTo(datasfarsit) == 0) {
                        if (e.mediafinalizare > mediafinalizare)
                            return 1;
                        else
                            return -1;
                    }
                }
            }
        } else {
            if (datainceput.compareTo(e.datainceput) < 0) {
                return 1;
            } else {
                return -1;
            }
        }
        return -1;
    }
    public String toString()
    {
        return datainceput + " " +datasfarsit + " " + numeleinstitutiei + " " + nivel + " " + mediafinalizare;
    }
}
