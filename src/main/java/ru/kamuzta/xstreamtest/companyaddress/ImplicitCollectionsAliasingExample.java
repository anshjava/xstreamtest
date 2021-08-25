package ru.kamuzta.xstreamtest.companyaddress;

import com.thoughtworks.xstream.XStream;

public class ImplicitCollectionsAliasingExample {

    public static void main(String[] args) {

        Company company = DataDAO.createCompany();

        XStream xstream = new XStream();

        // Class aliasing.
        xstream.alias("company", Company.class);


        // addImplicitArray(Class ownerType, String fieldName).
        xstream.addImplicitArray(Company.class, "websites", "website");

        // JAVA OBJECT --> XML
        String xml = xstream.toXML(company);

        System.out.println(xml);

    }

}
