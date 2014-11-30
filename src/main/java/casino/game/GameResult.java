package casino.game;

/**
 * Represents the set of all the winning game
 * combinations, i.e. all winning targets.
 */
public interface GameResult {

    BetTarget[] allWinningTargets();
}
