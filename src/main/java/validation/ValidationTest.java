package validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstructorDescriptor;
import javax.validation.metadata.MethodDescriptor;
import javax.validation.metadata.MethodType;
import javax.validation.metadata.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static validation.ValidationUtils.obtainCustomizedValidator;
import static validation.ValidationUtils.obtainValidator;
import static validation.ValidationUtils.printViolations;

public class ValidationTest {
    public static void main(String[] args) throws NoSuchMethodException {
        Person person = new Person();
        person.setAge(-1);

        // 1、使用【默认配置】得到一个校验工厂  这个配置可以来自于provider、SPI提供
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        // 2、得到一个校验器
        Validator validator = validatorFactory.getValidator();
        // 3、校验Java Bean（解析注解） 返回校验结果
        Set<ConstraintViolation<Person>> result = validator.validate(person);

        // 输出校验结果
        result.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
        System.out.println("-----");

//        @NotNull Person p1 = new PersonService().getOne2(null, "QQ");
        System.out.println("-----");

//        Person p2 = new PersonService().getOne3(1, "QQ");
        System.out.println("-----");

//        Person p3 = new PersonService().save(null);
//        Person p3 = new PersonService().save(new Person());
        System.out.println("-----");

        User user = new User();
        user.setName("YourBatman");
        user.setFullName("YourBatman");

        Set<ConstraintViolation<User>> userResult = obtainValidator().validate(user);
        printViolations(userResult);
        System.out.println("-----");


        /**
         * 校验Java Bean所有约束中的所有包括：
         * 1、属性上的约束
         * 2、类上的约束
         */
        // validateProperty：校验指定属性
        User user1 = new User();
        user1.setFullName("YourBatman");

        Set<ConstraintViolation<User>> userResult1 = obtainValidator().validateProperty(user, "fullName");
        printViolations(userResult1);
        System.out.println("-----");

        // validateValue：校验value值
        // 校验某个value值，是否符合指定属性上的所有约束。可理解为：若我把这个value值赋值给这个属性，是否合法？
//        Set<ConstraintViolation<User>> userResult2 = obtainValidator().validateValue(User.class, "fullName", "YourBatman-YourBatman");
        Set<ConstraintViolation<User>> userResult2 = obtainCustomizedValidator().validateValue(User.class, "fullName", null);
        printViolations(userResult2);
        System.out.println("-----");

        // 获取Class类型描述信息
        BeanDescriptor beanDescriptor = obtainValidator().getConstraintsForClass(User.class);
        System.out.println("此类是否需要校验：" + beanDescriptor.isBeanConstrained());

        // 获取属性、方法、构造器的约束
        Set<PropertyDescriptor> constrainedProperties = beanDescriptor.getConstrainedProperties();
        Set<MethodDescriptor> constrainedMethods = beanDescriptor.getConstrainedMethods(MethodType.GETTER);
        Set<ConstructorDescriptor> constrainedConstructors = beanDescriptor.getConstrainedConstructors();
        System.out.println("需要校验的属性：" + constrainedProperties);
        System.out.println("需要校验的方法：" + constrainedMethods);
        System.out.println("需要校验的构造器：" + constrainedConstructors);

        PropertyDescriptor fullNameDesc = beanDescriptor.getConstraintsForProperty("fullName");
        System.out.println(fullNameDesc);
        System.out.println("fullName属性的约束注解个数：" + fullNameDesc.getConstraintDescriptors().size());
        System.out.println("-----");

        /**
         * 运行程序，没有任何输出，也就是说并没有对rooms立面的元素进行验证。
         * 这里有一个误区：Bean Validator是基于Java Bean进行验证的，而此处你的rooms仅仅只是一个容器类型的变量而已，因此不会验证。
         *
         * 其实它是把List当作一个Bean，去验证List里面的标注有约束注解的属性/方法。很显然，List里面不可能标注有约束注解嘛，所以什么都不输出喽
         */
        List<@NotNull Room> rooms = new ArrayList<>();
        rooms.add(null);
        rooms.add(new Room());

        Room room = new Room();
        room.name = "YourBatman";
        rooms.add(room);

        printViolations(obtainValidator().validate(rooms));

        // 必须基于Java Bean，验证才会生效
        Rooms roomList = new Rooms(rooms);
        printViolations(obtainValidator().validate(roomList));

        System.out.println("-----");
        final var resultContainer = new Result<Room>();
        room.setName(null);
        resultContainer.setData(room);
        // 把Result作为属性放进去
        ResultDemo resultDemo = new ResultDemo();
        resultDemo.setRoomResult(resultContainer);

        // 注册自定义的值提取器
        printViolations(obtainCustomizedValidator().validate(resultDemo));

        System.out.println("-----");
        final var acc = new AccTransactionWebhookDTO();
        final var data = new AccTransactionWebhookDTO.AccTransactionDTO();
        acc.setData(data);
        printViolations(obtainCustomizedValidator().validate(acc));

    }

}
