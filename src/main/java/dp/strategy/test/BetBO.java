package dp.strategy.test;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@Builder
public class BetBO {
    private TemplateBet templateBet;

    public void validateBalance(BigDecimal bet) throws BalanceException {
        try {
            Optional.ofNullable(templateBet.getValidateBalanceStrategy())
                    .ifPresent(it -> it.accept(bet));
        } catch (Exception e) {
            throw new BalanceException(e);
        }
    }

    public void validateBetId() throws BetIdException {
        try {
            Optional.ofNullable(templateBet.getValidateBetIdStrategy())
                    .ifPresent(it -> it.accept(templateBet.getBetId()));
        } catch (Exception e) {
            throw new BetIdException(e);
        }
    }

    public TemplateBet buildConfirmBet() {
        return TemplateBet.builder()
                .betId(templateBet.getBetId())
                .bet(templateBet.getBet())
                .build();
    }
}
