package stream;

import entity.Dish;
import entity.Fruits;
import entity.Type;
import util.DataBuilder;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InitStream {
    public static void main(String[] args) {
        testStream();
    }

    /*
     * 构造流的几种常见方法
     * */
    public static void testStream() {
        System.out.println("testStream start...");
        List<Dish> dishes = DataBuilder.getDishs();
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");

        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);

        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();
        System.out.println("testStream end...");

        // 数值流的构造
        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.range(1, 3).sum();
        IntStream.rangeClosed(1, 3).forEach(System.out::println);

        // 流转换为其它数据结构
        List<String> stringList = Arrays.asList("a", "b");
        // 1. Array
        String[] strArray1 = stringList.stream().toArray(String[]::new);
        // 2. Collection
        List<String> list1 = stringList.stream().collect(Collectors.toList());
        List<String> list2 = stringList.stream().collect(Collectors.toCollection(ArrayList::new));
        Set set1 = stringList.stream().collect(Collectors.toSet());
        Stack stack1 = stringList.stream().collect(Collectors.toCollection(Stack::new));
        // 3. String
        String str = stringList.stream().collect(Collectors.joining()).toString();

        /**
         * Intermediate：
         * map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered
         *
         * Terminal：
         * forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
         *
         * Short-circuiting：
         * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
         * */

        // 一对多
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(111),
                Arrays.asList(2111, 3111),
                Arrays.asList(4111, 5111, 6111)
        );
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream());
        outputStream.forEach(System.out::println);

        // reduce 的用例
        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);

        // 生成 10 个随机整数
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
        //Another way
        IntStream.generate(() -> (int) (System.nanoTime() % 100)).
                limit(10).forEach(System.out::println);
    }
}