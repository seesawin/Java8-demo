package stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 流的使用详解
 * 简单说，对 Stream 的使用就是实现一个 filter-map-reduce 过程，产生一个最终结果，或者导致一个副作用（side effect）。
 */
public class StreamInit {
    public static void main(String[] args) throws IOException {

        /**
         * 流的构造与转换
         * 下面提供最常见的几种构造 Stream 的样例。
         * */
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");
        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();


        /**
         * 需要注意的是，对于基本数值型，目前有三种对应的包装类型 Stream：
         *
         * IntStream、LongStream、DoubleStream。当然我们也可以用 Stream、Stream >、Stream，
         * 但是 boxing 和 unboxing 会很耗时，所以特别为这三种基本数值型提供了对应的 Stream。
         *
         * Java 8 中还没有提供其它数值型 Stream，因为这将导致扩增的内容较多。而常规的数值型聚合运算可以通过上面三种 Stream 进行。
         *
         * 数值流的构造
         */
        IntStream intStream = IntStream.of(new int[]{1, 2, 3});
        intStream.forEach(System.out::println);

        IntStream range = IntStream.range(1, 3);
        range.forEach(System.out::println);

        IntStream rangeClosed = IntStream.rangeClosed(1, 3);
        rangeClosed.forEach(System.out::println);

        /**
         * 流转换为其它数据结构
         */
        Stream<String> stringStream = Stream.of("a", "b", "c");
        // 1. Array
        String[] strArray1 = stringStream.toArray(String[]::new);
        // 2. Collection
        stringStream = Stream.of("a", "b", "c");
        List<String> list1 = stringStream.collect(Collectors.toList());
        stringStream = Stream.of("a", "b", "c");
        List<String> list2 = stringStream.collect(Collectors.toCollection(ArrayList::new));
        stringStream = Stream.of("a", "b", "c");
        Set set1 = stringStream.collect(Collectors.toSet());
        stringStream = Stream.of("a", "b", "c");
        Stack stack1 = stringStream.collect(Collectors.toCollection(Stack::new));
        // 3. String
        stringStream = Stream.of("a", "b", "c");
        String str = stringStream.collect(Collectors.joining()).toString();

        /**
         * 流的操作
         * 接下来，当把一个数据结构包装成 Stream 后，就要开始对里面的元素进行各类操作了。常见的操作可以归类如下。
         *
         * Intermediate：
         * map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、
         * sequential、 unordered
         *
         * Terminal：
         * forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、
         * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
         *
         * Short-circuiting：
         * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
         *
         * */

        /**
         * map
         *
         * 我们先来看 map。如果你熟悉 scala 这类函数式语言，对这个方法应该很了解，
         * 它的作用就是把 input Stream 的每一个元素，映射成 output Stream 的另外一个元素。
         * */
        stringStream = Stream.of("a", "b", "c");
        List<String> output = stringStream.
                map(String::toUpperCase).
                collect(Collectors.toList());

        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums = nums.stream().
                map(n -> n * n).
                collect(Collectors.toList());

        /**
         * flatMap
         *
         * flatMap 把 input Stream 中的层级结构扁平化，就是将最底层元素抽出来放到一起，
         * 最终 output 的新 Stream 里面已经没有 List 了，都是直接的数字。
         * */
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream());

        /**
         * filter
         *
         * filter 对原始 Stream 进行某项测试，通过测试的元素被留下来生成一个新 Stream。
         * */

        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens = Stream.of(sixNums).filter(n -> n % 2 == 0).toArray(Integer[]::new);

//        FileReader reader = new FileReader("FilePath/filename.txt");
//        try (BufferedReader br = new BufferedReader(reader)) {
//            List<String> outputs = br.lines().
//                    flatMap(line -> Stream.of(line.split(" "))).
//                    filter(word -> word.length() > 0).
//                    collect(Collectors.toList());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        /**
         * forEach
         *
         * forEach 方法接收一个 Lambda 表达式，然后在 Stream 的每一个元素上执行该表达式。
         *
         * 对一个人员集合遍历，找出男性并打印姓名。可以看出来，forEach 是为 Lambda 而设计的，
         * 保持了最紧凑的风格。而且 Lambda 表达式本身是可以重用的，非常方便。当需要为多核系统优化时，
         * 可以 parallelStream().forEach()，只是此时原有元素的次序没法保证，并行的情况下将改变串行时操作的行为，
         * 此时 forEach 本身的实现不需要调整，而 Java8 以前的 for 循环 code 可能需要加入额外的多线程逻辑。
         *
         * 但一般认为，forEach 和常规 for 循环的差异不涉及到性能，它们仅仅是函数式风格与传统 Java 风格的差别。
         *
         * 另外一点需要注意，forEach 是 terminal 操作，因此它执行后，Stream 的元素就被”消费”掉了，
         * 你无法对一个 Stream 进行两次 terminal 运算。
         *
         * forEach 不能修改自己包含的本地变量值，也不能用 break/return 之类的关键字提前结束循环。
         * */
        stringStream = Stream.of("a", "b", "c");
        stringStream.forEach(System.out::println);

