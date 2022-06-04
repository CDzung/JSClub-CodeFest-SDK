package jsclub.codefest.bot;

import jsclub.codefest.sdk.model.Hero;

public class Main {
    public static void main(String[] args) {
        String GAME_ID = "839f710e-a42f-40f4-9c2e-59132ecc651c";

        Hero player1 = new Hero("player1-xxx", GAME_ID);
        player1.connectToServer();

        Hero player2 = new Hero("player2-xxx", GAME_ID);
        player2.connectToServer();
    }
}
