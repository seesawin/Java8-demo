package lombok;

import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NonFinal
@Value
@ToString(callSuper = true)
public class Parent {
    Integer id;
    String name;
}
