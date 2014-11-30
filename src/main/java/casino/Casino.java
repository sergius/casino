package casino;

import casino.game.Bet;
import casino.game.Game;
import casino.game.GameResultsListener;
import casino.game.exceptions.GameException;
import casino.game.roulette.Roulette;
import casino.persistence.GameRepository;
import casino.persistence.Persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import static casino.game.Game.aBetFor;

public class Casino implements GameResultsListener {

    private static final String REQUEST_TO_EXIT = "exit";
    private static final String RETRY_OR_EXIT = "Please, insert a different path or exit " +
                                                "(typing '" + REQUEST_TO_EXIT + "'):";

    private final Game game;

    public static void main(String[] args) {
        try {
            System.out.println("Starting game...");
            Casino casino = new Casino(args);
            casino.start();
            casino.stop();

        } catch (IOException e) {
            //TODO log
        }
    }

    public Casino(final String[] args) {
        game = tryLoadingGameUsing(firstElementFrom(args));
        game.addListener(this);
    }

    /**
     * Returns the first element of an array of Strings.
     * If the array is null or has no elements, returns
     * an empty String.
     * @param array Array of Strings.
     * @return The first element, if exists or empty String.
     */
    private static String firstElementFrom(final String[] array) {
        if (array == null || array.length == 0) {
            return "";
        } else {
            return array[0];
        }
    }

    /**
     * Will try to load the players' list from a file whose path
     * should be provided as parameter. If not found, will retry from a command
     * line prompt. The user can choose to exit the program.
     * @param initFilePath Path to the init file with players' list
     */
    private Game tryLoadingGameUsing(final String initFilePath) {
        String tryPath = initFilePath;

        while(true) {
            File tryFile = new File(tryPath);
            try {

                return loadGameFrom(tryFile);

            } catch (GameException e) {
                System.out.println("ERROR: " + e.getMessage());

                tryPath = changeFilePathOrExit();
            }
        }
    }

    /**
     * Loads the game from a file with player names.
     * @param file The file that is supposed to contain the players' list.
     * @return The game with its players loaded. It will be initialized
     * but not started at this point.
     * @throws GameException If file not found, if error reading file or
     * if no player names were found in the file's content.
     */
    private Game loadGameFrom(final File file) throws GameException {
        final GameRepository gameRepository = Persistence.loadFrom(file);
        return new Roulette(gameRepository);
    }

    /**
     * Will prompt for a file path or user choose to exit.
     */
    private String changeFilePathOrExit() {
        final Scanner scanner = new Scanner(System.in);
        System.out.println(RETRY_OR_EXIT);
        final String input = scanner.nextLine();

        if (input.equals(REQUEST_TO_EXIT)) {
            stop();
        }
        return input;
    }

    private void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        boolean working = true;
        while (working) {
            game.start();

            final String line = reader.readLine(); //waiting for bets
            if (line.equals(REQUEST_TO_EXIT)) {
                working = false;
            } else {
                tryToPlaceABetFromInput(line);
            }
        }

    }

    private void tryToPlaceABetFromInput(final String line) {
        final Bet bet = aBetFor(game).fromInput(line);
        if (bet != null) {
            game.placeBet(bet);
        }
    }

    private void stop() {
        System.exit(0);
    }

    @Override
    public void update(final String totals) {
        System.out.println(totals);
    }
}
