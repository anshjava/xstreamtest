package ru.kamuzta.xstreamtest.soma;

import com.thoughtworks.xstream.XStream;
import ru.kamuzta.xstreamtest.soma.entities.Manager;


public class Soma {
    public static void main(String[] args) {
        Manager manager = Utils.getRandomManager();
        manager.loadOrders(Utils.getRandomOrderSet(4));
        manager.distributeRollsToMachines();

        XStream xs = Marshaller.getMarshaller();

        String xml = xs.toXML(manager);
        System.out.println(xml);
        Utils.writeStringToFile(xml,"outputXml.xml");
    }
}
