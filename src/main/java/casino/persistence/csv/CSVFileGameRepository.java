package casino.persistence.csv;


import casino.game.exceptions.GameException;
import casino.game.exceptions.GameIOException;
import casino.game.exceptions.MissingPlayersListException;
import casino.persistence.GameRepository;
import casino.game.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class that facilitates the operations to retrieve data from and
 * store data to CSV files.
 */
public class CSVFileGameRepository implements GameRepository {

    private final File csvFile;

    public CSVFileGameRepository(final File csvFile) {
        this.csvFile = csvFile;
    }

    @Override
    public Collection<Player> allPlayers() throws GameException {
        return extractPlayers(readFrom(csvFile));
    }

    @Override
    public void save(Collection<Player> players) {
        new Thread(new StorageWorker(players)).start();
    }

    private Collection<Player> extractPlayers(final Collection<String> lines) throws MissingPlayersListException {
        List<Player> players = new ArrayList<Player>();

        for(String line : lines) {
            Player player = playerFromLine(line);
            if (player != null) {
                players.add(player);
            }
        }

        if (players.isEmpty()) {
            throw new MissingPlayersListException("Players were not found for the game.");
        }

        return players;
    }

    private Player playerFromLine(final String line) {
        String[] lineElements = line.split(",");
        if (lineElements.length == 0) {
            return null;
        }
        if (lineElements.length == LineElement.values().length) {
            final String name = lineElements[LineElement.NAME.index()];
            final String totalWin = lineElements[LineElement.TOTAL_WIN.index()];
            final String totalBet = lineElements[LineElement.TOTAL_BET.index()];
            return new Player(name, validate(totalWin), validate(totalBet));
        } else {
            return new Player(lineElements[LineElement.NAME.index()]);
        }
    }

    private double validate(final String string) {
        try {
            return Double.valueOf(string);
        } catch (NumberFormatException ex) {
            return 0.0;
        }
    }

    private Collection<String> readFrom(final File file) throws GameIOException {
        final List<String> lines = new ArrayList<String>();

        try {

            final BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while((line = reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();

        } catch (IOException e) {
            throw new GameIOException(e.getMessage(), e.getCause());
        }

        return lines;
    }

    private class StorageWorker implements Runnable {


        private final Collection<Player> players;

        public StorageWorker(final Collection<Player> players) {
            this.players = players;
        }

        @Override
        public void run() {
            try {
                String coma = ",";
                final StringBuilder content = new StringBuilder();
                for(Player player : players) {
                    content.append(player.name()).append(coma)
                            .append(player.totalWin()).append(coma)
                            .append(player.totalBet())
                            .append("\n");
                }
                final BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
                writer.write(content.toString());
                writer.close();
            } catch (IOException e) {
                //TODO depends of the project requirements,
                // normally would be logged and some action would be taken: retries, notifications, etc.
            }

        }
    }
}
