package casino.game.roulette;

import casino.game.BetTarget;

class NumberTarget implements BetTarget {

    public static final double PAYOFF = 36.0;
    private final int number;

    NumberTarget(final int number) {
        this.number = number;
    }

    @Override
    public double payoff() {
        return PAYOFF;
    }

    @Override
    public String valueToString() {
        return String.valueOf(number);
    }

    @Override
    public boolean equals(final BetTarget other) {
        try {
            return this.number == Integer.valueOf(other.valueToString());
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
