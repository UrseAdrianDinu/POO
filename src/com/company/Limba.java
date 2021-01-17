package com.company;

class Limba {
    private String nume;
    private String nivel;

    public String getNivel() {
        return nivel;
    }

    public String getNume() {
        return nume;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
    public String toString()
    {
        return nume + " " +nivel;
    }
}
