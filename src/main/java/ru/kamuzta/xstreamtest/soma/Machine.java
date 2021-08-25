package ru.kamuzta.xstreamtest.soma;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Machine implements Comparable<Machine> {
    private static AtomicInteger staticId = new AtomicInteger();
    private int id;
    private String sn;
    private float width;
    private Paper paper;
    private State state;
    private int fullCapacity;
    private int rollsInQueue;
    private Manager manager;
    private int freeCapacity;
    private Queue<Roll> rolls = new LinkedList<>();

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }
    public void setSn(String sn) {
        this.sn = sn;
    }

    public float getWidth() {
        return width;
    }
    public void setWidth(float width) {
        this.width = width;
    }

    public Paper getPaper() {
        return paper;
    }
    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public State getState() {
        return state;
    }
    private void setState(State state) {
        this.state = state;
    }

    public int getFullCapacity() {
        return fullCapacity;
    }
    public void setFullCapacity(int fullCapacity) {
        this.fullCapacity = fullCapacity;
    }

    public int getRollsInQueue() {
        int count = 0;
        for (Roll roll : rolls) {
            count += roll.getCount();
        }
        return count;
    }
    public void setRollsInQueue(int rollsInQueue) {
        this.rollsInQueue = rollsInQueue;
    }

    public Manager getManager() {
        return manager;
    }
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Queue<Roll> getRolls() {
        return rolls;
    }
    private void setRolls(Queue<Roll> rolls) {
        this.rolls = rolls;
    }

    public int getFreeCapacity() {
//        int rollsInQueue = 0;
//        if (this.getRollQueue().size() > 0) {
//            for (Roll roll : this.getRollQueue()) {
//                rollsInQueue += roll.getCount();
//            }
//        }
//        return this.getFullCapacity() - rollsInQueue;
        return freeCapacity;
    }
    public void setFreeCapacity(int freeCapacity) {
        this.freeCapacity = freeCapacity;
    }

    public Machine() {
    }

    public Machine(String sn, float width, Paper paper, State state, int fullCapacity, int rollsInQueue) {
        setSn(sn);
        setWidth(width);
        setPaper(paper);
        setState(state);
        setFullCapacity(fullCapacity);
        setRollsInQueue(rollsInQueue);
        setId(staticId.incrementAndGet());
        System.out.println("СТАНОК СОЗДАН: " + this);
    }

    public void updateState() {
        if(getRolls().size() == 0) {
            changeState(State.OFF);
            getManager().updateState();
        } else if (getState() == State.OFF && getRolls().size() > 0){
            changeState(State.ON);
            getManager().updateState();
        }
    }
    public void changeState(State newState) {
        State oldState = this.getState();
        if (oldState != newState) {
            try {
                this.setState(newState);
                Thread.sleep(500); //включение и выключение станка занимает 500мс
            } catch (InterruptedException e) {
                System.out.println("InterruptedException во время смены состояния станка #" + this.getId() + " из " + oldState.getStateName() + " в " + newState.getStateName());
            }
            System.out.println("Состояние станка #" + this.getId() + " переведено из " + oldState.getStateName() + " в " + newState.getStateName());
        }
    }


    public void loadRolls(Roll...rolls) {
        Arrays.sort(rolls);
        for (Roll roll : rolls) {
            roll.setMachine(this);
            this.rolls.add(roll);
            System.out.println("Ролик " + roll + " распределен на станок #" + this.getId());
        }
        this.updateState();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Machine machine = (Machine) o;
        return id == machine.id &&
                sn.equals(machine.sn) &&
                Float.compare(machine.width, width) == 0 &&
                fullCapacity == machine.fullCapacity &&
                rollsInQueue == machine.rollsInQueue &&
                freeCapacity == machine.freeCapacity &&
                paper == machine.paper &&
                state == machine.state &&
                rolls.equals(machine.rolls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sn, width, paper, state, fullCapacity, rollsInQueue, freeCapacity, rolls);
    }

    @Override
    public String toString() {
        return String.format("Machine #%d with S/N %s is %s Width:%.0f Paper: %s Capacity(Free/InQueue/Full): %d / %d / %d",
                getId(),
                getSn(),
                getState().getStateName(),
                getWidth(),
                getPaper().getCode(),
                getFreeCapacity(),
                getRollsInQueue(),
                getFullCapacity());
    }

    //сортировка в обратном порядке по свободной емкости, потом в прямом порядке по занятой емкости
    //потом по айди, серийнику и хэшкоду
    @Override
    public int compareTo(Machine o) {
        int result = 0;
        if (this.equals(o)) {
            return result;
        } else if ((result = o.getFreeCapacity() - getFreeCapacity()) == 0) {
            if ((result = getRollsInQueue() - o.getRollsInQueue()) == 0) {
                if ((result = o.getId() - getId()) == 0) {
                    if ((result = o.getSn().compareTo(getSn())) == 0) {
                        result = hashCode() - o.hashCode();
                    }
                }
            }
        }
        return result;
    }
}
