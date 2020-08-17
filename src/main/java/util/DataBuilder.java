package util;

import entity.CaloricLevel;
import entity.Dish;
import entity.Fruits;
import entity.Type;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class DataBuilder {

    public static List<Dish> getDishs() {
        final List<Dish> dishes = Arrays.asList(
                new Dish("pork", false, 800, Type.MEAT),
                new Dish("beef", false, 700, Type.MEAT),
                new Dish("chicken", false, 400, Type.MEAT),
                new Dish("french fries", true, 530, Type.OTHER),
                new Dish("rice", true, 350, Type.OTHER),
                new Dish("season fruit", true, 120, Type.OTHER),
                new Dish("pizza", true, 550, Type.OTHER),
                new Dish("prawns", false, 300, Type.FISH),
                new Dish("salmon", false, 450, Type.FISH)
        );
        return dishes;
    }

    public static List<Dish> getDishs2() {
        final List<Dish> dishes = Arrays.asList(
                new Dish("pork", false, 800, null)
        );
        return dishes;
    }

    public static List<Dish> getDishs3() {
        final List<Dish> dishes = Arrays.asList(
                new Dish("beef", false, 0, Type.FISH)
        );
        return dishes;
    }

    public static CaloricLevel getCaloricLevel(Dish d) {
        if (d.getCalories() <= 400) {
            return CaloricLevel.DIET;
        } else if (d.getCalories() <= 700) {
            return CaloricLevel.NORMAL;
        } else {
            return CaloricLevel.FAT;
        }
    }

    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
    }

    public static List<String> getFruits() {
        final List<String> fruits = Arrays.asList("apple", "apple", "banana",
                "apple", "orange", "banana", "papaya");
        return fruits;
    }

    public static List<Fruits> getFruitBeans() {
        List<Fruits> fruits = Arrays.asList(
                new Fruits("apple", 10, new BigDecimal("9.99")),
                new Fruits("banana", 20, new BigDecimal("19.99")),
                new Fruits("orang", 10, new BigDecimal("29.99")),
                new Fruits("watermelon", 10, new BigDecimal("29.99")),
                new Fruits("papaya", 20, new BigDecimal("9.99")),
                new Fruits("apple", 10, new BigDecimal("9.99")),
                new Fruits("banana", 10, new BigDecimal("19.99")),
                new Fruits("apple", 20, new BigDecimal("9.99"))
        );
        return fruits;
    }
}
