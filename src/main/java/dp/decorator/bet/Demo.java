package dp.decorator.bet;

import dp.decorator.bet.bo.BetHandlerDTO;
import dp.decorator.bet.bo.BetResultDTO;
import dp.decorator.bet.bo.Player;
import dp.decorator.bet.bo.TemplateBet;
import dp.decorator.bet.factory.validator.BetValidatorFactory;
import dp.decorator.bet.handler.BetValidator;
import dp.decorator.bet.handler.ConfirmBetCreator;
import dp.decorator.bet.handler.DataPersistenceHandler;
import dp.decorator.bet.handler.OTLMaintainer;

public class Demo {
    public static void main(String[] args) {
        final var transferWalletDTO = BetHandlerDTO.builder()
                .player(Player.builder().build())
                .templateBet(TemplateBet.builder().build())
                .build();

        // Mock spring
        Object redis = new Object();
        OTLMaintainer otlMaintainer = new OTLMaintainer(redis);
        BetValidator betValidator = new BetValidator(new BetValidatorFactory());
        ConfirmBetCreator confirmBetCreator = new ConfirmBetCreator();
        DataPersistenceHandler dataPersistenceHandler = new DataPersistenceHandler();
        BetExecutor betExecutor = new BetExecutor(otlMaintainer, betValidator, confirmBetCreator, dataPersistenceHandler);

        BetResultDTO result1 = betExecutor.bet(transferWalletDTO);
        System.out.println("result1: " + result1);
        System.out.println("====================================");

        BetResultDTO result2 = betExecutor.betForValidatorFirst(transferWalletDTO);
        System.out.println("Result2: " + result2);
    }
}
