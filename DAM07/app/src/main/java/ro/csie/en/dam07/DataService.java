package ro.csie.en.dam07;

import retrofit.Callback;
import retrofit.http.GET;

public interface DataService {
    @GET("/templates/6aXJX9STJIot/data")
    void getDataObjects(Callback<ObjectCollection> callback);
}
