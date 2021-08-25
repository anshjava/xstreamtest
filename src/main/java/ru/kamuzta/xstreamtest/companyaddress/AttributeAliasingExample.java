package ru.kamuzta.xstreamtest.companyaddress;

import com.thoughtworks.xstream.XStream;

public class AttributeAliasingExample {

    public static void main(String[] args) {

        Company company = DataDAO.createCompany();

        XStream xstream = new XStream();

        // Class aliasing.
        xstream.alias("company", Company.class);


        // useAttributeFor(Class definedIn, String fieldName).
        xstream.useAttributeFor(Company.class, "id");

        xstream.useAttributeFor(Company.class, "name");
        xstream.aliasAttribute("companyName", "name");

        // JAVA OBJECT --> XML
        String xml = xstream.toXML(company);

        System.out.println(xml);

    }

}
