/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsclub.codefest.sdk.algorithm;

import java.util.Map;
import java.util.Stack;
import jsclub.codefest.sdk.model.Bomberman;
import jsclub.codefest.sdk.socket.data.Node;

/**
 *
 * @author Mido
 */
public abstract class MainAlgorithm extends BaseAlgorithm{
    public abstract boolean isEndanger();
    
    public abstract String getEscapePath();
    
    public abstract Map<Node, Stack<Node>> getPathToAllFood(Bomberman player);
    
    public abstract Map<Node, Stack<Node>> getPathToBox(Bomberman Player);
    
    public abstract boolean isNeedToKillEnemy();
    
    public abstract String getBombPlacePath();
}
