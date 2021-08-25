package ru.kamuzta.xstreamtest.deptemployees;

import java.util.ArrayList;
import java.util.List;

public class DataDAO {

    public static Department createDepartment() {
        Department dept = new Department();
        dept.setDeptNo(10);
        dept.setDeptName("ACCOUNTING");

        Employee king = new Employee(7839, "KING", 5000f);
        Employee clark = new Employee(7839, "CLARK", 2450f);
        Employee miller = new Employee(7839, "MILLER", 1300f);

        List<Employee> list = new ArrayList<Employee>();
        list.add(king);
        list.add(clark);
        list.add(miller);
        dept.setEmployees(list);
        return dept;
    }

}
