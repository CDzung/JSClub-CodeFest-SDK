package jsclub.codefest.sdk.socket.data;

import com.google.gson.Gson;

public class Driver {
    public String player_id;
    public String direction;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
