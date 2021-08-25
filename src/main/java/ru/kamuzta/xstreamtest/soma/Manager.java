package ru.kamuzta.xstreamtest.soma;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Manager  implements Comparable<Manager> {
    private static AtomicInteger staticId = new AtomicInteger();
    private int id;
    private String name;
    private State state;
    private List<Machine> machines = new ArrayList<>(); // Хранит станки в порядке убывания FreeCapacity (обновление порядка вручную)
    private SortedSet<Order> orders = new TreeSet<>(); //Хранит заказы в порядке от старого к новому
    private Queue<Roll> rolls = new LinkedList<>(); //Хранит ролики в очереди в порядке добавления

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    public List<Machine> getMachines() {
        return machines;
    }
    public void setMachines(List<Machine> machineList) {
        this.machines = machineList;
    }

    public SortedSet<Order> getOrders() {
        return orders;
    }
    public void setOrders(SortedSet<Order> orderSet) {
        this.orders = orderSet;
    }

    public Queue<Roll> getRolls() {
        return rolls;
    }
    public void setRolls(Queue<Roll> rolls) {
        this.rolls = rolls;
    }

    public Manager () {

    }
    public Manager(String name) {
        setName(name);
        setState(State.OFF);
        setId(staticId.incrementAndGet());
        System.out.println("МЕНЕДЖЕР СОЗДАН: " + this.getName());
    }

    private void changeState(State newState) {
        State oldState = getState();
        try {
            this.setState(newState);
            Thread.sleep(500); //включение и выключение цеха занимает 500мс
        } catch (InterruptedException e) {
            System.out.println("InterruptedException во время смены состояния цеха из " + oldState.getStateName() + " в " + newState.getStateName());
        }
        System.out.println("Состояние цеха менеджера " + this.getName() +" переведено из " + oldState.getStateName() + " в " + newState.getStateName());
    }
    public void updateState() {
        int countOfWorkingMachines = 0;
        for (Machine machine : machines) {
            countOfWorkingMachines += (machine.getState() == State.ON) ? 1 : 0;
        }
        if (getState() == State.ON && countOfWorkingMachines == 0) {
            changeState(State.OFF);
        } else if (getState() == State.OFF && countOfWorkingMachines > 0) {
            changeState(State.ON);
        }
    }

    public void loadOrders(SortedSet<Order> orders) {
        setOrders(orders);
        for (Order order : orders) {
            order.setManager(this);
        }
        loadRolls();
    }
    private void loadRolls() {
        System.out.println("ВЫГРУЖАЕМ РОЛИКИ ИЗ ЗАКАЗОВ В СТАТУСЕ NEW В ОБЩУЮ ОЧЕРЕДЬ МЕНЕДЖЕРА " + getName());
        for (Order order : orders) {
            if (order.getStatus() == Status.NEW) {
                rolls.addAll(order.getRolls());
                order.changeStatus(Status.QUEUED);
            }
        }
    }

    //логика распределения роликов по машинам. берем первый ролик, перебираем все машины с такой же шириной,
    // отдаем ролик машине, куда ролик в станет с наименьшим оставшимся фриспейсом
    public void distributeRollsToMachines() { //TODO продумать что делать с роликами если нет подходящего по ширине станка
        Collections.sort(machines);
        System.out.println("МЕНЕДЖЕР " + this.getName() + " РАСПРЕДЕЛЯЕТ РОЛИКИ ИЗ СВОЕЙ ОЧЕРЕДИ ПО СВОБОДНЫМ СТАНКАМ");
        while (!rolls.isEmpty()) {
            Roll plannedRoll = getRolls().poll();
            for (Machine machine : getMachines()) {
                if (machine.getWidth() == plannedRoll.getWidth()) {
                    plannedRoll.changeStatus(Status.QUEUED);
                    machine.loadRolls(plannedRoll);
                    break;
                }
            }
            Collections.sort(machines);
        }
        System.out.println("НЕВОЗМОЖНО РАСПРЕДЕЛИТЬ РОЛИКИ. БОЛЬШЕ НЕТ РОЛИКОВ В ОЧЕРЕДИ У МЕНЕДЖЕРА " + this.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return id == manager.id &&
                name.equals(manager.name) &&
                state == manager.state &&
                machines.equals(manager.machines) &&
                orders.equals(manager.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, state, machines, orders);
    }

    @Override
    public String toString() {
        return String.format("Manager #%d %s in state %s with %d machines",
                getId(),
                getName(),
                getState().getStateName(),
                getMachines() != null ? getMachines().size() : 0);
    }

    //упорядочивание цехов(менеджеров), сначала идут те у кого суммарный freeCapacity станков больше
    @Override //TODO реализовать определение менее загруженного цеха в %
    public int compareTo(Manager o) {

        int result = 0;
        if (this.equals(o)) {
            return result;
        } else {
            int freeCapacityThis = 0;
            int freeCapacityO = 0;


            for (Machine machine : machines) {
                freeCapacityThis += machine.getFreeCapacity();
            }
            for (Machine machine : o.machines) {
                freeCapacityO += machine.getFreeCapacity();
            }

            if ((result = freeCapacityO - freeCapacityThis) == 0) {
                if ((result = o.getId() - getId()) == 0) {
                    if ((result = getName().compareTo(o.getName())) == 0) {
                        if ((result = getState().compareTo(o.getState())) == 0) {
                            result = hashCode() - o.hashCode();
                        }
                    }
                }
            }

        }
        return result;
    }


}
