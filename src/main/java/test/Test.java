package test;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        TestDataGenerator testDataGenerator = new TestDataGenerator();
        testDataGenerator.create();
        try {
            CompressionTest.run("test.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}