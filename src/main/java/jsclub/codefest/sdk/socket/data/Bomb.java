package jsclub.codefest.sdk.socket.data;

import com.google.gson.Gson;

public class Bomb extends Position {
    public String playerId;
    public int remainTime;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
