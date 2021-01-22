package com.company;

import java.util.ArrayList;
import java.util.Set;

class Company implements Subject {
    String nume;
    Manager manager;
    ArrayList<Department> departments;
    ArrayList<Recruiter> recruiters;
    ArrayList<User> observers;
    Notification notification;

    public Company(String nume) {
        this.nume = nume;
        departments = new ArrayList<>();
        recruiters = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public void add(Department department) {
        departments.add(department);
    }

    public void add(Recruiter recruiter) {
        recruiters.add(recruiter);
    }

    public void add(Employee employee, Department department) {
        department.add(employee);
    }

    public void remove(Employee employee) {
        for (Department d : departments) {
            if (d.angajati.contains(employee)) {
                d.angajati.remove(employee);
                break;
            }
        }
    }

    public void remove(Department department) {
        for (Employee e : department.angajati) {
            department.angajati.remove(e);
        }
        departments.remove(department);
    }

    public void remove(Recruiter recruiter) {
        recruiters.remove(recruiter);
    }

    public void move(Department source, Department destination) {
        destination.angajati.addAll(source.angajati);
        destination.jobs.addAll(source.getJobs());
        remove(source);
    }

    public void move(Employee employee, Department newDepartment) {
        remove(employee);
        newDepartment.angajati.add(employee);
    }

    public boolean contains(Department department) {
        return departments.contains(department);
    }

    public boolean contains(Employee employee) {
        boolean flag = false;
        for (Department d : departments) {
            if (d.angajati.contains(employee)) {
                flag = true;
                return flag;
            }
        }
        return flag;
    }

    public boolean contains(Recruiter recruiter) {
        return recruiters.contains(recruiter);
    }

    public Recruiter getRecruiter(User user) {

        Recruiter r = null;
        int maxdegree = 0;
        //Am parcurs lista de recruiteri
        for (Recruiter rec : recruiters) {
            //AM calculat gradul de prietenie dintre user si recruiterul curent
            int k = user.getDegreeInFriendship(rec);
            //Daca este mai mare gradul maxim ii preia valoarea
            //Si recruiterul cel mai indepartat ia valoarea recruiterului curent
            if (k > maxdegree) {
                maxdegree = k;
                r = rec;
            }
            //Daca recruiterul curent are acelasi grad
            //atunci comparatia se face dupa rating
            if (k == maxdegree && r != null) {
                if (rec.rating > r.rating)
                    r = rec;
            }
        }
        return r;
    }

    public ArrayList<Job> getJobs() {
        ArrayList<Job> jobs = new ArrayList<>();
        for (Department d : departments) {
            jobs.addAll(d.getJobs());
        }
        return jobs;
    }

    //Cautarea unui departament dupa un job
    public Department getDepartment(Job job) {
        for (Department d : departments) {
            if (d.jobs.contains(job))
                return d;
        }
        return null;
    }

    public void addObserver(User user) {
        observers.add(user);
    }

    public void remove(User c) {
        observers.remove(c);
    }

    public void notifyAllObservers() {
        for (User u : observers) {
            u.update(notification);
        }
    }

    //Cautarea unui departament dupa tipul departamentului
    public Department getDepartment(String tip) {
        for (Department d : departments) {
            if (d.getType().compareTo(tip) == 0)
                return d;
        }
        return null;
    }

    public String toString() {
        return nume + " " + manager + "\n" + "Departamente: " + departments + "\n" + "Recruiters: " + recruiters;
    }
}
