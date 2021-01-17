package com.company;

import java.util.ArrayList;
import java.util.List;

class Application {
    ArrayList<Company> companies;
    ArrayList<User> utilizatori;
    private static Application instance = null;

    private Application() {
        companies = new ArrayList<>();
        utilizatori = new ArrayList<>();
    }

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public Company getCompany(String name) {
        for (Company c : companies) {
            if (c.nume.compareTo(name) == 0)
                return c;
        }
        return null;
    }

    public void add(Company company) {
        companies.add(company);
    }

    public void add(User user) {
        utilizatori.add(user);
    }

    public boolean remove(Company company) {
        if (companies.contains(company))
            companies.remove(company);
        else
            return false;
        return true;
    }

    public boolean remove(User user) {
        if (utilizatori.contains(user))
            utilizatori.remove(user);
        else
            return false;
        return true;
    }

    public ArrayList<Job> getJobs(List<String> companies) {
        ArrayList<Job> jobs = new ArrayList<>();
        for (String nume : companies) {
            Company c = getCompany(nume);
            jobs.addAll(c.getJobs());

        }
        return jobs;
    }

    public User getUser(Long id) {
        for (User u : utilizatori) {
            if (u.id == id)
                return u;
        }
        return null;
    }

    public Employee getEmployee(Long id) {
        for (Company c : companies) {
            for (Department d : c.departments) {
                for (Employee e : d.angajati) {
                    if (e.id == id)
                        return e;
                }
            }
        }
        return null;
    }

    public Recruiter getRectuiter(Long id) {
        for (Company c : companies) {
            for (Recruiter r : c.recruiters) {
                if (r.id == id)
                    return r;
            }
        }
        return null;
    }

    public User getUser(String nume) {
        for (User u : utilizatori) {
            String fullname = u.cv.datepersonale.getPrenume() + " " + u.cv.datepersonale.getNume();
            String name = u.cv.datepersonale.getPrenume();
            if(fullname.compareTo(nume)==0 || name.compareTo(nume)==0)
                return u;

        }
        return null;
    }
}
