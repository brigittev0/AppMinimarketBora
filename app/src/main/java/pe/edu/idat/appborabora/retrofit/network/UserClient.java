package pe.edu.idat.appborabora.retrofit.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserClient {

    //--Ejemplo --> "http://192.132.12.4:8081/user/";
    //--CAMBIAR IP Y PUERTO
    //--SOLO AÑADE TU IP - NO CAMBIAR LO DEMAS
    private static String BASE_URL = "http:// TU IP :8081/user/";

    private ApiService apiService;

    private static UserClient INSTANCE;

    public UserClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static UserClient getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new UserClient();
        }
        return INSTANCE;
    }

    public ApiService getApiService() {
        return apiService;
    }
}
