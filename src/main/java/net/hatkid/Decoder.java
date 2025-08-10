package net.hatkid;

import net.hatkid.tree.HuffBaseNode;
import net.hatkid.tree.HuffInternalNode;
import net.hatkid.tree.HuffLeafNode;
import net.hatkid.tree.HuffTree;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Decoder {

    private static final Logger LOGGER = Logger.getLogger(Decoder.class.getName());

    public String decode(String path) throws IOException {
        LOGGER.info("Decoding process has started");

        FileInputStream fileInputStream = new FileInputStream(path);

        LOGGER.info("Reading header data...");

        HeaderData headerData = readHeader(fileInputStream);

        Map<Character, Integer> frequency = headerData.frequency();
        int paddingBits = headerData.paddingBits();

        LOGGER.info("Creating Huffman tree data...");

        HuffTree huffTree = HuffTree.createTree(frequency);

        HuffBaseNode curNode = huffTree.getRoot();
        StringBuilder stringBuilder = new StringBuilder();

        LOGGER.info("Reading compressed data...");
        BitInputStream bitInputStream = new BitInputStream(fileInputStream);

        long headerSizeInBytes = fileInputStream.getChannel().position();
        long totalFileSizeInBytes = Files.size(Path.of(path));
        bitInputStream.skipBits(8);

        long totalCompressedBits = (totalFileSizeInBytes - headerSizeInBytes) * 8 - paddingBits;

        try {
            for (int i = 0; i < totalCompressedBits; i++) {
                int bit = bitInputStream.readBit();
                if (bit == -1) {
                    break;
                }
                if (bit == 1) {
                    curNode = ((HuffInternalNode) curNode).getLeftChild();
                } else {
                    curNode = ((HuffInternalNode) curNode).getRightChild();
                }
                if (curNode.isLeaf()) {
                    stringBuilder.append(((HuffLeafNode) curNode).getElement());
                    curNode = huffTree.getRoot();
                }

            }
            LOGGER.info("Decoding process is done");
            return stringBuilder.toString();
        } catch (EOFException e) {
            System.out.println(e.getMessage());
        }
        LOGGER.log(Level.SEVERE, "Something went wrong");
        return "-1";
    }

    private HeaderData readHeader(FileInputStream fileInputStream) throws IOException {
        Map<Character, Integer> frequency = new HashMap<>();
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            char c = dataInputStream.readChar();
            int count = dataInputStream.readInt();
            frequency.put(c, count);
        }
        int paddingBits = dataInputStream.readInt();
        return new HeaderData(frequency, paddingBits);
    }

}
