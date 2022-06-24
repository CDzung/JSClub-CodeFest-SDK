package jsclub.codefest.sdk.algorithm;

import jsclub.codefest.sdk.constant.ClientConfig;
import jsclub.codefest.sdk.model.Bomberman;
import jsclub.codefest.sdk.socket.data.Node;
import jsclub.codefest.sdk.socket.data.Dir;
import jsclub.codefest.sdk.socket.data.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class BaseAlgorithm {
    /**
     * Calculate Manhattan distance
     * @param src source position
     * @param des destination position
     * @return shortest distance
     */
    int manhattanDistance(Position src, Position des) {
        return Math.abs(src.getX() - des.getX()) + Math.abs(src.getY() - des.getY());
    }
    String getStepsInString(Node first, Stack<Node> path, int numOfSteps) {
        StringBuilder steps = new StringBuilder();
        Node previousStep = first;
        int size = numOfSteps == ClientConfig.GO_FULL_PATH ? path.size() : numOfSteps;
        for (int i = 0; i <= size; i++) {
            if (path.size() > 0) {
                Node nextStep = path.pop();
                int x = nextStep.getX();
                int y = nextStep.getY();
                if (x > previousStep.getX() && y == previousStep.getY()) {
                    steps.append(Dir.RIGHT);
                }
                if (x < previousStep.getX() && y == previousStep.getY()) {
                    steps.append(Dir.LEFT);
                }
                if (x == previousStep.getX() && y > previousStep.getY()) {
                    steps.append(Dir.DOWN);
                }
                if (x == previousStep.getX() && y < previousStep.getY()) {
                    steps.append(Dir.UP);
                }
                previousStep = nextStep;
            } else {
                break;
            }
        }
        return steps.toString();
    }
    protected double distanceBetweenTwoPoints(Position p1, Position p2) {
        //return Math.abs(src.getX() - des.getX()) + Math.abs(src.getY() - des.getY());
        return distanceBetweenTwoPoints(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public double distanceBetweenTwoPoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
    
    
}
