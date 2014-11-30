package casino.game;


import casino.game.strings.ResultFormatter;

import static casino.game.GameMessages.LOSE;
import static casino.game.GameMessages.WIN;
import static casino.game.GameMessages.getString;

/**
 * PlayerResult holds the data for a player's bet
 * and the winning BetTargets.
 */
public class PlayerResult {
    public static final double NO_PAY = 0.0;
    private final Bet bet;
    private final BetTarget[] winningTargets;

    public PlayerResult(final Bet bet, final BetTarget[] winningTargets) {
        this.bet = bet;
        this.winningTargets = winningTargets;
    }

    Bet bet() {
        return bet;
    }

    /**
     * This method creates a String which contains the details
     * about the outcome of the bet made.
     * @return The String with created content
     */
    public String toString() {
        if (win()) {
            return ResultFormatter.gameResultRow(bet.player().name(),
                    bet.target().valueToString(),
                    getString(WIN.name()),
                    payoff());
        } else {
            return ResultFormatter.gameResultRow(bet.player().name(),
                    bet.target().valueToString(),
                    getString(LOSE.name()),
                    String.valueOf(NO_PAY));
        }
    }

    boolean win() {
        for(BetTarget betTarget : winningTargets){
            if(betTarget.equals(bet.target())) {
                return true;
            }
        }
        return false;
    }

    String payoff() {
        return String.valueOf(bet.amount() * bet.target().payoff());
    }
}
