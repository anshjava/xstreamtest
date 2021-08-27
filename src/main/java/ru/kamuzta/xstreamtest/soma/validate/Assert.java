package ru.kamuzta.xstreamtest.soma.validate;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.math.BigDecimal;
import java.util.Collection;

public class Assert {
    public Assert() {
    }

    public static <T> T notNull(@Nullable T testee) throws AssertException {
        return notNull(testee, "assertion failed: testee must not be null");
    }

    public static <T> T notNull(@Nullable T testee, @NotNull String claim) throws AssertException {
        if (testee == null) {
            throw new AssertException(claim);
        } else {
            return testee;
        }
    }

    public static void isNotEmpty(@NotNull Collection collection) throws AssertException {
        isFalse(collection.isEmpty(), "Collection must be not empty");
    }

    public static void isEmpty(@NotNull Collection collection) throws AssertException {
        isTrue(collection.isEmpty(), "Collection must be empty");
    }

    public static void fail() throws AssertException {
        isTrue(false, "assertion failed.");
    }

    public static void isFalse(boolean test) throws AssertException {
        isFalse(test, "assertion failed: test expression must be false");
    }

    public static void isFalse(boolean test, @NotNull String claim) throws AssertException {
        isTrue(!test, claim);
    }

    public static void isTrue(boolean test) throws AssertException {
        isTrue(test, "assertion failed: test expression must be true");
    }

    public static void isTrue(boolean test, @NotNull String claim) throws AssertException {
        if (!test) {
            throw new AssertException(claim);
        }
    }

    public static void scaleIs2(@NotNull BigDecimal value) throws AssertException {
        equals(2, value.scale(), "Number " + value + " should have 2 digits after decimal point.");
    }

    public static void equals(@NotNull Object o1, @NotNull Object o2) throws AssertException {
        equals(o1, o2, "Assertion failed: objects must be equal");
    }

    public static void equals(@NotNull Object o1, @NotNull Object o2, @NotNull String claim) throws AssertException {
        if (!o1.equals(o2)) {
            throw new AssertException(claim);
        }
    }
}
