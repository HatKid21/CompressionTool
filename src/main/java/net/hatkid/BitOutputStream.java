package net.hatkid;

import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream implements AutoCloseable {

    private final OutputStream outputStream;
    private int currentByte;
    private int bitIndex;

    public BitOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.currentByte = 0;
        this.bitIndex = 0;
    }

    public void writeBit(int bit) throws IOException {
        if (bit != 0 && bit != 1) {
            throw new IllegalArgumentException("Bit must be 0 or 1");
        }

        if (bit == 1) {
            currentByte |= (1 << (7 - bitIndex));
        }

        bitIndex++;

        if (bitIndex == 8) {
            outputStream.write(currentByte);
            currentByte = 0;
            bitIndex = 0;
        }

    }

    public void flush() throws IOException {
        if (bitIndex > 0) {
            outputStream.write(currentByte);
            currentByte = 0;
            bitIndex = 0;
        }
        outputStream.flush();
    }

    @Override
    public void close() throws Exception {
        flush();
        outputStream.close();
    }
}
