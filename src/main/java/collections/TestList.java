package collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口名	Java8新加入的方法
 * Collection	removeIf() spliterator() stream() parallelStream() forEach()
 * List	        replaceAll() sort()
 * Map	        getOrDefault() forEach() replaceAll() putIfAbsent() remove() replace() computeIfAbsent() computeIfPresent() compute() merge()
 */
public class TestList {
    public static void main(String[] args) {
        final List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.forEach(System.out::println);
        System.out.println("---");

        list.removeIf(it -> it.equals(2));
        list.forEach(System.out::println);
        System.out.println("---");

        list.replaceAll(it -> it * 5);
        list.forEach(System.out::println);
        System.out.println("---");

//        list.sort((a, b) -> b > a ? 1 : -1);
//        list.sort(Comparator.reverseOrder());
        list.sort(Comparator.naturalOrder());
        list.forEach(System.out::println);
        System.out.println("--------------------");

        final var map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("---");

        final var r1 = map.getOrDefault("d", 4);
        System.out.println(r1);
        System.out.println("---");

        map.remove("c", 31);
        map.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("---");

        map.replace("c", 3, 4);
        map.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("---");

        map.replaceAll((k, v) -> (int) v * 10);
        map.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("---");

        map.merge("a", 10, (k, v) -> 333);
        map.merge("e", 10, (k, v) -> 333);
        map.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("---");

        map.compute("a", (k, v) -> null);
        map.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("---");

        map.compute("a", (k, v) -> 5555);
        map.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("---");

        Map<Integer, List<String>> map2 = new HashMap<>();
        // Java7及以前的实现方式
//        if (map2.containsKey(1)) {
//            map2.get(1).add("one");
//        } else {
//            Set<String> valueSet = new HashSet<String>();
//            valueSet.add("one");
//            map2.put(1, valueSet);
//        }
        // Java8的实现方式
        map2.computeIfAbsent(1, v -> new ArrayList<>()).add("yi1");
        map2.computeIfAbsent(1, v -> new ArrayList<>()).add("yi2");
        map2.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("---");

        map2.computeIfPresent(1, (k, v) -> new ArrayList<>()).add("yi3");
        map2.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("---");

        map2.computeIfAbsent(1, v -> new ArrayList<>()).add("yi4");
        map2.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("---");


    }
}
