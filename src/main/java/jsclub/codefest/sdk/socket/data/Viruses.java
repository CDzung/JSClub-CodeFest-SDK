package jsclub.codefest.sdk.socket.data;

import com.google.gson.Gson;

public class Viruses {
    public int direction;
    public Position position;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
