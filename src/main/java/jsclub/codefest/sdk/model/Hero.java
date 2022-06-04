package jsclub.codefest.sdk.model;
import io.socket.client.Socket;

import jsclub.codefest.sdk.util.SocketUtils;
import jsclub.codefest.sdk.constant.ServerConfig;

public class Hero {
    private String playerName = "";
    private String gameID = "";
    private Socket mSocket;

    public Hero(String playerName, String gameID) {
        this.playerName = playerName;
        this.gameID = gameID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public Boolean connectToServer() {
        if (this.mSocket != null) {
            this.mSocket.disconnect();
            this.mSocket = null;
        }
        this.mSocket = SocketUtils.init(ServerConfig.SERVER_URL);
        if (mSocket == null) {
            System.out.println("Socket null - can't connect");
            return false;
        }

        return true;
    }
}
