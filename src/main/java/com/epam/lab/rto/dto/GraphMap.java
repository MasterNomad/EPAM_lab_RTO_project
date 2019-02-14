package com.epam.lab.rto.dto;

import java.util.*;
import java.util.stream.Collectors;

public class GraphMap {

    private Map<String, Node> graphMap = new HashMap<>();

    private class Node {
        private String name;
        private Map<Node, Integer> connections = new HashMap<>();

        private Node(String name) {
            this.name = name;
        }

        private int getConnectionWeightToNode(Node node) {
            return connections.get(node);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(name, node.name) &&
                    Objects.equals(connections, node.connections);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public void add(String name) {
        graphMap.put(name, new Node(name));
    }

    private Node get(String key) {
        return graphMap.get(key);
    }

    public void addConnection(String firstKey, String secondKey, int weight) {

        if (this.hasConnection(firstKey, secondKey)) {
            return;
        }

        Node firstNode = get(firstKey);
        Node secondNode = get(secondKey);
        firstNode.connections.put(secondNode, weight);
        secondNode.connections.put(firstNode, weight);
    }

    public Set<Node> getConnectNodes(Node node) {
        return new HashSet<>(node.connections.keySet());
    }

    public Set<Node> getConnectNodes(String key) {
        return getConnectNodes(get(key));
    }

    public boolean hasConnection(String firstKey, String secondKey) {
        Node firstNode = get(firstKey);
        Node secondNode = get(secondKey);
        return firstNode.connections.containsKey(secondNode) && secondNode.connections.containsKey(firstNode);
    }


    private Node finish;
    private List<Node> shortestWay;
    private int shortestDistance;

    public List<String> getWay(String startKey, String finishKey) {

        if (startKey.equals(finishKey)) {
            return new ArrayList<>(Collections.singletonList(startKey));
        }
        if (getConnectNodes(startKey) == null) {
            return null;
        }
        if (hasConnection(startKey, finishKey)) {
            return new ArrayList<>(Arrays.asList(startKey, finishKey));
        }

        List<Node> currentWay = new ArrayList<>();
        currentWay.add(get(startKey));

        finish = get(finishKey);
        shortestWay = null;
        shortestDistance = 0;

        findShortestWay(currentWay);

        return shortestWay.stream().map(Node::toString).collect(Collectors.toList());
    }

    public List<String> getWay(String startKey, String secondKey, String... keys) {

        List<String> nodeList = new ArrayList<>();
        nodeList.add(startKey);
        nodeList.add(secondKey);
        nodeList.addAll(Arrays.asList(keys));

        List<String> result = new ArrayList<>();

        for (int i = 1; i < nodeList.size(); i++) {
            List<String> tempWay = getWay(nodeList.get(i - 1), nodeList.get(i));
            if (tempWay == null) return null;
            result.addAll(tempWay);
            if (i + 1 != nodeList.size()) {
                result.remove(result.size() - 1);
            }
        }

        return result;
    }

    public int getNodeWayDistance(List<Node> way) {
        int result = 0;
        for (int i = 1; i < way.size(); i++) {
            result += way.get(i).getConnectionWeightToNode(way.get(i - 1));
        }
        return result;
    }

    public int getStringWayDistance(List<String> way) {
         return getNodeWayDistance(way.stream().map(graphMap::get).collect(Collectors.toList()));
    }

    public List<Node> getNodeList(List<String> list) {
        return list.stream().map(graphMap::get).collect(Collectors.toList());
    }

    private List<Node> findShortestWay(List<Node> currentWay) {

        if (shortestWay != null && getNodeWayDistance(currentWay) > shortestDistance) return null;

        Node currentNode = currentWay.get(currentWay.size() - 1);

        if (isFinish(currentNode)) {
            return currentWay;
        }

        for (Node node : getConnectNodes(currentNode)) {
            if (!currentWay.contains(node)) {
                currentWay.add(node);
                List<Node> tmp = findShortestWay(currentWay);
                if (tmp != null && isFinish(tmp.get(tmp.size() - 1))) {
                    applyShortestWay(tmp);
                }
                currentWay.remove(currentWay.size() - 1);
            }
        }
        return null;
    }

    public Set<String> keySet() {
        return graphMap.keySet();
    }

    public void clear() {
        graphMap = new HashMap<>();
    }

    private boolean isFinish(Node node) {
        return node.equals(finish);
    }

    private void applyShortestWay(List<Node> currentWay) {
        int currentWayDistance = getNodeWayDistance(currentWay);
        if (shortestWay == null || shortestDistance > currentWayDistance) {
            shortestWay = new ArrayList<>(currentWay);
            shortestDistance = currentWayDistance;
        }
    }

}
