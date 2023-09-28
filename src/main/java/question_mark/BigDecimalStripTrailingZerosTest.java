package question_mark;

import java.math.BigDecimal;

public class BigDecimalStripTrailingZerosTest {
    public static void main(String[] args) {
        final var bi3 = new BigDecimal("100.1100000").stripTrailingZeros();
        final var bi4 = new BigDecimal("100.1100").stripTrailingZeros();
        System.out.println(bi3.equals(bi4));
    }
}
