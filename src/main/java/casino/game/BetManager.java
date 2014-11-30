package casino.game;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This is a class of inner use, that takes care of placing the bets
 * and should be used by game
 */
public class BetManager {

    private final List<Bet> betList = new ArrayList<Bet>();
    private final BlockingQueue<Bet> betQueue = new LinkedBlockingQueue<Bet>();
    private boolean accepting = false;

    BetManager() {
        Registrar registrar = new Registrar(betQueue, betList);
        new Thread(registrar).start();
    }

    public void acceptBets(final boolean accept) {
        accepting = accept;
    }

    /**
     * Returns the current bets in game. If called when a round of a game
     * isn't finished, may be different to the final amount of bets, as
     * during the game new bets may be placed.
     * @return The Collection of the current bets
     */
    public Collection<Bet> currentBets() {
        final ArrayList<Bet> copy = new ArrayList<Bet>();
        copy.addAll(betList);
        return copy;
    }

    boolean placeBet(final Bet bet) {
        if (accepting) {
            return betQueue.offer(bet);
        }
        return false;
    }

    /**
     * Clears the list of bets. This normally occurs
     * when a new round of game is being prepared.
     */
    public void reset() {
        betList.clear();
    }

    /**
     * A helper class that takes the bets from the
     * queue (where they initially enter) and puts
     * them on the list.
     */
    private class Registrar  implements Runnable {

        private final BlockingQueue<Bet> queue;
        private final List<Bet> list;

        public Registrar(final BlockingQueue<Bet> betQueue, final List<Bet> betList) {
            this.queue = betQueue;
            this.list = betList;
        }

        @Override
        public void run() {
            while(true) {
                if(accepting) {
                    final Bet bet;
                    try {
                        bet = queue.take();
                        list.add(bet);
                    } catch (InterruptedException e) {
                        //TODO log and notify affected player
                    }
                }
            }
        }
    }
}
