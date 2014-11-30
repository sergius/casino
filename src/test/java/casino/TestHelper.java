package casino;

import java.io.*;

public class TestHelper {

    public final static String DUMMY_FILE_NAME = "/dummy";
    public static final File dummyFile = resourceFile(DUMMY_FILE_NAME);


    public static File initFileFromContent(final String... content) throws IOException {

        cleanFile(dummyFile);

        final StringBuilder stringContent = new StringBuilder();
        for (String string : content) {
            stringContent.append(string).append("\n");
        }

        FileWriter writer = new FileWriter(dummyFile);
        writer.write(stringContent.toString());
        writer.close();
        return dummyFile;
    }

    private static void cleanFile(final File file) throws FileNotFoundException {
        new PrintWriter(file).close();
    }

    private static File resourceFile(final String resourceFileName) {
        String filePath = new TestHelper().getClass().getResource(resourceFileName).getFile();
        return new File(filePath);
    }
}
