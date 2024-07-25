package dp.strategy.test;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Data
@Builder
public class TemplateBet {
    private String betId;
    private BigDecimal bet;
    private Consumer<BigDecimal> validateBalanceStrategy;
    private Consumer<String> validateBetIdStrategy;

    @Override
    public String toString() {
        return "TemplateBet{" +
                "betId='" + betId + '\'' +
                ", bet=" + bet +
                '}';
    }
}
