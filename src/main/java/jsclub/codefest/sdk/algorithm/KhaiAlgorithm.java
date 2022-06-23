/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsclub.codefest.sdk.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import jsclub.codefest.sdk.model.Bomberman;
import jsclub.codefest.sdk.socket.data.GameInfo;
import jsclub.codefest.sdk.socket.data.Node;
import jsclub.codefest.sdk.socket.data.Spoil;

/**
 *
 * @author 84943
 */
public class KhaiAlgorithm extends MainAlgorithm {

    @Override
    public boolean isEndanger() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getEscapePath() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Map<Node, Stack<Node>> getPathToAllFood(Bomberman Player) {
        GameInfo gameInfo = new GameInfo();
        int[][] matrix = gameInfo.map_info.getMap();
        List<Spoil> listFood = gameInfo.map_info.spoils;
        Map<Node, Stack<Node>> path = new HashMap<>();
        AStarSearch search = new AStarSearch();
        for (int i = 0; i < listFood.size(); i++) {
            Node nodeFood = Node.createFromPosition(listFood.get(i).col, listFood.get(i).row);
            Stack<Node> pathToFood = search.aStarSearch(matrix, Node.createFromPosition(Player.getPosition()), nodeFood);
            path.put(nodeFood, pathToFood);
        }
        if (Player.myPill >= Player.dangerHumanList.size()) {
            for (int i = 0; i < Player.dangerHumanList.size(); i++) {
                Node nodeFood = Node.createFromPosition(Player.dangerHumanList.get(i).col, Player.dangerHumanList.get(i).row);
                Stack<Node> pathToFood = search.aStarSearch(matrix, Node.createFromPosition(Player.getPosition()), nodeFood);
                path.put(nodeFood, pathToFood);
            }
        } else {
            for (int i = 0; i < Player.normalHumanList.size(); i++) {
                Node nodeFood = Node.createFromPosition(Player.normalHumanList.get(i).col, Player.normalHumanList.get(i).row);
                Stack<Node> pathToFood = search.aStarSearch(matrix, Node.createFromPosition(Player.getPosition()), nodeFood);
                path.put(nodeFood, pathToFood);
            }
        }
        return path;
    }

    @Override
    public Map<Node, Stack<Node>> getPathToBox(Bomberman Player) {
        GameInfo gameInfo = new GameInfo();
        int[][] matrix = gameInfo.map_info.getMap();
        List<Node> boxs = gameInfo.map_info.boxs;
        BaseAlgorithm algo = new BaseAlgorithm();
        boxs.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return (int) (algo.distanceBetweenTwoPoints(o1.col, o1.row, Player.getPosition().col, Player.getPosition().row) - algo.distanceBetweenTwoPoints(o2.col, o2.row, Player.getPosition().col, Player.getPosition().row));
            }

        });
        AStarSearch search = new AStarSearch();
        Map<Node, Stack<Node>> path = new HashMap<>();
        for (int i = 0; i < boxs.size(); i++) {
            Node nodeFood = boxs.get(i);
            
            Stack<Node> pathToFood = search.aStarSearch(matrix, Node.createFromPosition(Player.getPosition()), nodeFood);
            path.put(nodeFood, pathToFood);
        }
        return path;

    }

    @Override
    public boolean isNeedToKillEnemy() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getBombPlacePath() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
