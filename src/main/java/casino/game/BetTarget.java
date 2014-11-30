package casino.game;

/**
 * A target of a bet is the specific combination, number, etc.
 * For example, playing roulette all are roulette bets, but we
 * can distinguish between betting on numbers, even/odd or dozens
 */
public interface BetTarget {

    double payoff();

    String valueToString();

    boolean equals(BetTarget other);
}