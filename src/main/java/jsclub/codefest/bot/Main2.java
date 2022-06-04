package jsclub.codefest.bot;

import com.google.gson.Gson;
import io.socket.emitter.Emitter;
import jsclub.codefest.sdk.model.Hero;
import jsclub.codefest.sdk.socket.data.GameInfo;

public class Main2 {
    public static void main(String[] args) {
        String GAME_ID = "13f6df15-4e31-4634-91f8-b7e098b72d63";

        Hero player2 = new Hero("player2-xxx", GAME_ID);
//        Emitter.Listener onTickTackListener = objects -> {
//            if (objects != null && objects.length != 0) {
//                String data = objects[0].toString();
//                GameInfo gameInfo = new Gson().fromJson(data, GameInfo.class);
//            }
//        };
//        player2.setOnTickTackListener(onTickTackListener);
        player2.connectToServer();
    }
}
