package jsclub.codefest.sdk.util;

import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.*;

import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class SocketUtils {
    private static final int TIMEOUT_IN_MINUTES = 1;

    public static Socket init(String url) {
        OkHttpClient okHttpClient = getHttpClientBuilder();
        IO.setDefaultOkHttpCallFactory((Call.Factory) okHttpClient);
        IO.setDefaultOkHttpWebSocketFactory((WebSocket.Factory) okHttpClient);
        try {
            return IO.socket(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static OkHttpClient getHttpClientBuilder() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().connectTimeout(TIMEOUT_IN_MINUTES, TimeUnit.MINUTES).writeTimeout(TIMEOUT_IN_MINUTES, TimeUnit.MINUTES).readTimeout(TIMEOUT_IN_MINUTES, TimeUnit.MINUTES);
        return clientBuilder.build();
    }
}
