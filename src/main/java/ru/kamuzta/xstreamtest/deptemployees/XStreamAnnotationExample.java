package ru.kamuzta.xstreamtest.deptemployees;

import com.thoughtworks.xstream.XStream;

public class XStreamAnnotationExample {

    public static void main(String[] args) {
        Department dept = DataDAO.createDepartment();

        XStream xstream = new XStream();

        // Using annotations in class Department
        xstream.processAnnotations(Department.class);

        // Using annotations in class Employee
        xstream.processAnnotations(Employee.class);

        String xml = xstream.toXML(dept);

        System.out.println(xml);
    }



}
