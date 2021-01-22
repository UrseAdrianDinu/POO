package com.company;

import java.util.*;

abstract class Consumer {
    ArrayList<Consumer> cunoscuti;
    Resume cv;

    public Consumer(Resume cv) {
        this.cv = cv;
        cunoscuti = new ArrayList<>();
    }

    static class Resume {
        Information datepersonale; //required
        TreeSet<Education> educatie; //required
        TreeSet<Experience> experienta; //optional

        private Resume(ResumeBuilder builder) throws ResumeIncompleteException {
            if (builder.experienta != null && builder.datepersonale != null) {
                this.datepersonale = builder.datepersonale;
                this.educatie = builder.educatie;
                this.experienta = builder.experienta;
            } else {
                //Daca builder-ul nu contine Information sau nu contine Experince
                //atunci se arunca exceptia ResumeIncompleteException
                throw new ResumeIncompleteException("Incomplete Resume");
            }
        }

        public static class ResumeBuilder {
            private Information datepersonale;
            private TreeSet<Education> educatie;
            private TreeSet<Experience> experienta;

            public ResumeBuilder(Information datepersonale, TreeSet<Education> educatie) {
                this.datepersonale = datepersonale;
                this.educatie = educatie;
            }

            public ResumeBuilder experienta(TreeSet<Experience> experienta) {
                this.experienta = experienta;
                return this;
            }

            public Resume build() throws ResumeIncompleteException {
                return new Resume(this);
            }
        }

        public String toString() {
            return datepersonale + "\n" + educatie + "\n" + experienta;
        }
    }

    public void add(Education education) {
        cv.educatie.add(education);
    }

    public void add(Experience experience) {
        cv.experienta.add(experience);
    }

    public void add(Consumer consumer) {
        cunoscuti.add(consumer);
    }

    public int getDegreeInFriendship(Consumer consumer) {
        //Lista pentru a marca consumerii vizitati
        ArrayList<Consumer> visited = new ArrayList<>();
        Queue<Consumer> queue = new LinkedList<>();
        //Dictionar care retine gradul de prietenie cu toti prietenii consumer-ului curent
        // [Consumer = grad]
        HashMap<Consumer, Integer> degrees = new HashMap<>();
        visited.add(this);
        queue.add(this);
        degrees.put(this, 0);
        while (queue.size() != 0) {
            Consumer current = queue.poll();
            for (Consumer c : current.cunoscuti) {
                if (!visited.contains(c)) {
                    visited.add(c);
                    queue.add(c);
                    degrees.put(c, degrees.get(current) + 1);
                }
            }
        }
        //Daca dictionarul de grade contine consumer-ul dat ca paramteru
        //atunci returnez valoarea lui din dictionar
        if (degrees.containsKey(consumer))
            return degrees.get(consumer);
        else
            return 0;
    }

    public void remove(Consumer consumer) {
        cunoscuti.remove(consumer);
    }

    public Integer getGraduationYear() {
        //Parcurg colectia education
        for (Education e : cv.educatie) {
            //Daca nivelul este college si data de sfarsit nu este nula
            //atunci o returnez
            if (e.nivel.compareTo("college") == 0) {
                if (e.datasfarsit != null)
                    return e.datasfarsit.getYear();
            }
        }
        return null;
    }

    public Double meanGPA() {

        double k = 0;
        double s = 0;
        //Am parcurs colectia educatie si am facut media
        for (Education e : cv.educatie) {
            k++;
            s += e.mediafinalizare;
        }
        return s / k;
    }

    public void setCunoscuti(ArrayList<Consumer> cunoscuti) {
        this.cunoscuti = cunoscuti;
    }

}
