package ru.kamuzta.xstreamtest.soma;

public enum RollType {
    LENGTH("LENGTH"),
    DIAMETER("DIAMETER");

    private String typeName;

    RollType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return getTypeName();
    }
}
