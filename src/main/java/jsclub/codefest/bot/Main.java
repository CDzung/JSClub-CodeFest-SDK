package jsclub.codefest.bot;

import com.google.gson.Gson;
import io.socket.emitter.Emitter.Listener;
import jsclub.codefest.sdk.model.Hero;
import jsclub.codefest.sdk.socket.data.GameInfo;

public class Main {
    public static void main(String[] args) {
        String GAME_ID = "13f6df15-4e31-4634-91f8-b7e098b72d63";

        Hero player1 = new Hero("player1-xxx", GAME_ID);
        Listener onTickTackListener = objects -> {
            if (objects != null && objects.length != 0) {
                String data = objects[0].toString();
                GameInfo gameInfo = new Gson().fromJson(data, GameInfo.class);

                player1.move("111");
            }
        };
        player1.setOnTickTackListener(onTickTackListener);
        player1.connectToServer();
    }
}
