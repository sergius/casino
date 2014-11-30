package casino.game.roulette;

import casino.game.Bet;
import casino.game.BetFactory;
import casino.game.BetTarget;
import casino.game.Player;

/**
 * Class that together with RouletteBetTargetFactory is used to
 * create Roulette bets
 */
class RouletteBetFactory implements BetFactory {

    public static final String SEPARATOR = "\\s+";
    private static final int DEFAULT_INPUT_LENGTH = 3;
    private static final int PLAYER_NAME_INDEX = 0;
    private static final int BET_TARGET_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final double DEFAULT_INVALID_AMOUNT = -1.0;
    private final Roulette roulette;
    private final RouletteBetTargetFactory betTargetFactory = new RouletteBetTargetFactory();

    RouletteBetFactory(final Roulette roulette) {
        this.roulette = roulette;
    }

    @Override
    public Bet fromInput(final String input) {

        String[] inputElements = input.trim().split(SEPARATOR);

        if (inputElements.length == DEFAULT_INPUT_LENGTH) {
           return tryToCreateFrom(inputElements);
        }

        return null;
    }

    private Bet tryToCreateFrom(final String[] inputElements) {

        final Player player = roulette.playerByName(inputElements[PLAYER_NAME_INDEX].trim());

        if (player == null) {
            return null;
        }

        final BetTarget betTarget = betTargetFactory.createFrom(inputElements[BET_TARGET_INDEX].trim());
        if (betTarget == null) {
            return null;
        }

        final double amount = validateDouble(inputElements[AMOUNT_INDEX].trim());
        if (amount < 0) {
            return null;
        }

        return new Bet(player, betTarget, amount);
    }

    private double validateDouble(final String doubleString) {
        try {
            return Double.valueOf(doubleString);
        } catch (NumberFormatException ex) {
            return DEFAULT_INVALID_AMOUNT;
        }
    }
}
