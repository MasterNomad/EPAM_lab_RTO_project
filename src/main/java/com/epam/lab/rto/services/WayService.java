package com.epam.lab.rto.services;

import com.epam.lab.rto.dto.GraphMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WayService {

    private GraphMap graphMap;
    private GraphMap.Node finish;
    private List<GraphMap.Node> shortestWay;
    private int shortestDistance;

    public List<String> getWay(GraphMap graphMap, String... keys) {
        this.graphMap = graphMap;
        List<String> nodeList = Arrays.asList(keys);

        if (nodeList.size() < 2) {
            return nodeList;
        }

        List<String> result = new ArrayList<>();
        for (int i = 1; i < nodeList.size(); i++) {
            List<String> tempWay = findWay(nodeList.get(i - 1), nodeList.get(i));
            if (tempWay == null) {
                return null;
            }
            result.addAll(tempWay);
            if (i + 1 != nodeList.size()) {
                result.remove(result.size() - 1);
            }
        }
        return result;
    }

    public int getWayDistance(GraphMap graphMap, List<String> way) {
        return getNodeWayDistance(way.stream().map(graphMap::get).collect(Collectors.toList()));
    }

    private List<String> findWay(String startKey, String finishKey) {

        if (startKey.equals(finishKey)) {
            return new ArrayList<>(Collections.singletonList(startKey));
        }
        if (graphMap.getConnectNodes(startKey) == null) {
            return null;
        }
        if (graphMap.hasConnection(startKey, finishKey)) {
            return new ArrayList<>(Arrays.asList(startKey, finishKey));
        }

        List<GraphMap.Node> currentWay = new ArrayList<>();
        currentWay.add(graphMap.get(startKey));

        finish = graphMap.get(finishKey);
        shortestWay = null;
        shortestDistance = 0;

        findShortestWay(currentWay);

        return shortestWay.stream().map(GraphMap.Node::toString).collect(Collectors.toList());
    }

    private List<GraphMap.Node> findShortestWay(List<GraphMap.Node> currentWay) {
        if (shortestWay != null && getNodeWayDistance(currentWay) > shortestDistance) {
            return null;
        }
        GraphMap.Node currentNode = currentWay.get(currentWay.size() - 1);
        if (isFinish(currentNode)) {
            return currentWay;
        }
        for (GraphMap.Node node : graphMap.getConnectNodes(currentNode)) {
            if (!currentWay.contains(node)) {
                currentWay.add(node);
                List<GraphMap.Node> tmp = findShortestWay(currentWay);
                if (tmp != null && isFinish(tmp.get(tmp.size() - 1))) {
                    applyShortestWay(tmp);
                }
                currentWay.remove(currentWay.size() - 1);
            }
        }
        return null;
    }

    private boolean isFinish(GraphMap.Node node) {
        return node.equals(finish);
    }

    private void applyShortestWay(List<GraphMap.Node> currentWay) {
        int currentWayDistance = getNodeWayDistance(currentWay);
        if (shortestWay == null || shortestDistance > currentWayDistance) {
            shortestWay = new ArrayList<>(currentWay);
            shortestDistance = currentWayDistance;
        }
    }

    public int getNodeWayDistance(List<GraphMap.Node> way) {
        int result = 0;
        for (int i = 1; i < way.size(); i++) {
            result += way.get(i).getConnectionWeightToNode(way.get(i - 1));
        }
        return result;
    }

}
