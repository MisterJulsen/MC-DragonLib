package de.mrjulsen.mcdragonlib.common;

public interface IIterableEnum<T extends Enum<T>> {
    T[] getValues();

    @SuppressWarnings("unchecked")
    default T next() {
        return getValues()[(((T)this).ordinal() + 1) % getValues().length];
    }
}

