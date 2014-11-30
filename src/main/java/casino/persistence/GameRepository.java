package casino.persistence;


import casino.game.Player;
import casino.game.exceptions.GameException;

import java.util.Collection;

/**
 * Interface that should be implemented by all
 * the types of repositories available.
 */
public interface GameRepository {

    Collection<Player> allPlayers() throws GameException;

    void save(Collection<Player> players);
}
