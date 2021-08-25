package ru.kamuzta.xstreamtest.soma;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.security.SecureRandom;
import java.util.*;

public class Utils {
    public static Paper getRandomPaper() {
        return Paper.values()[(int) (Math.random() * Paper.values().length)];
    }
    public static RollType getRandomRollType() {
        return RollType.values()[(int) (Math.random() * RollType.values().length)];
    }
    public static State getRandomState() {
        return State.values()[(int) (Math.random() * State.values().length)];
    }
    public static Status getRandomStatus() {
        return Status.values()[(int) (Math.random() * Status.values().length)];
    }

    public static List<Roll> getRandomRollsList() {
        int pcs = (int) (3+ Math.random() * 7);
        System.out.println("Создание списка случайных " + pcs +" роликов...");
        List<Roll> rolls = new ArrayList<>();
        for (int i = 0; i < pcs; i++) {
            rolls.add(getRandomRoll());
        }
        Collections.sort(rolls);
        return rolls;
    }
    public static Roll getRandomRoll() {
        RollType rollType = getRandomRollType();
        Paper paper = getRandomPaper();
        float width = Math.random() > 0.5d ? 57.0f : 80.0f;
        float core = (width == 57.0f) ? 12.0f : ((Math.random() > 0.5d) ? 18.0f : 26.0f);
        int count = (int) (Math.random() * 10000);
        float value;

        switch (rollType) {
            case LENGTH:
                value = width == 57.0f ? 10.0f + (float) (Math.random() * 80) : 30.0f + (float) (Math.random() * 200);
                break;
            case DIAMETER:
                value = width == 57.0f ? core + 10.0f + (float) (Math.random() * 80) : core + 10.0f + (float) (Math.random() * 200);
                break;
            default:
                System.out.println("Ошибка в определении типа ролика, выбран тип ролика по умолчанию и value - длина.");
                value = width == 57.0f ? 10.0f + (float) (Math.random() * 80) : 30.0f + (float) (Math.random() * 200);
        }

        return new Roll(rollType, paper, width, core, count, value);
    }

    public static SortedSet<Order> getRandomOrderSet(int count) {
        System.out.println("Создание массива случайных " + count +" заказов...");
        SortedSet<Order> orders = new TreeSet<>();
        for (int i = 0; i < count; i++) {
            orders.add(getRandomOrder());
        }
        System.out.println("в рандомном сэте всего заказов: " + orders.size());
        return orders;
    }
    public static Order getRandomOrder() {
        String name;
        switch ((int) (Math.random() * 4)) {
            case 0 :
                name = "Komus";
                break;
            case 1 :
                name = "SamsonOpt";
                break;
            case 2 :
                name = "deVente";
                break;
            case 3 :
                name = "ReliefOpt";
                break;
            default:
                System.out.println("Ошибка в определении клиента. Выбран клиент по умолчанию.");
                name = "Komus";
        }
        Order order = new Order(name);
        order.setRolls(getRandomRollsList());
        for (Roll roll : order.getRolls()) {
            roll.setOrder(order);
        }

        return order;
    }

    public static void randomizeStatus(Order order) {
        System.out.println("Меняем случайным образом статусы роликов в заказе " + order.getId() + "...");
        for (Roll roll : order.getRolls()) {
            if (Math.random() > 0.95d) {
                roll.setStatus(Status.COMPLETED);
            } else if (Math.random() > 0.80d) {
                roll.setStatus(Status.INPROGRESS);
            }
        }
        order.updateStatus();
    }

    public static List<Machine> getRandomMachinesList(int count) {
        System.out.println("Создание массива случайных " + count +" станков...");
        List<Machine> machines = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            machines.add(getRandomMachine());
        }
        Collections.sort(machines);
        return machines;
    }
    public static Machine getRandomMachine() {
        float width = Math.random() > 0.5d ? 57.0f : 80.0f;
        Paper paper = getRandomPaper();
        State state = State.OFF;
        int fullCapacity = (int) (15000.0d + Math.random() * 10000.0d);
        int rollsInQueue = 0;
        String sn = getRandomSn();
        return new Machine(sn, width, paper, state, fullCapacity, rollsInQueue);
    }

    private static String getRandomSn() {
        String symbols = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++)
            sb.append(symbols.charAt(rnd.nextInt(symbols.length())));
        return sb.toString();
    }

    public static String getRandomName() {
        String symbols1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String symbols2 = "abcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        int nameLength = 7 + rnd.nextInt(3);
        StringBuilder sb = new StringBuilder(nameLength);
        sb.append(symbols1.charAt(rnd.nextInt(symbols1.length())));
        for (int i = 1; i < nameLength; i++)
            sb.append(symbols2.charAt(rnd.nextInt(symbols2.length())));
        return sb.toString();
    }

    public static int getRandomId() {
        SecureRandom rnd = new SecureRandom();
        return rnd.nextInt(100);
    }

    public static Manager getRandomManager() {
        Manager manager = new Manager(getRandomName());
        manager.setMachines(getRandomMachinesList(5));
        return manager;
    }

}
