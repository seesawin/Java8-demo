package lombok;

import lombok.experimental.SuperBuilder;


@SuperBuilder
@Data
@ToString(callSuper = true)
public class Child2 extends Parent2 {
    String address;
}
