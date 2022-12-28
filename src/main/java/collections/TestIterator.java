package collections;

import java.util.ArrayList;
import java.util.LinkedList;

public class TestIterator {
    public static void main(String[] args) {
//        final var list = Arrays.asList(1, 2, 3, 4, 5, 6);
        final var list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        final var iterator = list.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("---");
        list.forEach(System.out::println);

        System.out.println("-----");
        final var link = new LinkedList<>();
        link.add(1);
        link.add(2);
        link.add(3);
        link.add(4);
        link.add(5);
        link.add(6);
        final var iterator2 = link.iterator();
        while(iterator2.hasNext()) {
            System.out.println(iterator2.next());
        }
    }
}
