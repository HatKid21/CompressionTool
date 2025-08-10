package net.hatkid;

import java.io.IOException;
import java.io.InputStream;

public class BitInputStream implements AutoCloseable {

    private final InputStream inputStream;
    private int currentByte;
    private int bitIndex;
    private boolean endOfStream;

    public BitInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        this.currentByte = 0;
        this.bitIndex = 0;
        this.endOfStream = false;
    }

    public int readBit() throws IOException {
        if (endOfStream) {
            return -1;
        }

        if (bitIndex == 8) {
            currentByte = inputStream.read();
            if (currentByte == -1) {
                endOfStream = true;
                return -1;
            }
            bitIndex = 0;
        }

        int bit = (currentByte >> (7 - bitIndex)) & 1;
        bitIndex++;
        return bit;
    }

    public void skipBits(int n) throws IOException {
        for (int i = 0; i < n; i++) {
            readBit();
        }
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
