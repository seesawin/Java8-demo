package stream.predicate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateTest1 {
    public static void main(String[] args) {
        PredicateTest1 test = new PredicateTest1();
        test.test();
    }

    public void test() {
        List<Fruits> fruitBeans = this.getFruitBeans();
        List<Fruits> collect = fruitBeans.stream().distinct().filter(distinctByKey(Fruits::getName)).collect(Collectors.toList());
        collect.forEach(f -> System.out.println(f.getName()));
        System.out.println("---------------");
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        List<Fruits> fruitBeans3 = this.getFruitBeans();
        List<Fruits> collect3 = fruitBeans3.stream().distinct().filter(t -> seen.putIfAbsent(t.getName(), Boolean.TRUE) == null).collect(Collectors.toList());
        collect3.forEach(f -> System.out.println(f.getName()));

        Predicate<String> stringPredicate = distinctByKey(new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s;
            }
        });

        Fruits f = new Fruits("apple1", 10, new BigDecimal("9.99"));
        boolean test = stringPredicate.test(f.getName());
        System.out.println(test);

        Fruits f2 = new Fruits("apple1", 10, new BigDecimal("9.99"));
        boolean test2 = stringPredicate.test(f2.getName());
        System.out.println(test2);

        List<Fruits> fruitBeans2 = this.getFruitBeans();
        fruitBeans2.forEach(forEachByKey(Function.identity()));

    }

    static <T> Consumer<T> forEachByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        System.out.println("22222");
        return t -> {
            seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE);
            System.out.println(keyExtractor.apply(t));
        };
    }

    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        System.out.println("111111111");
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
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

class Fruits {
    private String name;
    private int qty;
    private BigDecimal price;

    public Fruits(String name, int qty, BigDecimal price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Fruits{" +
                "name='" + name + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
