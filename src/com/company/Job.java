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
        Recruiter r = c.getRecruiter(user);
        double scor = r.evaluate(this, user);
        listacandidati.add(user);
        if (c.observers.contains(user) == false)
            c.addObserver(user);
    }

    public boolean meetsRequirments(User user) {
        boolean flag = true;
        if (user.getGraduationYear() != null) {
            int graduationyear = user.getGraduationYear();
            if (anabsolvire.limitasuperioara != null && anabsolvire.limitainferioara != null)
                if (graduationyear < anabsolvire.limitainferioara || graduationyear > anabsolvire.limitasuperioara) {
                    flag = false;
                    return flag;
                }
        } else {
            if (anabsolvire.limitainferioara == null) {
                flag = true;
                return true;
            }
            flag = false;
            return flag;
        }
        double aniexperinta = 0;
        for (Experience e : user.cv.experienta) {
            Period period = Period.between(e.datainceput, e.datasfarsit);
            aniexperinta += period.getYears();
            if (period.getMonths() > 3)
                aniexperinta += 1;
        }
        if (nraniexperienta.limitasuperioara != null) {
            if (aniexperinta < nraniexperienta.limitainferioara || aniexperinta > nraniexperienta.limitasuperioara) {
                flag = false;
                return flag;
            }
        }
        double medie = user.meanGPA();
        if (medieacademica.limitasuperioara != null) {
            if (medie < medieacademica.limitainferioara || medie > medieacademica.limitasuperioara) {
                flag = false;
                return flag;
            }
        }
        return flag;
    }

    public String toString() {
        return numejob + " " + numecompanie + " " + flag + "\n" + anabsolvire + "\n" + nraniexperienta + "\n" + medieacademica + "\n" + nrcandidati + " " + salariu;
    }

}
