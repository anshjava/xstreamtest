package ru.kamuzta.xstreamtest.soma.marshalling;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import ru.kamuzta.xstreamtest.soma.entities.Machine;

public class FreeCapacityConverter extends AbstractSingleValueConverter {
    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(Integer.class);
    }

    @Override
    public Object fromString(String s) {
        return Integer.parseInt(s);
    }


    @Override
    public String toString(Object obj) {
        return String.valueOf(7777777);
    }
}
