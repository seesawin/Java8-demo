package dp.decorator.bet;

import dp.decorator.bet.bo.BetHandlerDTO;
import dp.decorator.bet.bo.BetResultDTO;
import dp.decorator.bet.bo.ConfirmBet;
import dp.decorator.bet.bo.Player;
import dp.decorator.bet.handler.BetExecutor;

public class Demo {
    public static void main(String[] args) {
        final var transferWalletDTO = BetHandlerDTO.builder()
                .player(Player.builder().build())
                .confirmBet(ConfirmBet.builder().build())
                .build();

        BetExecutor betExecutor = new BetExecutor();
        BetResultDTO result1 = betExecutor.bet(transferWalletDTO);
        System.out.println("result1: " + result1);
        System.out.println("====================================");

        BetResultDTO result2 = betExecutor.betForValidatorFirst(transferWalletDTO);
        System.out.println("Result2: " + result2);
    }
}
