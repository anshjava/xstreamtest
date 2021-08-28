package ru.kamuzta.xstreamtest.soma.validate;

import java.util.function.Function;

public class MyValidate<T> {

    T base;
    String message;

    public MyValidate(T base, String message) {
        this.base = base;
        this.message = message;
    }

    public void assertNotNull() {
        try {
            Assert.notNull(base);
        } catch (Exception e) {
            throw new AssertException("обнаружен НУЛЕВОЙ объект " + message);
        }
        System.out.println("валидация успешна" + message);

    }


}
