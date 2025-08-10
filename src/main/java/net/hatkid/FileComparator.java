package net.hatkid;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class FileComparator {

    private static final Logger LOGGER = Logger.getLogger(FileComparator.class.getName());

    private static int getByteSize(String path) {
        try {
            return Files.readAllBytes(Path.of(path)).length;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void compareBySize(String path1, String path2) {
        int byteSize1 = getByteSize(path1);
        int byteSize2 = getByteSize(path2);
        if (byteSize1 > byteSize2) {
            LOGGER.info(path1 + " is larger than " + path2 + " by " + (byteSize1 - byteSize2) + " bytes");
        } else if (byteSize1 == byteSize2) {
            LOGGER.info(path1 + " and " + path2 + " are equal and weights " + byteSize1 + " bytes");
        } else {
            LOGGER.info(path2 + " is larger than " + path1 + " by " + (byteSize2 - byteSize1) + " bytes");
        }

    }

    public static boolean isSame(String path1, String path2) throws IOException {
        long size1 = Files.size(Path.of(path1));
        long size2 = Files.size(Path.of(path2));
        if (size1 != size2) {
            return false;
        }
        try (FileInputStream fis1 = new FileInputStream(path1);
             FileInputStream fis2 = new FileInputStream(path2)) {
            int byte1, byte2;
            while ((byte1 = fis1.read()) != -1 && (byte2 = fis2.read()) != -1) {
                if (byte1 != byte2) {
                    return false;
                }
            }
        }
        return true;

    }

}
