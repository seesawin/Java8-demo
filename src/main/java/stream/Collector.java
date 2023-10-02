package stream;

import entity.Dish;
import entity.Type;
import util.DataBuilder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Collector {
    public static void main(String[] args) {
        testCollector();
        test1();
    }

    /*
     * 連線收集器
     * */
    public static void testCollector() {
        System.out.println("testCollector start...");
        List<Dish> dishes = DataBuilder.getDishs();

        //join directly
        String join1 = dishes.stream().map(Dish::getName).collect(Collectors.joining());
        System.out.println("join directly: " + join1);

        //join by comma
        String join2 = dishes.stream().map(Dish::getName).collect(Collectors.joining(", "));
        System.out.println("join by comma: " + join2);

        //toList
        List<String> names = dishes.stream().map(Dish::getName).collect(Collectors.toList());
        System.out.println("toList: " + names);

        //toSet
        Set<Type> types = dishes.stream().map(Dish::getType).collect(Collectors.toSet());
        System.out.println("toSet: " + types);

        //toMap, Exception in thread "main" java.lang.IllegalStateException: Duplicate key Dish{name='pork', isExists=false, calories=800, type=MEAT}
        try {
            Map<Type, Dish> byType = dishes.stream().collect(Collectors.toMap(Dish::getType, Function.identity()));
            /*
            有時候可能需要將一個陣列轉為map，做快取，方便多次計算獲取。
            toMap提供的方法k和v的生成函式。(注意，上述demo是一個坑，不可以這樣用！！！, 請使用toMap(Function, Function, BinaryOperator))

            上面幾個幾乎是最常用的收集器了，也基本夠用了。但作為初學者來說，理解需要時間。想要真正明白為什麼這樣可以做到收集，
            就必須檢視內部實現，可以看到，這幾個收集器都是基於java.util.stream.Collectors.CollectorImpl，
            也就是開頭提到過了Collector的一個實現類。後面自定義收集器會學習具體用法。
            * */
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("testCollector end...");
    }

    private static void test1() {
        System.out.println("test1 start...");
        final var l1 = Arrays.asList("1", "2", "3", "3");
        final var l2 = l1.stream().collect(Collectors.toCollection(LinkedList::new));
        final var l21 = l1.stream().collect(Collectors.toUnmodifiableList());
//        l21.add("4");
//        l21.forEach(System.out::println);
        System.out.println("---");

//        final var l3 = l1.stream().collect(Collectors.toMap(Function.identity(), it -> it + "-a"));
//        final var l3 = l1.stream().collect(Collectors.toMap(Function.identity(), it -> it + "-a", (e, r) -> e));
        final var l3 = l1.stream().collect(Collectors.toMap(Function.identity(), it -> it + "-a", (e, r) -> e, HashMap::new));
        l3.forEach((k, v) -> System.out.println(k + ":" + v));

        final var l31 = l1.stream().collect(Collectors.toUnmodifiableMap(Function.identity(), it -> it + "-a", (e, r) -> e));
//        l31.put("4", "4-b");
//        l31.forEach((k, v) -> System.out.println(k + ":" + v));

//        final var l32 = l1.stream().collect(Collectors.toConcurrentMap(Function.identity(), it -> it + "-a"));
//        final var l32 = l1.stream().collect(Collectors.toConcurrentMap(Function.identity(), it -> it + "-a", (e, r) -> e));
        final var l32 = l1.stream().collect(Collectors.toConcurrentMap(Function.identity(), it -> it + "-a", (e, r) -> e, ConcurrentHashMap::new));
        l32.forEach((k, v) -> System.out.println(k + ":" + v));

        System.out.println("---");

        final var l4 = l1.stream().collect(Collectors.joining());
        System.out.println(l4);

        final var l41 = l1.stream().collect(Collectors.joining("-"));
        System.out.println(l41);

        final var l42 = l1.stream().collect(Collectors.joining("-", "[", "]"));
        System.out.println(l42);

        System.out.println("---");
        final var l5 = l1.stream().collect(Collectors.mapping(it -> it + "b", Collectors.toList()));
        System.out.println(l5);

        System.out.println("---");
        final var l6 = l1.stream().collect(Collectors.collectingAndThen(Collectors.toSet(), it -> it.stream().collect(Collectors.joining(","))));
        System.out.println(l6);

        System.out.println("---");
        final var l7 = l1.stream().collect(Collectors.counting());
        System.out.println(l7);

        System.out.println("---");
//        final var l8 = l1.stream().collect(Collectors.maxBy((a, b) -> a.length() - b.length()));
        final var l8 = l1.stream().max(Comparator.comparingInt(String::length));
        System.out.println(l8.get());

//        final var l9 = l1.stream().collect(Collectors.minBy((a, b) -> a.length() - b.length()));
        final var l9 = l1.stream().min(Comparator.comparingInt(String::length));
        System.out.println(l9.get());

        System.out.println("---");
        final var l10 = l1.stream().collect(Collectors.summingInt(String::length));
        System.out.println(l10);

        final var l101 = l1.stream().collect(Collectors.averagingInt(String::length));
        System.out.println(l101);

        System.out.println("---");
        final var ints = Arrays.asList(1, 2, 3, 4, 5, 6);
        final var l11 = ints.stream().collect(Collectors.reducing((a, b) -> a + b));
        System.out.println(l11.get());

        final var l112 = ints.stream().collect(Collectors.reducing((Integer::sum)));
        System.out.println(l112.get());

        final var l113 = ints.stream().collect(Collectors.reducing(100, (Integer::sum)));
        System.out.println(l113);

        final var l114 = ints.stream().collect(Collectors.reducing(100, it -> it - 10, (Integer::sum)));
        System.out.println(l114);

        System.out.println("---");
        final var g1 = l1.stream().collect(Collectors.groupingBy(Function.identity()));
        System.out.println(g1);

        final var g2 = l1.stream().collect(Collectors.groupingBy(String::length, Collectors.toMap(Function.identity(), it -> it + "-a", (e, r) -> e)));
        System.out.println(g2);

        List<String> list = Arrays.asList("123", "456", "789", "1101", "212121121", "asdaa", "3e3e3e", "2321eew");
        groupingByTest(list);

        System.out.println("---");
        final var p1 = ints.stream().collect(Collectors.partitioningBy(it -> it > 2));
        System.out.println(p1);

        final var p2 = ints.stream().collect(Collectors.partitioningBy(it -> it > 2, Collectors.toSet()));
        System.out.println(p2);

        System.out.println("---");
        final var all = ints.stream().mapToInt(it -> it).sum();
        System.out.println(all);
        final var sum = ints.stream().collect(Collectors.summarizingInt(it -> it));
        System.out.println(sum);
        System.out.println("test1 end...");
    }

    public static void groupingByTest(List<String> list) {
        Map<Integer, List<String>> s = list.stream().collect(Collectors.groupingByConcurrent(String::length));
        Map<Integer, List<String>> ss = list.stream().collect(Collectors.groupingByConcurrent(String::length, Collectors.toList()));
        Map<Integer, Set<String>> sss = list.stream().collect(Collectors.groupingByConcurrent(String::length, ConcurrentHashMap::new, Collectors.toSet()));
        System.out.println(s.toString() + "\n" + ss.toString() + "\n" + sss.toString());
    }
}