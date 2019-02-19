package com.epam.lab.rto.dto;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RouteMap {

    private Map<String, Node> routeMap = new HashMap<>();

    public class Node {
        private String title;
        private Map<Node, Set<String>> connections = new HashMap<>();

        private Node(String title) {
            this.title = title;
        }

        public Set<String> getConnectionStations(Node node) {
            return connections.get(node);
        }

        public String getTitle() {
            return title;
        }
    }

    private RouteMap() {
    }

    public void clear() {
        this.routeMap = new HashMap<>();
    }

    public void add(String title) {
        routeMap.put(title, new Node(title));
    }

    public void add(List<String> titleList) {
        for (String title : titleList) {
            add(title);
        }
    }

    public void addConnection(String firstRoute, String secondRoute, String station) {
        if (this.hasConnection(firstRoute, secondRoute, station)) {
            return;
        }
        Node firstNode = get(firstRoute);
        Node secondNode = get(secondRoute);
        if (firstNode.connections.containsKey(secondNode)) {
            firstNode.connections.get(secondNode).add(station);
        } else {
            firstNode.connections.put(secondNode, new HashSet<>());
            firstNode.connections.get(secondNode).add(station);
        }
    }

    public List <String> getConnectedRoutes (String routeTitle) {
        return routeMap.get(routeTitle).connections.keySet().stream().map(Node::getTitle).collect(Collectors.toList());
    }

    public Set<Node> getConnectNodes(Node node) {
        return new HashSet<>(node.connections.keySet());
    }

    public Set<Node> getConnectNodes(String key) {
        return getConnectNodes(get(key));
    }

    public Set<String> getMapRoutes() {
        return routeMap.keySet();
    }

    private Node get(String key) {
        return routeMap.get(key);
    }

    private boolean hasConnection(String firstKey, String secondKey, String station) {
        Node firstNode = get(firstKey);
        Node secondNode = get(secondKey);
        return firstNode.connections.containsKey(secondNode) && firstNode.connections.get(secondNode).contains(station);
    }
}
