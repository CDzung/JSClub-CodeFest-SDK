package jsclub.codefest.bot;

import com.google.gson.Gson;
import io.socket.emitter.Emitter;
import jsclub.codefest.bot.constant.GameConfig;
import jsclub.codefest.sdk.model.Hero;
import jsclub.codefest.sdk.socket.data.GameInfo;
import jsclub.codefest.sdk.util.GameUtil;

import java.util.Random;
import jsclub.codefest.sdk.algorithm.AStarSearch;
import jsclub.codefest.sdk.algorithm.DungAlgorithm;
import jsclub.codefest.sdk.model.Bomberman;
import jsclub.codefest.sdk.socket.data.MapInfo;
import jsclub.codefest.sdk.socket.data.Player;

public class Main2 {

    private static final String id = "player2-xxx";

    public static void main(String[] aDrgs) {
        Hero player2 = new Hero("player2-xxx", GameConfig.GAME_ID);
        Emitter.Listener onTickTackListener = objects -> {
            GameInfo gameInfo = GameUtil.getMapInfo(objects);
            MapInfo mapInfo = gameInfo.map_info;
            int[][] matrix = mapInfo.getMap();
            Player myPlayer = mapInfo.getPlayerByKey(id);
            Bomberman ownBomPlayer = new Bomberman();
            ownBomPlayer.initPlayerInfo(myPlayer, mapInfo);
            DungAlgorithm dungAlgorithm = new DungAlgorithm();
            AStarSearch aStarSearch = new AStarSearch();
            String path = "";
            if (ownBomPlayer.isEndanger()) {
                path = dungAlgorithm.getEscapePath(ownBomPlayer, mapInfo, -1);
                if (path.equals("")) {
                    path = dungAlgorithm.getEatPath(ownBomPlayer, mapInfo, true, myPlayer);
                }
            } else {
                path = dungAlgorithm.getEatPath(ownBomPlayer, mapInfo, false, myPlayer);
                if (path == null || path.equals("")) {
                    path = dungAlgorithm.getPathToBox(ownBomPlayer, mapInfo);
                    System.out.println("box path " + path);
                }
                if (path == null || path.equals("")) {
                    path = dungAlgorithm.getEscapePath(ownBomPlayer, mapInfo, -1);
                }
            }

            player2.move(path);
        };
        player2.setOnTickTackListener(onTickTackListener);
        player2.connectToServer();
    }
}
