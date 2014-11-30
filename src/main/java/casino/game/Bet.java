package casino.game;

public class Bet {

    private final BetTarget target;
    private final double amount;
    private final Player player;

    /**
     *
     * @param player The player that is betting
     * @param target The specific target of the bet (eg. number or odd/even)
     * @param amount The amount played
     */
    public Bet (final Player player, final BetTarget target, final double amount) {
        this.player = player;
        this.target = target;
        this.amount = amount;
    }

    public Player player() {
        return player;
    }

    public BetTarget target() {
        return target;
    }

    public double amount() {
        return amount;
    }
}
