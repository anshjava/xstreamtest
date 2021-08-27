package ru.kamuzta.xstreamtest.soma.validate;

import java.util.function.Function;

public class Validate<T>{

    T base;

    public Validate(T base) {
        this.base = base;
    }

    public <R> Validate<R> of(Function<T,R> f){
        //TODO: найти возможность получаь из f  название операнда и запоминать его
        System.out.println(base.toString());
        return new Validate<R>(f.apply(base));
    }

    public static <T> Validate<T> of(T base){
        return new Validate<T>(base);
    }

    public void assertNotNull() {
        try {
            Assert.notNull(base);
        } catch (Exception e) {
            throw new AssertException(String.valueOf(base.getClass() + "НУЛЕВОЙ"));
        }

    }

    public void assertTrueNull() {

    }

    public static <T> void assertNotNull(Validate<T> validate){
        validate.assertNotNull();
    }
}
