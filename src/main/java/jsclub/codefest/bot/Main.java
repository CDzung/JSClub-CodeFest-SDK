package jsclub.codefest.bot;

import io.socket.emitter.Emitter.Listener;
import jsclub.codefest.bot.constant.GameConfig;
import jsclub.codefest.sdk.model.Hero;
import jsclub.codefest.sdk.socket.data.GameInfo;
import jsclub.codefest.sdk.util.GameUtil;

import java.util.Random;
import jsclub.codefest.sdk.algorithm.AStarSearch;
import jsclub.codefest.sdk.algorithm.DungAlgorithm;
import jsclub.codefest.sdk.model.Bomberman;
import jsclub.codefest.sdk.socket.data.MapInfo;
import jsclub.codefest.sdk.socket.data.Node;
import jsclub.codefest.sdk.socket.data.Player;
import jsclub.codefest.sdk.socket.data.Viruses;

public class Main {

    private static final String id = "player1-xxx";

    public static String getRandomPath() {
        Random rand = new Random();
        int random_integer = rand.nextInt(5);

        return "1234b".charAt(random_integer) + "";
    }

    public static void main(String[] aDrgs) {
        Hero player1 = new Hero("player1-xxx", GameConfig.GAME_ID);
        Listener onTickTackListener = objects -> {
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
                System.out.println("#Find Safe Place");
                path = dungAlgorithm.getEscapePath(ownBomPlayer, mapInfo, -1);
                if (path == null || path.equals("")) {
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

            player1.move(path);
        };
        player1.setOnTickTackListener(onTickTackListener);
        player1.connectToServer();
    }
}
