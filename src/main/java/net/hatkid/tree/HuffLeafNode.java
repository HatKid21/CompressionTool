package net.hatkid.tree;

public class HuffLeafNode implements HuffBaseNode {

    private final char el;
    private final int weight;

    public HuffLeafNode(char c, int weight) {
        this.el = c;
        this.weight = weight;
    }

    public char getElement() {
        return el;
    }
    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public int weight() {
        return weight;
    }
}
