package dp.decorator.bet.handler;

import dp.decorator.bet.bo.BetHandlerDTO;
import dp.decorator.bet.bo.BetResultDTO;
import dp.decorator.bet.factory.validator.BetValidatorFactory;

import java.math.BigDecimal;

public class BetExecutor {
    public BetResultDTO bet(BetHandlerDTO betHandlerDTO) {

        final IBetHandler betHandler = new OTLMaintainer(
                new BetValidator(
                        new DataPersistenceHandler(), new BetValidatorFactory()
                )
        );

        BigDecimal balance = betHandler.handle(betHandlerDTO);

        return BetResultDTO.builder()
                .afterBalance(balance)
                .build();
    }

    public BetResultDTO betForValidatorFirst(BetHandlerDTO betHandlerDTO) {

        final IBetHandler betDecorators = new BetValidator(
                new OTLMaintainer(
                        new DataPersistenceHandler()), new BetValidatorFactory()
        );

        BigDecimal balance = betDecorators.handle(betHandlerDTO);

        return BetResultDTO.builder()
                .afterBalance(balance)
                .build();
    }

}
