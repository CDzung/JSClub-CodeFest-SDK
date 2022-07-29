package jsclub.codefest.sdk.algorithm;

import jsclub.codefest.sdk.socket.data.Node;
import java.util.*;
import jsclub.codefest.sdk.model.Bomberman;

public class AStarSearch extends BaseAlgorithm {


    public String aStarSearch(int[][] matrix, Bomberman player, Node target, int numOfSteps) {
        return getStepsInString(player.getPosition(), aStarSearch(matrix, player, target), numOfSteps);
    }

    public Stack<Node> aStarSearch(int[][] matrix, Bomberman player, Node target) {
        int mMapWidth = matrix[0].length;
        int mMapHeight = matrix.length;
        Node playerNode = player.getPosition();
        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closeList = new ArrayList<>();
        Stack<Node> stack = new Stack<>();// Elephant to eat the path
        openList.add(Node.createFromPosition(playerNode));// Place the start Node in the open list;
        playerNode.setH(manhattanDistance(playerNode, target));

        while (!openList.isEmpty()) {
            Node now = null;
            int minValue = Integer.MAX_VALUE;
            for (Node n : openList) {// We find the F value (the description farthest from the target), if the same
                // we choose behind the list is the latest addition.
                if (n.getF() < minValue) {
                    minValue = n.getF();
                    now = n;
                }
                if (now != null && n.getF() == minValue
                        && (distanceBetweenTwoPoints(n, playerNode) < distanceBetweenTwoPoints(now, playerNode))) {
                    now = n;
                }

            }
            // Remove the current Node from the open list and add it to the closed list
            openList.remove(now);
            closeList.add(now);
            // Neighbor in four directions
            Node left = Node.createFromPosition(now.leftPosition(1));
            Node right = Node.createFromPosition(now.rightPosition(1));
            Node up = Node.createFromPosition(now.upPosition(1));
            Node down = Node.createFromPosition(now.downPosition(1));
            List<Node> temp = new ArrayList<>(4);
            temp.add(up);
            temp.add(right);
            temp.add(down);
            temp.add(left);
            for (Node n : temp) {
                // If the neighboring Node is not accessible or the neighboring Node is already
                // in the closed list, then no action is taken and the next Node continues to be
                // examined;
                if ((!n.equals(target)
                        && player.getRestrictedNodes().contains(n.toString()))
                        || closeList.contains(n)
                        || n.getX() > mMapWidth
                        || n.getX() < 1
                        || n.getY() > mMapHeight
                        || n.getY() < 1) {
                    continue;
                }

                // If the neighbor is not in the open list, add the Node to the open list,
                // and the adjacent Node'elephant father Node as the current Node, while saving the
                // adjacent Node G and H value, F value calculation I wrote directly in the Node
                // class
                if (!openList.contains(n)) {
                    // Logger.println("ok");
                    n.setFather(now);
                    n.setG(now.getG() + 1);
                    n.setH(manhattanDistance(n, target));
                    openList.add(n);
                    // When the destination Node is added to the open list as the Node to be
                    // checked, the path is found, and the loop is terminated and the direction is
                    // returned.
                    if (n.equals(target)) {
                        // Go forward from the target Node, .... lying groove there is a pit, Node can
                        // not use f, because f and find the same Node coordinates but f did not record
                        // father
                        Node node = openList.get(openList.size() - 1);
                        while (node != null //                                && !node.equals(playerNode)???????
                                ) {
                            stack.push(node);
                            node = node.getFather();
                        }
                        // Create previous step to finding out next step

                        return stack;
                    }
                }
                // If the neighbor is in the open list,
                // // judge whether the value of G that reaches the neighboring Node via the
                // current Node is greater than or less than the value of G that is stored
                // earlier than the current Node (if the value of G is greater than or smaller
                // than the value of G), set the father Node of the adjacent Node as Current
                // Node, and reset the G and F values ??of the adjacent Node.
                if (openList.contains(n)) {
                    if (n.getG() > (now.getG() + 1)) {
                        n.setFather(now);
                        n.setG(now.getG() + 1);
                    }
                }
            }
        }
        // When the open list is empty, indicating that there is no new Node to add, and
        // there is no end Node in the tested Node, the path can not be found. At this
        // moment, the loop returns -1 too.
        return new Stack<>();
    }

    public Map<Node, Stack<Node>> getPathsToAllFoods(int[][] matrix, Bomberman player, List<Node> targets, boolean isCollectSpoils) {
        int mMapWidth = matrix[0].length;
        int mMapHeight = matrix.length;
        Bomberman clonePlayer = Bomberman.clone(player);
        Map<Node, Stack<Node>> allPaths = new HashMap<>();
        Queue<Node> open = new LinkedList<>();
        Set<String> visited = new HashSet<>();// Record the visited Node
        List<Node> target = new ArrayList<>(targets);
        open.add(clonePlayer.getPosition());
        for(Node targetNode: targets) {
            Stack<Node> path = aStarSearch(matrix, player, targetNode);
            if(path!=null){
                allPaths.put(targetNode, path);
            }
        }
        return allPaths;
    }
}
