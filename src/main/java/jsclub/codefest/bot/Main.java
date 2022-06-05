package jsclub.codefest.bot;

import io.socket.emitter.Emitter.Listener;
import jsclub.codefest.bot.constant.GameConfig;
import jsclub.codefest.sdk.model.Hero;
import jsclub.codefest.sdk.socket.data.GameInfo;
import jsclub.codefest.sdk.util.GameUtil;

import java.util.Random;

public class Main {
    public static String getRandomPath() {
        Random rand = new Random();
        int random_integer = rand.nextInt(5);

        return "1234b".charAt(random_integer) + "";
    }

    public static void main(String[] aDrgs) {
        Hero player1 = new Hero("player1-xxx", GameConfig.GAME_ID);
        Listener onTickTackListener = objects -> {
            GameInfo gameInfo = GameUtil.getMapInfo(objects);

            player1.move(getRandomPath());
        };
        player1.setOnTickTackListener(onTickTackListener);
        player1.connectToServer();
    }
}
