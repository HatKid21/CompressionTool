package test;

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;

public class TestDataGenerator {

    public void create() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("test.txt"))) {
            StringBuilder builder = new StringBuilder();
            builder.repeat('C', 32);
            builder.repeat('D', 42);
            builder.repeat('E', 120);
            builder.repeat('K', 7);
            builder.repeat('L', 42);
            builder.repeat('M', 24);
            builder.repeat('U', 37);
            builder.repeat('Z', 2);
            bufferedWriter.write(builder.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
