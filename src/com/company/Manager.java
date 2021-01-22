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
        //Lista de cereri care contine doar cererile pentru jobul dat ca parametru
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
        //Am sortat lista de cereri descrescator dupa scor
        Collections.sort(requests, new comparatorscor());
        //Lista de useri respinsi
        ArrayList<User> respinsi = new ArrayList<>();

        for (Request r : requests) {
            User u = (User) r.getValue1();
            Application app = Application.getInstance();
            //Daca mai exista locuri disponibile
            if (i < job.nrcandidati) {
                //Daca utilizatorul curent este inca in lista de useri din aplicatie
                //si indeplineste conditiile
                if (app.utilizatori.contains(u) && job.meetsRequirments(u)) {
                    //User-ul devine employee
                    Employee e = u.convert();
                    e.salariu = job.salariu;
                    e.numecompanie = job.numecompanie;
                    //Am creat o notificare noua
                    Notification n = new Notification("User-ul " + e.cv.datepersonale.getNume() + " " + e.cv.datepersonale.getPrenume() + " scor - " + r.getScore() + " a fost angajat in compania " + job.numecompanie + " pe pozita " + job.numejob + ".");
                    //Am notificat user-ul ca a fost angajat
                    u.update(n);
                    //L-am sters din lista de useri din aplicatie
                    app.utilizatori.remove(u);
                    Department d = app.getCompany(job.numecompanie).getDepartment(job);
                    //Il adaug in departamentul corespunzator jobului
                    d.add(e);
                    i++;
                    //L-am sters din lista de cereri ai managerului
                    cereri.remove(r);
                    //L-am sters din lista de candidati ai jobului
                    job.listacandidati.remove(u);
                    //L-am sters din lista de observers din toate companiile
                    for (Company c : app.companies) {
                        c.observers.remove(u);
                    }
                } else
                    //Daca nu indeplinestele conditiile sau user-ul este deja angajat
                    respinsi.add(u);
            } else
                //Daca nu mai exista locuri disponibile
                respinsi.add(u);
        }

        for (User u : respinsi) {
            //Trimit notificare userilor respinsi
            Notification n = new Notification("Cererea de aplicare trimisa de " + u.cv.datepersonale.getNume() + " " + u.cv.datepersonale.getPrenume() + " " + "a fost respinsa pentru job- ul " + job.numejob + " din compania " + job.numecompanie + ".");
            u.update(n);
        }

        if (i == job.nrcandidati) {
            //Daca s-au angajat job.nopositions, atunci nu mai exista locuri disponibile
            //Setez flag-ul jobului la false
            job.flag = false;
            //Trimit notificare observatorilor ca jobul a fost inchis
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

