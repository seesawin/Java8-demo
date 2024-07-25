package dp.decorator.bet.handler;

import dp.decorator.bet.bo.BetHandlerDTO;

import java.math.BigDecimal;

public interface IBetHandler {
    BigDecimal handle(BetHandlerDTO betHandlerDTO);
}
