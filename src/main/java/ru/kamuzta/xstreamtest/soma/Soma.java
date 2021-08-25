package ru.kamuzta.xstreamtest.soma;

public class Soma {
    public static void main(String[] args) {
        Manager manager = Utils.getRandomManager();
        manager.loadOrders(Utils.getRandomOrderSet(10));
        manager.distributeRollsToMachines();
    }
}
