package com.epam.lab.rto.dto;

import java.util.*;

public class GraphMap {

    private Map<String, Node> graphMap = new HashMap<>();

    public class Node {
        private String name;
        private Map<Node, Integer> connections = new HashMap<>();

        private Node(String name) {
            this.name = name;
        }

        public int getConnectionWeightToNode(Node key) {
            return connections.get(key);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public GraphMap() {
    }

    public void add(String name) {
        graphMap.put(name, new Node(name));
    }

    public Node get(String key) {
        return graphMap.get(key);
    }

    public void addConnection(String firstKey, String secondKey, int weight) {
        if (hasConnection(firstKey, secondKey)) {
            return;
        }
        Node firstNode = get(firstKey);
        Node secondNode = get(secondKey);
        firstNode.connections.put(secondNode, weight);
    }

    public Set<Node> getConnectNodes(Node key) {
        return new HashSet<>(key.connections.keySet());
    }

    public Set<Node> getConnectNodes(String key) {
        return getConnectNodes(get(key));
    }

    public boolean hasConnection(String firstKey, String secondKey) {
        Node firstNode = get(firstKey);
        Node secondNode = get(secondKey);
        return firstNode.connections.containsKey(secondNode);
    }

    public boolean contains(String key) {
        return graphMap.containsKey(key);
    }
}
