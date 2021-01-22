package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.time.Period;
import java.util.ArrayList;
import java.util.Locale;

public class GUI extends JFrame {

    private JButton button1; //buton care deschide Admin Page
    private JButton button2; //buton care deschide Manager Page
    private JButton button3; //buton care dechide Profile Page
    private JPanel Temp;
    private JPanel HomePage;
    private JSplitPane ProfilePage;
    private JButton BACKButton; //Buton de BACK Profile Page
    private JTextField textField1; //TextField de cautare a unui user ProfilePage
    private JPanel Info;
    private JScrollPane ScrollInfo;
    private JSplitPane AdminPage;
    private JPanel PanelAd1;
    private JSplitPane PanelAd2;
    private JSplitPane ManagerPage;
    private JPanel PanelMan1;
    private JSplitPane PanelMan2;
    private JPanel Login;
    private JButton BackHomePage;
    private JTabbedPane tabbedPaneusers;
    private JTabbedPane tabbedPanecompanies;
    private JPanel jPanelcompanieslist;
    private JPanel jPaneldepartments;
    private JPanel jPanelemployees;
    private JPanel topPanelcompanies;
    private JPanel jPaneluserslist;
    private JPanel jPanelusersInfo;
    private JLabel nume;
    private JSplitPane top;
    private JTabbedPane managers;
    private JPanel managerslist;
    private JPanel cereri;
    private JPanel infocerere;
    private JSplitPane topmanager;
    private JTextField username;
    private JPasswordField password;
    private JButton login;
    private boolean flaguser;
    private int k1;


