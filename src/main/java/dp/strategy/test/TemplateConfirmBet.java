package dp.strategy.test;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TemplateConfirmBet {
    private String betId;
    private BigDecimal bet;
}
