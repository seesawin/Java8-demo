package serialize;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Person implements Serializable {
    private int age;
    private String name;
    private int sex;
}
