/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsclub.codefest.sdk.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import jsclub.codefest.sdk.model.Bomberman;
import jsclub.codefest.sdk.socket.data.MapInfo;
import jsclub.codefest.sdk.socket.data.Node;
import jsclub.codefest.sdk.socket.data.Player;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Mido
 */
public class DungAlgorithm extends MainAlgorithm {

    @Override
    public boolean isEndanger() {
        return false;
    }

    @Override
    public String getEscapePath(Bomberman ownBomPlayer, MapInfo mapInfo, int numStep, boolean isReverse) {
        Bomberman cloneBommer = Bomberman.clone(ownBomPlayer);
        int[][] matrix = mapInfo.getMap();
        List<Node> safeNodes = mapInfo.blank;
        safeNodes.removeAll(ownBomPlayer.getBombs());
        List<Node> restrictNode = new ArrayList<>();
        restrictNode.addAll(cloneBommer.getWalls());
        restrictNode.addAll(cloneBommer.getVirusLists());
        restrictNode.addAll(cloneBommer.getdangerHumanLists());
        restrictNode.addAll(cloneBommer.getBoxs());
        restrictNode.addAll(cloneBommer.getSelfisolatedZone());
        if (safeNodes.contains(ownBomPlayer.getPosition())) {
            restrictNode.addAll(cloneBommer.dangerBombs);
        } else {
            restrictNode.addAll(cloneBommer.getBombs());
        }
        safeNodes.removeAll(restrictNode);
        cloneBommer.setRestrictedNodes(restrictNode);
        Map<Node, Stack<Node>> pathToAllSafePlace = sortByComparator(getPathsToAllTarget(matrix, cloneBommer, safeNodes, false), false);
        if (pathToAllSafePlace.isEmpty()) {
            restrictNode.clear();
            cloneBommer.setBombs(mapInfo.bombs, cloneBommer.getEnemyPlayer().power, false);
            cloneBommer.setVirusLists(mapInfo.getVirus(), true);
            cloneBommer.setDangerHumanList(mapInfo.getDhuman(), true);
            restrictNode.addAll(cloneBommer.getBombs());
            restrictNode.addAll(cloneBommer.getBoxs());
            restrictNode.addAll(cloneBommer.getSelfisolatedZone());
            restrictNode.addAll(cloneBommer.getVirusLists());
            restrictNode.addAll(cloneBommer.getdangerHumanLists());
            cloneBommer.setRestrictedNodes(restrictNode);
            pathToAllSafePlace = sortByComparator(getPathsToAllTarget(matrix, cloneBommer, safeNodes, false), false);
        }
        System.out.println("path to safe place " + pathToAllSafePlace.size());
        AStarSearch algorithm = new AStarSearch();
        for (Map.Entry<Node, Stack<Node>> path : pathToAllSafePlace.entrySet()) {
            String steps = algorithm.aStarSearch(matrix, cloneBommer, path.getKey(), 3);
            if (steps!=null && !steps.equals("")) {
                return steps;
            }
        }
        return "";
    }

    public Map<Node, Stack<Node>> getPathToAllFood(Bomberman ownBomPlayer, MapInfo mapInfo, boolean isNeedCheckEffectBomb,Player myPlayer) {
        Bomberman cloneBommer = Bomberman.clone(ownBomPlayer);
        List<Node> restrictNode = new ArrayList<>();
        restrictNode.addAll(cloneBommer.getWalls());
        restrictNode.addAll(cloneBommer.getBoxs());
        restrictNode.addAll(cloneBommer.getSelfisolatedZone());
        cloneBommer.setNormalHumanList(mapInfo.getNHuman());
        if (cloneBommer.metadata.score - cloneBommer.mEnemyPlayer.score > 20 || mapInfo.boxs.size() < 10) {
            cloneBommer.listShouldEatSpoils.addAll(cloneBommer.normalHumanList);
        }
        if (myPlayer.pill > 2) {
            cloneBommer.setVirusLists(mapInfo.getVirus(),false);
            cloneBommer.setDangerHumanList(mapInfo.getDhuman(),false);
            cloneBommer.listShouldEatSpoils.addAll(cloneBommer.getdangerHumanLists());
            cloneBommer.listShouldEatSpoils.addAll(cloneBommer.getVirusLists());
        } else {
            restrictNode.addAll(cloneBommer.getdangerHumanLists());
            restrictNode.addAll(cloneBommer.getVirusLists());
        }
        if (isNeedCheckEffectBomb) {
            restrictNode.addAll(cloneBommer.dangerBombs);
        }
        cloneBommer.setRestrictedNodes(restrictNode);
        System.out.println("list should eat " + cloneBommer.listShouldEatSpoils.size());
        Map<Node, Stack<Node>> listFood = getPathsToAllTarget(mapInfo.getMap(), cloneBommer, cloneBommer.listShouldEatSpoils, true);
        System.out.println("list food " + listFood.size());
        return sortByComparator(listFood, false);
    }

