package casino.game;

/**
 * A listener interface for classes that need to
 * receive the results of the game.
 */
public interface GameResultsListener {

    void update(String totals);
}
