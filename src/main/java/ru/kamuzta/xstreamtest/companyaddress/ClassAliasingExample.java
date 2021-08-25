package ru.kamuzta.xstreamtest.companyaddress;

import com.thoughtworks.xstream.XStream;

public class ClassAliasingExample {

    public static void main(String[] args) {

        Company company = DataDAO.createCompany();

        XStream xs = new XStream();

        xs.alias("company", Company.class);

        // JAVA OBJECT --> XML
        String xml = xs.toXML(company);

        System.out.println(xml);
    }

}
