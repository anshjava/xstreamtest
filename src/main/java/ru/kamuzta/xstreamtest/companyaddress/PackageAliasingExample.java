package ru.kamuzta.xstreamtest.companyaddress;

import com.thoughtworks.xstream.XStream;

public class PackageAliasingExample {

    public static void main(String[] args) {

        Company company = DataDAO.createCompany();

        XStream xstream = new XStream();

        String packgeName=  Address.class.getPackage().getName();

        // aliasPackage(String name, String pkgName).
        xstream.aliasPackage("com.newcompany", packgeName);



        // JAVA OBJECT --> XML
        String xml = xstream.toXML(company);

        System.out.println(xml);

    }

}
