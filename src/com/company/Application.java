package com.company;

import java.util.ArrayList;
import java.util.List;

class Application {
    ArrayList<Company> companies;
    ArrayList<User> utilizatori;

    //Singleton pattern - instantiere întârziată.
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

    //Metoda care cauta un user dupa id, folosita pentru a crea reteaua sociala
    public User getUser(Long id) {
        for (User u : utilizatori) {
            if (u.id == id)
                return u;
        }
        return null;
    }

    //Metoda care cauta un angajat dupa id, folosita pentru a crea reteaua sociala
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

    //Metoda care cauta un recruiter dupa id, folosita pentru a crea reteaua sociala
    public Recruiter getRectuiter(Long id) {
        for (Company c : companies) {
            for (Recruiter r : c.recruiters) {
                if (r.id == id)
                    return r;
            }
        }
        return null;
    }

    //Metoda care cauta un user dupa numele complet/nume, folosita pentru Profile Page
    public User getUser(String nume) {
        for (User u : utilizatori) {
            String fullname = u.cv.datepersonale.getPrenume() + " " + u.cv.datepersonale.getNume();
            String name = u.cv.datepersonale.getPrenume();
            if (fullname.compareTo(nume) == 0 || name.compareTo(nume) == 0)
                return u;

        }
        return null;
    }

    //Metoda care cauta un angajat dupa numele complet/nume, folosita pentru Profile Page
    public Employee getEmployee(String nume) {
        for (Company c : companies) {
            for (Department d : c.departments) {
                for (Employee e : d.getEmployees()) {
                    String fullname = e.cv.datepersonale.getPrenume() + " " + e.cv.datepersonale.getNume();
                    String name = e.cv.datepersonale.getPrenume();
                    if (fullname.compareTo(nume) == 0 || name.compareTo(nume) == 0)
                        return e;
                }
            }
        }
        return null;
    }

    //Metoda care cauta un recruiter dupa numele complet/nume, folosita pentru Profile Page
    public Recruiter getRecruiter(String nume) {
        for (Company c : companies) {
            for (Recruiter r : c.recruiters) {
                String fullname = r.cv.datepersonale.getPrenume() + " " + r.cv.datepersonale.getNume();
                String name = r.cv.datepersonale.getPrenume();
                if (fullname.compareTo(nume) == 0 || name.compareTo(nume) == 0)
                    return r;
            }
        }
        return null;
    }

    //Metoda care cauta un manager dupa numele complet/nume, folosita pentru Profile Page
    public Manager getManager(String nume) {
        for (Company c : companies) {
            String fullname = c.manager.cv.datepersonale.getPrenume() + " " + c.manager.cv.datepersonale.getNume();
            String name = c.manager.cv.datepersonale.getPrenume();
            if (fullname.compareTo(nume) == 0 || name.compareTo(nume) == 0)
                return c.manager;
        }
        return null;
    }


    //Metoda care cauta un user dupa email, folosita pentru sistemul de autentificare
    public User getUserByEmail(String email) {
        for (User u : utilizatori) {
            if (u.cv.datepersonale.getEmail().compareTo(email) == 0)
                return u;
        }
        return null;
    }

    //Metoda care cauta un manager dupa email, folosita pentru sistemul de autentificare
    public Manager getManagerByEmail(String email) {
        for (Company c : companies) {
            if (c.manager.cv.datepersonale.getEmail().compareTo(email) == 0)
                return c.manager;
        }
        return null;
    }

    //Metoda care cauta un anagajat dupa email, folosita pentru sistemul de autentificare
    public Employee getEmployeeByEmail(String mail) {
        for (Company c : companies) {
            for (Department d : c.departments) {
                for (Employee e : d.angajati)
                    if (e.cv.datepersonale.getEmail().compareTo(mail) == 0 && c.recruiters.contains(e) == false)
                        return e;
            }
        }
        return null;
    }

    //Metoda care cauta un recruiter dupa email, folosita pentru sistemul de autentificare
    public Recruiter getRecruiterByEmail(String mail) {
        for (Company c : companies) {
            for (Recruiter r : c.recruiters) {
                if (r.cv.datepersonale.getEmail().compareTo(mail) == 0) {
                    return r;
                }
            }
        }
        return null;
    }
}
