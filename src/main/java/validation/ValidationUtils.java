package validation;

import org.hibernate.validator.internal.engine.DefaultClockProvider;
import org.hibernate.validator.internal.engine.DefaultParameterNameProvider;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.util.Set;

/**
 * 获得Validator实例的两种姿势
 */
public class ValidationUtils {
    /**
     * 方式一：工厂直接获取
     *
     * @return
     */
    // 用于Java Bean校验的校验器
    public static Validator obtainValidator() {
        // 1、使用【默认配置】得到一个校验工厂  这个配置可以来自于provider、SPI提供
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        // 2、得到一个校验器
        return validatorFactory.getValidator();
    }

    // 用于方法校验的校验器
    public static ExecutableValidator obtainExecutableValidator() {
        return obtainValidator().forExecutables();
    }

    public static <T> void printViolations(Set<ConstraintViolation<T>> violations) {
        violations.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
    }

    /**
     * 方式二：从上下文获取
     * 校验器上下文也就是ValidatorContext喽，它的步骤是先得到上下文实例，然后做定制，再通过上下文实例创建出Validator校验器实例了。
     *
     * 这两种方式结合起来，不就是典型的默认 + 定制扩展的搭配麽？另外，Validator是线程安全的，一般来说一个应用只需要初始化一个 Validator实例即可，
     * 所以推荐使用方式二进行初始化，对个性扩展更友好。
     *
     * @return
     */
    public static Validator obtainCustomizedValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.usingContext()
                .parameterNameProvider(new DefaultParameterNameProvider())
                .clockProvider(DefaultClockProvider.INSTANCE)
                .addValueExtractor(new ResultValueExtractor())
                .getValidator();
        return validator;
    }

}
