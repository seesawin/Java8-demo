import lombok.Data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

    public static String getObject(Object obj) throws IllegalAccessException {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        List<Field> list = Stream.of(declaredFields).sorted(Comparator.comparing(Field::getName)).collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            Field field = list.get(i);
            Object o = field.get(obj);
            sb.append(field.getName()).append(o);
        }

        return sb.toString();
    }

    public static void main(String[] args) throws IllegalAccessException {
        Model model = new Model();
        model.setName("小陳");
        model.setAge(11);
        model.setAddress("新北市");
        System.out.println(Test.getObject(model));
    }

    @Data
    public static class Model {
        private String name;
        private int age;
        private String address;
    }
}
