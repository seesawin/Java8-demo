package dp.decorator.bet.handler;

import dp.decorator.bet.bo.BetHandlerDTO;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public final class DataPersistenceHandler implements IBetHandler {

    private final IBetHandler next;

    @Override
    public BigDecimal handle(BetHandlerDTO betHandlerDTO) {
        System.out.println("bet check balance");
        System.out.println("bet confirmBet");
        System.out.println("bet statementTransaction");
        System.out.println("bet OTL");

        betHandlerDTO.setNewBalance("100");

        return BigDecimal.valueOf(100L);
    }

}
