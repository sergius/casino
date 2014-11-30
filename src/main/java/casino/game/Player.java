package casino.game;


import casino.game.strings.ResultFormatter;

public class Player {

    private final String name;
    private double totalWin;
    private double totalBet;

    public Player(final String name, final double totalWin, final double totalBet) {
        this.name = name;
        this.totalWin = totalWin;
        this.totalBet = totalBet;
    }

    public Player(final String name) {
        this(name, 0.0, 0.0);
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public double totalWin() {
        return totalWin;
    }

    public double totalBet() {
        return totalBet;
    }

    /**
     * This method applies the outcome of the (last) round
     * of game on the status of a player. If a player didn't
     * make bets, his status doesn't change.
     * @param playerResult An instance of a PlayerResult
     */
    public void apply(final PlayerResult playerResult) {
        final Bet bet = playerResult.bet();
        if(playerResult.win()) {
            totalWin += (bet.target().payoff() * bet.amount());
        }
        totalBet += bet.amount();
    }

    public String toString() {
        return ResultFormatter.gameTotalsRow(name, String.valueOf(totalWin), String.valueOf(totalBet));
    }
}
