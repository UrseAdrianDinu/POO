package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class TestGUI {
    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();
        Application app = Application.getInstance();
        ArrayList<Job> jobs = new ArrayList<>();
        try {
            File file = new File("src/input.json");
            String path = file.getAbsolutePath();
            FileReader reader = new FileReader(path);
            Object o = jsonParser.parse(reader);
            JSONObject j = (JSONObject) o;

            JSONArray companiesJSON = (JSONArray) j.get("companies");
            for (Object obj : companiesJSON) {
                JSONObject jsonObject = (JSONObject) obj;
                String numecompanie = (String) jsonObject.get("name");

                String numemanager = (String) jsonObject.get("manager");

                JSONArray departmentsJSON = (JSONArray) jsonObject.get("departments");
                DepartmentFactory.DepartmentType dtype;
                DepartmentFactory departmentFactory = new DepartmentFactory();
                Department IT = null;
                Department Management = null;
                Department Marketing = null;
                Department Finance = null;
                for (Object ob : departmentsJSON) {
                    String type = (String) ob;
                    switch (type) {
                        case "IT":
                            dtype = DepartmentFactory.DepartmentType.IT;
                            IT = departmentFactory.createDepartment(dtype);
                        case "Management":
                            dtype = DepartmentFactory.DepartmentType.Management;
                            Management = departmentFactory.createDepartment(dtype);
                        case "Marketing":
                            dtype = DepartmentFactory.DepartmentType.Marketing;
                            Marketing = departmentFactory.createDepartment(dtype);
                        case "Finance":
                            dtype = DepartmentFactory.DepartmentType.Finance;
                            Finance = departmentFactory.createDepartment(dtype);
                    }
                }
                Company company = new Company(numecompanie);
                app.add(company);
                company.add(Management);
                company.add(Marketing);
                company.add(Finance);
                JSONArray jobsJSON = (JSONArray) jsonObject.get("jobs");
                for (Object ob : jobsJSON) {
                    JSONObject jo = (JSONObject) ob;
                    String numejob = (String) jo.get("numejob");
                    String flagJson = (String) jo.get("flag");
                    boolean flag = Boolean.parseBoolean(flagJson);
                    long nrcandidati = (long) jo.get("nrcandidati");
                    long salariu = (long) jo.get("salariu");
                    Long limitainfanabs = (Long) jo.get("infanabs");
                    Long limitasupanabs = (Long) jo.get("supanabs");
                    Constraint anabsolvire = new Constraint(limitainfanabs, limitasupanabs);
                    Long limitainfexp = (Long) jo.get("infexp");
                    Long limitasupexp = (Long) jo.get("supexp");
                    Constraint experienta = new Constraint(limitainfexp, limitasupexp);
                    long limitainfmedie = ((Number) jo.get("infmedie")).longValue();
                    Long limitasupmedie;
                    try {
                        limitasupmedie = ((Number) jo.get("supmedie")).longValue();
                    } catch (Exception e) {
                        limitasupmedie = null;
                    }
                    Constraint medie = new Constraint(limitainfmedie, limitasupmedie);
                    Job job = new Job(numejob, numecompanie, flag, anabsolvire, experienta, medie, nrcandidati, salariu);
                    jobs.add(job);
                }
                company.add(IT);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            File file = new File("src/consumers.json");
            String path = file.getAbsolutePath();
            FileReader reader = new FileReader(path);

            Object o = jsonParser.parse(reader);
            JSONObject j = (JSONObject) o;

            JSONArray employees = (JSONArray) j.get("employees");
            Long idemployees = 1l;
            for (Object obj : employees) {
                JSONObject jo = (JSONObject) obj;

                String name = (String) jo.get("name");
                String[] array = name.split(" ");
                String nume = array[0];
                String prenume = array[1];

                String email = (String) jo.get("email");

                String phone = (String) jo.get("phone");

                String datanasterii = (String) jo.get("date_of_birth");

                String genre = (String) jo.get(("genre"));

                JSONArray limbiJSON = (JSONArray) jo.get("languages");
                ArrayList<Limba> Limbi = new ArrayList<>();
                ArrayList<String> numelimbi = new ArrayList<>();
                ArrayList<String> nivele = new ArrayList<>();
                for (Object ob : limbiJSON) {
                    String numelimba = (String) ob;
                    numelimbi.add(numelimba);
                }
                JSONArray niv = (JSONArray) jo.get("languages_level");
                for (Object ob : niv) {
                    String nivellimba = (String) ob;
                    nivele.add(nivellimba);
                }
                int i;
                for (i = 0; i < numelimbi.size(); i++) {
                    Limba l = new Limba();
                    l.setNume(numelimbi.get(i));
                    l.setNivel(nivele.get(i));
                    Limbi.add(l);
                }
                Information info = new Information.InformationBuilder(nume, prenume, datanasterii, genre).telefon(phone).email(email).limbi(Limbi).build();
                long salariu = (long) jo.get("salary");

                JSONArray educationJson = (JSONArray) jo.get("education");
                TreeSet<Education> educatie = new TreeSet<>();
                for (Object ob : educationJson) {
                    JSONObject jsonObject = (JSONObject) ob;
                    String niveleducatie = (String) jsonObject.get("level");
                    String numeinstitutie = (String) jsonObject.get("name");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    String datainceputJSON = (String) jsonObject.get("start_date");
                    LocalDate datainceput = LocalDate.parse(datainceputJSON, formatter);
                    String datasfarsitJSON = (String) jsonObject.get("end_date");
                    LocalDate datasfarsit = null;
                    double mediafinalizare;
                    Education ed = null;
                    if (datasfarsitJSON != null) {
                        datasfarsit = LocalDate.parse(datasfarsitJSON, formatter);
                    }
                    try {
                        mediafinalizare = (double) jsonObject.get("grade");
                    } catch (Exception e) {
                        mediafinalizare = (long) jsonObject.get("grade");
                    }
                    try {
                        ed = new Education(datainceput, datasfarsit, numeinstitutie, niveleducatie, mediafinalizare);
                    } catch (InvalidDatesException e) {
                        e.printStackTrace();
                    }
                    educatie.add(ed);
                }

                JSONArray experinceJson = (JSONArray) jo.get("experience");
                TreeSet<Experience> experience = new TreeSet<>();
                String numecompaniecurenta = new String();
                String departamentcurent = new String();
                for (Object ob : experinceJson) {
                    JSONObject jsonObject = (JSONObject) ob;
                    String numecompanie = (String) jsonObject.get("company");
                    String pozitie = (String) jsonObject.get("position");
                    String department = (String) jsonObject.get("department");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    String datainceputJSON = (String) jsonObject.get("start_date");
                    LocalDate datainceput = LocalDate.parse(datainceputJSON, formatter);
                    String datasfarsitJSON = (String) jsonObject.get("end_date");
                    LocalDate datasfarsit = null;
                    if (datasfarsitJSON != null) {
                        datasfarsit = LocalDate.parse(datasfarsitJSON, formatter);
                    } else {
                        numecompaniecurenta = numecompanie;
                        departamentcurent = department;
                    }
                    Experience exp = null;
                    try {
                        exp = new Experience(datainceput, datasfarsit, pozitie, numecompanie);
                    } catch (InvalidDatesException e) {
                        e.printStackTrace();
                    }
                    experience.add(exp);
                }

                Consumer.Resume resume = null;
                try {
                    resume = new Consumer.Resume.ResumeBuilder(info, educatie).experienta(experience).build();
                } catch (ResumeIncompleteException e) {
                    e.printStackTrace();
                }
                Employee e = new Employee(resume, numecompaniecurenta, salariu, idemployees);
                idemployees++;
                Company com = app.getCompany(numecompaniecurenta);
                Department d = com.getDepartment(departamentcurent);
                d.add(e);
            }
            JSONArray recruiters = (JSONArray) j.get("recruiters");

            Long idrecruiters = 1l;
            for (Object obj : recruiters) {
                JSONObject jo = (JSONObject) obj;

                String name = (String) jo.get("name");
                String[] array = name.split(" ");
                String nume = array[1];
                String prenume = array[0];

                String email = (String) jo.get("email");

                String phone = (String) jo.get("phone");

                String datanasterii = (String) jo.get("date_of_birth");

                String genre = (String) jo.get(("genre"));

                JSONArray limbiJSON = (JSONArray) jo.get("languages");
                ArrayList<Limba> Limbi = new ArrayList<>();
                ArrayList<String> numelimbiJSON = new ArrayList<>();
                ArrayList<String> niveleJSON = new ArrayList<>();
                for (Object ob : limbiJSON) {
                    String numelimba = (String) ob;
                    numelimbiJSON.add(numelimba);
                }
                JSONArray niv = (JSONArray) jo.get("languages_level");
                for (Object ob : niv) {
                    String nivellimba = (String) ob;
                    niveleJSON.add(nivellimba);
                }
                int i;
                for (i = 0; i < numelimbiJSON.size(); i++) {
                    Limba l = new Limba();
                    l.setNume(numelimbiJSON.get(i));
                    l.setNivel(niveleJSON.get(i));
                    Limbi.add(l);
                }
                Information info = new Information.InformationBuilder(nume, prenume, datanasterii, genre).telefon(phone).email(email).limbi(Limbi).build();
                long salariu = (long) jo.get("salary");

                JSONArray educationJson = (JSONArray) jo.get("education");
                TreeSet<Education> educatie = new TreeSet<>();
                for (Object ob : educationJson) {
                    JSONObject jsonObject = (JSONObject) ob;
                    String niveleducatie = (String) jsonObject.get("level");
                    String numeinstitutie = (String) jsonObject.get("name");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    String datainceputJSON = (String) jsonObject.get("start_date");
                    LocalDate datainceput = LocalDate.parse(datainceputJSON, formatter);
                    String datasfarsitJSON = (String) jsonObject.get("end_date");
                    LocalDate datasfarsit = null;
                    double mediafinalizare;
                    Education ed = null;
                    if (datasfarsitJSON != null) {
                        datasfarsit = LocalDate.parse(datasfarsitJSON, formatter);
                    }
                    try {
                        mediafinalizare = (double) jsonObject.get("grade");
                    } catch (Exception e) {
                        mediafinalizare = (long) jsonObject.get("grade");
                    }
                    try {
                        ed = new Education(datainceput, datasfarsit, numeinstitutie, niveleducatie, mediafinalizare);
                    } catch (InvalidDatesException e) {
                        e.printStackTrace();
                    }
                    educatie.add(ed);
                }

                JSONArray experinceJson = (JSONArray) jo.get("experience");
                TreeSet<Experience> experience = new TreeSet<>();
                String numecompaniecurenta = new String();

                for (Object ob : experinceJson) {
                    JSONObject jsonObject = (JSONObject) ob;
                    String numecompanie = (String) jsonObject.get("company");
                    String pozitie = (String) jsonObject.get("position");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    String datainceputJSON = (String) jsonObject.get("start_date");
                    LocalDate datainceput = LocalDate.parse(datainceputJSON, formatter);
                    String datasfarsitJSON = (String) jsonObject.get("end_date");
                    LocalDate datasfarsit = null;
                    if (datasfarsitJSON != null) {
                        datasfarsit = LocalDate.parse(datasfarsitJSON, formatter);
                    } else {
                        numecompaniecurenta = numecompanie;
                    }
                    Experience exp = null;
                    try {
                        exp = new Experience(datainceput, datasfarsit, pozitie, numecompanie);
                    } catch (InvalidDatesException e) {
                        e.printStackTrace();
                    }
                    experience.add(exp);
                }

                Consumer.Resume resume = null;
                try {
                    resume = new Consumer.Resume.ResumeBuilder(info, educatie).experienta(experience).build();
                } catch (ResumeIncompleteException e) {
                    e.printStackTrace();
                }
                Recruiter recruiter = new Recruiter(resume, numecompaniecurenta, salariu, idrecruiters);
                Company c = app.getCompany(numecompaniecurenta);
                Department d = c.getDepartment("IT");
                d.add(recruiter);
                c.add(recruiter);
                idrecruiters++;
            }

            JSONArray users = (JSONArray) j.get("users");
            Long idusers = 1l;
            for (Object obj : users) {
                JSONObject jo = (JSONObject) obj;

                String name = (String) jo.get("name");
                String[] array = name.split(" ");
                String nume = array[1];
                String prenume = array[0];

                String email = (String) jo.get("email");

                String phone = (String) jo.get("phone");

                String datanasterii = (String) jo.get("date_of_birth");

                String genre = (String) jo.get(("genre"));

                JSONArray limbiJSON = (JSONArray) jo.get("languages");
                ArrayList<Limba> Limbi = new ArrayList<>();
                ArrayList<String> numelimbiJSON = new ArrayList<>();
                ArrayList<String> niveleJSON = new ArrayList<>();
                for (Object ob : limbiJSON) {
                    String numelimba = (String) ob;
                    numelimbiJSON.add(numelimba);
                }
                JSONArray niv = (JSONArray) jo.get("languages_level");
                for (Object ob : niv) {
                    String nivellimba = (String) ob;
                    niveleJSON.add(nivellimba);
                }
                int i;
                for (i = 0; i < numelimbiJSON.size(); i++) {
                    Limba l = new Limba();
                    l.setNume(numelimbiJSON.get(i));
                    l.setNivel(niveleJSON.get(i));
                    Limbi.add(l);
                }

                JSONArray interested_companiesJSON = (JSONArray) jo.get("interested_companies");
                ArrayList<String> interested_companies = new ArrayList<>();

                for (Object ob : interested_companiesJSON) {
                    String numecom = (String) ob;
                    interested_companies.add(numecom);
                }

                Information info = new Information.InformationBuilder(nume, prenume, datanasterii, genre).telefon(phone).email(email).limbi(Limbi).build();

                JSONArray educationJson = (JSONArray) jo.get("education");
                TreeSet<Education> educatie = new TreeSet<>();
                for (Object ob : educationJson) {
                    JSONObject jsonObject = (JSONObject) ob;
                    String niveleducatie = (String) jsonObject.get("level");
                    String numeinstitutie = (String) jsonObject.get("name");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    String datainceputJSON = (String) jsonObject.get("start_date");
                    LocalDate datainceput = LocalDate.parse(datainceputJSON, formatter);
                    String datasfarsitJSON = (String) jsonObject.get("end_date");
                    LocalDate datasfarsit = null;
                    double mediafinalizare;
                    Education ed = null;
                    if (datasfarsitJSON != null) {
                        datasfarsit = LocalDate.parse(datasfarsitJSON, formatter);
                    }
                    try {
                        mediafinalizare = (double) jsonObject.get("grade");
                    } catch (Exception e) {
                        mediafinalizare = (long) jsonObject.get("grade");
                    }
                    try {
                        ed = new Education(datainceput, datasfarsit, numeinstitutie, niveleducatie, mediafinalizare);
                    } catch (InvalidDatesException e) {
                        e.printStackTrace();
                    }
                    educatie.add(ed);
                }

                JSONArray experinceJson = (JSONArray) jo.get("experience");
                TreeSet<Experience> experience = new TreeSet<>();
                String numecompaniecurenta = new String();
                for (Object ob : experinceJson) {
                    JSONObject jsonObject = (JSONObject) ob;
                    String numecompanie = (String) jsonObject.get("company");
                    String pozitie = (String) jsonObject.get("position");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    String datainceputJSON = (String) jsonObject.get("start_date");
                    LocalDate datainceput = LocalDate.parse(datainceputJSON, formatter);
                    String datasfarsitJSON = (String) jsonObject.get("end_date");
                    LocalDate datasfarsit = null;
                    if (datasfarsitJSON != null) {
                        datasfarsit = LocalDate.parse(datasfarsitJSON, formatter);
                    } else {
                        numecompaniecurenta = numecompanie;
                    }
                    Experience exp = null;
                    try {
                        exp = new Experience(datainceput, datasfarsit, pozitie, numecompanie);
                    } catch (InvalidDatesException e) {
                        e.printStackTrace();
                    }
                    experience.add(exp);
                }

                Consumer.Resume resume = null;
                try {
                    resume = new Consumer.Resume.ResumeBuilder(info, educatie).experienta(experience).build();
                } catch (ResumeIncompleteException e) {
                    e.printStackTrace();
                }
                User u = new User(resume, interested_companies, idusers);
                idusers++;
                app.add(u);
            }

            JSONArray managers = (JSONArray) j.get("managers");
            Long idmanagers = 1l;
            for (Object obj : managers) {
                JSONObject jo = (JSONObject) obj;

                String name = (String) jo.get("name");
                String[] array = name.split(" ");
                String nume = array[0];
                String prenume = array[1];

                String email = (String) jo.get("email");

                String phone = (String) jo.get("phone");

                String datanasterii = (String) jo.get("date_of_birth");

                String genre = (String) jo.get(("genre"));

                JSONArray limbiJSON = (JSONArray) jo.get("languages");
                ArrayList<Limba> Limbi = new ArrayList<>();
                ArrayList<String> numelimbiJSON = new ArrayList<>();
                ArrayList<String> niveleJSON = new ArrayList<>();
                for (Object ob : limbiJSON) {
                    String numelimba = (String) ob;
                    numelimbiJSON.add(numelimba);
                }
                JSONArray niv = (JSONArray) jo.get("languages_level");
                for (Object ob : niv) {
                    String nivellimba = (String) ob;
                    niveleJSON.add(nivellimba);
                }
                int i;
                for (i = 0; i < numelimbiJSON.size(); i++) {
                    Limba l = new Limba();
                    l.setNume(numelimbiJSON.get(i));
                    l.setNivel(niveleJSON.get(i));
                    Limbi.add(l);
                }
                Information info = new Information.InformationBuilder(nume, prenume, datanasterii, genre).telefon(phone).email(email).limbi(Limbi).build();
                long salariu = (long) jo.get("salary");

                JSONArray educationJson = (JSONArray) jo.get("education");
                TreeSet<Education> educatie = new TreeSet<>();
                for (Object ob : educationJson) {
                    JSONObject jsonObject = (JSONObject) ob;
                    String niveleducatie = (String) jsonObject.get("level");
                    String numeinstitutie = (String) jsonObject.get("name");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    String datainceputJSON = (String) jsonObject.get("start_date");
                    LocalDate datainceput = LocalDate.parse(datainceputJSON, formatter);
                    String datasfarsitJSON = (String) jsonObject.get("end_date");
                    LocalDate datasfarsit = null;
                    double mediafinalizare;
                    Education ed = null;
                    if (datasfarsitJSON != null) {
                        datasfarsit = LocalDate.parse(datasfarsitJSON, formatter);
                    }
                    try {
                        mediafinalizare = (double) jsonObject.get("grade");
                    } catch (Exception e) {
                        mediafinalizare = (long) jsonObject.get("grade");
                    }
                    try {
                        ed = new Education(datainceput, datasfarsit, numeinstitutie, niveleducatie, mediafinalizare);
                    } catch (InvalidDatesException e) {
                        e.printStackTrace();
                    }
                    educatie.add(ed);
                }

                JSONArray experinceJson = (JSONArray) jo.get("experience");
                TreeSet<Experience> experience = new TreeSet<>();
                String numecompaniecurenta = new String();
                for (Object ob : experinceJson) {
                    JSONObject jsonObject = (JSONObject) ob;
                    String numecompanie = (String) jsonObject.get("company");
                    String pozitie = (String) jsonObject.get("position");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    String datainceputJSON = (String) jsonObject.get("start_date");
                    LocalDate datainceput = LocalDate.parse(datainceputJSON, formatter);
                    String datasfarsitJSON = (String) jsonObject.get("end_date");
                    LocalDate datasfarsit = null;
                    if (datasfarsitJSON != null) {
                        datasfarsit = LocalDate.parse(datasfarsitJSON, formatter);
                    } else {
                        numecompaniecurenta = numecompanie;
                    }
                    Experience exp = null;
                    try {
                        exp = new Experience(datainceput, datasfarsit, pozitie, numecompanie);
                    } catch (InvalidDatesException e) {
                        e.printStackTrace();
                    }
                    experience.add(exp);
                }

                Consumer.Resume resume = null;
                try {
                    resume = new Consumer.Resume.ResumeBuilder(info, educatie).experienta(experience).build();
                } catch (ResumeIncompleteException e) {
                    e.printStackTrace();
                }
                Manager manager = new Manager(resume, numecompaniecurenta, salariu, idmanagers);
                Company c = app.getCompany(numecompaniecurenta);
                c.manager = manager;
                idmanagers++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File file = new File("src/reteasociala.json");
            String path = file.getAbsolutePath();
            FileReader reader = new FileReader(path);
            Object o = jsonParser.parse(reader);
            JSONObject j = (JSONObject) o;

            JSONArray usersJSON = (JSONArray) j.get("users");
            for (Object obj : usersJSON) {
                ArrayList<Consumer> cunoscuti = new ArrayList<>();
                JSONObject jo = (JSONObject) obj;
                Long id = (Long) jo.get("id");
                User u = app.getUser(id);
                JSONArray users = (JSONArray) jo.get("users");
                for (Object ob : users) {
                    Long uid = (Long) ob;
                    User ui = app.getUser(uid);
                    u.add(ui);
                }
                JSONArray employees = (JSONArray) jo.get("employees");
                for (Object ob : employees) {
                    Long uid = (Long) ob;
                    Employee ei = app.getEmployee(uid);
                    u.add(ei);
                }
                JSONArray recruiters = (JSONArray) jo.get("recruiters");
                if (recruiters != null) {
                    for (Object ob : recruiters) {
                        Long uid = (Long) ob;
                        Recruiter ri = app.getRectuiter(uid);
                        if (ri != null)
                            u.add(ri);
                    }
                }
            }

            JSONArray employyesJSON = (JSONArray) j.get("employees");
            for (Object obj : employyesJSON) {
                ArrayList<Consumer> cunoscuti = new ArrayList<>();
                JSONObject jo = (JSONObject) obj;
                Long id = (Long) jo.get("id");
                Employee e = app.getEmployee(id);
                JSONArray users = (JSONArray) jo.get("users");
                for (Object ob : users) {
                    Long uid = (Long) ob;
                    User ui = app.getUser(uid);
                    if (ui != null)
                        e.add(ui);
                }
                JSONArray employees = (JSONArray) jo.get("employees");
                for (Object ob : employees) {
                    Long uid = (Long) ob;
                    Employee ei = app.getEmployee(uid);
                    if (ei != null)
                        e.add(ei);
                }
                JSONArray recruiters = (JSONArray) jo.get("recruiters");
                if (recruiters != null) {
                    for (Object ob : recruiters) {
                        Long uid = (Long) ob;
                        Recruiter ri = app.getRectuiter(uid);
                        if (ri != null)
                            e.add(ri);
                    }
                }
            }

            JSONArray recruitersJSON = (JSONArray) j.get("recruiters");
            for (Object obj : recruitersJSON) {
                ArrayList<Consumer> cunoscuti = new ArrayList<>();
                JSONObject jo = (JSONObject) obj;
                Long id = (Long) jo.get("id");
                Recruiter r = app.getRectuiter(id);
                JSONArray users = (JSONArray) jo.get("users");
                for (Object ob : users) {
                    Long uid = (Long) ob;
                    User ui = app.getUser(uid);
                    if (ui != null)
                        r.add(ui);
                }
                JSONArray employees = (JSONArray) jo.get("employees");
                for (Object ob : employees) {
                    Long uid = (Long) ob;
                    Employee ei = app.getEmployee(uid);
                    if (ei != null)
                        r.add(ei);
                }
                JSONArray recruiters = (JSONArray) jo.get("recruiters");
                if (recruiters != null) {
                    for (Object ob : recruiters) {
                        Long uid = (Long) ob;
                        Recruiter ri = app.getRectuiter(uid);
                        if (ri != null)
                            r.add(ri);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Job j : jobs) {
            Company c = app.getCompany(j.numecompanie);
            Department d = c.getDepartment("IT");
            d.add(j);
        }
        for (User u : app.utilizatori) {
            for (String s : u.companies) {
                Company c = app.getCompany(s);
                for (Job j : c.getJobs()) {
                    j.apply(u);
                }
            }
        }

        HashMap<String, Double> scoruri = new HashMap<>();
        for (Company c : app.companies) {
            for (Request r : c.manager.cereri) {
                User u = (User) r.getValue1();
                String nume = u.cv.datepersonale.getNume() + " " + u.cv.datepersonale.getPrenume();
                Double scor = r.getScore();
                if (scoruri.containsKey(nume) == false)
                    scoruri.put(nume, scor);
            }
        }
        System.out.println("Scoruri");
        System.out.println(scoruri + "\n");
        GUI gui = new GUI("EJobs");

//
//        for (Company c : app.companies) {
//            for (Job j : c.getJobs()) {
//                c.manager.process(j);
//            }
//        }
    }
}

