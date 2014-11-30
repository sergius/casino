package casino.persistence.csv;

/**
 * Represents elements in a line of a CSV file in
 * an established order.
 */
enum LineElement {
    NAME(0), TOTAL_WIN(1), TOTAL_BET(2);

    private final int index;

    private LineElement(final int index) {
        this.index = index;
    }

    int index() {
        return index;
    }
}