    @Override
    public Map<Node, Stack<Node>> getPathToAllBox(Bomberman Player,MapInfo mapInfo) {
        
        int[][] matrix = mapInfo.getMap();
        List<Node> boxs = mapInfo.boxs;
        BaseAlgorithm algo = new BaseAlgorithm();
        AStarSearch search = new AStarSearch();
        Map<Node, Stack<Node>> path = new HashMap<>();
        for (int i = 0; i < boxs.size(); i++) {
            Node nodeFood = boxs.get(i);
            Stack<Node> pathToFood = search.aStarSearch(matrix, Player, nodeFood);
            if(pathToFood != null && pathToFood.size()>0)
                path.put(nodeFood, pathToFood);
        }
        return path;

    }
    
    
    public String getPathToBox(Bomberman Player,MapInfo mapInfo) {
        Bomberman cloneBommer = Bomberman.clone(Player);
        List<Node> restrictNode = new ArrayList<>();
        restrictNode.addAll(cloneBommer.getWalls());
        restrictNode.addAll(cloneBommer.getVirusLists());
        restrictNode.addAll(cloneBommer.getdangerHumanLists());
        restrictNode.addAll(cloneBommer.getBoxs());
        restrictNode.addAll(cloneBommer.getSelfisolatedZone());
        restrictNode.addAll(cloneBommer.dangerBombs);
        cloneBommer.setRestrictedNodes(restrictNode);
        Map<Node, Stack<Node>> pathToAllBox = sortByComparator(getPathToAllBox(cloneBommer, mapInfo), false);
        System.out.println("path to all box " +pathToAllBox.size());
        AStarSearch algorithm = new AStarSearch();
        System.out.println(pathToAllBox.size());
        for (Map.Entry<Node, Stack<Node>> path : pathToAllBox.entrySet()) {
            long searchTime = System.currentTimeMillis();
            String steps = algorithm.aStarSearch(mapInfo.getMap(), cloneBommer, path.getKey(), -1);
            
            if (steps!=null && !steps.equals("")) {
                if(steps.length()==1) {
                    restrictNode.add(cloneBommer.getPosition());
                    cloneBommer.setRestrictedNodes(restrictNode);
                    String pathEscape = getEscapePath(cloneBommer, mapInfo, -1, false);
                    if(pathEscape!=null && !pathEscape.equals("")) {
                        restrictNode.remove(restrictNode.get(restrictNode.size()-1));
                        cloneBommer.setRestrictedNodes(restrictNode);
                        return "b"+pathEscape;
                    }
                    restrictNode.remove(restrictNode.get(restrictNode.size()-1));
                    cloneBommer.setRestrictedNodes(restrictNode);
                }
                return steps;
            }
        }
        return "";
    }

    @Override
    public boolean isNeedToKillEnemy() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getBombPlacePath() {

        return "";
    }

    private Map<Node, Stack<Node>> sortByComparator(Map<Node, Stack<Node>> unsortedMap, boolean isReverse) {
        List<Map.Entry<Node, Stack<Node>>> list = new LinkedList<>(unsortedMap.entrySet());
        list.sort((o1, o2) -> {
            double o1Path = o1.getValue().size() / o1.getKey().getV();
            double o2Path = o2.getValue().size() / o2.getKey().getV();
            return Double.compare(o1Path, o2Path);
        });
        if (isReverse) {
            Collections.reverse(list);
        }
        Map<Node, Stack<Node>> listFood = new LinkedHashMap<>();
        for (Map.Entry<Node, Stack<Node>> entry : list) {
            Node food = Node.createFromPosition(entry.getKey());
            listFood.put(food, entry.getValue());
        }
        return listFood;
    }
    
    public Map<Node, Stack<Node>> getPathsToAllTarget(int[][] matrix, Bomberman player, List<Node> targets, boolean isCollectSpoils) {
        AStarSearch aSearch = new AStarSearch();
        return aSearch.getPathsToAllFoods(matrix, player, targets, isCollectSpoils);
    }
    
    public String getEatPath(Bomberman ownBomPlayer,MapInfo mapInfo, boolean forceEat, Player myPlayer) {
        Bomberman cloneBommer = Bomberman.clone(ownBomPlayer);
        AStarSearch algorithm = new AStarSearch();
        List<Node> restrictNode = new ArrayList<>();
        restrictNode.addAll(cloneBommer.getWalls());
        restrictNode.addAll(cloneBommer.getBoxs());
        restrictNode.addAll(cloneBommer.getSelfisolatedZone());
        restrictNode.addAll(cloneBommer.getdangerHumanLists());
        restrictNode.addAll(cloneBommer.getVirusLists());
        if (!forceEat) {
//            restrictNode.addAll(cloneBommer.listNoEatSpoils);
            restrictNode.addAll(cloneBommer.dangerBombs);
        }
        cloneBommer.setRestrictedNodes(restrictNode);
        Map<Node, Stack<Node>> pathToAllSpoil = getPathToAllFood(ownBomPlayer,mapInfo, !forceEat, myPlayer);
        System.out.println("path to all spoil " + pathToAllSpoil.size());
        if (!pathToAllSpoil.isEmpty()) {
            for (Map.Entry<Node, Stack<Node>> path : pathToAllSpoil.entrySet()) {
                String steps = algorithm.aStarSearch(mapInfo.getMap(), cloneBommer, path.getKey(), -1);
                if (steps != null && !steps.equals("")) {
                    return steps;
                }
            }
        }
        return "";
    }

}
