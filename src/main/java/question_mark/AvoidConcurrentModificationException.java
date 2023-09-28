package question_mark;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AvoidConcurrentModificationException {
    public static void main(String[] args) {

//        List<String> myList = new CopyOnWriteArrayList<String>();
//
//        myList.add("1");
//        myList.add("2");
//        myList.add("3");
//        myList.add("4");
//        myList.add("5");
//
//        Iterator<String> it = myList.iterator();
//        while (it.hasNext()) {
//            String value = it.next();
//            System.out.println("List Value:" + value);
//            if (value.equals("3")) {
//                myList.remove("4");
//                myList.add("6");
//                myList.add("7");
//            }
//        }
//        System.out.println("List Size:" + myList.size());

        Map<String, String> temp = null;
        for (int i = 0; i < 100; i++) {
            Map<String, String> nMap = test();
            final var flag = nMap.entrySet().stream().filter(it -> it.getValue() == null).count() > 0;
            if (flag) {
                Iterator<String> it1 = nMap.keySet().iterator();
                while (it1.hasNext()) {
                    String key = it1.next();
                    System.out.println("Map Value:" + nMap.get(key));
                }
                System.out.println("nMap Size:" + nMap.size());
                System.out.println("####################################");
                System.out.println("####################################");
                throw new RuntimeException("error");
            }
            System.out.println("====================================");
            System.out.println("====================================");
        }
    }

    public static Map<String, String> test() {
        Map<String, String> myMap = new ConcurrentHashMap<String, String>();
        myMap.put("1", "1");
        myMap.put("2", "2");
        myMap.put("3", "3");

        Iterator<String> it1 = myMap.keySet().iterator();
        while (it1.hasNext()) {
            String key = it1.next();
            System.out.println("Map Value:" + myMap.get(key));
            if (key.equals("1")) {
                myMap.remove("3");
                myMap.put("4", "4");
                myMap.put("5", "5");
            }
        }

        System.out.println("Map Size:" + myMap.size());
        return myMap;
    }
}
