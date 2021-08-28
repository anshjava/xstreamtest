package ru.kamuzta.xstreamtest.soma.validate;

import java.util.function.Function;

public class Validate<T>{

    T base;

    public Validate(T base) {
        this.base = base;
    }

    public <R> Validate<R> of(Function<T,R> f) {
        //TODO: найти возможность получаь из f  название операнда и запоминать его
        return new Validate<R>(f.apply(base));
    }

    public static <T> Validate<T> of(T base){
        return new Validate<T>(base);
    }

    public void assertNotNull() {
        try {
            Assert.notNull(base);
        } catch (Exception e) {
            throw new AssertException("обнаружен НУЛЕВОЙ объект");
        }
        System.out.println("валидация успешна, объект base - " + base.getClass().getSimpleName());

    }

    public void assertTrueNull() {

    }

    public static <T> void assertNotNull(Validate<T> validate){
        System.out.println("мы в методе assertNotNull(Validate<T> validate)");
        validate.assertNotNull();
    }
}
