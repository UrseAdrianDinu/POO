package com.company;

import java.util.ArrayList;
import java.util.List;

class Information {
    private final String nume;
    private final String prenume;
    private final String sex;
    private final String datanastere;
    private String email;
    private String telefon;
    private ArrayList<Limba> limbi;

    private Information(InformationBuilder builder) {
        this.nume = builder.nume;
        this.prenume = builder.prenume;
        this.datanastere = builder.datanastere;
        this.sex = builder.sex;
        this.email = builder.email;
        this.telefon = builder.telefon;
        this.limbi = builder.limbi;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getDatanastere() {
        return datanastere;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public List<Limba> getLimbi() {
        return limbi;
    }

    public void setLimbi(ArrayList<Limba> limbi) {
        this.limbi = limbi;
    }

    public static class InformationBuilder {
        private final String nume;
        private final String prenume;
        private final String datanastere;
        private final String sex;
        private String telefon;
        private String email;
        private ArrayList<Limba> limbi;

        public InformationBuilder(String nume, String prenume, String datanastere, String sex) {
            this.nume = nume;
            this.prenume = prenume;
            this.datanastere = datanastere;
            this.sex = sex;
        }

        public InformationBuilder telefon(String telefon) {
            this.telefon = telefon;
            return this;
        }

        public InformationBuilder email(String email) {
            this.email = email;
            return this;
        }

        public InformationBuilder limbi(ArrayList<Limba> limbi) {
            this.limbi = limbi;
            return this;
        }

        public Information build() {
            return new Information(this);
        }
    }
    public String toString()
    {
        return getNume() + " " + getPrenume() + " " + getSex() + " " +getDatanastere() + " " + getEmail() + " " +getTelefon() + " " + getLimbi();
    }
}
