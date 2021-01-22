package com.company;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

class Job {
    String numejob;
    String numecompanie;
    boolean flag;
    Constraint anabsolvire;
    Constraint nraniexperienta;
    Constraint medieacademica;
    ArrayList<User> listacandidati;
    long nrcandidati;
    long salariu;

    public Job(String numejob, String numecompanie, boolean flag, Constraint anabsolvire, Constraint nraniexperienta, Constraint medieacademica, long nrcandidati, long salariu) {
        this.numejob = numejob;
        this.numecompanie = numecompanie;
        this.flag = flag;
        this.anabsolvire = anabsolvire;
        this.nraniexperienta = nraniexperienta;
        this.medieacademica = medieacademica;
        this.nrcandidati = nrcandidati;
        this.salariu = salariu;
        this.listacandidati = new ArrayList<>();
    }


    public void apply(User user) {
        Application app = Application.getInstance();
        Company c = app.getCompany(numecompanie);
            //Selectez recruiter-ul cel mai indepartat
        Recruiter r = c.getRecruiter(user);
        //Calculez scorul
        double scor = r.evaluate(this, user);
        //Adaug user-ul in lista de candidati
        listacandidati.add(user);
        //Adaug user-ul in lista de observers ai companiei
        if (c.observers.contains(user) == false)
            c.addObserver(user);
    }

    public boolean meetsRequirments(User user) {

        if (user.getGraduationYear() != null) {
            int graduationyear = user.getGraduationYear();
            if (anabsolvire.limitasuperioara != null && anabsolvire.limitainferioara != null)
                //Daca anul de absolvire nu incadreaza intre limite, atunci returnez false
                if (graduationyear < anabsolvire.limitainferioara || graduationyear > anabsolvire.limitasuperioara) {
                    return false;
                }
        } else {
            //Cazul in care user-ul are anul de absolvire null
            //si limita inferioara a jobului este diferita de null
            //atunci returnez false
            if (anabsolvire.limitainferioara != null) {
                return false;
            }
        }
        double aniexperinta = 0;
        for (Experience e : user.cv.experienta) {
            Period period = Period.between(e.datainceput, e.datasfarsit);
            aniexperinta += period.getYears();
            if (period.getMonths() > 3)
                aniexperinta += 1;
        }
        if (nraniexperienta.limitasuperioara != null) {
            //Daca numarul anilor experienta nu se incadreaza intre limite, atunci returnez false
            if (aniexperinta < nraniexperienta.limitainferioara || aniexperinta > nraniexperienta.limitasuperioara) {
                return false;
            }
        } else {
            //Daca limita superioara este nula si limita inferioara este mai mare
            //decat numarul anilor de experienta, atunci returnez false
            if (aniexperinta < nraniexperienta.limitainferioara) {
                return false;
            }
        }
        double medie = user.meanGPA();
        if (medieacademica.limitasuperioara != null) {
            //Daca media academica nu incadreaza in limite, atunci returnze false
            if (medie < medieacademica.limitainferioara || medie > medieacademica.limitasuperioara) {
                return false;
            }
        } else {
            //Daca limita superiara este nula si limita inferioara este mai mare
            //decat media academica, atunci returnez false
            if (medie < medieacademica.limitainferioara) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return numejob + " " + numecompanie + " " + flag + "\n" + anabsolvire + "\n" + nraniexperienta + "\n" + medieacademica + "\n" + nrcandidati + " " + salariu;
    }

}
