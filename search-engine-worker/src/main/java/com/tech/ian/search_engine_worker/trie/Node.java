package com.tech.ian.search_engine_worker.trie;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private Map<Character, Node> nodeMap;
    private boolean isCompleteWord;
    private int frequency;


    public Node() {
        this.nodeMap = new HashMap<>();
        this.isCompleteWord = false;
        this.frequency = 0;
    }

    public Node(Map<Character, Node> nodeMap, boolean isCompleteWord, int frequency) {
        this.nodeMap = nodeMap;
        this.isCompleteWord = isCompleteWord;
        this.frequency = frequency;
    }

    public Map<Character, Node> getNodeMap() {
        return nodeMap;
    }

    public void setNodeMap(Map<Character, Node> nodeMap) {
        this.nodeMap = nodeMap;
    }

    public boolean isCompleteWord() {
        return isCompleteWord;
    }

    public void setCompleteWord(boolean completeWord) {
        isCompleteWord = completeWord;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
