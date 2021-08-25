package ru.kamuzta.xstreamtest.deptemployees;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("emp")
public class Employee {

    @XStreamAlias("no")
    @XStreamAsAttribute
    private int empNo;
    private String empName;

    private float salary;

    // Ignore this field.
    @XStreamOmitField
    private float bonus;

    public Employee(int empNo, String empName, float salary) {
        this.empNo = empNo;
        this.empName = empName;
        this.salary = salary;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

}
