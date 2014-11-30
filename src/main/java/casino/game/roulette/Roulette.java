package casino.game.roulette;

import casino.game.*;
import casino.game.exceptions.GameException;
import casino.game.strings.ResultFormatter;
import casino.persistence.GameRepository;
import sun.net.www.content.text.plain;

import java.util.Random;

import static casino.game.GameMessages.WIN_NUMBER;
import static casino.game.GameMessages.getString;




public class Roulette extends Game {

    public static final int ROULETTE_MIN = 0;
    public static final int ROULETTE_MAX = 36;

    private final static int DEFAULT_INTERVAL_SEC = 30;

    private final Thread spinnerThread;


    private int spinInterval = DEFAULT_INTERVAL_SEC;
    private boolean playing;


    public Roulette(final GameRepository csvFileGameRepository) throws GameException {
        super(csvFileGameRepository, new RouletteBetTargetFactory());
        spinnerThread = new Thread(new Spinner());
    }

    @Override
    public void start() {
        if (!playing) {
            playing = true;
            spinnerThread.start();
        }
    }

    @Override
    public void stop() {
        acceptBets(false);
    }

    @Override
    public String gameResultToString(final GameResult result) {
        final StringBuilder gameResult = new StringBuilder();
        gameResult.append(winningNumberHeader(result))
                  .append(ResultFormatter.defaultGameResultHeader());

        for (PlayerResult playerResult : lastPlayerResults) {
            gameResult.append(playerResult.toString());
        }

        return "\n" + gameResult.toString() + "\n";
    }

    private String winningNumberHeader(GameResult result) {
        final StringBuilder winningTargets = new StringBuilder();
        for(BetTarget betTarget : result.allWinningTargets()) {
            winningTargets.append(betTarget.valueToString()).append(" ");
        }

        return getString(WIN_NUMBER.name(), winningTargets.toString());
    }

    @Override
    protected BetFactory betFactory() {
        return new RouletteBetFactory(this);
    }

    public void spinInterval(final int spinInterval) {
        this.spinInterval = spinInterval;
    }


    /**
     * This class represents the actual physical roulette where the
     * ball is spun for every game. Using casino jargon we will call
     * 1 roulette game: 1 spin.
     * It is an inner class because the physical roulette has no
     * sense without the table (the game). On contrary, they are
     * totally coupled. The same logic is sound here.
     */
    private class Spinner implements Runnable {
        final Random random = new Random();

        @Override
        public void run() {
            while (playing) {
                try {
                    initSpin();
                    spinning();
                    haltBets();
                    spinEnd();
                } catch (InterruptedException e) {
                    // TODO log
                }
            }
        }

        /**
         * Tasks at the beginning of each spin.
         */
        private void initSpin() {
            lastPlayerResults.clear();
            betManager.acceptBets(true);
        }

        /**
         * The pause that represents the time while the ball is spinning
         * inside the roulette and the bets are being placed.
         * @throws InterruptedException Thrown if interrupted while spinning.
         */
        private void spinning() throws InterruptedException {
            Thread.sleep(spinInterval * 1000);
        }

        /**
         * Informs the BetManager to stop accepting the bets.
         */
        private void haltBets() {
            betManager.acceptBets(false);
        }

        /**
         * Necessary tasks after every spin.
         */
        private void spinEnd() {
            lastResult = new RouletteResult(winningNumber());
            storeResult(lastResult);
            reportResult(lastResult);
            betManager.reset();
        }

        private int winningNumber() {
            return random.nextInt(ROULETTE_MAX + 1);
        }

        /**
         * Stores the result to persistence and in locally (in Game memory).
         * Runs in a separate thread, in order to avoid blocking the Spinner's main
         * thread in case persisting lasts (due to volumes or connection).
         * @param newResult The new result to be stored.
         */
        private void storeResult(final GameResult newResult) {
            for (Bet bet : betManager.currentBets()) {
                PlayerResult playerResult = new PlayerResult(bet, newResult.allWinningTargets());
                lastPlayerResults.add(playerResult);
                bet.player().apply(playerResult);
            }
            gameRepository.save(players);
        }

        protected void reportResult(final GameResult result) {
            notifyGameResultsListeners(gameResultToString(result));
            notifyGameResultsListeners(playersTotalsToString());
        }

        private void notifyGameResultsListeners(final String totals) {
            for (GameResultsListener listener : listeners) {
                listener.update(totals);
            }
        }
    }
}
