package ad.play.REST;

import com.squareup.okhttp.OkHttpClient;


import retrofit.RestAdapter;
import retrofit.client.OkClient;


public class DataClient {

    private static final String BASE_URL = " http://starlord.hackerearth.com";
    private ServerDataAPIs mServerDataCall = null;

    public DataClient() {
        OkHttpClient okHttpClient = new OkHttpClient();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mServerDataCall = restAdapter.create(ServerDataAPIs.class);
    }

    public ServerDataAPIs getDataAPIs() {
        if (mServerDataCall == null) {
            throw new NullPointerException("Data APIs not initialized");
        }
        return mServerDataCall;
    }


}
