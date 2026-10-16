package com.tech.ian.search_engine_worker.trie;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Trie {
    private final int THRESHOLD = 5;
    private final Node root;

    public Trie() {
        this.root = new Node(new HashMap<>(), false, 0);
    }

    public void search(String prefix) {
        Node temp = root;
        if (!temp.getNodeMap().containsKey(prefix.charAt(0))) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Character ch : prefix.toCharArray()) {
            stringBuilder.append(ch);
            temp = temp.getNodeMap().get(ch);
        }
        Map<String, Integer> map = new HashMap<>();
        getList(temp, map, stringBuilder);
        List<String> list = findMostFrequentlySearchedWords(map);
    }

    private void getList(Node root, Map<String, Integer> list, StringBuilder stringBuilder) {
        if (root.isCompleteWord()) {
            list.put(stringBuilder.toString(), root.getFrequency());
        }
        if (root.getNodeMap() == null || root.getNodeMap().isEmpty()) return;

        for (Character ch : root.getNodeMap().keySet()) {
            getList(root.getNodeMap().get(ch), list, stringBuilder.append(ch));
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
    }

    private List<String> findMostFrequentlySearchedWords(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> aux = new ArrayList<>(map.entrySet());
        aux.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        List<String> list = new ArrayList<>();

        for (int i = 0; i < THRESHOLD && i < aux.size(); i++) {
            list.add(aux.get(i).getKey());
        }
        return list;
    }

    public Map<String, String> addWord(String word) {
        Map<String, String> preComputed = new HashMap<>();
        Node temp = root;
        StringBuilder sb = new StringBuilder();
        for (Character ch : word.toCharArray()) {
            temp = temp.getNodeMap().computeIfAbsent(ch, k -> new Node());
            preComputed.put(sb.append(ch).toString(), word);
        }
        temp.setCompleteWord(true);

        return preComputed;
    }

    private void rec(Node temp, Map<String, String> preComputed, String word, String prefix, int idx) {
        char key = word.charAt(idx);
        temp.getNodeMap().computeIfAbsent(key, k -> new Node());
        temp = temp.getNodeMap().get(key);
        prefix = prefix+key;
        if(word.equals(prefix)) {
            temp.setCompleteWord(true);
            preComputed.put(prefix, word);
            return;
        }

        rec(temp, preComputed, word, prefix, idx+1);
        preComputed.put(prefix, word);
    }

    private static Node getTemp(String word, Node temp) {
        for (Character ch : word.toCharArray()) {
            temp = temp.getNodeMap().computeIfAbsent(ch, k -> new Node());
        }
        return temp;
    }

    public void getWord(String word) {
        Node temp = root;
        temp = getTemp(word, temp);
        temp.setFrequency(temp.getFrequency()+1);
    }
}
