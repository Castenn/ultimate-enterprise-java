package com.borovyk.homework.randomFieldComparator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A generic comparator that is comparing a random field of the given class. The field is either primitive or
 * {@link Comparable}. It is chosen during comparator instance creation and is used for all comparisons.
 * <p>
 * By default it compares only accessible fields, but this can be configured via a constructor property. If no field is
 * available to compare, the constructor throws {@link IllegalArgumentException}
 *
 * @param <T> the type of the objects that may be compared by this comparator
 */
public class RandomFieldComparator<T> implements Comparator<T> {

    private Field randomField;

    public RandomFieldComparator(Class<T> targetType) throws Exception {
        this(targetType, true);
    }

    /**
     * A constructor that accepts a class and a property indicating which fields can be used for comparison. If property
     * value is true, then only public fields or fields with public getters can be used.
     *
     * @param targetType                  a type of objects that may be compared
     * @param compareOnlyAccessibleFields config property indicating if only publicly accessible fields can be used
     */
    public RandomFieldComparator(Class<T> targetType, boolean compareOnlyAccessibleFields) throws Exception {
        var typeInstance = targetType.getConstructor().newInstance();
        var declaredFields = targetType.getDeclaredFields();
        var comparableFields = Arrays.stream(declaredFields)
                .filter(field -> field.getType().isPrimitive() || Comparable.class.isAssignableFrom(field.getType()))
                .filter(field -> !compareOnlyAccessibleFields || field.canAccess(typeInstance))
                .toList();

        if (comparableFields.isEmpty()) {
            throw new IllegalArgumentException();
        }
        var randomIndex = ThreadLocalRandom.current().nextInt(comparableFields.size());
        randomField = comparableFields.get(randomIndex);
        randomField.setAccessible(true);
    }

    /**
     * Compares two objects of the class T by the value of the field that was randomly chosen. It allows null values
     * for the fields, and it treats null value grater than a non-null value (nulls last).
     */
    @Override
    public int compare(T o1, T o2) {
        try {
            var value1 = (Comparable) randomField.get(o1);
            var value2 = (Comparable) randomField.get(o2);

            if (value1 == null && value2 == null) {
                return 0;
            } else if (value1 == null) {
                return 1;
            } else if (value2 == null) {
                return -1;
            } else {
                return value1.compareTo(value2);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a statement "Random field comparator of class '%s' is comparing '%s'" where the first param is the name
     * of the type T, and the second parameter is the comparing field name.
     *
     * @return a predefined statement
     */
    @Override
    public String toString() {
        return String.format("Random field comparator of class '%s' is comparing '%s'",
                randomField.getType(), randomField.getName());
    }

}