    public GUI(String title) {
        super(title);
        setMinimumSize(new Dimension(800, 800));
        setLocationRelativeTo(null);
        Font f = new Font("Arial", Font.PLAIN, 20);
        Login.setLayout(new GridBagLayout());
        Color logincolor = new Color(50, 168, 82);
        //Constructie pagina Login
        GridBagConstraints constraintslogin = new GridBagConstraints();
        constraintslogin.gridy = 2;
        constraintslogin.gridx = 2;
        constraintslogin.weighty = 0;
        constraintslogin.weightx = 0;
        constraintslogin.anchor = GridBagConstraints.CENTER;
        constraintslogin.fill = GridBagConstraints.CENTER;
        constraintslogin.insets = new Insets(300, 0, 0, 0);
        username = new JTextField("E-mail", 30);
        username.setFont(f);
        password = new JPasswordField(30);
        password.setFont(f);
        Login.add(username, constraintslogin);
        constraintslogin.gridy++;
        constraintslogin.insets = new Insets(0, 0, 0, 0);
        Login.add(password, constraintslogin);
        login = new JButton("Login");
        login.setFont(f);
        login.setBackground(logincolor);
        username.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                username.setText("");
            }
        });
        constraintslogin.gridy++;
        constraintslogin.insets = new Insets(20, 0, 0, 0);
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Logare ca admin
                if (username.getText().compareTo("admin") == 0 && String.valueOf(password.getPassword()).compareTo("admin") == 0) {
                    button2.setVisible(false);
                    button3.setVisible(false);
                    System.out.println("Logged as - admin");
                    setContentPane(HomePage);
                    setVisible(true);
                } else {
                    User u = Application.getInstance().getUserByEmail(username.getText());
                    //Logare ca user
                    if (u != null) {
                        if (String.valueOf(password.getPassword()).compareTo(u.cv.datepersonale.getNume().toLowerCase(Locale.ROOT)) == 0) {
                            button1.setVisible(false);
                            button2.setVisible(false);
                            System.out.println("Logged as User - " + u.cv.datepersonale.getNume() + " " + u.cv.datepersonale.getPrenume());
                            setContentPane(HomePage);
                            setVisible(true);
                        }
                    } else {
                        Manager m = Application.getInstance().getManagerByEmail(username.getText());
                        //Logare ca manager
                        if (m != null) {
                            if (String.valueOf(password.getPassword()).compareTo(m.cv.datepersonale.getNume().toLowerCase(Locale.ROOT)) == 0) {
                                button1.setVisible(false);
                                System.out.println("Logged as Manager - " + m.cv.datepersonale.getNume() + " " + m.cv.datepersonale.getPrenume());
                                setContentPane(HomePage);
                                setVisible(true);
                            }
                        } else {
                            Employee employee = Application.getInstance().getEmployeeByEmail(username.getText());
                            //Logare ca angajat
                            if (employee != null) {
                                if (String.valueOf(password.getPassword()).compareTo(employee.cv.datepersonale.getNume().toLowerCase(Locale.ROOT)) == 0) {
                                    button1.setVisible(false);
                                    button2.setVisible(false);
                                    System.out.println("Logged as Employee - " + employee.cv.datepersonale.getNume() + " " + employee.cv.datepersonale.getPrenume());
                                    setContentPane(HomePage);
                                    setVisible(true);
                                }
                            } else {
                                Recruiter recruiter = Application.getInstance().getRecruiterByEmail(username.getText());
                                //Logare ca recruiter
                                if (recruiter != null) {
                                    if (String.valueOf(password.getPassword()).compareTo(recruiter.cv.datepersonale.getNume().toLowerCase(Locale.ROOT)) == 0) {
                                        button1.setVisible(false);
                                        button2.setVisible(false);
                                        System.out.println("Logged as Recruiter - " + recruiter.cv.datepersonale.getNume() + " " + recruiter.cv.datepersonale.getPrenume());
                                        setContentPane(HomePage);
                                        setVisible(true);
                                    }
                                } else {
                                    System.out.println("Invalid email/password");
                                }
                            }
                        }
                    }

                }
            }
        });
        Login.add(login, constraintslogin);
        JLabel templogin = new JLabel();
        constraintslogin.gridy++;
        constraintslogin.weightx = 1;
        constraintslogin.weighty = 1;
        Login.add(templogin, constraintslogin);
        setContentPane(Login);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        textField1.setText("Introduceti numele utilizatorului");
        button1.setFocusable(false);
        button2.setFocusable(false);
        button3.setFocusable(false);

        Font ang = new Font("Arial", Font.PLAIN, 25);

        Border border1 = BorderFactory.createLineBorder(Color.RED, 5);
        Border border2 = BorderFactory.createLineBorder(Color.YELLOW, 5);
        Border border3 = BorderFactory.createLineBorder(Color.BLUE, 5);
        Border border4 = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3);

        Color comp = new Color(245, 171, 44);
        Color depart = new Color(118, 242, 111);
        Color useri = new Color(111, 214, 242);
        Color cangajati = new Color(179, 128, 255);
        Color manageri = new Color(247, 96, 96);
        Color cerericolor = new Color(247, 239, 124);
        Color rejectcolor = new Color(252, 38, 38);
        Color acceptcolor = new Color(97, 252, 25);

        //Constructie Admin Page
        AdminPage.setDividerLocation(150);
        AdminPage.setEnabled(false);

        PanelAd1.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        JButton BackAdminPage = new JButton("BACK");
        BackAdminPage.setFont(f);
        PanelAd1.add(BackAdminPage, constraints);
        JButton Users = new JButton("Users");
        Users.setPreferredSize(new Dimension(50, 30));
        Users.setFont(f);
        constraints.gridy = 1;
        constraints.insets = new Insets(50, 0, 0, 0);
        PanelAd1.add(Users, constraints);
        JButton Companies = new JButton("Companies");
        Companies.setFont(f);
        Companies.setPreferredSize(new Dimension(50, 30));
        constraints.gridy = 2;
        constraints.insets = new Insets(10, 0, 0, 0);
        PanelAd1.add(Companies, constraints);
        constraints.gridy++;
        constraints.weighty = 1;
        constraints.weightx = 1;
        JLabel temp = new JLabel("");
        PanelAd1.add(temp, constraints);
        jPaneluserslist = new JPanel();
        jPaneluserslist.setLayout(new GridBagLayout());
        jPanelusersInfo = new JPanel();
        jPanelusersInfo.setLayout(new GridBagLayout());
        PanelAd2.setDividerLocation(30);
        PanelAd2.setEnabled(false);
        PanelAd2.setRightComponent(new JPanel());
        PanelAd2.setLeftComponent(new JPanel());
        Users.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (top != null) {
                    top.removeAll();
                }
                PanelAd2.setDividerLocation(30);
                tabbedPaneusers = new JTabbedPane();
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridy = 0;
                constraints.gridx = 0;
                constraints.anchor = GridBagConstraints.NORTH;
                constraints.weightx = 0;
                constraints.weighty = 0;
                constraints.fill = GridBagConstraints.HORIZONTAL;
                if (jPaneluserslist != null)
                    jPaneluserslist.removeAll();
                for (User u : Application.getInstance().utilizatori) {
                    JButton user = new JButton(u.cv.datepersonale.getNume() + " " + u.cv.datepersonale.getPrenume());
                    user.setPreferredSize(new Dimension(20, 30));
                    user.setFocusable(false);
                    user.setFont(f);
                    user.setBackground(useri);
                    user.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jPanelusersInfo.removeAll();
                            tabbedPaneusers.setSelectedIndex(1);
                            nume = new JLabel(u.cv.datepersonale.getNume() + " " + u.cv.datepersonale.getPrenume());
                            nume.setFont(f);
                            nume.setHorizontalAlignment(JLabel.CENTER);
                            PanelAd2.setLeftComponent(nume);
                            GridBagConstraints constraintsuser = new GridBagConstraints();
                            constraintsuser.gridy = 0;
                            constraintsuser.gridx = 0;
                            constraintsuser.weightx = 0;
                            constraintsuser.weighty = 0;
                            constraintsuser.anchor = GridBagConstraints.NORTH;
                            constraintsuser.fill = GridBagConstraints.HORIZONTAL;
                            JLabel datepersonale = new JLabel("Date personale");
                            datepersonale.setFont(f);
                            datepersonale.setBorder(border1);
                            datepersonale.setHorizontalAlignment(JLabel.CENTER);
                            jPanelusersInfo.add(datepersonale, constraintsuser);
                            constraintsuser.gridy++;
                            String[][] data = {{"Nume", u.cv.datepersonale.getNume() + " " + u.cv.datepersonale.getPrenume()},
                                    {"Sex", u.cv.datepersonale.getSex()},
                                    {"Data de nastere", u.cv.datepersonale.getDatanastere()},
                                    {"Telefon", u.cv.datepersonale.getTelefon()},
                                    {"Email", u.cv.datepersonale.getEmail()}};
                            String[] column = new String[]{"", ""};
                            JTable jTable = new JTable(data, column);
                            jTable.setSize(new Dimension(Info.getWidth(), 50));
                            jTable.setFont(f);
                            jTable.setRowHeight(30);
                            jTable.setEnabled(false);
                            jPanelusersInfo.add(jTable, constraintsuser);
                            constraintsuser.gridy++;
                            JLabel educatie = new JLabel("Educatie");
                            educatie.setFont(f);
                            educatie.setBorder(border2);
                            educatie.setHorizontalAlignment(JLabel.CENTER);
                            jPanelusersInfo.add(educatie, constraintsuser);
                            for (Education education : u.cv.educatie) {
                                String[][] edu = {{"Numele institutiei", education.numeleinstitutiei},
                                        {"Nivel", education.nivel},
                                        {"Medie finalizare", String.valueOf(education.mediafinalizare)},
                                        {"Data inceput", String.valueOf(education.datainceput)},
                                        {"Data sfarsit", String.valueOf(education.datasfarsit)}};
                                JTable ed = new JTable(edu, column);
                                constraintsuser.gridy++;
                                ed.setSize(new Dimension(Info.getWidth(), 50));
                                ed.setFont(f);
                                ed.setRowHeight(30);
                                ed.setEnabled(false);
                                jPanelusersInfo.add(ed, constraintsuser);
                            }
                            constraintsuser.gridy++;
                            JLabel experienta = new JLabel("Experienta");
                            experienta.setFont(f);
                            experienta.setBorder(border3);
                            experienta.setHorizontalAlignment(JLabel.CENTER);
                            jPanelusersInfo.add(experienta, constraintsuser);
                            for (Experience exp : u.cv.experienta) {
                                String[][] ex = {{"Pozitie", exp.pozitie},
                                        {"companie", exp.companie},
                                        {"Data inceput", String.valueOf(exp.datainceput)},
                                        {"Data sfarsit", String.valueOf(exp.datasfarsit)}};
                                JTable xp = new JTable(ex, column);
                                constraintsuser.gridy++;
                                xp.setSize(new Dimension(Info.getWidth(), 50));
                                xp.setFont(f);
                                xp.setRowHeight(30);
                                xp.setEnabled(false);
                                jPanelusersInfo.add(xp, constraintsuser);
                            }
                            constraintsuser.weighty = 1;
                            constraintsuser.weightx = 1;
                            constraintsuser.gridy++;
                            JLabel temp = new JLabel("");
                            jPanelusersInfo.add(temp, constraintsuser);
                        }

                    });

                    jPaneluserslist.add(user, constraints);
                    constraints.gridy++;
                }

                constraints.gridy++;
                constraints.weighty = 1;
                constraints.weightx = 1;
                JLabel temp = new JLabel("");
                jPaneluserslist.add(temp, constraints);
                JScrollPane jScrollPaneusersinfo = new JScrollPane(jPanelusersInfo);
                jScrollPaneusersinfo.setPreferredSize(new Dimension(PanelAd2.getRightComponent().getWidth(), PanelAd2.getRightComponent().getHeight()));
                tabbedPaneusers.addTab("Lista useri", jPaneluserslist);
                tabbedPaneusers.addTab("Informatii utilizator", jScrollPaneusersinfo);
                PanelAd2.setRightComponent(tabbedPaneusers);

            }
        });

        jPanelcompanieslist = new JPanel();
        jPanelcompanieslist.setLayout(new GridBagLayout());

        jPaneldepartments = new JPanel();
        jPaneldepartments.setLayout(new GridBagLayout());

        jPanelemployees = new JPanel();
        jPanelemployees.setLayout(new GridBagLayout());

        topPanelcompanies = new JPanel();
        topPanelcompanies.setLayout(new GridBagLayout());

        Companies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nume != null)
                    nume.setText("");
                PanelAd2.setDividerLocation(30);
                tabbedPanecompanies = new JTabbedPane();

                GridBagConstraints constraintscompanies = new GridBagConstraints();
                constraintscompanies.gridy = 0;
                constraintscompanies.gridx = 0;
                constraintscompanies.weighty = 0;
                constraintscompanies.weightx = 0;
                constraintscompanies.fill = GridBagConstraints.HORIZONTAL;
                constraintscompanies.anchor = GridBagConstraints.NORTH;


                for (Company c : Application.getInstance().getCompanies()) {
                    JButton com = new JButton(c.nume);
                    com.setPreferredSize(new Dimension(20, 50));
                    com.setFocusable(false);
                    com.setFont(f);
                    com.setForeground(Color.BLACK);
                    com.setBackground(comp);
                    com.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jPaneldepartments.removeAll();
                            tabbedPanecompanies.setSelectedIndex(1);
                            GridBagConstraints cdep = new GridBagConstraints();
                            cdep.weightx = 0;
                            cdep.weighty = 0;
                            cdep.gridy = 0;
                            cdep.gridx = 0;
                            cdep.anchor = GridBagConstraints.NORTH;
                            cdep.fill = GridBagConstraints.HORIZONTAL;
                            for (Department d : c.departments) {
                                JButton department = new JButton(d.getType());
                                department.setPreferredSize(new Dimension(20, 50));
                                department.setFocusable(false);
                                department.setFont(f);
                                department.setBackground(depart);
                                department.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        tabbedPanecompanies.setSelectedIndex(2);
                                        jPanelemployees.removeAll();
                                        topPanelcompanies.removeAll();

                                        JButton calculate = new JButton("Calculate budget");
                                        calculate.setFont(f);
                                        JLabel buget = new JLabel("BUDGET");
                                        buget.setFont(f);
                                        buget.setHorizontalAlignment(JLabel.CENTER);
                                        calculate.setPreferredSize(new Dimension(PanelAd2.getWidth() / 2, 10));
                                        calculate.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                buget.setText(String.valueOf(d.getTotalSalaryBudget()) + " lei");
                                            }
                                        });
                                        top = new JSplitPane();
                                        top.setDividerLocation(0.5);
                                        top.setLeftComponent(calculate);
                                        top.setRightComponent(buget);
                                        PanelAd2.setLeftComponent(top);

                                        GridBagConstraints employees = new GridBagConstraints();
                                        employees.gridy = 0;
                                        employees.gridx = 0;
                                        employees.weightx = 0;
                                        employees.weighty = 0;
                                        employees.anchor = GridBagConstraints.NORTH;
                                        employees.fill = GridBagConstraints.HORIZONTAL;
                                        JLabel angajati = new JLabel("Angajati");
                                        angajati.setFont(ang);
                                        angajati.setBorder(border4);
                                        angajati.setHorizontalAlignment(JLabel.CENTER);
                                        jPanelemployees.add(angajati, employees);
                                        employees.gridy++;
                                        for (Employee emp : d.angajati) {
                                            JButton employee = new JButton(emp.cv.datepersonale.getNume() + " " + emp.cv.datepersonale.getPrenume());
                                            employee.setPreferredSize(new Dimension(20, 50));
                                            employee.setFocusable(false);
                                            employee.setBackground(cangajati);
                                            employee.setFont(f);
                                            jPanelemployees.add(employee, employees);
                                            employees.gridy++;
                                        }
                                        JLabel joburidisponibile = new JLabel("Joburi disponibile");
                                        joburidisponibile.setFont(ang);
                                        joburidisponibile.setBorder(border4);
                                        joburidisponibile.setHorizontalAlignment(JLabel.CENTER);
                                        jPanelemployees.add(joburidisponibile, employees);
                                        employees.gridy++;
                                        for (Job j : d.getJobs()) {
                                            JLabel job = new JLabel(j.numejob);
                                            job.setFont(f);
                                            job.setHorizontalAlignment(JLabel.CENTER);
                                            jPanelemployees.add(job, employees);
                                            employees.gridy++;
                                        }

                                        employees.weighty = 1;
                                        employees.weightx = 1;
                                        employees.gridy++;
                                        JLabel temp = new JLabel("");
                                        jPanelemployees.add(temp, employees);
                                    }
                                });
                                jPaneldepartments.add(department, cdep);
                                cdep.gridy++;
                            }
                            cdep.weightx = 1;
                            cdep.weighty = 1;
                            cdep.gridy++;
                            JLabel temp = new JLabel("");
                            jPaneldepartments.add(temp, cdep);


                        }
                    });
                    jPanelcompanieslist.add(com, constraintscompanies);
                    constraintscompanies.gridy++;
                }

                constraintscompanies.weighty = 1;
                constraintscompanies.weightx = 1;
                constraintscompanies.gridy++;
                JLabel temp = new JLabel();
                jPanelcompanieslist.add(temp, constraintscompanies);

                tabbedPanecompanies.addTab("Lista companii", jPanelcompanieslist);
                tabbedPanecompanies.addTab("Departamente", jPaneldepartments);
                tabbedPanecompanies.addTab("Detalii departament", jPanelemployees);
                PanelAd2.setRightComponent(tabbedPanecompanies);

            }
        });
        //Buton de BACK Admin Page
        BackAdminPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(HomePage);
                if (nume != null)
                    nume.setText("");
                if (tabbedPaneusers != null)
                    tabbedPaneusers.setSelectedIndex(0);
                if (top != null)
                    top.removeAll();
                PanelAd2.setRightComponent(new JPanel());
                PanelAd2.setDividerLocation(40);
                setVisible(true);
            }
        });
        //Buton pentru a deschide Admin Page
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(AdminPage);
                setVisible(true);
            }
        });
        //Buton pentru a deschide Manager Page
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Constructie Manager Page
                PanelMan2.setRightComponent(new JPanel());
                PanelMan2.setLeftComponent(new JPanel());
                PanelMan2.setDividerLocation(40);
                managers = new JTabbedPane();
                managerslist = new JPanel();
                managerslist.setLayout(new GridBagLayout());
                cereri = new JPanel();
                cereri.setLayout(new GridBagLayout());
                infocerere = new JPanel();
                infocerere.setLayout(new GridBagLayout());
                GridBagConstraints cmanagerslist = new GridBagConstraints();
                cmanagerslist.weighty = 0;
                cmanagerslist.weightx = 0;
                cmanagerslist.gridy = 0;
                cmanagerslist.gridx = 0;
                cmanagerslist.fill = GridBagConstraints.HORIZONTAL;
                cmanagerslist.anchor = GridBagConstraints.NORTH;

                for (Company c : Application.getInstance().getCompanies()) {
                    JButton manager = new JButton("Manager " + c.nume + " - " + c.manager.cv.datepersonale.getNume() + " " + c.manager.cv.datepersonale.getPrenume());
                    manager.setFont(f);
                    manager.setBackground(manageri);
                    manager.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            managers.setSelectedIndex(1);
                            if (cereri != null)
                                cereri.removeAll();
                            GridBagConstraints constraintscereri = new GridBagConstraints();
                            constraintscereri.gridy = 0;
                            constraintscereri.gridx = 0;
                            constraintscereri.weightx = 0;
                            constraintscereri.weighty = 0;
                            constraintscereri.fill = GridBagConstraints.HORIZONTAL;
                            constraintscereri.anchor = GridBagConstraints.NORTH;
                            for (Request r : c.manager.cereri) {
                                User u = (User) r.getValue1();
                                Job j = (Job) r.getKey();
                                String nume = u.cv.datepersonale.getNume() + " " + u.cv.datepersonale.getPrenume();
                                JButton cerere = new JButton(nume + " - " + j.numejob);
                                cerere.setBackground(cerericolor);
                                cerere.setFont(f);
                                cerere.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        managers.setSelectedIndex(2);
                                        if (infocerere != null)
                                            infocerere.removeAll();
                                        GridBagConstraints infoc = new GridBagConstraints();
                                        infoc.gridy = 0;
                                        infoc.gridx = 0;
                                        infoc.weightx = 0;
                                        infoc.weighty = 0;
                                        infoc.fill = GridBagConstraints.HORIZONTAL;
                                        infoc.anchor = GridBagConstraints.NORTH;
                                        topmanager = new JSplitPane();
                                        topmanager.setDividerLocation(350);
                                        topmanager.setEnabled(false);
                                        JButton accept = new JButton("ACCEPT");
                                        accept.setFont(f);
                                        accept.setBackground(acceptcolor);
                                        accept.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                if (Application.getInstance().utilizatori.contains(u) == true) {
                                                    if (j.flag != false) {
                                                        managers.setSelectedIndex(1);
                                                        Employee employee = u.convert();
                                                        employee.salariu = j.salariu;
                                                        employee.numecompanie = j.numecompanie;
                                                        Application.getInstance().remove(u);
                                                        Notification n = new Notification("User-ul " + employee.cv.datepersonale.getNume() + " " + employee.cv.datepersonale.getPrenume() + " scor - " + r.getScore() + " a fost angajat in compania " + j.numecompanie + " pe pozita " + j.numejob + ".");
                                                        Department d = Application.getInstance().getCompany(j.numecompanie).getDepartment(j);
                                                        d.add(employee);
                                                        c.manager.cereri.remove(r);
                                                        j.listacandidati.remove(u);
                                                        j.flag = false;
                                                        for (Company c : Application.getInstance().companies) {
                                                            c.observers.remove(u);
                                                        }
                                                        u.update(n);
                                                        for (Request req : c.manager.cereri) {
                                                            User user = (User) req.getValue1();
                                                            Job job = (Job) req.getKey();
                                                            if (job.numejob.compareTo(j.numejob) == 0 && user.cv.datepersonale.getNume().compareTo(u.cv.datepersonale.getNume()) != 0) {
                                                                Notification not = new Notification("Cererea de aplicare trimisa de " + user.cv.datepersonale.getNume() + " " + user.cv.datepersonale.getPrenume() + " " + "a fost respinsa pentru job- ul " + job.numejob + " din compania " + job.numecompanie + ".");
                                                                user.update(not);
                                                            }
                                                        }
                                                        for (Request req : c.manager.cereri) {
                                                            User user = (User) req.getValue1();
                                                            Job job = (Job) req.getKey();
                                                            if (job.flag == false) {
                                                                for (User u : job.listacandidati) {
                                                                    if (u.cv.datepersonale.getNume().compareTo(employee.cv.datepersonale.getNume()) != 0) {
                                                                        Notification notif = new Notification("Job-ul " + job.numejob + " din cadrul companiei " + job.numecompanie + " este inchis.");
                                                                        u.update(notif);
                                                                    }
                                                                }
                                                                break;
                                                            }
                                                        }
                                                        ArrayList<Request<Job, User>> cereri = new ArrayList<>();
                                                        for (Company c : Application.getInstance().getCompanies()) {
                                                            for (Request r : c.manager.cereri) {
                                                                User user = (User) r.getValue1();
                                                                if (user.cv.datepersonale.getNume().compareTo(employee.cv.datepersonale.getNume()) == 0) {
                                                                    cereri.add(r);
                                                                }
                                                            }
                                                        }
                                                        for (Company c : Application.getInstance().getCompanies()) {
                                                            for (Request r : cereri) {
                                                                c.manager.cereri.remove(r);
                                                            }
                                                        }

                                                    } else {
                                                        System.out.println("Acest job este inchis.");
                                                    }
                                                } else {
                                                    System.out.println("Acest utilizator este deja anagajat.");
                                                }
                                                manager.doClick();
                                            }
                                        });
                                        JButton reject = new JButton("REJECT");
                                        reject.setFont(f);
                                        reject.setBackground(rejectcolor);
                                        reject.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                managers.setSelectedIndex(1);
                                                c.manager.cereri.remove(r);
                                                manager.doClick();
                                            }
                                        });
                                        topmanager.setLeftComponent(accept);
                                        topmanager.setRightComponent(reject);
                                        PanelMan2.setLeftComponent(topmanager);
                                        JLabel job = new JLabel("Job - " + j.numejob);
                                        job.setFont(f);
                                        job.setHorizontalAlignment(JLabel.CENTER);
                                        infocerere.add(job, infoc);
                                        infoc.gridy++;
                                        JLabel user = new JLabel("Candidat - " + nume);
                                        user.setFont(f);
                                        user.setHorizontalAlignment(JLabel.CENTER);
                                        infocerere.add(user, infoc);
                                        infoc.gridy++;

                                        double aniexperinta = 0;
                                        for (Experience exp : u.cv.experienta) {
                                            Period period = Period.between(exp.datainceput, exp.datasfarsit);
                                            aniexperinta += period.getYears();
                                            if (period.getMonths() > 3)
                                                aniexperinta += 1;
                                        }
                                        String[][] cer = {{"Anul absolvirii", String.valueOf(u.getGraduationYear())},
                                                {"Medie finalizare", String.valueOf(u.meanGPA())},
                                                {"Ani experienta", String.valueOf(aniexperinta)},
                                                {"Scor", String.valueOf(r.getScore())}};
                                        String[] column = new String[]{"", ""};
                                        JTable table = new JTable(cer, column);
                                        table.setSize(new Dimension(infocerere.getWidth(), 50));
                                        table.setRowHeight(40);
                                        table.setFont(f);
                                        infocerere.add(table, infoc);
                                        infoc.gridy++;
                                        infoc.gridy++;
                                        infoc.weighty = 1;
                                        infoc.weightx = 1;
                                        JLabel temp = new JLabel();
                                        infocerere.add(temp, infoc);
                                    }
                                });
                                cereri.add(cerere, constraintscereri);
                                constraintscereri.gridy++;
                            }

                            constraintscereri.gridy++;
                            constraintscereri.weighty = 1;
                            constraintscereri.weightx = 1;
                            JLabel temp = new JLabel("");
                            cereri.add(temp, constraintscereri);
                        }
                    });
                    managerslist.add(manager, cmanagerslist);
                    cmanagerslist.gridy++;

                }
                cmanagerslist.gridy++;
                cmanagerslist.weightx = 1;
                cmanagerslist.weighty = 1;
                JLabel temp = new JLabel();
                managerslist.add(temp, cmanagerslist);

                managers.addTab("Manageri", managerslist);
                managers.addTab("Cereri", cereri);
                managers.addTab("Detalii cerere", infocerere);
                PanelMan2.setRightComponent(managers);
                setContentPane(ManagerPage);
                setVisible(true);
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Info.removeAll();
                setContentPane(ProfilePage);
                setVisible(true);
            }
        });


        BACKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("Introduceti numele utilizatorului");
                setContentPane(HomePage);
                setVisible(true);
                pack();
            }
        });
        textField1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                textField1.setText("");
            }
        });
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application app = Application.getInstance();
                User user = app.getUser(textField1.getText());
                if (user != null) {
                    Info.removeAll();
                    Info.setLayout(new GridBagLayout());
                    ScrollInfo.setPreferredSize(Info.getPreferredSize());
                    GridBagConstraints constraints = new GridBagConstraints();
                    constraints.gridx = 0;
                    constraints.gridy = 0;
                    constraints.gridheight = 1;
                    constraints.gridwidth = 1;
                    constraints.weightx = 0;
                    constraints.weighty = 0;
                    constraints.fill = GridBagConstraints.HORIZONTAL;
                    constraints.anchor = GridBagConstraints.WEST;
                    JLabel datepersonale = new JLabel("Date personale");
                    datepersonale.setFont(f);
                    datepersonale.setBorder(border1);
                    datepersonale.setHorizontalAlignment(JLabel.CENTER);
                    Info.add(datepersonale, constraints);
                    constraints.gridy++;
                    String[][] data = {{"Nume", user.cv.datepersonale.getNume() + " " + user.cv.datepersonale.getPrenume()},
                            {"Sex", user.cv.datepersonale.getSex()},
                            {"Data de nastere", user.cv.datepersonale.getDatanastere()},
                            {"Telefon", user.cv.datepersonale.getTelefon()},
                            {"Email", user.cv.datepersonale.getEmail()}};
                    String[] column = new String[]{"", ""};
                    JTable jTable = new JTable(data, column);
                    jTable.setSize(new Dimension(Info.getWidth(), 50));
                    jTable.setFont(f);
                    jTable.setRowHeight(30);
                    jTable.setEnabled(false);
                    Info.add(jTable, constraints);
                    constraints.gridy++;
                    JLabel educatie = new JLabel("Educatie");
                    educatie.setFont(f);
                    educatie.setBorder(border2);
                    educatie.setHorizontalAlignment(JLabel.CENTER);
                    Info.add(educatie, constraints);
                    for (Education education : user.cv.educatie) {
                        String[][] edu = {{"Numele institutiei", education.numeleinstitutiei},
                                {"Nivel", education.nivel},
                                {"Medie finalizare", String.valueOf(education.mediafinalizare)},
                                {"Data inceput", String.valueOf(education.datainceput)},
                                {"Data sfarsit", String.valueOf(education.datasfarsit)}};
                        JTable ed = new JTable(edu, column);
                        constraints.gridy++;
                        ed.setSize(new Dimension(Info.getWidth(), 50));
                        ed.setFont(f);
                        ed.setRowHeight(30);
                        ed.setEnabled(false);
                        Info.add(ed, constraints);
                    }
                    constraints.gridy++;
                    JLabel experienta = new JLabel("Experienta");
                    experienta.setFont(f);
                    experienta.setBorder(border3);
                    experienta.setHorizontalAlignment(JLabel.CENTER);
                    Info.add(experienta, constraints);
                    for (Experience exp : user.cv.experienta) {
                        String[][] ex = {{"Pozitie", exp.pozitie},
                                {"companie", exp.companie},
                                {"Data inceput", String.valueOf(exp.datainceput)},
                                {"Data sfarsit", String.valueOf(exp.datasfarsit)}};
                        JTable xp = new JTable(ex, column);
                        constraints.gridy++;
                        xp.setSize(new Dimension(Info.getWidth(), 50));
                        xp.setFont(f);
                        xp.setRowHeight(30);
                        xp.setEnabled(false);
                        Info.add(xp, constraints);
                    }
                    constraints.weighty = 1;
                    constraints.weightx = 1;
                    constraints.gridy++;
                    JLabel temp = new JLabel("");
                    Info.add(temp, constraints);
                    ProfilePage.setVisible(true);
                    pack();
                } else {
                    Employee employee = app.getEmployee(textField1.getText());
                    if (employee != null) {
                        Info.removeAll();
                        Info.setLayout(new GridBagLayout());
                        ScrollInfo.setPreferredSize(Info.getPreferredSize());
                        GridBagConstraints constraints = new GridBagConstraints();
                        constraints.gridx = 0;
                        constraints.gridy = 0;
                        constraints.gridheight = 1;
                        constraints.gridwidth = 1;
                        constraints.weightx = 0;
                        constraints.weighty = 0;
                        constraints.fill = GridBagConstraints.HORIZONTAL;
                        constraints.anchor = GridBagConstraints.WEST;
                        JLabel datepersonale = new JLabel("Date personale");
                        datepersonale.setFont(f);
                        datepersonale.setBorder(border1);
                        datepersonale.setHorizontalAlignment(JLabel.CENTER);
                        Info.add(datepersonale, constraints);
                        constraints.gridy++;
                        String[][] data = {{"Nume", employee.cv.datepersonale.getNume() + " " + employee.cv.datepersonale.getPrenume()},
                                {"Sex", employee.cv.datepersonale.getSex()},
                                {"Data de nastere", employee.cv.datepersonale.getDatanastere()},
                                {"Telefon", employee.cv.datepersonale.getTelefon()},
                                {"Email", employee.cv.datepersonale.getEmail()}};
                        String[] column = new String[]{"", ""};
                        JTable jTable = new JTable(data, column);
                        jTable.setSize(new Dimension(Info.getWidth(), 50));
                        jTable.setFont(f);
                        jTable.setRowHeight(30);
                        jTable.setEnabled(false);
                        Info.add(jTable, constraints);
                        constraints.gridy++;
                        JLabel educatie = new JLabel("Educatie");
                        educatie.setFont(f);
                        educatie.setBorder(border2);
                        educatie.setHorizontalAlignment(JLabel.CENTER);
                        Info.add(educatie, constraints);
                        for (Education education : employee.cv.educatie) {
                            String[][] edu = {{"Numele institutiei", education.numeleinstitutiei},
                                    {"Nivel", education.nivel},
                                    {"Medie finalizare", String.valueOf(education.mediafinalizare)},
                                    {"Data inceput", String.valueOf(education.datainceput)},
                                    {"Data sfarsit", String.valueOf(education.datasfarsit)}};
                            JTable ed = new JTable(edu, column);
                            constraints.gridy++;
                            ed.setSize(new Dimension(Info.getWidth(), 50));
                            ed.setFont(f);
                            ed.setRowHeight(30);
                            ed.setEnabled(false);
                            Info.add(ed, constraints);
                        }
                        constraints.gridy++;
                        JLabel experienta = new JLabel("Experienta");
                        experienta.setFont(f);
                        experienta.setBorder(border3);
                        experienta.setHorizontalAlignment(JLabel.CENTER);
                        Info.add(experienta, constraints);
                        for (Experience exp : employee.cv.experienta) {
                            String[][] ex = {{"Pozitie", exp.pozitie},
                                    {"companie", exp.companie},
                                    {"Data inceput", String.valueOf(exp.datainceput)},
                                    {"Data sfarsit", String.valueOf(exp.datasfarsit)}};
                            JTable xp = new JTable(ex, column);
                            constraints.gridy++;
                            xp.setSize(new Dimension(Info.getWidth(), 50));
                            xp.setFont(f);
                            xp.setRowHeight(30);
                            xp.setEnabled(false);
                            Info.add(xp, constraints);
                        }
                        constraints.weighty = 1;
                        constraints.weightx = 1;
                        constraints.gridy++;
                        JLabel temp = new JLabel("");
                        Info.add(temp, constraints);
                        ProfilePage.setVisible(true);
                        pack();
                    } else {
                        Recruiter r = app.getRecruiter(textField1.getText());
                        if (r != null) {
                            Info.removeAll();
                            Info.setLayout(new GridBagLayout());
                            ScrollInfo.setPreferredSize(Info.getPreferredSize());
                            GridBagConstraints constraints = new GridBagConstraints();
                            constraints.gridx = 0;
                            constraints.gridy = 0;
                            constraints.gridheight = 1;
                            constraints.gridwidth = 1;
                            constraints.weightx = 0;
                            constraints.weighty = 0;
                            constraints.fill = GridBagConstraints.HORIZONTAL;
                            constraints.anchor = GridBagConstraints.WEST;
                            JLabel datepersonale = new JLabel("Date personale");
                            datepersonale.setFont(f);
                            datepersonale.setBorder(border1);
                            datepersonale.setHorizontalAlignment(JLabel.CENTER);
                            Info.add(datepersonale, constraints);
                            constraints.gridy++;
                            String[][] data = {{"Nume", r.cv.datepersonale.getNume() + " " + r.cv.datepersonale.getPrenume()},
                                    {"Sex", r.cv.datepersonale.getSex()},
                                    {"Data de nastere", r.cv.datepersonale.getDatanastere()},
                                    {"Telefon", r.cv.datepersonale.getTelefon()},
                                    {"Email", r.cv.datepersonale.getEmail()}};
                            String[] column = new String[]{"", ""};
                            JTable jTable = new JTable(data, column);
                            jTable.setSize(new Dimension(Info.getWidth(), 50));
                            jTable.setFont(f);
                            jTable.setRowHeight(30);
                            jTable.setEnabled(false);
                            Info.add(jTable, constraints);
                            constraints.gridy++;
                            JLabel educatie = new JLabel("Educatie");
                            educatie.setFont(f);
                            educatie.setBorder(border2);
                            educatie.setHorizontalAlignment(JLabel.CENTER);
                            Info.add(educatie, constraints);
                            for (Education education : r.cv.educatie) {
                                String[][] edu = {{"Numele institutiei", education.numeleinstitutiei},
                                        {"Nivel", education.nivel},
                                        {"Medie finalizare", String.valueOf(education.mediafinalizare)},
                                        {"Data inceput", String.valueOf(education.datainceput)},
                                        {"Data sfarsit", String.valueOf(education.datasfarsit)}};
                                JTable ed = new JTable(edu, column);
                                constraints.gridy++;
                                ed.setSize(new Dimension(Info.getWidth(), 50));
                                ed.setFont(f);
                                ed.setRowHeight(30);
                                ed.setEnabled(false);
                                Info.add(ed, constraints);
                            }
                            constraints.gridy++;
                            JLabel experienta = new JLabel("Experienta");
                            experienta.setFont(f);
                            experienta.setBorder(border3);
                            experienta.setHorizontalAlignment(JLabel.CENTER);
                            Info.add(experienta, constraints);
                            for (Experience exp : r.cv.experienta) {
                                String[][] ex = {{"Pozitie", exp.pozitie},
                                        {"companie", exp.companie},
                                        {"Data inceput", String.valueOf(exp.datainceput)},
                                        {"Data sfarsit", String.valueOf(exp.datasfarsit)}};
                                JTable xp = new JTable(ex, column);
                                constraints.gridy++;
                                xp.setSize(new Dimension(Info.getWidth(), 50));
                                xp.setFont(f);
                                xp.setRowHeight(30);
                                xp.setEnabled(false);
                                Info.add(xp, constraints);
                            }
                            constraints.weighty = 1;
                            constraints.weightx = 1;
                            constraints.gridy++;
                            JLabel temp = new JLabel("");
                            Info.add(temp, constraints);
                            ProfilePage.setVisible(true);
                            pack();
                        } else {
                            Manager m = app.getManager(textField1.getText());
                            if (m != null) {
                                Info.removeAll();
                                Info.setLayout(new GridBagLayout());
                                ScrollInfo.setPreferredSize(Info.getPreferredSize());
                                GridBagConstraints constraints = new GridBagConstraints();
                                constraints.gridx = 0;
                                constraints.gridy = 0;
                                constraints.gridheight = 1;
                                constraints.gridwidth = 1;
                                constraints.weightx = 0;
                                constraints.weighty = 0;
                                constraints.fill = GridBagConstraints.HORIZONTAL;
                                constraints.anchor = GridBagConstraints.WEST;
                                JLabel datepersonale = new JLabel("Date personale");
                                datepersonale.setFont(f);
                                datepersonale.setBorder(border1);
                                datepersonale.setHorizontalAlignment(JLabel.CENTER);
                                Info.add(datepersonale, constraints);
                                constraints.gridy++;
                                String[][] data = {{"Nume", m.cv.datepersonale.getNume() + " " + m.cv.datepersonale.getPrenume()},
                                        {"Sex", m.cv.datepersonale.getSex()},
                                        {"Data de nastere", m.cv.datepersonale.getDatanastere()},
                                        {"Telefon", m.cv.datepersonale.getTelefon()},
                                        {"Email", m.cv.datepersonale.getEmail()}};
                                String[] column = new String[]{"", ""};
                                JTable jTable = new JTable(data, column);
                                jTable.setSize(new Dimension(Info.getWidth(), 50));
                                jTable.setFont(f);
                                jTable.setRowHeight(30);
                                jTable.setEnabled(false);
                                Info.add(jTable, constraints);
                                constraints.gridy++;
                                JLabel educatie = new JLabel("Educatie");
                                educatie.setFont(f);
                                educatie.setBorder(border2);
                                educatie.setHorizontalAlignment(JLabel.CENTER);
                                Info.add(educatie, constraints);
                                for (Education education : m.cv.educatie) {
                                    String[][] edu = {{"Numele institutiei", education.numeleinstitutiei},
                                            {"Nivel", education.nivel},
                                            {"Medie finalizare", String.valueOf(education.mediafinalizare)},
                                            {"Data inceput", String.valueOf(education.datainceput)},
                                            {"Data sfarsit", String.valueOf(education.datasfarsit)}};
                                    JTable ed = new JTable(edu, column);
                                    constraints.gridy++;
                                    ed.setSize(new Dimension(Info.getWidth(), 50));
                                    ed.setFont(f);
                                    ed.setRowHeight(30);
                                    ed.setEnabled(false);
                                    Info.add(ed, constraints);
                                }
                                constraints.gridy++;
                                JLabel experienta = new JLabel("Experienta");
                                experienta.setFont(f);
                                experienta.setBorder(border3);
                                experienta.setHorizontalAlignment(JLabel.CENTER);
                                Info.add(experienta, constraints);
                                for (Experience exp : m.cv.experienta) {
                                    String[][] ex = {{"Pozitie", exp.pozitie},
                                            {"companie", exp.companie},
                                            {"Data inceput", String.valueOf(exp.datainceput)},
                                            {"Data sfarsit", String.valueOf(exp.datasfarsit)}};
                                    JTable xp = new JTable(ex, column);
                                    constraints.gridy++;
                                    xp.setSize(new Dimension(Info.getWidth(), 50));
                                    xp.setFont(f);
                                    xp.setRowHeight(30);
                                    xp.setEnabled(false);
                                    Info.add(xp, constraints);
                                }
                                constraints.weighty = 1;
                                constraints.weightx = 1;
                                constraints.gridy++;
                                JLabel temp = new JLabel("");
                                Info.add(temp, constraints);
                                ProfilePage.setVisible(true);
                                pack();
                            } else {
                                System.out.println("User-ul nu a fost gasit");
                            }
                        }

                    }
                }

            }
        });
        JButton backman = new JButton("BACK");
        backman.setFont(f);
        PanelMan1.setLayout(new GridBagLayout());
        GridBagConstraints manager = new GridBagConstraints();
        manager.gridy = 0;
        manager.gridx = 0;
        manager.anchor = GridBagConstraints.NORTH;
        manager.fill = GridBagConstraints.HORIZONTAL;
        manager.weightx = 0;
        manager.weighty = 0;
        PanelMan1.add(backman, manager);
        //Buton de BACK Manager Page
        backman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(HomePage);
                if (topmanager != null)
                    topmanager.removeAll();
                setVisible(true);
            }
        });

        JLabel tempman = new JLabel();
        manager.gridy++;
        manager.weightx = 1;
        manager.weighty = 1;
        PanelMan1.add(tempman, manager);
        setVisible(true);
        pack();
        BackHomePage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(Login);
                button1.setVisible(true);
                button2.setVisible(true);
                button3.setVisible(true);
                username.setText("E-mail");
                password.setText("");
                setVisible(true);
            }
        });
    }
}