        /**
         * peek
         *
         * 对每个元素执行操作并返回一个新的 Stream
         * */

        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());

        /**
         * reduce
         *
         * 这个方法的主要作用是把 Stream 元素组合起来。它提供一个起始值（种子），然后依照运算规则（BinaryOperator），和前面 Stream 的第一个、第二个、第 n 个元素组合。从这个意义上说，字符串拼接、数值的 sum、min、max、average 都是特殊的 reduce。例如 Stream 的 sum 就相当于
         *
         * Integer sum = integers.reduce(0, (a, b) -> a+b); 或
         *
         * Integer sum = integers.reduce(0, Integer::sum);
         *
         * 也有没有起始值的情况，这时会把 Stream 的前面两个元素组合起来，返回的是 Optional。
         *
         * 上面代码例如第一个示例的 reduce()，第一个参数（空白字符）即为起始值，
         * 第二个参数（String::concat）为 BinaryOperator。这类有起始值的 reduce() 都返回具体的对象。
         * 而对于第四个示例没有起始值的 reduce()，由于可能没有足够的元素，返回的是 Optional，请留意这个区别。
         * */
        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MIN_VALUE, Double::min);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);

        /**
         * limit/skip
         *
         * limit 返回 Stream 的前面 n 个元素；skip 则是扔掉前 n 个元素（它是由一个叫 subStream 的方法改名而来）。
         * */
        StreamInit streamInit = new StreamInit();
        streamInit.testLimitAndSkip();
        streamInit.testLimitAndSkipAndSorted();

        /**
         * sorted
         *
         * 对 Stream 的排序通过 sorted 进行，它比数组的排序更强之处在于你可以首先对 Stream 进行各类 map、filter、limit、skip
         * 甚至 distinct 来减少元素数量后，再排序，这能帮助程序明显缩短执行时间。我们对清单 14 进行优化：
         * */
        streamInit.testLimitAndSkipBeforeSorted();

        /**
         * min/max/distinct
         *
         * min 和 max 的功能也可以通过对 Stream 元素先排序，再 findFirst 来实现，但前者的性能会更好，
         * 为 O(n)，而 sorted 的成本是 O(n log n)。同时它们作为特殊的 reduce 方法被独立出来也是因为求最大最小值是很常见的操作。
         * */

        // 找出最长一行的长度
//        BufferedReader br = new BufferedReader(new FileReader("c:\\SUService.log"));
//        int longest = br.lines().
//                mapToInt(String::length).
//                max().
//                getAsInt();
//        br.close();
//        System.out.println(longest);

        // 找出全文的单词，转小写，并排序
//        List<String> words = br.lines().
//                flatMap(line -> Stream.of(line.split(" "))).
//                filter(word -> word.length() > 0).
//                map(String::toLowerCase).
//                distinct().
//                sorted().
//                collect(Collectors.toList());
//        br.close();
//        System.out.println(words);

        /**
         * Match
         *
         * Stream 有三个 match 方法，从语义上说：
         *
         * allMatch：Stream 中全部元素符合传入的 predicate，返回 true
         * anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
         * noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
         * 它们都不是要遍历全部元素才能返回结果。例如 allMatch 只要一个元素不满足条件，
         * 就 skip 剩下的所有元素，返回 false。对清单 13 中的 Person 类稍做修改，加入一个 age 属性和 getAge 方法。
         * */

        streamInit.testMatch();

    }

    /**
     * 这是一个有 10，000 个元素的 Stream，但在 short-circuiting 操作 limit 和 skip 的作用下，
     * 管道中 map 操作指定的 getName() 方法的执行次数为 limit 所限定的 10 次，
     * 而最终返回结果在跳过前 3 个元素后只有后面 7 个返回。
     * <p>
     * 有一种情况是 limit/skip 无法达到 short-circuiting 目的的，
     * 就是把它们放在 Stream 的排序操作后，原因跟 sorted 这个 intermediate 操作有关：
     * 此时系统并不知道 Stream 排序后的次序如何，所以 sorted 中的操作看上去就像完全没有被 limit 或者 skip 一样。
     */
    public void testLimitAndSkip() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<String> personList2 = persons.stream().
                map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(personList2);
    }

    /**
     * limit 和 skip 对 sorted 后的运行次数无影响
     * <p>
     * 即虽然最后的返回元素数量是 2，但整个管道中的 sorted 表达式执行次数没有像前面例子相应减少。
     * <p>
     * 最后有一点需要注意的是，对一个 parallel 的 Steam 管道来说，如果其元素是有序的，
     * 那么 limit 操作的成本会比较大，因为它的返回对象必须是前 n 个也有一样次序的元素。
     * 取而代之的策略是取消元素间的次序，或者不要用 parallel Stream。
     */
    public void testLimitAndSkipAndSorted() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<Person> personList2 = persons.stream().sorted((p1, p2) ->
                p1.getName().compareTo(p2.getName())).limit(2).collect(Collectors.toList());
        System.out.println(personList2);
    }

    /**
     * 排序前进行 limit 和 skip
     * <p>
     * 当然，这种优化是有 business logic 上的局限性的：即不要求排序后再取值。
     */
    public void testLimitAndSkipBeforeSorted() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<Person> personList2 = persons.stream().sorted((p1, p2) ->
                p1.getName().compareTo(p2.getName())).limit(2).collect(Collectors.toList());
        System.out.println(personList2);
    }

    public void testMatch() {
        List<Person> persons = new ArrayList();
        persons.add(new Person(1, "name" + 1, 10));
        persons.add(new Person(2, "name" + 2, 21));
        persons.add(new Person(3, "name" + 3, 34));
        persons.add(new Person(4, "name" + 4, 6));
        persons.add(new Person(5, "name" + 5, 55));
        boolean isAllAdult = persons.stream().
                allMatch(p -> p.getAge() > 18);
        System.out.println("All are adult? " + isAllAdult);
        boolean isThereAnyChild = persons.stream().
                anyMatch(p -> p.getAge() < 12);
        System.out.println("Any child? " + isThereAnyChild);
    }

    class Person {
        public int no;
        private String name;
        private int age;

        public Person(int no, String name) {
            this.no = no;
            this.name = name;
        }

        public Person(int no, String name, int age) {
            this.no = no;
            this.name = name;
            this.age = age;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
