package lombok;

import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NonFinal
@Value
public class Child extends Parent {
    String address;
}
