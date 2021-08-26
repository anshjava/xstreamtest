package ru.kamuzta.xstreamtest.soma.entities;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Roll implements Comparable<Roll> {
    private static AtomicInteger staticId = new AtomicInteger();
    private int id;
    private RollType type;
    private Paper paper;
    private Status status;
    private float width;            // mm
    private float core;             // mm
    private int count;              // pcs
    private float value;            //value specified by client, may be length or diameter
    private Order order;
    private Machine machine;

    //calculated parameters
    private float length;           // m
    private float diameter;          // mm
    private float weight;           // kg

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public RollType getType() {
        return type;
    }
    public void setType(RollType rollType) {
        this.type = rollType;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public float getWidth() {
        return width;
    }
    private void setWidth(float width) {
        this.width = width;
    }

    public float getCore() {
        return core;
    }
    private void setCore(float core) {
        this.core = core;
    }

    public int getCount() {
        return count;
    }
    private void setCount(int count) {
        this.count = count;
    }

    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    public Machine getMachine() {
        return machine;
    }
    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Paper getPaper() {
        return paper;
    }
    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public float getLength() {
        return length;
    }
    private void setLength(float value) {
        switch (this.type) {
            case LENGTH:
                this.length = value;
                break;
            case DIAMETER:
                this.length = (float) Math.PI * (value * value - this.getCore() * this.getCore()) / (4 * this.paper.getThickness());
                break;
        }
    }

    public float getDiameter() {
        return diameter;
    }
    private void setDiameter(float value) {
        switch (this.type) {
            case LENGTH:
                this.diameter = (float) Math.sqrt((4 * this.paper.getThickness() * value) / Math.PI + this.getCore() * this.getCore());
                break;
            case DIAMETER:
                this.diameter = value;
                break;
        }
    }

    public float getWeight() {
        return weight;
    }
    private void setWeight() {
        this.weight = this.getWidth() / 1000 * this.getLength() * this.paper.getWeight() / 1000;
    }

    public Roll() {
    }

    public Roll(RollType rollType, Paper paper, float width, float core, int count, float value) {
        setType(rollType);
        setCore(core);
        setPaper(paper);
        setWidth(width);
        setCount(count);
        setValue(value);
        setLength(value);
        setDiameter(value);
        setWeight();
        setStatus(Status.NEW);
        setId(staticId.incrementAndGet());
        System.out.println("РОЛИК СОЗДАН: " + this);
    }

    public void changeStatus(Status newStatus) {
        Status oldStatus = getStatus();
        if (oldStatus != newStatus) {
            try {
                setStatus(newStatus);
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException во время смены статуса ролика из заказа #" + getOrder().getId() + " из " + oldStatus.getStatusName() + " в " + newStatus.getStatusName());
            }
            System.out.println("Статус ролика из заказа #" + getOrder().getId() + " переведен из " + oldStatus.getStatusName() + " в " + newStatus.getStatusName());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roll roll = (Roll) o;
        return id == roll.id &&
                Float.compare(roll.width, width) == 0 &&
                Float.compare(roll.core, core) == 0 &&
                count == roll.count &&
                Float.compare(roll.value, value) == 0 &&
                Float.compare(roll.length, length) == 0 &&
                Float.compare(roll.diameter, diameter) == 0 &&
                Float.compare(roll.weight, weight) == 0 &&
                type == roll.type &&
                paper == roll.paper &&
                status == roll.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, paper, status, width, core, count, value, length, diameter, weight);
    }

    @Override
    public String toString() {
        return String.format("%s Roll #%d %.0f x %.1f%s x %.0f %.0fg/m2 %dpcs %.1fkg",
                getStatus().getStatusName(),
                getId(),
                getWidth(),
                getType() == RollType.LENGTH ? getLength() : getDiameter(),
                getType() == RollType.LENGTH ? "M" : "mm",
                getCore(),
                getPaper().getWeight(),
                getCount(),
                getCount() * getWeight());
    }

    //упорядочивание в reverse-order по count, если не получается,
    //то по остальным численным показателям и в конце - по хэшу
    @Override
    public int compareTo(Roll o) {
        int result = 0;

        if (this.equals(o)) {
            return result;
        } else if ((result = o.count - count) == 0) {
            if ((result = o.getId() - getId()) == 0) {
                if ((result = Float.compare(o.weight, weight)) == 0) {
                    if ((result = Float.compare(o.weight, weight)) == 0) {
                        if ((result = Float.compare(o.weight, weight)) == 0) {
                            result = o.hashCode() - hashCode();
                        }
                    }
                }
            }
        }
        return result;
    }
}