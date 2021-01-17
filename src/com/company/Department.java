package com.company;

import java.util.ArrayList;

abstract class Department {
    ArrayList<Employee> angajati;
    ArrayList<Job> jobs;

    public Department() {
        angajati = new ArrayList<>();
        jobs = new ArrayList<>();
    }

    public abstract double getTotalSalaryBudget();

    public abstract String getType();

    public ArrayList<Job> getJobs() {
        ArrayList<Job> jobs = new ArrayList<>();
        for (Job j : this.jobs) {
            if (j.flag)
                jobs.add(j);
        }
        return jobs;
    }

    public void add(Employee employee) {
        angajati.add(employee);
    }

    public void remove(Employee employee) {
        angajati.remove(employee);
    }

    public void add(Job job) {
        jobs.add(job);
        Notification n = new Notification("Exista un job nou - " + job.numejob + " - in compania " + job.numecompanie);
        Application app = Application.getInstance();
        Company c = app.getCompany(job.numecompanie);
        c.notification = n;
        c.notifyAllObservers();
    }

    public ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (Employee e : angajati) {
            employees.add(e);
        }
        return employees;
    }

    @Override
    public String toString() {
        return "Department{" + getType() + " " +
                "angajati=" + angajati +
                ",\n jobs=" + jobs +
                '}';
    }
}
