package jsclub.codefest.bot;

import io.socket.emitter.Emitter.Listener;
import jsclub.codefest.sdk.model.Hero;

public class Main {
    public static void main(String[] args) {
        String GAME_ID = "3b53a9af-d3f3-4622-a821-aecb6fdc8b3e";

        Hero player1 = new Hero("player1-xxx", GAME_ID);
        Listener onTickTackListener = objects -> {
            if (objects != null && objects.length != 0) {
                String data = objects[0].toString();
                System.out.println(data);
            }
        };
        player1.setOnTickTackListener(onTickTackListener);

        Hero player2 = new Hero("player2-xxx", GAME_ID);

        player1.connectToServer();
        player2.connectToServer();
    }
}
