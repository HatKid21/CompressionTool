package net.hatkid.tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffTree implements Comparable {

    private final HuffBaseNode root;

    public HuffTree(char el, int weight) {
        root = new HuffLeafNode(el, weight);
    }

    public HuffTree(HuffBaseNode left, HuffBaseNode right, int weight) {
        root = new HuffInternalNode(left, right, weight);
    }

    public HuffBaseNode getRoot() {
        return root;
    }

    public int getWeight() {
        return root.weight();
    }

    public static HuffTree createTree(Map<Character, Integer> data) {
        PriorityQueue<HuffTree> nodes = new PriorityQueue<>();
        for (char key : data.keySet()) {
            nodes.add(new HuffTree(key, data.get(key)));
        }
        HuffTree tmp1, tmp2, tmp3 = null;
        while (nodes.size() > 1) {
            tmp1 = nodes.poll();
            tmp2 = nodes.poll();
            assert tmp2 != null;
            tmp3 = new HuffTree(tmp1.root, tmp2.root, tmp1.getWeight() + tmp2.getWeight());
            nodes.add(tmp3);
        }
        return tmp3;
    }


    @Override
    public int compareTo(Object o) {
        HuffTree that = (HuffTree) o;
        if (root.weight() < that.getWeight()) {
            return -1;
        } else if (root.weight() == that.getWeight()) {
            return 0;
        }
        return 1;
    }

    public static Map<Character, Integer> createTable(File file) {
        Map<Character, Integer> frequency = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int charInt;
            char c;
            while ((charInt = bufferedReader.read()) != -1) {
                c = (char) charInt;
                if (frequency.containsKey(c)) {
                    frequency.computeIfPresent(c, (k, value) -> value + 1);
                } else {
                    frequency.put(c, 1);
                }
            }
            return frequency;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
