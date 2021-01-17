package com.company;

class DepartmentFactory {
    public enum DepartmentType {
        IT,
        Management,
        Marketing,
        Finance
    }

    public Department createDepartment(DepartmentType departmentType) {
        return switch (departmentType) {
            case IT -> new IT();
            case Management -> new Management();
            case Marketing -> new Marketing();
            case Finance -> new Finance();
        };
    }

}
