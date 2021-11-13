package ro.csie.en.dam07;

import retrofit.*;

public class RestApiAdapter {

    private static final String TOKEN = "Bearer 20og2qstzdr8p13u0ddv3pupbejim59bqxq98n9u";
    public DataService getData()
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.json-generator.com")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Authorization", TOKEN);
                    }
                })
                .build();
        return  restAdapter.create(DataService.class);
    }
}
