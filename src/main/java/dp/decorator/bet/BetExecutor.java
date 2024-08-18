package dp.decorator.bet;

import dp.decorator.bet.bo.BetHandlerDTO;
import dp.decorator.bet.bo.BetResultDTO;
import dp.decorator.bet.handler.BetValidator;
import dp.decorator.bet.handler.ConfirmBetCreator;
import dp.decorator.bet.handler.DataPersistenceHandler;
import dp.decorator.bet.handler.OTLMaintainer;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class BetExecutor {
    private final OTLMaintainer otlMaintainer;
    private final BetValidator betValidator;
    private final ConfirmBetCreator confirmBetCreator;
    private final DataPersistenceHandler dataPersistenceHandler;

    public BetResultDTO bet(BetHandlerDTO betHandlerDTO) {
        BigDecimal balance = otlMaintainer.setNext(
                        betValidator.setNext(
                                confirmBetCreator.setNext(dataPersistenceHandler)
                        )
                )
                .handle(betHandlerDTO);

        return BetResultDTO.builder()
                .afterBalance(balance)
                .build();
    }

    public BetResultDTO betForValidatorFirst(BetHandlerDTO betHandlerDTO) {
        BigDecimal balance = confirmBetCreator.setNext(
                        betValidator.setNext(
                                otlMaintainer.setNext(dataPersistenceHandler)
                        )
                )
                .handle(betHandlerDTO);

        return BetResultDTO.builder()
                .afterBalance(balance)
                .build();
    }

}
