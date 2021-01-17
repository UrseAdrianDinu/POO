package com.company;

import java.util.ArrayList;
import java.util.List;

class Recruiter extends Employee {
    int rating;
    Long id;

    public Recruiter(Resume cv, String numecompanie, long salariu, Long id) {
        super(cv, numecompanie, salariu, id);
        this.rating = 5;
        this.id = id;
    }

    public int evaluate(Job job, User user) {
        int old = rating;
        rating += 0.1;
        Application app = Application.getInstance();
        Company c = app.getCompany(job.numecompanie);
        double evaluatescore = old * user.getTotalScore();
        c.manager.cereri.add(new Request(job, user, this, evaluatescore));
        return (int) evaluatescore;
    }

    public String toString() {
        return cv + "\n" + numecompanie + " " + salariu + "ID = " + id;
    }
}
