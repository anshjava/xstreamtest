package ru.kamuzta.xstreamtest.companyaddress;

import com.thoughtworks.xstream.XStream;

public class FieldAliasingExample {

    public static void main(String[] args) {

        Company company = DataDAO.createCompany();

        XStream xstream = new XStream();

        // Class aliasing.
        xstream.alias("company", Company.class);

        // Field aliasing.
        // aliasField(String alias, Class definedIn, String fieldName).
        xstream.aliasField("companyName", Company.class, "name");
        xstream.aliasField("companyId", Company.class, "id");

        xstream.aliasField("addressStreet", Address.class, "street");
        xstream.aliasField("addressCity", Address.class, "city");

        // JAVA OBJECT --> XML
        String xml = xstream.toXML(company);

        System.out.println(xml);
    }

}
