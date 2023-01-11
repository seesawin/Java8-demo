package validation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ToString
@Setter
@Getter
public class Person {

    @NotNull
    public String name;
    @NotNull
    @Min(0)
    public Integer age;
}