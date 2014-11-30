package casino.game.exceptions;


public class GameException extends RuntimeException {

    public GameException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GameException(final String message) {
        super(message);
    }
}
