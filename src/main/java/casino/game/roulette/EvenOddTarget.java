package casino.game.roulette;

import casino.game.BetTarget;


class EvenOddTarget implements BetTarget {

    static final double PAYOFF = 2.0;

    enum Type {
        EVEN, ODD;

        static Type byName(final String name) {
            for (Type type : values()) {
                if(type.name().equals(name)) {
                    return type;
                }
            }
            return null;
        }
    }

    private final Type type;

    EvenOddTarget(final Type type) {
        this.type = type;
    }

    @Override
    public double payoff() {
        return PAYOFF;
    }

    @Override
    public String valueToString() {
        return type.name();
    }

    @Override
    public boolean equals(final BetTarget other) {
        return this.valueToString().equals(other.valueToString());
    }
}
