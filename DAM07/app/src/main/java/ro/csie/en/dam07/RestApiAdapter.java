package ro.csie.en.dam07;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiAdapter {

    private static final String TOKEN = "Bearer 20og2qstzdr8p13u0ddv3pupbejim59bqxq98n9u";
    public DataService getData()
    {
//        RestAdapter restAdapter = new RestAdapter.Builder()
//                .setEndpoint("https://api.json-generator.com")
//                .setRequestInterceptor(new RequestInterceptor() {
//                    @Override
//                    public void intercept(RequestFacade request) {
//                        request.addHeader("Authorization", TOKEN);
//                    }
//                })
//                .build();
//        return  restAdapter.create(DataService.class);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder reqBuilder = request.newBuilder();
                reqBuilder.addHeader("Authorization", TOKEN);
                Response response = chain.proceed(reqBuilder.build());
                return response;
            }
        });
        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.json-generator.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(DataService.class);
    }
}
