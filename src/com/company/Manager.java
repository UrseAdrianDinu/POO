package com.company;

import java.util.*;

class Manager extends Employee {
    ArrayList<Request> cereri;
    Long id;

    public Manager(Resume cv, String numecompanie, long salariu, Long id) {
        super(cv, numecompanie, salariu, id);
        cereri = new ArrayList<>();
        this.id = id;
    }

    public void process(Job job) {
        ArrayList<Request> requests = new ArrayList<>();

        for (Request r : cereri) {
            Job j = (Job) r.getKey();
            if (j.numejob.compareTo(job.numejob) == 0) {
                if (j.numecompanie.compareTo(job.numecompanie) == 0) {
                    requests.add(r);
                }
            }
        }

        int i = 0;
        Collections.sort(requests, new comparatorscor());
        ArrayList<User> respinsi = new ArrayList<>();

        for (Request r : requests) {
            User u = (User) r.getValue1();
            Application app = Application.getInstance();
            if (i < job.nrcandidati) {
                if (app.utilizatori.contains(u) && job.meetsRequirments(u)) {
                    Employee e = u.convert();
                    e.salariu = job.salariu;
                    e.numecompanie = job.numecompanie;
                    Notification n = new Notification("User-ul " + e.cv.datepersonale.getNume() + " " + e.cv.datepersonale.getPrenume() + " scor - " + r.getScore() + " a fost angajat in compania " + job.numecompanie + " pe pozita " + job.numejob + ".");
                    u.update(n);
                    app.utilizatori.remove(u);
                    Department d = app.getCompany(job.numecompanie).getDepartment(job);
                    d.add(e);
                    i++;
                    cereri.remove(r);
                    job.listacandidati.remove(u);
                    for (Company c : app.companies) {
                        c.observers.remove(u);
                    }
                } else
                    respinsi.add(u);
            } else
                respinsi.add(u);
        }

        for (User u : respinsi) {
            Notification n = new Notification("Cererea de aplicare trimisa de " + u.cv.datepersonale.getNume() + " " + u.cv.datepersonale.getPrenume() + " " + "a fost respinsa pentru job- ul " + job.numejob + " din compania " + job.numecompanie + ".");
            u.update(n);
        }

        if (i == job.nrcandidati) {
            job.flag = false;
            Notification n = new Notification("Job-ul " + job.numejob + " din cadrul companiei " + job.numecompanie + " este inchis.");
            Application app = Application.getInstance();
            Company c = app.getCompany(job.numecompanie);
            c.notification = n;
            c.notifyAllObservers();
        }
    }

    public String toString() {
        return cv + "\n" + numecompanie + " " + salariu + " ID = " + id;
    }
}

