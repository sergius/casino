package casino.persistence;

import casino.persistence.csv.CSVFileGameRepository;

import java.io.File;

/**
 * This class is a factory where different types of repositories
 * should be created.
 */
public class Persistence {

    public static GameRepository loadFrom(final File file) {
        return new CSVFileGameRepository(file);
    }
}