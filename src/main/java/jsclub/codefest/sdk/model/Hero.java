package jsclub.codefest.sdk.model;
import io.socket.client.Socket;
import jsclub.codefest.sdk.constant.ServerSocketConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jsclub.codefest.sdk.util.SocketUtils;
import jsclub.codefest.sdk.constant.ServerConfig;
import org.json.JSONException;
import org.json.JSONObject;

public class Hero {
    private static final Logger LOGGER = LogManager.getLogger(Hero.class);
    private String playerName = "";
    private String gameID = "";
    private Socket socket;

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
        if (socket != null) {
            socket.disconnect();
            socket = null;
        }
        socket = SocketUtils.init(ServerConfig.SERVER_URL);

        if (socket == null) {
            LOGGER.error("Socket null - can't connect");
            return false;
        }

        socket.on(Socket.EVENT_CONNECT, objects -> {
            String gameParams = new Game(gameID, playerName).toString();
            try {
                socket.emit(ServerSocketConfig.JOIN_GAME, new JSONObject(gameParams));
            } catch (JSONException e) {
                LOGGER.error(e);
            }
        });
        LOGGER.info("{} connected into game {}!", this.playerName, this.gameID);
        socket.connect();
        return true;
    }
}
