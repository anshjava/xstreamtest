package ru.kamuzta.xstreamtest.soma.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Order implements Comparable<Order> {
    private static AtomicInteger staticId = new AtomicInteger();
    private int id;
    private String client;
    private LocalDateTime date;
    private Status status;
    private Manager manager;
    private List<Roll> rolls = new ArrayList<>();

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }
    public void setClient(String client) {
        this.client = client;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Roll> getRolls() {
        return rolls;
    }
    public void setRolls(List<Roll> rollList) {
        this.rolls = rollList;
    }

    public Manager getManager() {
        return manager;
    }
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Order() {
    }
    public Order(String client) {
        setClient(client);
        setDate(LocalDateTime.now());
        setStatus(Status.NEW);
        setId(staticId.incrementAndGet());
        System.out.println("ЗАКАЗ СОЗДАН: " + this);
    }
    public void updateStatus() {
        Map<Status, Integer> statusMap = new TreeMap<>();
        for (Roll roll : this.getRolls()) {
            if (statusMap.containsKey(roll.getStatus())) {
                statusMap.put(roll.getStatus(), statusMap.get(roll.getStatus()) + 1);
            } else {
                statusMap.put(roll.getStatus(), 1);
            }
        }

        if (statusMap.size() == 1 && statusMap.containsKey(Status.NEW)) {
            this.changeStatus(Status.NEW);
        } else if (statusMap.size() == 1 && statusMap.containsKey(Status.COMPLETED)) {
            this.changeStatus(Status.COMPLETED);
        } else {
            this.changeStatus(Status.INPROGRESS);
        }
    }
    public void changeStatus(Status newStatus) {
        Status oldStatus = this.getStatus();
        if (oldStatus != newStatus) {
            try {
                this.setStatus(newStatus);
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException во время смены статуса заказа #" + this.getId() + " из " + oldStatus.getStatusName() + " в " + newStatus.getStatusName());
            }
            System.out.println("Состояние заказа #" + this.getId() + " переведено из " + oldStatus.getStatusName() + " в " + newStatus.getStatusName());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return  id == order.id &&
                client.equals(order.client) &&
                date.equals(order.date) &&
                status == order.status &&
                rolls.equals(order.rolls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, date, status, rolls);
    }

    @Override
    public String toString() {
        int totalRollsCount = 0;
        float totalRollsWeight = 0.0f;
        if (getRolls() != null && getRolls().size() > 0) {
            for (Roll roll : getRolls()) {
                totalRollsCount += roll.getCount();
                totalRollsWeight += roll.getWeight() * roll.getCount();
            }
        }
        return String.format("Order #%d %s %s Client: %s Rolls:%dpcs Weight:%.1fkg",
                this.getId(),
                this.getStatus().getStatusName(),
                this.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
                this.getClient(),
                totalRollsCount,
                totalRollsWeight);
    }

    //функция отвечает за упорядочивание заказов в TreeSet-е manager-а. Если compareTo == 0,
    // то TreeSet считает такие объекты одинаковыми, поэтому после проверки айди и даты,
    // мы пытаемся сверить другие численные поля. Если заказы получильсь удивительно идентичными,
    // что в природе не возможно - мы сравниваем их хэши. Одинаковые заказы получаются только
    // при тестировании, когда время создания объектов совпадает до наносекунд.
    @Override
    public int compareTo(Order o) {
        int result = 0;

        if (this.equals(o)) {
            return result;
        } else if ((result = getDate().compareTo(o.getDate())) == 0) {
            if ((result = getId() - o.getId()) == 0) {
                if ((result = getRolls().size() - o.getRolls().size()) == 0) {
                    int rollsTotalPCSinThis = 0;
                    int rollsTotalPCSInO = 0;
                    for (Roll roll : rolls) {
                        rollsTotalPCSinThis += roll.getCount();
                    }
                    for (Roll roll : o.rolls) {
                        rollsTotalPCSInO += roll.getCount();
                    }
                    if ((result = rollsTotalPCSinThis - rollsTotalPCSInO) == 0) {
                        result = hashCode() - o.hashCode();
                    }
                }
            }
        }
        return result;
    }


}
