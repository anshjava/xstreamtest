package ru.kamuzta.xstreamtest.companyaddress;

import com.thoughtworks.xstream.XStream;

public class JavaObject2Xml {

    public static void main(String[] args) {

        Company company = DataDAO.createCompany();

        XStream xs = new XStream();

        // JAVA OBJECT --> XML
        String xml = xs.toXML(company);

        System.out.println(xml);
    }


}
