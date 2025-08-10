package test;

import net.hatkid.Compressor;
import net.hatkid.Decoder;
import net.hatkid.FileComparator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class CompressionTest {

    private static final Logger LOGGER = Logger.getLogger(CompressionTest.class.getName());

    public static void run(String path) throws IOException {
        TestDataGenerator test = new TestDataGenerator();
        test.create();
        String compressedPath = path.replace(".txt", "_compressed.txt");
        String decompressedPath = path.replace(".txt", "_decompressed.txt");
        Compressor compressor = new Compressor();
        compressor.compress(path);
        FileComparator.compareBySize(path, compressedPath);
        Decoder decoder = new Decoder();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(decompressedPath))) {
            String decodedText = decoder.decode(compressedPath);
            writer.write(decodedText);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        boolean isSame = FileComparator.isSame(path, decompressedPath);
        if (isSame) {
            LOGGER.info(String.format("Files %s and %s are equal", path, decompressedPath));
        } else {
            LOGGER.info(String.format("Files %s and %s are NOT equal", path, decompressedPath));
        }

    }

}
