package net.hatkid;

import net.hatkid.tree.HuffTree;

import java.io.*;
import java.util.Map;
import java.util.logging.Logger;


public class Compressor {

    private static final Logger LOGGER = Logger.getLogger(Compressor.class.getName());


    public void compress(String path) {
        LOGGER.info(path + "'s data compressing in process...");
        createEncodedFile(path);
        LOGGER.info(path + "'s data compressing is done");
    }

    private void createEncodedFile(String path) {
        FileOutputStream fileOutputStream;
        LOGGER.info("Creating symbol codes...");
        Map<Character, Integer> frequency = HuffTree.createTable(new File(path));
        Map<Character, String> data = DFS.search(HuffTree.createTree(frequency));
        File file = new File(path.replace(".txt", "_compressed.txt"));
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (BitOutputStream bitOutputStream = new BitOutputStream(fileOutputStream); BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            LOGGER.info("Writing header...");
            writeHeader(fileOutputStream, frequency);
            LOGGER.info("Writing compressed data...");
            int charInt;
            while ((charInt = bufferedReader.read()) != -1) {
                String codeWord = data.get((char) charInt);
                for (int i = 0; i < codeWord.length(); i++) {
                    if (codeWord.charAt(i) == '0') {
                        bitOutputStream.writeBit(0);
                    } else {
                        bitOutputStream.writeBit(1);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int countPaddingBits(Map<Character, Integer> frequency) {
        HuffTree tree = HuffTree.createTree(frequency);
        Map<Character, String> codes = DFS.search(tree);
        int totalBits = 0;
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            char c = entry.getKey();
            int codeSize = entry.getValue().length();
            totalBits += frequency.get(c) * codeSize;
        }
        int reminder = totalBits % 8;
        if (reminder == 0) {
            return 0;
        }
        return 8 - reminder;
    }

    private void writeHeader(FileOutputStream fileOutputStream, Map<Character, Integer> frequency) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        int codeCount = frequency.size();
        dataOutputStream.writeInt(codeCount);
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            char c = entry.getKey();
            int count = entry.getValue();
            dataOutputStream.writeChar(c);
            dataOutputStream.writeInt(count);
        }
        int paddingBits = countPaddingBits(frequency);
        dataOutputStream.writeInt(paddingBits);
    }

}
