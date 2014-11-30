package casino.persistence.csv;


import casino.game.Player;
import casino.game.exceptions.GameException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import static casino.TestHelper.dummyFile;
import static casino.TestHelper.initFileFromContent;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CSVFileGameRepositoryTest {


    @Before
    public void clean() {
        try {
            new PrintWriter(dummyFile).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void should_load_a_list_with_one_player_from_file_with_one_player() throws GameException, IOException {
        final String name = "Tiki_Monkey";
        final String playerString = name + ", 80.0,160.0";
        final File testFile = initFileFromContent(playerString);

        final Player player = new Player(playerString.split(",")[0].trim());

        final Collection<Player> resultPlayersList = new CSVFileGameRepository(testFile).allPlayers();

        assertThat(resultPlayersList, hasItem(player));
        assertThat(resultPlayersList.toArray(new Player[resultPlayersList.size()])[0].name(), is(name));
    }

    @Test
    public void should_load_a_list_with_two_players_from_file_with_two_players() throws GameException, IOException {
        final String name1 = "Tiki_Monkey"; final double win1 = 80.0; final double bet1 = 160.0;
        final String name2 = "Barbara"; final double win2 = 80.0; final double bet2 = 65.0;
        final String playerString1 = name1 + ", " + win1 + "," + bet1;
        final String playerString2 = name2 + ", " + win2 + "," + bet2;
        final File testFile = initFileFromContent(playerString1, playerString2);

        final Collection<Player> resultPlayersList = new CSVFileGameRepository(testFile).allPlayers();

        Player[] resultPlayersArray = resultPlayersList.toArray(new Player[resultPlayersList.size()]);
        final Player player1 = resultPlayersArray[0];
        assertThat(player1.name(), is(name1));
        assertThat(player1.totalWin(), is(win1));
        assertThat(player1.totalBet(), is(bet1));

        final Player player2 = resultPlayersArray[1];
        assertThat(player2.name(), is(name2));
        assertThat(player2.totalWin(), is(win2));
        assertThat(player2.totalBet(), is(bet2));
    }

    @Test
    public void should_load_a_list_of_two_players_with_only_names_no_totals() throws IOException {
        final String name1 = "Tiki_Monkey";
        final String name2 = "Barbara";
        final File testFile = initFileFromContent(name1, name2);

        final Collection<Player> resultPlayersList = new CSVFileGameRepository(testFile).allPlayers();

        Player[] resultPlayersArray = resultPlayersList.toArray(new Player[resultPlayersList.size()]);
        final Player player1 = resultPlayersArray[0];
        assertThat(player1.name(), is(name1));

        final Player player2 = resultPlayersArray[1];
        assertThat(player2.name(), is(name2));
    }
}
