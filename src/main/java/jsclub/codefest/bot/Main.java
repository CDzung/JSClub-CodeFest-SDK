package jsclub.codefest.bot;

import jsclub.codefest.sdk.model.Hero;

public class Main {
    public static void main(String[] args) {
        String GAME_ID = "a9a7f739-3d08-4421-95c5-92e13eb7635a";

        Hero player1 = new Hero("player1-xxx", GAME_ID);

        System.out.println(player1.connectToServer());
    }
}
