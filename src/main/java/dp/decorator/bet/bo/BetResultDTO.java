package dp.decorator.bet.bo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BetResultDTO {
    private BigDecimal beforeBalance;
    private BigDecimal afterBalance;
}
