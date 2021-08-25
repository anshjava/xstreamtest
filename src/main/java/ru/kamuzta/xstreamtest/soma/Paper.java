package ru.kamuzta.xstreamtest.soma;

import java.lang.reflect.Field;

//determines paper sorts
public enum Paper {
    NTC44("NTC44", "Hansol", "NTC ST 44", 44.0f, 49.0f),
    NTC48("NTC48", "Hansol", "NTC ST 48", 48.0f, 52.0f),
    NTC55("NTC55", "Hansol", "NTC ST 55", 55.0f, 59.0f),
    NTC58("NTC58", "Hansol", "NTC STH 58", 58.0f, 71.0f);


    private String code;
    private String manufacturer;
    private String sort;
    private float weight;
    private float thickness;

    public int getId() {
        return ordinal();
    }
    public void setId(int id) {
        Field field;
        try {
            field = this.getClass().getSuperclass().getDeclaredField("ordinal");
            field.setAccessible(true);
            field.set(this, id);
        } catch (Exception ex) {
            System.out.println("Can't update enum ordinal");
        }
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }

    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getThickness() {
        return thickness;
    }
    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    Paper(String code, String manufacturer, String sort, float weight, float thickness) {
        this.code = code;
        this.manufacturer = manufacturer;
        this.sort = sort;
        this.weight = weight;
        this.thickness = thickness;
    }

    @Override
    public String toString() {
        return getManufacturer() + " " + getSort();
    }

}


