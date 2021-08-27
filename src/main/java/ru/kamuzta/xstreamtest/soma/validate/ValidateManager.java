package ru.kamuzta.xstreamtest.soma.validate;

import ru.kamuzta.xstreamtest.soma.Utils;
import ru.kamuzta.xstreamtest.soma.entities.Machine;
import ru.kamuzta.xstreamtest.soma.entities.Manager;
import ru.kamuzta.xstreamtest.soma.entities.State;

import java.util.Arrays;

public class ValidateManager {

    public static void main(String[] args) {
        Manager manager = Utils.getRandomManager();
        manager.getMachines().set(0,null);
        //validateManager(manager);
        newValidate(manager);

    }

    private static void validateManager(Manager manager) {
        Assert.notNull(manager);
        Assert.notNull(manager.getId());
        Assert.notNull(manager.getName());
        Assert.isTrue(Arrays.asList(State.values()).contains(manager.getState()));
        Assert.notNull(manager.getMachines());
        manager.getMachines().forEach(Assert::notNull);
    }

    private static void newValidate(Manager manager) {
        //new Validate<>(manager).of(m->m.getName()).assertNotNull();
        new Validate<>(manager).of(m->m.getMachines().get(0)).assertNotNull();


    }
}

