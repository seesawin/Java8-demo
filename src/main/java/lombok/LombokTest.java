package lombok;

public class LombokTest {

    public static void main(String[] args) {
        System.out.println(Child.builder()
                .id(1)
                .name("name")
                .address("address").build());
        System.out.println(Child2.builder()
                .id(1)
                .name("name")
                .address("address").build());
    }

}
