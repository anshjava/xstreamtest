package ru.kamuzta.xstreamtest.soma.validate;

import ru.kamuzta.xstreamtest.soma.Utils;
import ru.kamuzta.xstreamtest.soma.entities.Machine;
import ru.kamuzta.xstreamtest.soma.entities.Manager;
import ru.kamuzta.xstreamtest.soma.entities.State;

import java.util.Arrays;

public class ValidateManager {

    public static void main(String[] args) {
        Manager manager = new Manager("Андрей");
        new MyValidate<>(manager, "Проверка менеджера").assertNotNull();
        new MyValidate<>(manager.getName(), "Проверка имени менеджера").assertNotNull();

    }


    private static void validateManager(Manager manager) {
        Assert.notNull(manager);
        Assert.notNull(manager.getId());
        Assert.notNull(manager.getName());
        Assert.isTrue(Arrays.asList(State.values()).contains(manager.getState()));
        Assert.notNull(manager.getMachines());
        manager.getMachines().forEach(Assert::notNull);
    }


}

