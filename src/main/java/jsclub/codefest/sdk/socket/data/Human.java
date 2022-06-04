package jsclub.codefest.sdk.socket.data;

import com.google.gson.Gson;

public class Human {
    public int direction;
    public Position position;
    public boolean infected;
    public int curedRemainTime;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
