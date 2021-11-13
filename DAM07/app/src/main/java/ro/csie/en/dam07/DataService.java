package ro.csie.en.dam07;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {
    @GET("/templates/6aXJX9STJIot/data")
    Call<ObjectCollection> getDataObjects();
}
