package ru.kamuzta.xstreamtest.soma.marshalling;

import com.thoughtworks.xstream.XStream;
import ru.kamuzta.xstreamtest.soma.entities.*;

public class Marshaller {
    public static XStream getMarshaller() {
        XStream xs = new XStream();

        xs.alias("paper", Paper.class);
        xs.alias("rollType", RollType.class);
        xs.alias("state", State.class);
        xs.alias("status", Status.class);


        xs.alias("manager", Manager.class);
        xs.useAttributeFor(Manager.class, "id");
        xs.useAttributeFor(Manager.class, "name");
        xs.useAttributeFor(Manager.class, "state");
        xs.omitField(Manager.class, "rolls");


        xs.alias("machine", Machine.class);
        xs.useAttributeFor(Machine.class, "id");
        xs.useAttributeFor(Machine.class, "sn");
        xs.useAttributeFor(Machine.class, "width");
        xs.useAttributeFor(Machine.class, "paper");
        xs.useAttributeFor(Machine.class, "state");
        xs.useAttributeFor(Machine.class, "fullCapacity");
        xs.useAttributeFor(Machine.class, "rollsInQueue");
        xs.useAttributeFor(Machine.class, "freeCapacity");
        xs.omitField(Machine.class, "manager");
        xs.registerLocalConverter(Machine.class, "freeCapacity", new FreeCapacityConverter());

        xs.alias("order", Order.class);
        xs.useAttributeFor(Order.class, "id");
        xs.useAttributeFor(Order.class, "client");
        xs.useAttributeFor(Order.class, "date");
        xs.useAttributeFor(Order.class, "status");
        xs.omitField(Order.class, "manager");
        xs.omitField(Order.class, "rolls");

        xs.alias("roll", Roll.class);
        xs.useAttributeFor(Roll.class, "id");
        xs.useAttributeFor(Roll.class, "type");
        xs.useAttributeFor(Roll.class, "paper");
        xs.useAttributeFor(Roll.class, "status");
        xs.useAttributeFor(Roll.class, "width");
        xs.useAttributeFor(Roll.class, "core");
        xs.useAttributeFor(Roll.class, "count");
        xs.useAttributeFor(Roll.class, "length");
        xs.useAttributeFor(Roll.class, "diameter");
        xs.useAttributeFor(Roll.class, "weight");
        xs.omitField(Roll.class, "value");
        xs.omitField(Roll.class, "order");
        xs.omitField(Roll.class, "machine");

        return xs;
    }
}
