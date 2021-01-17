package com.company;

import java.util.*;

abstract class Consumer {
    ArrayList<Consumer> cunoscuti;
    Resume cv;

    public Consumer(Resume cv) {
        this.cv = cv;
        cunoscuti=new ArrayList<>();
    }

    static class Resume {
        Information datepersonale;
        TreeSet<Education> educatie;
        TreeSet<Experience> experienta;

        private Resume(ResumeBuilder builder) throws ResumeIncompleteException {
            if (builder.experienta != null && builder.datepersonale != null) {
                this.datepersonale = builder.datepersonale;
                this.educatie = builder.educatie;
                this.experienta = builder.experienta;
            } else {
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
        ArrayList<Consumer> visited = new ArrayList<>();
        Queue<Consumer> queue = new LinkedList<>();
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

        if (degrees.containsKey(consumer))
            return degrees.get(consumer);
        else
            return 0;
    }

    public void remove(Consumer consumer) {
        cunoscuti.remove(consumer);
    }

    public Integer getGraduationYear() {
        for (Education e : cv.educatie) {
            if (e.nivel.compareTo("college") == 0) {
                if (e.datasfarsit != null)
                    return e.datasfarsit.getYear();
            }
        }
        return null;
    }

    public Double meanGPA() {
        Iterator<Education> it = cv.educatie.iterator();
        double k = 0;
        double s = 0;
        while (it.hasNext()) {
            k++;
            s += it.next().mediafinalizare;
        }
        return s / k;
    }

    public void setCunoscuti(ArrayList<Consumer> cunoscuti) {
        this.cunoscuti = cunoscuti;
    }

}
