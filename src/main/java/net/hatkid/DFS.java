package net.hatkid;

import net.hatkid.tree.HuffBaseNode;
import net.hatkid.tree.HuffInternalNode;
import net.hatkid.tree.HuffLeafNode;
import net.hatkid.tree.HuffTree;

import java.util.HashMap;
import java.util.Map;

public class DFS {

    public static Map<Character, String> search(HuffTree tree) {
        HuffBaseNode node = tree.getRoot();
        Map<Character, String> data = new HashMap<>();
        dfs(node, data, "");
        return data;
    }

    private static void dfs(HuffBaseNode node, Map<Character, String> data, String code) {
        if (node == null) {
            return;
        }

        if (node.isLeaf()) {
            HuffLeafNode leaf = (HuffLeafNode) node;
            data.put(leaf.getElement(), code);
            return;
        }

        HuffInternalNode internalNode = (HuffInternalNode) node;
        dfs(internalNode.getLeftChild(), data, code + "1");
        dfs(internalNode.getRightChild(), data, code + "0");

    }


}
