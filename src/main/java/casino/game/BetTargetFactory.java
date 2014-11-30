package casino.game;

/**
 * This interface should be implemented by each Game
 * in order to create specific bets from String input
 */
public interface BetTargetFactory {

    BetTarget createFrom(String string);
}
