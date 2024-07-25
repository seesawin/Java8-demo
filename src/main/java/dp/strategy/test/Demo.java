package dp.strategy.test;

import java.math.BigDecimal;

public class Demo {
    public static void main(String[] args) throws BetIdException, BalanceException {
        // Game Provider request to TemplateBet
        TemplateBet templateBet1 = TemplateBet.builder()
                .betId("betId001")
                .bet(new BigDecimal(100))
                .validateBetIdStrategy(betId -> System.out.println("validate betId 001: " + betId))
                .validateBalanceStrategy(bet -> System.out.println("validate balance 001: " + bet))
                .build();
        TemplateBet templateBet2 = TemplateBet.builder()
                .betId("betId002")
                .bet(new BigDecimal(100))
                .build();
        TemplateBet templateBet3 = TemplateBet.builder()
                .betId("betId003")
                .bet(new BigDecimal(100))
                .validateBetIdStrategy(betId -> System.out.println("validate betId 003: " + betId))
                .build();
        TemplateBet templateBet4 = TemplateBet.builder()
                .betId("betId004")
                .bet(new BigDecimal(100))
                .validateBalanceStrategy(bet -> System.out.println("validate balance 004: " + bet))
                .build();
        // BetBO 當作 TemplateBet 的代理，betService 只需要認識 BetBO，betBO 是面向流程的提供 betService 需要的驗證與類別
        BetBO betBO1 = BetBO.builder().templateBet(templateBet1).build();
        BetBO betBO2 = BetBO.builder().templateBet(templateBet2).build();
        BetBO betBO3 = BetBO.builder().templateBet(templateBet3).build();
        BetBO betBO4 = BetBO.builder().templateBet(templateBet4).build();

        // betService 制定下注流程，細節交由 betBO 處理
        Demo.betService(betBO1);
        Demo.betService(betBO2);
        Demo.betService(betBO3);
        Demo.betService(betBO4);

        TemplateBet templateBet5 = TemplateBet.builder()
                .betId("betId005")
                .bet(new BigDecimal(100))
                .validateBetIdStrategy(betId -> {
                    System.out.println("validate betId 005: " + betId);
                    throw new RuntimeException("validate betId 005 exception");
                })
                .validateBalanceStrategy(bet -> System.out.println("validate balance 005: " + bet))
                .build();

        try {
            BetBO betBO5 = BetBO.builder().templateBet(templateBet5).build();
            Demo.betService(betBO5);
        } catch (Exception e) {
            e.printStackTrace();
        }


        TemplateBet templateBet6 = TemplateBet.builder()
                .betId("betId006")
                .bet(new BigDecimal(100))
                .validateBetIdStrategy(betId -> System.out.println("validate betId 006: " + betId))
                .validateBalanceStrategy(bet -> {
                    System.out.println("validate balance 006: " + bet);
                    throw new RuntimeException("validate balance 006 exception");
                })
                .build();

        try {
            BetBO betBO6 = BetBO.builder().templateBet(templateBet6).build();
            Demo.betService(betBO6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void betService(BetBO betBO) throws BetIdException, BalanceException {
        System.out.println("====================================");
        betBO.validateBalance(new BigDecimal(999));
        betBO.validateBetId();
        System.out.println(betBO.buildConfirmBet());
    }
}
