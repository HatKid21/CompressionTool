package net.hatkid.tree;

public class HuffInternalNode implements HuffBaseNode {

    private final int weight;
    private final HuffBaseNode leftChild;
    private final HuffBaseNode rightChild;

    public HuffInternalNode(HuffBaseNode left, HuffBaseNode right, int weight) {
        this.leftChild = left;
        this.rightChild = right;
        this.weight = weight;
    }

    public HuffBaseNode getLeftChild() {
        return leftChild;
    }


    public HuffBaseNode getRightChild() {
        return rightChild;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public int weight() {
        return weight;
    }
}
