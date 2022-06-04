package jsclub.codefest.sdk.model;
import io.socket.client.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jsclub.codefest.sdk.util.SocketUtils;
import jsclub.codefest.sdk.constant.ServerConfig;

public class Hero {
    private static final Logger LOGGER = LogManager.getLogger(Hero.class);
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
        if (mSocket != null) {
            mSocket.disconnect();
            mSocket = null;
        }
        mSocket = SocketUtils.init(ServerConfig.SERVER_URL);

        if (mSocket == null) {
           LOGGER.error("Socket null - can't connect");
            return false;
        }
        return true;
    }
}
