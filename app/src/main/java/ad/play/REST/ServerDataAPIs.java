package ad.play.REST;

import java.util.ArrayList;
import java.util.List;

import ad.play.REST.Models.songs;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


public interface ServerDataAPIs {

    @GET("/studio")
    public void getPlantList(
            Callback<ArrayList<songs>> response);
}