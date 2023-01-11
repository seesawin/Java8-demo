package validation;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.util.Set;

import static validation.ValidationUtils.obtainExecutableValidator;

public class PersonService {

//    public Person getOne(Integer id, String name) {
//        return null;
//    }

    /**
     * 这类代码没啥营养，如果校验逻辑稍微多点就会显得臭长臭长的
     * 不看你的执行逻辑，调用者无法知道你的语义。比如调用者不知道返回是是否可能为null，没有形成契约
     * 代码侵入性强
     *
     * @param id
     * @param name
     * @return
     */
    public Person getOne1(Integer id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("id不能为null");
        }
        if (id < 1) {
            throw new IllegalArgumentException("id必须大于等于1");
        }

        // test
        Person result = null;

        // 在结果返回之前校验
        if (result == null) {
            throw new IllegalArgumentException("返回结果不能为null");
        }

        return result;
    }

    /**
     * 代码侵入性强
     *
     * @param id
     * @param name
     * @return
     * @throws NoSuchMethodException
     */
    public @NotNull Person getOne2(@NotNull @Min(1) Integer id, String name) throws NoSuchMethodException {
        Person person = null;
        // 校验逻辑
        Method currMethod = this.getClass().getMethod("getOne2", Integer.class, String.class);
        Set<ConstraintViolation<PersonService>> validResult = obtainExecutableValidator().validateParameters(this, currMethod, new Object[]{id, name});
        if (!validResult.isEmpty()) {
            // ... 输出错误详情validResult
            validResult.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
            throw new IllegalArgumentException("参数错误");
        }
        return person;
    }

    public @NotNull Person getOne3(@NotNull @Min(1) Integer id, String name) throws NoSuchMethodException {
        // ... 模拟逻辑执行，得到一个result
        Person result = null;

        // 在结果返回之前校验
        Method currMethod = this.getClass().getMethod("getOne", Integer.class, String.class);
        Set<ConstraintViolation<PersonService>> validResult = obtainExecutableValidator().validateReturnValue(this, currMethod, result);
        if (!validResult.isEmpty()) {
            // ... 输出错误详情validResult
            validResult.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
            throw new IllegalArgumentException("参数错误");
        }
        return result;
    }

    public Person save(@NotNull @Valid Person person) throws NoSuchMethodException {
        Method currMethod = this.getClass().getMethod("save", Person.class);
        Set<ConstraintViolation<PersonService>> validResult = obtainExecutableValidator().validateParameters(this, currMethod, new Object[]{person});
        if (!validResult.isEmpty()) {
            // ... 输出错误详情validResult
            validResult.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
            throw new IllegalArgumentException("参数错误");
        }
        return person;
    }


}
