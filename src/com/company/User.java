package com.company;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

class User extends Consumer implements Observer {
    ArrayList<String> companies;
    Long id;

    public User(Resume cv, ArrayList<String> companies, Long id) {
        super(cv);
        this.companies = new ArrayList<>();
        this.companies = companies;
        this.id = id;
        Application app = Application.getInstance();
        for (String s : companies) {
            Company c = app.getCompany(s);
            c.addObserver(this);
        }
    }

    public Employee convert() {
        Employee e = new Employee(cv, "", 0, 0l);
        e.cunoscuti = cunoscuti;
        return e;
    }

    public Double getTotalScore() {
        double aniexperinta = 0;
        for (Experience e : cv.experienta) {
            Period period = Period.between(e.datainceput, e.datasfarsit);
            aniexperinta += period.getYears();
            if (period.getMonths() > 3)
                aniexperinta += 1;
        }
        return aniexperinta * 1.5 + super.meanGPA();
    }

    public void update(Notification notification) {
        System.out.println(cv.datepersonale.getNume() + " " + cv.datepersonale.getPrenume() + ": " + notification.mesaj);
    }

    public String toString() {
        return cv + "\n" + companies + "ID = " + id + "\n";
    }
}
